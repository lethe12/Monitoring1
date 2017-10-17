package cn.com.grean;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.InvalidParameterException;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import android.util.Log;




import com.serial.SerialPort;

/*设置
 * */
public abstract class SerialPortCommunication {
	private static final String tag = "SerialPortCommunication";
	private final Lock lock = new ReentrantLock();
	private final Condition cond = lock.newCondition();
	protected boolean flag=false;//同步传输标志位 =true 有同步需求 = false 无同步通讯
	protected ConcurrentLinkedQueue<byte[]> sendBuff = new ConcurrentLinkedQueue<byte[]>();
	
	protected SerialPort serialPort;
	protected OutputStream mOutputStream;
	protected InputStream mInputStream;
	protected byte[] buf = new byte[512];
	protected byte[] mybuf = new byte[512];
	protected int size;
	//protected int mysize;
	protected int times=0;
	private String strRecData = "";
	private ReadThhread readThhread;
	private SendThread sendThread;

	protected abstract Boolean handleBuffer();
	protected abstract void CommunicationProtocal(byte[] rec,int len);
	protected abstract void AsynchronousCommunicationProtocal(byte[] rec,int len);//只接收，不发送
	
	protected void ChangeSerialPort(int devNum,int dataBits, int speed,int stopBits,int parity){
		/*serialPort.mDevNum = devNum;
		serialPort.mDataBits = dataBits;
		serialPort.mSpeed = speed;
		serialPort.mStopBits = stopBits;
		serialPort.mParity = parity;
		serialPort.openDev(serialPort.mDevNum);
		serialPort.setSpeed(serialPort.mFd, serialPort.mSpeed);
		serialPort.setParity(serialPort.mFd, serialPort.mDataBits, serialPort.mStopBits, serialPort.mParity);*/
		try {
			serialPort = getSerialPort(devNum,speed);
		} catch (IOException e) {
			e.printStackTrace();
		}
		mInputStream = serialPort.getInputStream();
		mOutputStream = serialPort.getOutputStream();
	}
	
	/*	例：
	 *  serialPort.mDevNum = 0;
		serialPort.mDataBits = 8;
		serialPort.mSpeed = 9600;
		serialPort.mStopBits = 1;
		serialPort.mParity = 'n';
	 * */
	public SerialPortCommunication(int devNum,int dataBits, int speed,int stopBits,int parity) {
		// TODO 自动生成的构造函数存根
		/*serialPort = new SerailPortOpt();
		serialPort.mDevNum = devNum;
		serialPort.mDataBits = dataBits;
		serialPort.mSpeed = speed;
		serialPort.mStopBits = stopBits;
		serialPort.mParity = parity;
		serialPort.openDev(serialPort.mDevNum);
		serialPort.setSpeed(serialPort.mFd, serialPort.mSpeed);
		serialPort.setParity(serialPort.mFd, serialPort.mDataBits, serialPort.mStopBits, serialPort.mParity);*/
		try {
			serialPort = getSerialPort(devNum,speed);
		} catch (IOException e) {
			e.printStackTrace();
		}


		mInputStream = serialPort.getInputStream();
		mOutputStream = serialPort.getOutputStream();
		
		readThhread = new ReadThhread();
		readThhread.start();
		sendThread = new SendThread();
		sendThread.start();
	}


	public SerialPort getSerialPort(int num,int baudrate) throws SecurityException, IOException, InvalidParameterException {
		if (serialPort == null) {

			String path = "/dev/ttyS1";
			switch (num){
				case 0:
					path = "/dev/ttyS0";
					break;
				case 1:
					path = "/dev/ttyS1";
					break;
				case 2:
					path = "/dev/ttyS2";
					break;
				case 3:
					path = "/dev/ttyS3";
					break;
				default:
					path = "/dev/ttyS3";
					break;
			}
			Log.d(tag,path);
			//int baudrate = 9600;//Integer.decode("9600");

			/* Check parameters */
			if ( (path.length() == 0) || (baudrate == -1)) {
				Log.d(tag,"error");
				throw new InvalidParameterException();
			}
			Log.d(tag,"right");
			/* Open the serial port */
			serialPort = new SerialPort(new File(path), baudrate, 0);
		}
		return serialPort;
	}

	private class SendThread extends Thread{
		
		@Override
		public void run() {
			int times=0;
			// TODO 自动生成的方法存根
			super.run();
			while (!isInterrupted()) {
				lock.lock();
				try {
					if (!sendBuff.isEmpty()) {						
						if (!flag) {						
							flag = true;		
							byte [] buffer = sendBuff.poll();
							mOutputStream.write(buffer);
							mOutputStream.flush();
							String str_s = tools.bytesToHexString(buffer, buffer.length);
							Log.d(tag,"RS232发送->"+str_s);
						}
						else {
							times++;
							if (times > 3) {
								times = 0;
								flag = false;
								//Log.d(tag, "通讯超时，发送下一条数据");
							}
							wait(50);
						}
					}
				} catch (Exception e) {
					// TODO: handle exception
				}
				finally{
					lock.unlock();
				}
				try {
					Thread.sleep(50);
				} catch (InterruptedException e) {
					// TODO 自动生成的 catch 块
					e.printStackTrace();
				}
			}
		}
	}
	
	/*
	 * 接收线程
	 * */
	
	private class ReadThhread extends Thread{
		@Override
		public void run() {
			// TODO 自动生成的方法存根
			super.run();
			//mysize=0;
			while (!isInterrupted()) {				
				if (mInputStream == null)
					return;

				while(!isInterrupted()){

                    try {
                        while (mInputStream.available()==0){
                            Thread.sleep(50);
                        }
                        Thread.sleep(100);
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    //synchronized (readThhread) {
                    //buf = new byte[512];
                    try {
                        size = mInputStream.read(buf);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    if (size>0) {
                        if (handleBuffer()) {
                            lock.lock();
                            try {

                                if (flag) {
                                    //Log.d(tag, "同步通讯");
                                    CommunicationProtocal(buf,size);
                                    flag = false;
                                    cond.signalAll();
                                }
                                else {
                                    //Log.d(tag, "异步通讯");
                                    AsynchronousCommunicationProtocal(buf,size);
                                }
                            } catch (Exception e) {
                                // TODO: handle exception

                            }
                            finally{
                                lock.unlock();
                            }



                            String str_s = tools.bytesToHexString(buf, size);

                            strRecData = strRecData + str_s;
                            Log.d(tag, "串口处理完<-" +strRecData + " size" + Integer.toString(size));
                            strRecData = "";
                        }
                        else {
                            /*String str_s = tools.bytesToHexString(buf, size);
                            strRecData = strRecData + str_s;
                            Log.d(tag,"串口收到<-" +  strRecData + " size" + Integer.toString(size));
                            strRecData = "";*/
                        }
                    }
                    //}
                }
				//Log.d("RS232", "2");
				
				
				/*try {
					Thread.sleep(50);
				} catch (InterruptedException e) {
					// TODO 自动生成的 catch 块
					e.printStackTrace();
				}*/
				
				if (times < 100) {
					times++;
				}
				else {
					times = 100;
				}

			}
			
		}
		
	}

}
