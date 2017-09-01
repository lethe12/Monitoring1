package cn.com.grean;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;

import cn.com.grean.protocol.ProtocolProcessorImp;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

public class TCPTask implements TCPController{
	private final String tag = "TCPTask";
	private static TCPTask instance = new TCPTask(); 
	private boolean isConnect = false;
	private String targetIP;
	private int targetPort;
	private InputStream isRead;
	private OutputStream isSend;
	private Socket mSocketClient = null;
	private TCPListeners listeners;
	private boolean heartRun = false;
	private Thread mThreadClient = null;
	private Thread mThreadHeart = null;
	private StartThread startThread = new StartThread();
	private HeartThread heartThread = new HeartThread();
	
	private TCPTask() {
		// TODO 自动生成的构造函数存根
	}

	@Override
	public boolean isConnected() {
		// TODO 自动生成的方法存根
		return isConnect;
	}

	

	public static TCPTask getInstance() {
		return instance;
	}
	
	/**
	 * 监听TCP是否链接的线程
	 * @author Administrator
	 *
	 */
	class HeartThread implements Runnable{

		@Override
		public void run() {
			// TODO 自动生成的方法存根
			
			heartRun = true;
			//开始判断是否联网
			while(heartRun){
				
				ConnectivityManager cwjManager=(ConnectivityManager)myApplication.getInstance().getApplicationContext()
						.getSystemService(myApplication.getInstance().getApplicationContext().CONNECTIVITY_SERVICE); 
				NetworkInfo info = cwjManager.getActiveNetworkInfo(); 
				if (info != null && info.isAvailable()){ 
					//do nothing 
					Log.d(tag, "联网啦");
					break;
				} 
				else
				{
					Log.d(tag, "没联网");
				}
				try {
					Thread.sleep(5000);
				} catch (InterruptedException e) {
					// TODO 自动生成的 catch 块
					e.printStackTrace();
				}
			}
			//开始处理心跳
			while (heartRun) {
				
				if (isConnect) {//发送心跳包
					//Log.d(tag, "发心跳");
					try {
						if (mSocketClient.isConnected()) {	
							
							//isSend.write(heartCmd);
							//Log.d(tag, "开始发送");
							//isSend.write(updataString);
							String heartString=ProtocolProcessorImp.getInstance().getHeartString();
							isSend.write(heartString.getBytes());
							isSend.flush();
							//Log.d(tag, "发送完成");
							
							/*SpecProtos.SpecPackage.Builder builder = SpecProtos.SpecPackage.newBuilder();
							SpecProtos.Spec spectrum = SpecProtos.Spec.newBuilder().setMeta1("201612290800")
							.setMeta2("123456").addData(0f).addData(1f).addData(2f).build();
							
							//Log.d(tag, "build完");
							builder.addSpec(spectrum);
							//Log.d(tag, "数据Add");
							builder.build().writeTo(isSend);*/
							//builder.
							
							//Log.d(tag, tools.bytesToHexString(builder.build().toByteArray(),builder.build().toByteArray().length));
						}
					} catch (IOException e) {
						// TODO 自动生成的 catch 块
						e.printStackTrace();
						Log.d(tag, "发心跳异常");
					}
				}
				else {
					if (heartRun) {	
						if (mThreadClient!=null) {
							
							if (!mThreadClient.isAlive()) {
								Log.d(tag, "重连");							
								mSocketClient = new Socket();// 定义套接字					
								mThreadClient = new Thread(startThread);// 用于给服务
								mThreadClient.start();
							}
						}
						else {
							Log.d(tag, "新建连接");							
							mSocketClient = new Socket();// 定义套接字					
							mThreadClient = new Thread(startThread);// 用于给服务
							mThreadClient.start();
						}
					}
				}
				try {
					//Thread.sleep(60000);
					Thread.sleep(10000);
				} catch (InterruptedException e) {
					// TODO 自动生成的 catch 块
					e.printStackTrace();
				}
				/*try {
					//Thread.sleep(60000);
					Thread.sleep(10000);
				} catch (InterruptedException e) {
					// TODO 自动生成的 catch 块
					e.printStackTrace();
				}*/
				
				//Log.d(tag, "心跳");
			}
			Log.d(tag, "监控结束");
			
		}
	}
	/**
	 * 启动TCP链接和
	 * @author Administrator
	 *
	 */
	class StartThread implements Runnable{

