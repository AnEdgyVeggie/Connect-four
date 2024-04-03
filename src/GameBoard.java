// Ethan Sylvester | 101479568   |   Taylor Martin | 100849882   |   Amanda Gurney | 101443253
// Data Structures and Algorithms | COMP 2080
// Assignment 2 : Connect 4
// Data of last update: 4/3/2024

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.lang.Integer;

public class GameBoard {

    public char[][] board;
    public int width = 7;
    public int height = 6;
    private int winner;
    private int simWin;
    private final Map<Integer, Integer> minimaxScore = new HashMap<Integer, Integer>();

    public GameBoard() {
        board = new char[height][width];
        winner = -1;
        simWin = -1;
        initializeBoard();
        minimaxScore.put(1, -1);
        minimaxScore.put(2, 1);
        minimaxScore.put(0, 0);
    }


    int dropPiece(int position, int player, boolean simulated) {
        int row = 5;
        char piece;
        if (player == 1) { piece = 'R'; }
        else { piece = 'Y'; }

        // the simulated variable splits checks up so that the AI's theoretical checks aren't misconstrued
        // as actual plays. This variable is passed into the win-condition checker
        while (row >= 0) {
            if (board[row][position] == ' ' ){
                board[row][position] = piece;
                checkWinCondition(position, row, piece, player, simulated);
                return row;
            }
            else {
                row--;
            }
        }
        // Returns -1 if the column is full
        return -1;
    }

    void removePiece(int position) {
        int row = 0;
        while (board[row][position] == ' ') {
            if (row == 6) return;
            row++;
        }
        board[row][position]  = ' ';
    }

