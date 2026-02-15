import javax.swing.*;
import java.awt.*;

public class ChessGUI extends JFrame {

    private JButton[][] felder = new JButton[8][8];
    private Point ausgewaehlt = null;

    private SchachSpiel spiel = new SchachSpiel();

    private JLabel weissLabel = new JLabel("05:00", SwingConstants.CENTER);
    private JLabel schwarzLabel = new JLabel("05:00", SwingConstants.CENTER);

    private ChessTimer weissTimer;
    private ChessTimer schwarzTimer;

    public ChessGUI() {

        setTitle("Schach");
        setSize(600, 650);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel oben = new JPanel(new GridLayout(1, 2));
        oben.add(weissLabel);
        oben.add(schwarzLabel);
        add(oben, BorderLayout.NORTH);

        JPanel brettPanel = new JPanel(new GridLayout(8, 8));
        add(brettPanel, BorderLayout.CENTER);

        for (int r = 0; r < 8; r++) {
            for (int c = 0; c < 8; c++) {

                JButton feld = new JButton();
                feld.setFocusPainted(false);
                feld.setBorderPainted(false);
                feld.setBackground((r + c) % 2 == 0 ? Color.WHITE : Color.LIGHT_GRAY);

                int zeile = r;
                int spalte = c;

                feld.addActionListener(e -> feldKlick(zeile, spalte));

                felder[r][c] = feld;
                brettPanel.add(feld);
            }
        }

        aktualisiereBrett();

        weissTimer = new ChessTimer(300, weissLabel);
        schwarzTimer = new ChessTimer(300, schwarzLabel);

        weissTimer.starten();

        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void feldKlick(int r, int c) {

        String figur = spiel.getPiece(r, c);

        if (ausgewaehlt == null) {
            if (!figur.equals("") && figur.startsWith(spiel.getCurrentTurn())) {
                ausgewaehlt = new Point(r, c);
                zeigeMoeglicheZuege(r, c);
            }
            return;
        }

        boolean zugErfolgreich = spiel.move(ausgewaehlt.x, ausgewaehlt.y, r, c);

        farbenZuruecksetzen();
        ausgewaehlt = null;

        if (zugErfolgreich) {
            aktualisiereBrett();

            if (spiel.getCurrentTurn().equals("weiss")) {
                schwarzTimer.stoppen();
                weissTimer.starten();
            } else {
                weissTimer.stoppen();
                schwarzTimer.starten();
            }
        }
    }

    private void zeigeMoeglicheZuege(int r, int c) {

        String figur = spiel.getPiece(r, c);

        if (figur.equals("") || !figur.startsWith(spiel.getCurrentTurn()))
            return;

        String[] teile = figur.split("_");
        String farbe = teile[0];
        String typ = teile[1];

        boolean[][] moeglicheZuege = new boolean[8][8];

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {

                if (ChessMoveCheck.checkMove(typ, farbe, r, c, i, j, spiel.getBoard())) {
                    String ziel = spiel.getPiece(i, j);
                    if (ziel.equals("") || !ziel.startsWith(farbe)) {
                        moeglicheZuege[i][j] = true;
                    }
                }
            }
        }

        for (int i = 0; i < 8; i++)
            for (int j = 0; j < 8; j++)
                if (moeglicheZuege[i][j])
                    felder[i][j].setBackground(Color.GREEN);

        felder[r][c].setBackground(Color.YELLOW);
    }

    private void aktualisiereBrett() {

        for (int r = 0; r < 8; r++) {
            for (int c = 0; c < 8; c++) {
                felder[r][c].setIcon(null);

                String figur = spiel.getPiece(r, c);

                if (!figur.equals("")) {
                    felder[r][c].setIcon(ladeIcon("images/" + figur + ".png"));
                }
            }
        }
    }

    private void farbenZuruecksetzen() {

        for (int r = 0; r < 8; r++)
            for (int c = 0; c < 8; c++)
              felder[r][c].setBackground((r + c) % 2 == 0 ? Color.WHITE : Color.LIGHT_GRAY);
    }

    private ImageIcon ladeIcon(String pfad) {

        ImageIcon icon = new ImageIcon(pfad);
        Image bild = icon.getImage().getScaledInstance(64, 64, Image.SCALE_SMOOTH);
        return new ImageIcon(bild);
    }
}

