public class SchachSpiel {

    private String[][] brett = new String[8][8];
    private TurnManager zugManager = new TurnManager();

    public SchachSpiel() { initBrett(); }

    private void initBrett() {
        for (int r=0;r<8;r++) for (int c=0;c<8;c++) brett[r][c]="";

        for (int c=0;c<8;c++) {
            brett[1][c]="schwarz_Bauer";
            brett[6][c]="weiss_Bauer";
        }

        brett[0][0]=brett[0][7]="schwarz_Turm";
        brett[7][0]=brett[7][7]="weiss_Turm";

        brett[0][1]=brett[0][6]="schwarz_Springer";
        brett[7][1]=brett[7][6]="weiss_Springer";

        brett[0][2]=brett[0][5]="schwarz_Laeufer";
        brett[7][2]=brett[7][5]="weiss_Laeufer";

        brett[0][3]="schwarz_Dame";
        brett[7][3]="weiss_Dame";

        brett[0][4]="schwarz_Koenig";
        brett[7][4]="weiss_Koenig";
    }

    public boolean move(int sx,int sy,int zx,int zy){

        if(brett[sx][sy].equals("")) return false;

        String figur=brett[sx][sy];
        String[] teile=figur.split("_");
        String farbe=teile[0];
        String typ=teile[1];

        if(!zugManager.canMove(farbe)) return false;

        if(!ChessMoveCheck.checkMove(typ,farbe,sx,sy,zx,zy,brett)) return false;

        String ziel=brett[zx][zy];
        if(!ziel.equals("") && ziel.startsWith(farbe)) return false;

        brett[zx][zy]=brett[sx][sy];
        brett[sx][sy]="";

        zugManager.switchTurn();
        return true;
    }

    public String getPiece(int r,int c){ return brett[r][c]; }
    public String getCurrentTurn(){ return zugManager.getCurrentTurn(); }
    public String[][] getBoard(){ return brett; }
}