    void checkWinCondition(int position, int row, char piece, int player, boolean simulated) {

         if (checkDown(0, position, row, piece)){
             simWin = player;
             if (!simulated) winner = player;
             return ;
         }
         // TTL (Time-To-Live) is the score needed to secure a win. Called TTL because below, it becomes the amount
        // of time spent in other branches
        // checks for last play and to the left
        if (checkSides("left",4, 0, position, row, row, piece, true)) {
            simWin = player;
            if (!simulated) winner = player;
            return ;
        }
        // checks for last play and to the right
        if (checkSides("right", 4, 0, position, row, row, piece, true)){
            simWin = player;
            if (!simulated) winner = player;
            return ;
        }

        // Below will check for the last play, to the right AND left
        // TTL total is 5, because both left and right will both count a point for the piece you play, instead of only
        // checking one direction, which means instead of starting a 1/4 points when starting at the end, you are
        // starting at 2, so it needs to be 2/5 so that it requires 3 other pieces still

        // ########## Straight line win
        if (checkSides("left",3, 0, position, row, row, piece, false)
         && checkSides("right", 2, 0, position, row, row, piece, false)){
            simWin = player;
            if (!simulated) winner = player;
            return ;
        }
        if (checkSides("left",2, 0, position, row, row, piece, false)
                && checkSides("right", 3, 0, position, row, row, piece, false)){
            simWin = player;
            if (!simulated) winner = player;
            return ;
        }

        // ##########  left to right slant win
        if (checkSides("left",3, 0, position,row,row + 1,  piece, false)
                && checkSides("right", 2, 0, position, row, row - 1, piece, false)){
            simWin = player;
            if (!simulated) winner = player;
            return ;
        }
        if (checkSides("left",2, 0, position, row, row + 1, piece, false)
                && checkSides("right", 3, 0, position, row, row - 1, piece, false)){
            simWin = player;
            if (!simulated) winner = player;
            return ;
        }

        // ########## right to left slant win
        if (checkSides("left",3, 0, position, row, row - 1, piece, false)
                && checkSides("right", 2, 0, position, row, row + 1, piece, false)){
            simWin = player;
            if (!simulated) winner = player;
            return ;
        }

        if (checkSides("left",2, 0, position, row, row - 1, piece, false)
                && checkSides("right", 3, 0, position, row, row + 1, piece, false)){
            simWin =  player;
            if (!simulated) winner = player;
            return;
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

    boolean checkSides(String direction, int TTL, int correct, int position, int row, int startingRow, char player, boolean initialLoop) {
        // base cases, we are going to check ALL possibilities to the sides
        // including diagonals, so check that you don't move off the board
        if (position == width) return false;
        if (position == -1) return false;
        if (row >= board.length) return false;
        if (row == -1) return false;

        // these are check conditions for all 3 directions, used at end of function
        boolean checkStraight = false, checkDown = false, checkUp = false;

        // initialLoop runs each direction to check in every direction at once. After the initial loop,
        // the row will either be equal, lower, or hight that the row it started at. It will end up
        // checking for any variation of tiles going left/right, instead of only straight directions
        if (board[row][position] == player) {
            correct++;
            if (correct == TTL) return true;
            if (direction.equals("right")) { position++; }
            else  { position--; }
            if (row == startingRow) {
                checkStraight =  checkSides(direction, TTL, correct, position, row, startingRow, player, false);
            }
            if (row < startingRow || initialLoop) {
                int checkRow = row - 1;
                checkUp = checkSides(direction, TTL, correct, position, checkRow, startingRow, player, false);
            }
            if (row > startingRow || initialLoop) {
                int checkRow = row + 1;

                checkDown = checkSides(direction, TTL, correct, position, checkRow, startingRow, player, false);
            }
        }
        // if any of these conditions are true, will indicate a win, otherwise it's a loss
        return (checkStraight || checkUp || checkDown);
    }
    
    public void initializeBoard() {
        for (int i = 0; i < board.length; i++) {
            Arrays.fill(board[i], ' ');
        }
    }

    void printBoard() {
        System.out.println("- 0 1 2 3 4 5 6 -");
        for (int i = 0; i < board.length; i++) {
            System.out.print("[|");
            for (char space : board[i]){
                System.out.print(space + "|");
            }
            System.out.println("]");
        }
        System.out.println("- 0 1 2 3 4 5 6 -");
        System.out.println();
        System.out.println("=================");
        System.out.println();
    }


    public int chooseBestMove() {
        int bestMove = -1;
        int bestScore = Integer.MIN_VALUE;
        int score = 0;

        for (int i = 0; i <= width - 1; i++) {
            // checks for space on the board so it isnt wasting a check
            if (board[0][i] == ' ') {
                // drop a simulated piece, check to see if it wins
                dropPiece(i, 2, true);
                // if the simulated piece wins, check to see who wins
                if (simWin != -1) {
                    // set this piece as a higher priority (higher than the -1 / 1) based on the player number
                    if (simWin == 2) score = 2;
                    else score = -2;
                }
                else {
                    score = minimax(false, Integer.MIN_VALUE, Integer.MAX_VALUE);
                }
                // complete the simulation: remove the simulated pieces
                removePiece(i);
                // reset the simulation win condition to allow it to run again
                simWin = -1;

                if (score >= bestScore) {
                    bestScore = score;
                    bestMove = i;
                }
            }
        }
        return bestMove;
    }

    private int minimax(boolean isMaximizing, int alpha, int beta) {
        // base case return when it finds a simulated win
        if (simWin != -1) {
            // consults the look-up table to determine the reward
            return minimaxScore.get(simWin);
        }

        // if isMaximizing, it is AI's turn, else it is Player.
        int bestScore;
        if (isMaximizing) {
            // sets bestscore to the minimal possible integer
            bestScore = Integer.MIN_VALUE;
            for (int i = 0; i <= width - 1; i++ ) {
                // dropPiece also checks win conditions
                if (dropPiece(i, 2, true) != -1) {
                    // recursively checks the other players best move
                    int score = minimax(false, alpha, beta);
                    removePiece(i);
                    bestScore = Math.max(score, bestScore);
                    alpha = Math.max(alpha, score);
                    if (beta <= alpha) {
                        break;
                    }
                }
            }
        } else {
            bestScore = Integer.MAX_VALUE;
            for (int i = 0; i <= width - 1; i++ ) {
                if (dropPiece(i, 1, true) != -1) {
                    int score = minimax(true, alpha, beta);
                    removePiece(i);
                    bestScore = Math.min(score, bestScore);
                    beta = Math.min(beta, score);
                    if (beta <= alpha) {
                        break;
                    }
                }
            }
        }
        return bestScore;
    }


    public int getWinner() {
        return winner;
    }

}
