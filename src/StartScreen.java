package src;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

public class StartScreen extends JPanel {

    private JFrame menuFrame;
    private JButton start = new JButton("Start");
    private JButton end = new JButton("End");

    public void init(){
        this.menuFrame = new JFrame("Tank Game");
        this.menuFrame.setSize(TRE.SCREEN_WIDTH, TRE.SCREEN_HEIGHT + 30);

        this.menuFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.menuFrame.setLayout(new BorderLayout());
        this.menuFrame.add(this);
        this.menuFrame.setResizable(false);
        this.menuFrame.setLocationRelativeTo(null);
        this.menuFrame.setVisible(true);

        JLabel myName = new JLabel();
        myName.setText("Dylan Luong, 413-03");

        JPanel titlePanel= new JPanel();
        BufferedImage titleImg = TRE.imgList.get("Title");
        JLabel titleLabel = new JLabel(new ImageIcon(titleImg));
        titlePanel.add(titleLabel);
        menuFrame.add(titlePanel,BorderLayout.BEFORE_FIRST_LINE);
        titlePanel.setBackground(Color.BLACK);
        //menuFrame.add(myName, BorderLayout.PAGE_END);

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(start);
        buttonPanel.add(end);
        buttonPanel.add(myName, BorderLayout.PAGE_END);
        menuFrame.add(buttonPanel,BorderLayout.SOUTH);

        start.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                TRE.getJf().setVisible(true);
                menuFrame.setVisible(false);
                menuFrame.dispose();
            }
        });
        end.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

    }


}
