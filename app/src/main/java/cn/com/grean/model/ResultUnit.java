package cn.com.grean.model;

import cn.com.grean.EquipmentInfo;

/**
 * Created by weifeng on 2017/10/20.
 */

public class ResultUnit {
    private String tag,result,unit;

    public ResultUnit(String tag,String result,String unit){
        this.tag = tag;
        this.result = result;
        this.unit = unit;
    }

    public String getTag() {
        return tag;
    }

    public String getResult() {
        return result;
    }

    public String getUnit() {
        return unit;
    }
}
