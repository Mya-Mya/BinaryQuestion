package basemodel;

import java.util.ArrayList;
import java.util.List;

abstract public class BinaryQuestion {
    public enum BQStatus{
        Questioning,
        Detected
    }
    private List<BinaryQuestionListener>listenerList=new ArrayList<>();
    protected void fireListeners(){
        for(BinaryQuestionListener l:listenerList)l.questionChanged();
    }
    public void addBinaryQuestionListener(BinaryQuestionListener l){
        listenerList.add(l);
        fireListeners();
    }

    protected BQStatus status=BQStatus.Questioning;
    public BQStatus getStatus(){
        return status;
    }

    public void answer(boolean b){
        if(status==BQStatus.Questioning)answer1(b);
    }

    protected abstract void answer1(boolean b);
    public abstract boolean canUndo();
    public abstract void undo();
    public abstract String getThesis1();
    public abstract String getThesis2();
    public abstract String getThesis3();

    @Override
    public String toString() {
        return getThesis1()+getThesis2()+getThesis3();
    }
}
