package model;

import basemodel.BigIntegerBinaryQuestion;
import basemodel.BinaryQuestion;
import com.sun.org.apache.xalan.internal.xsltc.compiler.Constants;

import java.math.BigInteger;

public class ImagingNaturalNumberQuestion extends BinaryQuestion {
    private BigInteger oldRough=BigInteger.ZERO;
    private BigInteger rough=BigInteger.ONE;
    private BigIntegerBinaryQuestion detailedQuestion;
    private static BigInteger TWO=BigInteger.valueOf(2);
    //step=0:2^n以上かを聞く step=1:[2^(n-1), 2^n-1]範囲内で攻める
    private int step=0;

    @Override
    protected void answer1(boolean b) {
        if(step==0){
            if(b){
                oldRough=new BigInteger(rough.toByteArray());
                rough=rough.multiply(TWO);
            }else{
                //[oldRough, rough-1]範囲内に答えがある
                step=1;
                detailedQuestion=new BigIntegerBinaryQuestion(oldRough,rough.subtract(BigInteger.ONE));
                status=detailedQuestion.getStatus();
            }
        }else{
            detailedQuestion.answer(b);
            status=detailedQuestion.getStatus();
        }
        fireListeners();
    }

    @Override
    public boolean canUndo() {
        if(step==0){
            return false;
        }else{
            return detailedQuestion.canUndo();
        }
    }

    @Override
    public void undo() {
        if(step==0){
            return;
        }else{
            detailedQuestion.undo();
            status=BQStatus.Questioning;
            fireListeners();
        }
    }

    @Override
    public String getThesis1() {
        return "定数は";
    }

    @Override
    public String getThesis2() {
        String out;
        if(step==0){
            out=rough.toString();
        }else{
            out=detailedQuestion.getThesis2();
        }
        return String.format("%,d",out);
    }

    @Override
    public String getThesis3() {
        if(step==0){
            return "以上である";
        }else{
            return detailedQuestion.getThesis3();
        }
    }

    @Override
    public String toString() {
        if(step==0){
            return "≮"+oldRough.toString();
        }else{
            return detailedQuestion.toString();
        }
    }
}
