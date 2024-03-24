public class Main {
    public static void main(String[] args) {

        GameBoard gb = new GameBoard();
        // Y WINS LEFT DIAGONAL
        {//        gb.dropPiece(0, 1);
//        gb.dropPiece(1, 1);
//        gb.dropPiece(0, 1);
//
//        gb.dropPiece(1, 1);
//        gb.dropPiece(0, 2);
//        gb.dropPiece(2, 1);
//
//        gb.dropPiece(1, 2);
//        gb.dropPiece(0, 2);
//        gb.dropPiece(2, 2);
//        gb.dropPiece(3, 2);
        }

        // R WINS STRAIGHT LEFT
        {
            gb.dropPiece(2, 1);
            gb.dropPiece(3, 1);
            gb.dropPiece(4, 1);


        }

        // Y WINS STRAIGHT RIGHT
        {
//            gb.dropPiece(0, 2);
//            gb.dropPiece(1, 2);
//            gb.dropPiece(2, 2);
//            gb.dropPiece(3, 2);
        }

        // Y WINS RIGHT DIAGONAL
        {
//            gb.dropPiece(3, 1);
//            gb.dropPiece(2, 1);
//            gb.dropPiece(1, 1);
//            gb.dropPiece(3, 1);
//            gb.dropPiece(2, 1);
//            gb.dropPiece(3, 1);
//
//            gb.dropPiece(3, 2);
//            gb.dropPiece(2, 2);
//            gb.dropPiece(1, 2);
//            gb.dropPiece(0, 2);
        }

        // R WINS STRAIGHT UP
        {//            gb.dropPiece(0, 1);
//            gb.dropPiece(0, 1);
//            gb.dropPiece(0, 1);
//            gb.dropPiece(0, 1);
        }

        gb.printBoard();
    }
}