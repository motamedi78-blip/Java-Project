import javax.swing.*;

public class SchachSpiel {

    private String[][] board = new String[8][8];
    private TurnManager turnManager = new TurnManager();
    private boolean gameOver = false;

    public SchachSpiel() { initBoard(); }

    public void resetGame() {
        initBoard();
        gameOver = false;
        turnManager = new TurnManager();
    }

    private void initBoard() {
        for (int r=0;r<8;r++)
            for (int c=0;c<8;c++)
                board[r][c]="";

        for (int c=0;c<8;c++){
            board[1][c]="black_Pawn";
            board[6][c]="white_Pawn";
        }

        board[0][0]=board[0][7]="black_Rook";
        board[7][0]=board[7][7]="white_Rook";

        board[0][1]=board[0][6]="black_Knight";
        board[7][1]=board[7][6]="white_Knight";

        board[0][2]=board[0][5]="black_Bishop";
        board[7][2]=board[7][5]="white_Bishop";

        board[0][3]="black_Queen";
        board[7][3]="white_Queen";

        board[0][4]="black_King";
        board[7][4]="white_King";
    }

    public boolean move(int sx,int sy,int zx,int zy) {
        if(gameOver) return false;

        if(board[sx][sy].equals("")) return false;

        String piece = board[sx][sy];
        String[] parts = piece.split("_");
        String color = parts[0];
        String type = parts[1];

        if(!turnManager.canMove(color)) return false;
        if(!ChessMoveCheck.checkMove(type,color,sx,sy,zx,zy,board)) return false;

        String target = board[zx][zy];
        if(!target.equals("") && target.startsWith(color)) return false;

        // Move piece
        board[zx][zy] = board[sx][sy];
        board[sx][sy] = "";

        // Check if King is captured
        if(!target.equals("") && target.endsWith("King")){
            JOptionPane.showMessageDialog(null, target + " captured! Game Over!");
            gameOver = true;
        }

        turnManager.switchTurn();
        return true;
    }

    public boolean isGameOver() { return gameOver; }
    public String getPiece(int r,int c){ return board[r][c]; }
    public String getCurrentTurn(){ return turnManager.getCurrentTurn(); }
    public String[][] getBoard(){ return board; }
}