		@Override
		public void run() {
			// TODO 自动生成的方法存根
			if (startPing(targetIP)) {//连上GREAN
				try {
										
					// 连接服务器
					mSocketClient.connect(new InetSocketAddress(targetIP, targetPort), 5000);
					// 客户端socket在接收数据时，有两种超时：1. 连接服务器超时，即连接超时；2. 连接服务器成功后，接收服务器数据超时，即接收超时  
					// 设置 socket 读取数据流的超时时间  					
					//mSocketClient.setSoTimeout(5000); 					
					// 发送数据包，默认为 false，即客户端发送数据采用 Nagle 算法；  
					// 但是对于实时交互性高的程序，建议其改为 true，即关闭 Nagle 算法，客户端每发送一次数据，无论数据包大小都会将这些数据发送出去  
					mSocketClient.setTcpNoDelay(true);  
					// 设置客户端 socket 关闭时，close() 方法起作用时延迟 30 秒关闭，如果 30 秒内尽量将未发送的数据包发送出去  
					mSocketClient.setSoLinger(true, 30);  
					// 设置输出流的发送缓冲区大小，默认是4KB，即4096字节  
					mSocketClient.setSendBufferSize(4096);  
					// 设置输入流的接收缓冲区大小，默认是4KB，即4096字节  
					mSocketClient.setReceiveBufferSize(4096);  
					// 作用：每隔一段时间检查服务器是否处于活动状态，如果服务器端长时间没响应，自动关闭客户端socket  
					// 防止服务器端无效时，客户端长时间处于连接状态  
					mSocketClient.setKeepAlive(true);  
					// 客户端向服务器端发送数据，获取客户端向服务器端输出流  
					// 取得输入、输出流					
					isRead = mSocketClient.getInputStream();
					isSend = mSocketClient.getOutputStream();					
					mSocketClient.setOOBInline(true);
					// 数据不经过输出缓冲区，立即发送  
					//mSocketClient.sendUrgentData(0x44);//"D" 

					/*String ipmsg=getIPandMAC()+":"+String.valueOf(mSocketClient.getLocalPort());
					
					Log.d("IP Address", ipmsg);
					Intent intent = new Intent();
					intent.setAction("IPMsg");
					intent.putExtra("ip", ipmsg);
					con.sendBroadcast(intent);*/		
					
					
					int count;
					byte [] readbuff=new byte[4096];					
					Log.d(tag, "start");
					try {				
						isConnect = true;
						if (listeners!= null) {
							listeners.onComplete(isConnect);
						}
						while (isConnect) {		
							if (mSocketClient.isConnected()) {
								while (((count=isRead.read(readbuff))!=-1)&&isConnect) {

									//content = tools.bytesToHexString(readbuff, count);
									//byte [] content = new byte[count];
									//System.arraycopy(readbuff, 0, content, 0, count);
									String contentString = new String(readbuff, 0, count);
									//Log.d("TCP", content+" sizeof "+new String(readbuff));
									Log.d(tag, "Content = "+contentString+" SizeOf = "+String.valueOf(count));
									String backString = ProtocolProcessorImp.getInstance().ASCIIProtocol(contentString);
									isSend.write(backString.getBytes());
									//manageRS485TCP(readbuff,count);
									//manageTCP(contentString);
								}
								Log.d(tag, "关闭链接");//结束进程
								isConnect = false;
								break;
							}
							else {
								isConnect = false;
							}
						}
						
						
						
						try {
							mSocketClient.close();
						} catch (IOException e) {
							// TODO 自动生成的 catch 块
							e.printStackTrace();
							isConnect = false;
							if (listeners!=null) {
								listeners.onComplete(isConnect);
							}
						}			
						
					} catch (Exception e) {
						// TODO: handle exception
						isConnect = false;					
						Log.d("联网","通讯失败");
					}
					Log.d(tag, "over");
					if (listeners!=null) {
						listeners.onComplete(isConnect);
					}
				} catch (Exception e) {
					isConnect = false;					
					Log.d(tag,"失败");					
				}					
			} 
			else {
				Log.d(tag,"找不到服务器");

				isConnect = false;
				if (listeners!=null) {
					listeners.onComplete(isConnect);
				}
			}
		}
		
	}
	
	
	private boolean startPing(String ip) {//ping  一个Ip
		//Log.e("Ping", "startPing...");
		boolean success = false;
		Process p = null;

		try {
			p = Runtime.getRuntime().exec("ping -c 1 -i 0.2 -W 1 " + ip);
			int status = p.waitFor();
			if (status == 0) {
				success = true;
			} else {
				success = false;
			}
		} catch (IOException e) {
			success = false;
		} catch (InterruptedException e) {
			success = false;
		} finally {
			p.destroy();
		}

		return success;
	}
	@Override
	synchronized public void setTCPDisconnect() {
		// TODO 自动生成的方法存根
		isConnect = false;
		try {
			if (heartRun) {				
				heartRun = false;
				if(mSocketClient != null){
					mSocketClient.shutdownInput();
					mSocketClient.shutdownOutput();
				}
			}
		} catch (IOException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
	}

	@Override
	public synchronized boolean setTCPConnect(String IP, int port, TCPListeners listeners) {
		// TODO 自动生成的方法存根
		targetIP = IP;
		targetPort = port;
		this.listeners = listeners;
		if (!heartRun) {
			if (mThreadHeart != null) {
				
				if (mThreadHeart.isAlive()) {
					return false;
				}
				else {
					
					mThreadHeart = new Thread(heartThread);
					mThreadHeart.start();
					return true;
				}
			}
			else {
				mThreadHeart = new Thread(heartThread);
				mThreadHeart.start();
				return true;
			}
		}
		else {
			return false;
		}
	}

}
