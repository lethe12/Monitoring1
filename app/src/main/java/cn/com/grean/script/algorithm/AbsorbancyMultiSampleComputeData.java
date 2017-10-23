package cn.com.grean.script.algorithm;


/**
 * Created by weifeng on 2017/10/23.
 */

public class AbsorbancyMultiSampleComputeData implements MultiSampleComputer{
    private Compute compute;
    private ComputerParams params;
    private ComputerListener listener;
    private float[] results = new float[8];
    private int testSampleNum;
    @Override
    public Compute getComputer() {
        if(compute == null){
            compute = new AbsCompute(params,listener);
        }
        return compute;
    }

    @Override
    public void setBackGroundValue(float data) {
        compute.setFirstValue(data);
    }


    @Override
    public float calcResult(float measureValue,int num) {
        if((num <=8)&&(num>0)) {
            this.testSampleNum = num;
            results[num] = compute.Result(measureValue);
            return results[num];
        }else {
            return 0f;
        }
    }

    @Override
    public float getComputeResult(int num) {
        if((num <=8)&&(num>0)) {
            return results[num];
        }else {
            return 0f;
        }
    }



    public AbsorbancyMultiSampleComputeData(ComputerParams params,ComputerListener listener){
        this.params = params;
        this.listener = listener;
    }

    private class AbsCompute extends AbsorbanceComputeData{
        public AbsCompute (ComputerParams params,ComputerListener listener){
            super(params,listener);
        }

        @Override
        public String getTestInfo() {
            return "样品"+String.valueOf(testSampleNum)+" "+super.getTestInfo();
        }
    }
}
