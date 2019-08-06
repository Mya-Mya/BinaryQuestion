package model;

import basemodel.BigIntegerBinaryQuestion;
import basemodel.BinaryQuestion;

import java.math.BigInteger;
import java.util.Calendar;
import java.util.Date;

public class MomentQuestion extends BigIntegerBinaryQuestion {

    public MomentQuestion() {
        super(BigInteger.ZERO, BigInteger.valueOf(Calendar.getInstance().getTime().getTime()));
    }

    @Override
    public String getThesis1() {
        return "あなたの思う瞬間は";
    }

    @Override
    public String getThesis2() {
        StringBuilder sb=new StringBuilder();
        Calendar target=Calendar.getInstance();
        target.setTime(new Date(getNowHistory().mid.longValue()));
        sb.append(target.get(Calendar.YEAR));
        sb.append(".");
        sb.append(target.get(Calendar.MONTH));
        sb.append(".");
        sb.append(target.get(Calendar.DATE));
        sb.append(" ");
        sb.append(target.get(Calendar.HOUR));
        sb.append(":");
        sb.append(target.get(Calendar.MINUTE));
        sb.append(":");
        sb.append(target.get(Calendar.SECOND));
        sb.append(".");
        sb.append(target.get(Calendar.MILLISECOND));
        return sb.toString();
    }

    @Override
    public String getThesis3() {
        return "かそれよりも後";
    }
}
