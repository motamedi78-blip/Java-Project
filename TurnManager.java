public class TurnManager {

    private String currentTurn;

    public TurnManager() { currentTurn = "white"; }

    public void switchTurn() { currentTurn = currentTurn.equals("white") ? "black" : "white"; }

    public String getCurrentTurn() { return currentTurn; }

    public boolean canMove(String color) { return currentTurn.equals(color); }
}
