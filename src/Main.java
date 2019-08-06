import model.BinaryQuestion;
import model.LongBinaryQuestion;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        debugAutomatically();
        return;
    }
    public static void debugHumanitically(){
        while(true) {
            BinaryQuestion q = new LongBinaryQuestion(0, 9999999999);
            while (q.getStatus() == BinaryQuestion.BQStatus.Questioning) {
                showThesis(q);
                String reply = new Scanner(System.in).nextLine();
                if (reply.equals("y")) q.answer(true);
                if (reply.equals("n")) q.answer(false);
                if (reply.equals("b")) q.undo();
            }
            System.out.println("detected");
            showThesis(q);
        }
    }
    public static void debugAutomatically(){
        int min=0;
        int max=8;
        for(int i=min;i<=max;i++){
            System.out.print(i+" ");
            BinaryQuestion q=new LongBinaryQuestion(min,max);
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
        System.out.println(q.getThesis1()+q.getThesis2()+q.getThesis3());
    }

}
