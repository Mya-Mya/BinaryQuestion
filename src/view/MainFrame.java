package view;

import basemodel.BinaryQuestion;
import basemodel.BinaryQuestionListener;
import model.ImagingNaturalNumberQuestion;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class MainFrame extends JFrame implements ActionListener, BinaryQuestionListener, KeyListener {
    private BinaryQuestion questionModel;
    private void createNewQuestion(){
        questionModel=new ImagingNaturalNumberQuestion();
        questionModel.addBinaryQuestionListener(this);
    }

    public static Font smallFont=new Font("メイリオ",Font.BOLD,28);
    public static Font bigFont=new Font("メイリオ",Font.BOLD,74);
    public static Color blue=new Color(62, 163, 220);
    public static Color black=new Color(33, 34, 35);
    public static Color red=new Color(191, 54, 72);
    public static Color orange=new Color(241, 131, 18);

    private JButton bYES;
    private JButton bNO;
    private JButton bUNDO;
    private JButton bNEXT;

    private JLabel lState;
    private JLabel lThesis1;
    private JLabel lThesis2;
    private JLabel lThesis3;

    public MainFrame(){
        super("BinaryQuestion");

        setPreferredSize(new Dimension(800,400));
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setFocusable(true);
        addKeyListener(this);

        lState=new JLabel();
        lState.setForeground(black);
        lState.setFont(smallFont);
        lState.setHorizontalAlignment(SwingConstants.CENTER);

        JPanel pThesis=new JPanel();
        pThesis.setLayout(new FlowLayout(FlowLayout.CENTER));
        lThesis1=new JLabel();
        lThesis1.setFont(smallFont);
        lThesis1.setHorizontalAlignment(SwingConstants.RIGHT);
        lThesis2=new JLabel();
        lThesis2.setFont(bigFont);
        lThesis2.setForeground(orange);
        lThesis2.setHorizontalAlignment(SwingConstants.CENTER);
        lThesis3=new JLabel();
        lThesis3.setFont(smallFont);
        lThesis3.setHorizontalAlignment(SwingConstants.LEFT);
        pThesis.add(lThesis1);
        pThesis.add(lThesis2);
        pThesis.add(lThesis3);

        JPanel pButtons =new JPanel();
        pButtons.setPreferredSize(new Dimension(700,100));
        pButtons.setLayout(new GridLayout(1,4,5,0));
        bYES=new JButton("YES (y)");
        bYES.setFont(smallFont);
        bYES.setActionCommand("YES");
        bYES.setForeground(blue);
        bYES.addActionListener(this);
        bNO=new JButton("NO (n)");
        bNO.setFont(smallFont);
        bNO.setActionCommand("NO");
        bNO.setForeground(red);
        bNO.addActionListener(this);
        bUNDO=new JButton("UNDO (u)");
        bUNDO.setFont(smallFont);
        bUNDO.setActionCommand("UNDO");
        bUNDO.setForeground(black);
        bUNDO.setPreferredSize(new Dimension(50,100));
        bUNDO.addActionListener(this);
        bNEXT=new JButton("NEXT");
        bNEXT.setFont(smallFont);
        bNEXT.setActionCommand("NEXT");
        bNEXT.setForeground(black);
        bNEXT.setPreferredSize(new Dimension(50,100));
        bNEXT.addActionListener(this);
        pButtons.add(bUNDO);
        pButtons.add(bYES);
        pButtons.add(bNO);
        pButtons.add(bNEXT);

        add(lState,BorderLayout.NORTH);
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
        if(questionModel.canUndo())bUNDO.setEnabled(true);
            else bUNDO.setEnabled(false);

        switch (questionModel.getStatus()){
            case Questioning:
                bYES.setEnabled(true);
                bNO.setEnabled(true);
                lThesis1.setForeground(black);
                lThesis3.setForeground(black);
                lState.setForeground(black);
                lState.setText("- 質問中 -");
                break;
            case Detected:
                bYES.setEnabled(false);
                bNO.setEnabled(false);
                lThesis1.setForeground(orange);
                lThesis3.setForeground(orange);
                lState.setForeground(orange);
                lState.setText("- 分かった！ -");
                break;
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        char keyChar=e.getKeyChar();
        if (keyChar=='y') questionModel.answer(true);
        if(keyChar=='n')questionModel.answer(false);
        if(keyChar=='u')if(questionModel.canUndo()) questionModel.undo();
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
