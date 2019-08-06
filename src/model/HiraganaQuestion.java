package model;

import basemodel.BigIntegerBinaryQuestion;
import basemodel.BinaryQuestion;
import com.sun.org.apache.xalan.internal.xsltc.compiler.Constants;

import java.math.BigInteger;

public class HiraganaQuestion extends BinaryQuestion {
    private int numChar;
    private BigIntegerBinaryQuestion numericModel;

    public HiraganaQuestion(){
        this(4);
    }
    public HiraganaQuestion(int numChar){
        this.numChar=numChar;
        numericModel=new BigIntegerBinaryQuestion(
                BigInteger.valueOf(0),
                numHiragana.pow(numChar).subtract(BigInteger.ONE));
        numericModel.addBinaryQuestionListener(() -> {fireListeners();});
    }

    @Override
    protected void answer1(boolean b) {
        numericModel.answer(b);

    }

    @Override
    public boolean canUndo() {
        return numericModel.canUndo();
    }

    @Override
    public void undo() {
        numericModel.undo();
    }

    @Override
    public String getThesis1() {
        return "その単語は";
    }

    @Override
    public String getThesis2() {
        return getHiraganasByNumeric(numericModel.getNowHistory().mid,numChar);
    }

    @Override
    public String getThesis3() {
        switch (numericModel.getStatus()){
            case Questioning:
                return "またはそれより辞書の順番で後";
            case Detected:
                return "である";
        }
        return Constants.EMPTYSTRING;
    }

    @Override
    public BQStatus getStatus() {
        return numericModel.getStatus();
    }

    public static String getHiraganasByNumeric(BigInteger i, int numChar){
        //numHiragana進数の数値を読んでいる
        StringBuilder out=new StringBuilder();
        for(int iDigit=numChar-1;iDigit>=0;iDigit--){
            BigInteger unitOfDigit=numHiragana.pow(iDigit);
            BigInteger dar[]=i.divideAndRemainder(unitOfDigit);
            out.append(hiraganaIndex[(int) dar[0].longValue()]);
            i=dar[1];
        }
        return out.toString();
    }

    @Override
    public String toString() {
        BigIntegerBinaryQuestion.BIBQHistory now=numericModel.getNowHistory();
        return "["+getHiraganasByNumeric(now.min,numChar)+","+getHiraganasByNumeric(now.max,numChar)+"]";
    }

    /**
     * 清音->濁点->半濁点
     * 大文字->小文字
     * つ->づ->っ
     */
    public static char[]hiraganaIndex={'あ','ぁ','い','ぃ','う','ぅ','え','ぇ','お','ぉ','か','が','き','ぎ','く','ぐ','け','げ','こ','ご','さ','ざ','し','じ','す','ず','せ','ぜ','そ','ぞ','た','だ','ち','ぢ','つ','づ','っ','て','で','と','ど','な','に','ぬ','ね','の','は','ば','ひ','び','ふ','ぶ','へ','べ','ほ','ぼ','ま','み','む','め','も','や','ゃ','ゆ','ゅ','よ','ょ','ら','り','る','れ','ろ','わ','ゎ','を','ん',};
    public static BigInteger numHiragana=BigInteger.valueOf(hiraganaIndex.length);
}
