import java.util.Arrays;

public class GameBoard {

    public char[][] board;


    public GameBoard() {
        board = new char[6][7];
        initializeBoard();
    }

    Boolean dropPiece(int position, int player) {
        int row = 5;
        char piece;
        if (player == 1) { piece = 'R'; }
        else { piece = 'Y'; }

        while (row >= 0) {
            if (board[row][position] == ' ' ){
                board[row][position] = piece;
                return true;
            }
            else {
                row--;
            }
        }
        // Returns false if the column is full
        return false;
    }




    public void initializeBoard() {
        for (int i = 0; i < board.length; i++) {
            Arrays.fill(board[i], ' ');
        }
    }

    void printBoard() {
        for (int i = 0; i < board.length; i++) {
            System.out.print("[|");
            for (char space : board[i]){
                System.out.print(space + "|");
            }
            System.out.println("]");
        }
            System.out.println("-----------------");
    }


}
