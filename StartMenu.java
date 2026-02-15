import javax.swing.*;
import java.awt.*;

public class StartMenu extends JFrame {

    public StartMenu(){
        setTitle("Chess - Start Menu");
        setSize(300,200);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new GridLayout(2,1));

        JButton startButton=new JButton("Start Game");
        JButton exitButton=new JButton("Exit");

        startButton.addActionListener(e -> {
            new ChessGUI();
            dispose();
        });

        exitButton.addActionListener(e -> System.exit(0));

        add(startButton);
        add(exitButton);

        setLocationRelativeTo(null);
        setVisible(true);
    }

    public static void main(String[] args){
        new StartMenu();
    }
}
