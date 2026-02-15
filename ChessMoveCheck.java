public class ChessMoveCheck {

    public static boolean checkMove(String type, String color,
                                    int sx, int sy,
                                    int zx, int zy,
                                    String[][] board) {

        int dx = zx - sx;
        int dy = zy - sy;
        int absDx = Math.abs(dx);
        int absDy = Math.abs(dy);

        switch (type) {

            case "Pawn":

                if (color.equals("white")) {

                    // Move one square forward
                    if (dx == -1 && dy == 0 && board[zx][zy].equals(""))
                        return true;

                    // Move two squares forward on first move
                    if (dx == -2 && dy == 0 && sx == 6 &&
                        board[sx - 1][sy].equals("") &&
                        board[zx][zy].equals(""))
                        return true;

                    // Capture diagonally
                    if (dx == -1 && Math.abs(dy) == 1 &&
                        !board[zx][zy].equals("") &&
                        !board[zx][zy].startsWith(color))
                        return true;

                } else { // black pawn

                    // Move one square forward
                    if (dx == 1 && dy == 0 && board[zx][zy].equals(""))
                        return true;

                    // Move two squares forward on first move
                    if (dx == 2 && dy == 0 && sx == 1 &&
                        board[sx + 1][sy].equals("") &&
                        board[zx][zy].equals(""))
                        return true;

                    // Capture diagonally
                    if (dx == 1 && Math.abs(dy) == 1 &&
                        !board[zx][zy].equals("") &&
                        !board[zx][zy].startsWith(color))
                        return true;
                }

                return false;

            case "Rook":
                if (dx != 0 && dy != 0) return false;
                return isPathClear(sx, sy, zx, zy, board);

            case "Bishop":
                if (absDx != absDy) return false;
                return isPathClear(sx, sy, zx, zy, board);

            case "Queen":
                if (dx == 0 || dy == 0 || absDx == absDy)
                    return isPathClear(sx, sy, zx, zy, board);
                return false;

            case "Knight":
                return (absDx == 2 && absDy == 1) || (absDx == 1 && absDy == 2);

            case "King":
                return absDx <= 1 && absDy <= 1;

            default:
                return false;
        }
    }

    private static boolean isPathClear(int sx, int sy,
                                       int zx, int zy,
                                       String[][] board) {

        int dx = Integer.signum(zx - sx);
        int dy = Integer.signum(zy - sy);

        int x = sx + dx;
        int y = sy + dy;

        while (x != zx || y != zy) {
            if (!board[x][y].equals("")) return false;
            x += dx;
            y += dy;
        }

        return true;
    }
}
