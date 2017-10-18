package cn.com.grean;

/**
 * Created by weifeng on 2017/10/18.
 */

public interface LocalConfig {
    public long getConfigLong(String key);
    public String getConfigString(String key);
    public float getConfigFloat(String key);
    public boolean getConfigBoolean(String key);
    public int getConfigInt(String key);
    public void putConfig(String key,String value);
    public void putConfig(String key,long value);
    public void putConfig(String key,float value);
    public void putConfig(String key,int value);
    public void putConfig(String key,boolean value);

}
