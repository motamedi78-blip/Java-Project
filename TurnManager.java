public class TurnManager {

    private String aktuellerZug;

    public TurnManager(){ aktuellerZug="weiss"; }

    public void switchTurn(){ aktuellerZug=aktuellerZug.equals("weiss")?"schwarz":"weiss"; }
    public String getCurrentTurn(){ return aktuellerZug; }
    public boolean canMove(String farbe){ return aktuellerZug.equals(farbe); }
}
