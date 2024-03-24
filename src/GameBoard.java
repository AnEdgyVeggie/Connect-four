import java.util.Arrays;

public class GameBoard {

    public char[][] board;
    public int width = 7;
    public int height = 6;


    public GameBoard() {
        board = new char[height][width];
        initializeBoard();
    }

    int dropPiece(int position, int player) {
        int row = 5;
        char piece;
        boolean gameWon = false;
        if (player == 1) { piece = 'R'; }
        else { piece = 'Y'; }

        while (row >= 0 || !gameWon) {
            if (board[row][position] == ' ' ){
                board[row][position] = piece;
                    // when win conditions are added, implement an if else
                    // that will display the winner
                    gameWon = checkWinCondition(position, row, piece) ;
                    return row;
            }
            else {
                row--;
            }
        }
        // Returns -1 if the column is full
        return -1;
    }

    boolean checkWinCondition(int position, int row, char player) {
         if (checkDown(0, position, row, player)) {
             System.out.println( player + " WINS");
         };
         if (checkLeft(0, position, row, row, player, true)) {
             System.out.println( player + " WINS");
         }
        if (checkRight(0, position, row, row, player, true)) {
            System.out.println( player + " WINS");
        }

         return false;
    }

    boolean checkDown(int correct, int position, int row, char player) {
        // base cases. if at the lowest row, cant look any further
        if ( row == board.length) return false;

        if (board[row][position] == player)
        {
            // if the piece above the last matches, correct++. if 4, you win
            correct++;
            if (correct == 4) return true;
            // if correct less than 4, look up another row
            row++;
            return checkDown(correct, position, row, player);
        }
        // if not correct, stop recursion, fail loops
        return false;
    }

    boolean checkLeft(int correct, int position, int row, int startingRow, char player, boolean initialLoop) {
        // base cases, we are going to check ALL possibilities to the left
        // including diagonals, so check that you don't move off the board
        if (position == -1) return false;
        if (row == board.length) return false;
        if (row == -1) return false;

        // these are check conditions for all 3 directions
        boolean checkStraight = false, checkDown = false, checkUp = false;


        // initialLoop allows this function to check in 3 directions at once. Without it, it will end up checking
        //for any variation of tiles going left, instead of only straight directions
        if (board[row][position] == player) {
            correct++;
            if (correct == 4) return true;
            position--;
            if (row == startingRow) {
                 checkStraight =  checkLeft(correct, position, row, startingRow, player, false);
            }
            if (row < startingRow || initialLoop) {
                int checkRow = row -= 1;
                checkUp = checkLeft(correct, position, checkRow, startingRow,  player, false);
            }
            if (row > startingRow || initialLoop) {
                int checkRow = row += 1;
                 checkDown = checkLeft(correct, position, checkRow, startingRow, player, false);
            }
        }
        // if any of these conditions are true, will indicate a win, otherwise its a loss
        return (checkStraight || checkUp || checkDown);
    }

    boolean checkRight(int correct, int position, int row, int startingRow, char player, boolean initialLoop) {
        // base cases, we are going to check ALL possibilities to the left
        // including diagonals, so check that you don't move off the board
        if (position == width) return false;
        if (row == board.length) return false;
        if (row == -1) return false;

        // these are check conditions for all 3 directions
        boolean checkStraight = false, checkDown = false, checkUp = false;


        // initialLoop allows this function to check in 3 directions at once. Without it, it will end up checking
        // for any variation of tiles going left, instead of only straight directions
        if (board[row][position] == player) {
            correct++;
            if (correct == 4) return true;
            position++;
            if (row == startingRow) {
                checkStraight =  checkRight(correct, position, row, startingRow, player, false);
            }
            if (row < startingRow || initialLoop) {
                int checkRow = row -= 1;
                checkUp = checkRight(correct, position, checkRow, startingRow,  player, false);
            }
            if (row > startingRow || initialLoop) {
                int checkRow = row += 1;
                checkDown = checkRight(correct, position, checkRow, startingRow, player, false);
            }
        }
        // if any of these conditions are true, will indicate a win, otherwise its a loss
        return (checkStraight || checkUp || checkDown);
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
