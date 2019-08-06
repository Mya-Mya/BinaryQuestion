import basemodel.BigIntegerBinaryQuestion;
import basemodel.BinaryQuestion;
import model.ImagingNaturalNumberQuestion;
import view.MainFrame;

import java.math.BigInteger;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        new MainFrame();
        return;
    }
    public static void debugHumanitically(){
        while(true) {
            System.out.println();
            BinaryQuestion q = new ImagingNaturalNumberQuestion();
            //BigIntegerBinaryQuestion(BigInteger.valueOf(0), BigInteger.valueOf(2));
            while (q.getStatus() == BinaryQuestion.BQStatus.Questioning) {
                showThesis(q);
                String reply = new Scanner(System.in).nextLine();
                if (reply.equals("y")) q.answer(true);
                if (reply.equals("n")) q.answer(false);
                if (reply.equals("b")) q.undo();
            }
            System.out.print("detected ");
            showThesis(q);
        }
    }
    public static void debugAutomatically(){
        int min=0;
        int max=1000;
        for(int i=min;i<=max;i++){
            System.out.print(i+" ");
            BinaryQuestion q=new BigIntegerBinaryQuestion(BigInteger.valueOf(min),BigInteger.valueOf(max));
            int numQuestion=0;
            while(q.getStatus()== BinaryQuestion.BQStatus.Questioning){
                int thesisMid=Integer.parseInt(q.getThesis2());
                if(thesisMid<=i){
                    q.answer(true);
                }else{
                    q.answer(false);
                }
                numQuestion++;
            }
            System.out.println("question times="+numQuestion);
        }
        /*
        range=(0,1000) question times=9
        0, 500, 531, 562, 750
        */
    }
    public static void showThesis(BinaryQuestion q){
        System.out.println(q.getThesis1()+q.getThesis2()+q.getThesis3()+" "+q.toString());
    }

}
