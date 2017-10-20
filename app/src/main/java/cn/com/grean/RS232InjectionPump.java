package cn.com.grean;

import android.util.Log;

import cn.com.grean.script.instruction.CommandSerialPort;

/**
 * Created by weifeng on 2017/10/18.
 */

public class RS232InjectionPump extends SerialPortCommunication implements CommandSerialPort {
    private static RS232InjectionPump instance = new RS232InjectionPump();
    private static final String tag = "RS232InjectionPump";

    public static RS232InjectionPump getInstance() {
        return instance;
    }

    private RS232InjectionPump(){
        super(2, 8, 9600, 1, 'n');//默认串口2 9600 8  无  1
    }

    @Override
    protected Boolean handleBuffer() {
        return true;
    }

    @Override
    protected void CommunicationProtocal(byte[] rec, int len) {
        Log.d(tag,tools.bytesToHexString(rec,len));
    }

    @Override
    protected void AsynchronousCommunicationProtocal(byte[] rec, int len) {
        Log.d(tag,tools.bytesToHexString(rec,len));
    }

    @Override
    public void Send(byte[] data) {
        sendBuff.add(data);
    }

    @Override
    public Object SyncSend(byte[] data) {
        sendBuff.add(data);
        return null;
    }
}
