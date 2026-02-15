import javax.swing.*;
import java.awt.event.*;

public class ChessTimer {

    private int sekunden;
    private Timer timer;
    private JLabel label;

    public ChessTimer(int sekunden,JLabel label){
        this.sekunden=sekunden;
        this.label=label;

        timer=new Timer(1000,new ActionListener(){
            public void actionPerformed(ActionEvent e){
                sekunden--;
                label.setText(format());
                if(sekunden<=0){
                    timer.stop();
                    JOptionPane.showMessageDialog(null,"Zeit vorbei!");
                }
            }
        });
    }

    private String format(){ int m=sekunden/60; int s=sekunden%60; return String.format("%02d:%02d",m,s); }
    public void starten(){ timer.start(); }
    public void stoppen(){ timer.stop(); }
}
