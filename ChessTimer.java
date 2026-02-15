import javax.swing.*;
import java.awt.event.*;

public class ChessTimer {

    private int seconds;
    private Timer timer;
    private JLabel label;

    public ChessTimer(int seconds, JLabel label){
        this.seconds = seconds;
        this.label = label;

        timer = new Timer(1000, e -> {
            seconds--;
            label.setText(format());
            if(seconds <= 0){
                seconds = 0;
                timer.stop();
                JOptionPane.showMessageDialog(null,"Time's up!");
            }
        });
    }

    private String format(){ int m=seconds/60; int s=seconds%60; return String.format("%02d:%02d",m,s); }
    public void start(){ timer.start(); }
    public void stop(){ timer.stop(); }
}
