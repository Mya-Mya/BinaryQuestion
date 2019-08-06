package basemodel;

import com.sun.org.apache.xalan.internal.xsltc.compiler.Constants;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

public class BigIntegerBinaryQuestion extends BinaryQuestion {
    private List<BIBQHistory> history=new ArrayList<>();

    private BIBQHistory getNowHistory(){
        if(history.isEmpty())return null;
        return history.get(history.size()-1);
    }
    public BigIntegerBinaryQuestion(BigInteger min,BigInteger max){
        history.add(new BIBQHistory(min,max));
        fireListeners();
    }

    @Override
    protected void answer1(boolean b) {
        BIBQHistory nowHistory=getNowHistory();
        BIBQHistory newHistory;
        if(b){
            newHistory=new BIBQHistory(nowHistory.mid,nowHistory.max);
        }else{
            newHistory=new BIBQHistory(nowHistory.min,nowHistory.mid.subtract(BigInteger.ONE));
        }
        if(newHistory.min.equals(newHistory.max)){
            status=BQStatus.Detected;
        }
        history.add(newHistory);
        fireListeners();
    }

    @Override
    public boolean canUndo() {
        return history.size()>=2;
    }

    @Override
    public void undo() {
        history.remove(getNowHistory());
        fireListeners();
    }

    @Override
    public String getThesis1() {
        return "定数は";
    }

    @Override
    public String getThesis2() {
        return getNowHistory().mid.toString();
    }

    @Override
    public String getThesis3() {
        switch (status){
            case Questioning:
                return"以上である";
            case Detected:
                return"である";
        }
        return Constants.EMPTYSTRING;
    }

    private static final BigInteger TWO=BigInteger.valueOf(2);
    private class BIBQHistory{
        public BIBQHistory(BigInteger min,BigInteger max){
            this.min=min;
            this.max=max;
            this.mid=min.add(max).add(BigInteger.ONE).divide(TWO);
        }
        public BigInteger min;
        public BigInteger mid;
        public BigInteger max;

        @Override
        public String toString() {
            return"min="+min+" mid="+mid+" max="+max;
        }
    }
    @Override
    public String toString() {
        BIBQHistory now=getNowHistory();
        return "("+now.min.toString()+","+now.max.toString()+")";
    }
}
