import model.BinaryQuestion;
import model.IntegerBinaryQuestion;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        BinaryQuestion q=new IntegerBinaryQuestion(0,1000);
        while(q.getStatus()== BinaryQuestion.BQStatus.Questioning){
            showThesis(q);
            String answer=new Scanner(System.in).nextLine();
            if(answer.equals("y")){
                q.answer(true);
            }
            if(answer.equals("n")){
                q.answer(false);
            }
            if(answer.equals("u")){
                if(q.canUndo())q.undo();
            }
        }
        System.out.println("Detected");
        showThesis(q);
        return;
    }
    public static void showThesis(BinaryQuestion q){
        System.out.println(q.getThesis1()+q.getThesis2()+q.getThesis3());
    }

}
