package view;

import basemodel.BinaryQuestion;
import basemodel.BinaryQuestionListener;
import model.ImagingNaturalNumberQuestion;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainFrame extends JFrame implements ActionListener, BinaryQuestionListener {
    private BinaryQuestion questionModel;
    private void createNewQuestion(){
        questionModel=new ImagingNaturalNumberQuestion();
        questionModel.addBinaryQuestionListener(this);
    }

    public static Font smallFont=new Font("メイリオ",Font.BOLD,32);
    public static Font mainFont=new Font("メイリオ",Font.PLAIN,48);
    public static Font bigFont=new Font("メイリオ",Font.BOLD,74);
    public static Color blue=new Color(62, 163, 220);
    public static Color black=new Color(33, 34, 35);
    public static Color red=new Color(191, 54, 72);
    public static Color orange=new Color(241, 131, 18);

    private JButton bYES;
    private JButton bNO;
    private JButton bUNDO;
    private JButton bNEXT;

    private JLabel lThesis1;
    private JLabel lThesis2;
    private JLabel lThesis3;

    public MainFrame(){
        super("BinaryQuestion");

        setPreferredSize(new Dimension(700,400));
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        JPanel pThesis=new JPanel();
        pThesis.setLayout(new BoxLayout(pThesis,BoxLayout.Y_AXIS));
        lThesis1=new JLabel();
        lThesis1.setFont(smallFont);
        lThesis1.setForeground(black);
        lThesis2=new JLabel();
        lThesis2.setFont(bigFont);
        lThesis2.setForeground(orange);
        lThesis3=new JLabel();
        lThesis3.setFont(smallFont);
        lThesis3.setBackground(black);
        pThesis.add(lThesis1);
        pThesis.add(lThesis2);
        pThesis.add(lThesis3);

        JPanel pButtons =new JPanel();
        pButtons.setPreferredSize(new Dimension(700,200));
        pButtons.setLayout(new BoxLayout(pButtons,BoxLayout.X_AXIS));
        bYES=new JButton("YES");
        bYES.setFont(mainFont);
        bYES.setActionCommand("YES");
        bYES.setForeground(blue);
        bYES.addActionListener(this);
        bNO=new JButton("NO");
        bNO.setFont(mainFont);
        bNO.setActionCommand("NO");
        bNO.setForeground(red);
        bNO.addActionListener(this);
        bUNDO=new JButton("UNDO");
        bUNDO.setFont(smallFont);
        bUNDO.setActionCommand("UNDO");
        bUNDO.setForeground(black);
        bUNDO.setPreferredSize(new Dimension(100,100));
        bUNDO.addActionListener(this);
        bNEXT=new JButton("NEXT");
        bNEXT.setFont(smallFont);
        bNEXT.setActionCommand("NEXT");
        bNEXT.setForeground(black);
        bNEXT.setPreferredSize(new Dimension(100,100));
        bNEXT.addActionListener(this);
        pButtons.add(bUNDO);
        pButtons.add(bYES);
        pButtons.add(bNO);
        pButtons.add(bNEXT);

        add(pThesis,BorderLayout.CENTER);
        add(pButtons,BorderLayout.SOUTH);

        createNewQuestion();

        pack();
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object source=e.getSource();
        if (source==bYES) {
            questionModel.answer(true);
        }
        if (source==bNO) {
            questionModel.answer(false);
        }
        if(source==bUNDO){
            if(questionModel.canUndo())questionModel.undo();
        }
        if(source==bNEXT){
            questionModel=new ImagingNaturalNumberQuestion();
            questionModel.addBinaryQuestionListener(this);
        }
    }

    @Override
    public void questionChanged() {
        lThesis1.setText(questionModel.getThesis1());
        lThesis2.setText(questionModel.getThesis2());
        lThesis3.setText(questionModel.getThesis3());
        switch (questionModel.getStatus()){
            case Questioning:
                bYES.setEnabled(true);
                bNO.setEnabled(true);
                bNEXT.setEnabled(false);
                break;
            case Detected:
                bYES.setEnabled(false);
                bNO.setEnabled(false);
                bNEXT.setEnabled(true);
                break;
        }
    }
}
