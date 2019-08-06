package model;

import com.sun.org.apache.xalan.internal.xsltc.compiler.Constants;

import java.util.ArrayList;
import java.util.List;

public class IntegerBinaryQuestion extends BinaryQuestion {
    private List<IBQHistory> history=new ArrayList<>();

    private IBQHistory getNowHistory(){
        if(history.isEmpty())return null;
        return history.get(history.size()-1);
    }

    public IntegerBinaryQuestion(int min,int max){
        history.add(new IBQHistory(min,max));
        fireListeners();
    }

    @Override
    protected void answer1(boolean b) {
        IBQHistory nowHistory=getNowHistory();
        IBQHistory newHistory;
        if(b){
            newHistory=new IBQHistory(nowHistory.mid,nowHistory.max);
        }else{
            newHistory=new IBQHistory(nowHistory.min,nowHistory.mid-1);
        }
        if(newHistory.max==newHistory.min){
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
    protected void undo1() {
        history.remove(history.size()-1);
        fireListeners();
    }

    @Override
    public String getThesis1() {
        return"定数は";
    }

    @Override
    public String getThesis2() {
        return Integer.toString(getNowHistory().mid);
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


    private class IBQHistory{
        public IBQHistory(int min,int max){
            this.min=min;
            this.max=max;
            this.mid=(min+max)/2;
        }
        public int min;
        public int mid;
        public int max;

        @Override
        public String toString() {
            return"min="+min+" mid="+mid+" max="+max;
        }
    }

    @Override
    public String toString() {
        IBQHistory now=getNowHistory();
        return "("+Integer.toString(now.min)+","+Integer.toString(now.max)+")";
    }
}
