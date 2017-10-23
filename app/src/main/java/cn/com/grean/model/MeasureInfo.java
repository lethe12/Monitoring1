package cn.com.grean.model;

import java.util.ArrayList;

import cn.com.grean.EquipmentInfo;
import cn.com.grean.myApplication;
import cn.com.grean.tools;

/**
 * 定义一次测量所需要显示信息
 * 
 * 
 * @author Administrator
 *
 */
public class MeasureInfo {
	private String resultString;//测量结果的数据
	private float result;//测量结果
	private String dateString;
	private String autoTestDateString;
	private String autoCalDateString;
    private ArrayList<ResultUnit> resultUnits = new ArrayList<ResultUnit>();
    private EquipmentInfo info;
	

	public String getAutoCalDateString() {
		return autoCalDateString;
	}


	public String getDateString() {
		return dateString;
	}


	public String getResultString() {
		return resultString;
	}

	public String getDevicesName() {
		return myApplication.getInstance().getParameterName();
	}


	public MeasureInfo(float result,long date,boolean autoTestEnable,long autoTest,boolean autoCalEnable,long autoCal,EquipmentInfo info) {
		// TODO 自动生成的构造函数存根
		this.result = result;
        this.info = info;
		if (date == 0) {
			resultString = "Nan";
			dateString = "测量时间:";
		}else {
			//DecimalFormat fnum = new DecimalFormat("##0.000"); //保留小数点后3位
			//resultString=fnum.format(result);
			resultString = info.getResult(result);
			dateString = "测量时间:"+tools.timestamp2string(date);
		}
		if(autoCalEnable){			
			autoCalDateString = "下次校准时间:"+ tools.timestamp2string(autoCal);
		}else {
			autoCalDateString = "下次校准时间:";
		}
		
		if (autoTestEnable) {
			autoTestDateString = "下次测量时间:"+ tools.timestamp2string(autoTest);
		}else {
			autoTestDateString = "下次测量时间:";
		}


	}

    public EquipmentInfo getInfo() {
        return info;
    }

    public void setResults(int sampleNumber, float[] results, EquipmentInfo info){
        for(int i=0;i<sampleNumber;i++){
            ResultUnit unit;
            if(results[i]==0f){
                unit = new ResultUnit(info.getTag(), "Nan", info.getUnit());
            }else {
                unit = new ResultUnit(info.getTag(), info.getResult(results[i]), info.getUnit());
            }
            resultUnits.add(unit);
        }
    }

    public ArrayList<ResultUnit> getResultUnits() {
        return resultUnits;
    }

    public float getResult() {
		return result;
	}


	public String getAutoTestDateString() {
		return autoTestDateString;
	}


}
