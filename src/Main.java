public class Main {
    public static void main(String[] args) {

        GameBoard gb = new GameBoard();
        gb.printBoard();

        gb.dropPiece(1, 1);
        gb.dropPiece(1, 2);
        gb.dropPiece(2, 1);
        gb.dropPiece(3, 2);
        gb.dropPiece(2, 1);
        gb.dropPiece(2, 2);
        gb.printBoard();
    }
}