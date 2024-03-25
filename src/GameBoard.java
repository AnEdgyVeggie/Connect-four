import java.util.Arrays;

public class GameBoard {

    public char[][] board;
    public int width = 7;
    public int height = 6;
    private int winner;


    public GameBoard() {
        board = new char[height][width];
        winner = -1;
        initializeBoard();
    }

    int dropPiece(int position, int player) {
        int row = 5;
        char piece;
        boolean gameWon = false;
        if (player == 1) { piece = 'R'; }
        else { piece = 'Y'; }

        while (row >= 0) {
            if (board[row][position] == ' ' ){
                board[row][position] = piece;
                    // when win conditions are added, implement an if else
                    // that will display the winner
                    checkWinCondition(position, row, piece, player);
                    return row;
            }
            else {
                row--;
            }
        }
        // Returns -1 if the column is full
        return -1;
    }

    void checkWinCondition(int position, int row, char piece, int player) {
         if (checkDown(0, position, row, piece)){
             winner = player;
             return;
         }

         // TTL (Time-To-Live is the score needed to secure a win. Called TTL because below, it becomes the amount
        // of time spent in other branches
        // checks for last play and to the left
        if (checkLeft(4, 0, position, row, row, piece, true)) {
            winner = player;
            return;
        }
        // checks for last play and to the right
        if (checkRight(4, 0, position, row, row, piece, true)){
            winner = player;
            return;
        }

        // Below will check for the last play, to the right AND left
        // TTL total is 5, because they both will award correct++, which means instead of starting at
        // 1/4 when starting at the end, you are starting at 2, so it needs to be 2/5 so that it requires 3 other pieces still

        // Straight line
        if (checkLeft(3, 0, position, row, row, piece, false)
         && checkRight(2, 0, position, row, row, piece, false)){
            winner = player;
            return;
        }
        if (checkLeft(2, 0, position, row, row, piece, false)
                && checkRight(3, 0, position, row, row, piece, false)){
            winner = player;
            return;
        }

        // left to right slant
        if (checkLeft(3, 0, position,row,row + 1,  piece, false)
                && checkRight(2, 0, position, row, row - 1, piece, false)){
            winner = player;
            return;
        }
        if (checkLeft(2, 0, position, row, row + 1, piece, false)
                && checkRight(3, 0, position, row, row - 1, piece, false)){
            winner = player;
            return;
        }

        // right to left slant
        if (checkLeft(3, 0, position, row, row - 1, piece, false)
                && checkRight(2, 0, position, row, row + 1, piece, false)){
            winner = player;
            return;
        }

        if (checkLeft(2, 0, position, row, row - 1, piece, false)
                && checkRight(3, 0, position, row, row + 1, piece, false)){
            winner = player;
        }

    }

    boolean checkDown(int correct, int position, int row, char player) {
        // base cases. if at the lowest row, cant look any further
        if ( row >= board.length) return false;

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

    boolean checkLeft(int TTL, int correct, int position, int row, int startingRow, char player, boolean initialLoop) {
        // base cases, we are going to check ALL possibilities to the left
        // including diagonals, so check that you don't move off the board
        if (position == -1) return false;
        if (row >= board.length) return false;
        if (row == -1) return false;

        // these are check conditions for all 3 directions, used at end of function
        boolean checkStraight = false, checkDown = false, checkUp = false;

        // initialLoop allows this function to check in 3 directions at once. Without it, it will end up checking
        //for any variation of tiles going left, instead of only straight directions
        if (board[row][position] == player) {
            correct++;
            if (correct == TTL) return true;
            position--;
            if (row == startingRow) {
                 checkStraight =  checkLeft(TTL, correct, position, row, startingRow, player, false);
            }
            if (row < startingRow || initialLoop) {
                int checkRow = row - 1;
                checkUp = checkLeft(TTL, correct, position, checkRow, startingRow, player, false);
            }
            if (row > startingRow || initialLoop) {
                int checkRow = row + 1;

                 checkDown = checkLeft(TTL, correct, position, checkRow, startingRow, player, false);
            }
        }
        // if any of these conditions are true, will indicate a win, otherwise it's a loss
        return (checkStraight || checkUp || checkDown);
    }

    boolean checkRight(int TTL, int correct, int position, int row, int startingRow, char player, boolean initialLoop) {
        // base cases, we are going to check ALL possibilities to the left
        // including diagonals, so check that you don't move off the board
        if (position == width) return false;
        if (row >= board.length) return false;
        if (row == -1) return false;
        // these are check conditions for all 3 directions
        boolean checkStraight = false, checkDown = false, checkUp = false;
        // initialLoop allows this function to check in 3 directions at once. Without it, it will end up checking
        // for any variation of tiles going right, instead of only straight directions
        if (board[row][position] == player) {
            correct++;
            if (correct == TTL) return true;
            position++;
            if (row == startingRow) {
                checkStraight =  checkRight(TTL, correct, position, row, startingRow, player, false);
            }
            if (row < startingRow || initialLoop) {
                int checkRow = row - 1;

                checkUp = checkRight(TTL, correct, position, checkRow, startingRow,  player, false);
            }
            if (row > startingRow || initialLoop) {
                int checkRow = row + 1;
                checkDown = checkRight(TTL, correct, position, checkRow, startingRow, player, false);
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

    public void setWinner(int player) {
        winner = player;
    }
    public int getWinner() {
        return winner;
    }

}
