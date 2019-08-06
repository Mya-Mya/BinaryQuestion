package basemodel;

import com.sun.org.apache.xalan.internal.xsltc.compiler.Constants;

import java.util.ArrayList;
import java.util.List;

public class LongBinaryQuestion extends BinaryQuestion {
    private List<LBQHistory> history=new ArrayList<>();

    private LBQHistory getNowHistory(){
        if(history.isEmpty())return null;
        return history.get(history.size()-1);
    }

    public LongBinaryQuestion(long min, long max){
        history.add(new LBQHistory(min,max));
        fireListeners();
    }

    @Override
    protected void answer1(boolean b) {
        LBQHistory nowHistory=getNowHistory();
        LBQHistory newHistory;
        if(b){
            newHistory=new LBQHistory(nowHistory.mid,nowHistory.max);
        }else{
            newHistory=new LBQHistory(nowHistory.min,nowHistory.mid-1);
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
    public void undo() {
        history.remove(getNowHistory());
        fireListeners();
    }

    @Override
    public String getThesis1() {
        return"定数は";
    }

    @Override
    public String getThesis2() {
        return Long.toString(getNowHistory().mid);
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


    private class LBQHistory {
        public LBQHistory(long min, long max){
            this.min=min;
            this.max=max;
            this.mid= (int) Math.ceil((double)(min+max)/(double)2);
        }
        public long min;
        public long mid;
        public long max;

        @Override
        public String toString() {
            return"min="+min+" mid="+mid+" max="+max;
        }
    }

    @Override
    public String toString() {
        LBQHistory now=getNowHistory();
        return "("+Long.toString(now.min)+","+Long.toString(now.max)+")";
    }
}
