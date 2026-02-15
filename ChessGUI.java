import javax.swing.*;
import java.awt.*;

public class ChessGUI extends JFrame {

    private JButton[][] squares = new JButton[8][8];
    private Point selected = null;
    private SchachSpiel game = new SchachSpiel();
    private JLabel whiteLabel = new JLabel("05:00", SwingConstants.CENTER);
    private JLabel blackLabel = new JLabel("05:00", SwingConstants.CENTER);
    private ChessTimer whiteTimer;
    private ChessTimer blackTimer;

    public ChessGUI() {
        setTitle("Chess");
        setSize(600,650);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel top = new JPanel(new GridLayout(1,2));
        top.add(whiteLabel);
        top.add(blackLabel);
        add(top,BorderLayout.NORTH);

        JPanel boardPanel = new JPanel(new GridLayout(8,8));
        add(boardPanel,BorderLayout.CENTER);

        for(int r=0;r<8;r++){
            for(int c=0;c<8;c++){
                JButton square = new JButton();
                square.setFocusPainted(false);
                square.setBorderPainted(false);
                square.setBackground((r+c)%2==0?Color.WHITE:Color.LIGHT_GRAY);

                int row=r, col=c;
                square.addActionListener(e->squareClick(row,col));

                squares[r][c]=square;
                boardPanel.add(square);
            }
        }

        updateBoard();

        whiteTimer=new ChessTimer(300,whiteLabel);
        blackTimer=new ChessTimer(300,blackLabel);
        whiteTimer.start();

        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void squareClick(int r,int c){
        String piece=game.getPiece(r,c);

        if(selected==null){
            if(!piece.equals("") && piece.startsWith(game.getCurrentTurn())){
                selected=new Point(r,c);
                showPossibleMoves(r,c);
            }
            return;
        }

        boolean moved=game.move(selected.x,selected.y,r,c);

        resetColors();
        selected=null;

        if(moved){
            updateBoard();
            if(game.getCurrentTurn().equals("white")){
                blackTimer.stop();
                whiteTimer.start();
            } else {
                whiteTimer.stop();
                blackTimer.start();
            }
        }
    }

    private void showPossibleMoves(int r,int c){
        String piece=game.getPiece(r,c);
        if(piece.equals("") || !piece.startsWith(game.getCurrentTurn())) return;

        String[] parts=piece.split("_");
        String color=parts[0], type=parts[1];

        boolean[][] possible=new boolean[8][8];

        for(int i=0;i<8;i++)
            for(int j=0;j<8;j++)
                if(ChessMoveCheck.checkMove(type,color,r,c,i,j,game.getBoard())){
                    String target=game.getPiece(i,j);
                    if(target.equals("") || !target.startsWith(color)) possible[i][j]=true;
                }

        for(int i=0;i<8;i++)
            for(int j=0;j<8;j++)
                if(possible[i][j]) squares[i][j].setBackground(Color.GREEN);

        squares[r][c].setBackground(Color.YELLOW);
    }

    private void updateBoard(){
        for(int r=0;r<8;r++)
            for(int c=0;c<8;c++){
                squares[r][c].setIcon(null);
                String piece=game.getPiece(r,c);
                if(!piece.equals("")) squares[r][c].setIcon(loadIcon("images/"+piece+".png"));
            }
    }

    private void resetColors(){
        for(int r=0;r<8;r++)
            for(int c=0;c<8;c++)
                squares[r][c].setBackground((r+c)%2==0?Color.WHITE:Color.LIGHT_GRAY);
    }

    private ImageIcon loadIcon(String path){
        ImageIcon icon=new ImageIcon(path);
        Image img=icon.getImage().getScaledInstance(64,64,Image.SCALE_SMOOTH);
        return new ImageIcon(img);
    }
}
