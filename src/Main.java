import java.util.Scanner;
public class Main {
    public static void main(String[] args) {
        selectGame();
    }

    static void onePlayerGame(GameBoard gb, Scanner input) {
        int activePlayer = 0;
        gb.printBoard();
        while (gb.getWinner() == -1) {

            gb.printBoard();
        }
        selectWinner(gb, input);
    }


    static void twoPlayerGame(GameBoard gb, Scanner input) {
        int activePlayer = 0;
        gb.printBoard();
        while(gb.getWinner() == -1) {

            if (activePlayer == 1) activePlayer = 2;
            else activePlayer = 1;

            boolean moveSuccess = false;
            while (!moveSuccess) {

                System.out.print("Player " + activePlayer + ", please make a move (0 - 6)    ");
                String selection =  input.nextLine();
                System.out.println();
                int move = Integer.parseInt(selection);

                // need try / catch block
                moveSuccess = gb.dropPiece(move, activePlayer);
                System.out.println();

                gb.printBoard();

                if (!moveSuccess) {
                    System.out.println("### INVALID MOVE, THAT LANE IS FULL ###");
                }
            }
        }
        selectWinner(gb, input);
    }

    static void selectWinner(GameBoard gb, Scanner input) {
        System.out.println("WINNER!!");
        System.out.println("PLAYER " + gb.getWinner() + " WINS!");
        System.out.println();
        System.out.print("Play Again?   ");
        String selection = input.nextLine().toUpperCase();
        if (selection.equals("Y") || selection.equals("YES")) {
            selectGame();
        }
        else {
            System.out.println("Thanks for playing!!");
        }
    }

    static void selectGame() {
        Scanner input = new Scanner(System.in);
        GameBoard gb = new GameBoard();

        System.out.println("======================================");
        System.out.println("=                                    =");
        System.out.println("=      Let's Play: Connect Four      =");
        System.out.println("=                                    =");
        System.out.println("======================================");
        System.out.println();
        System.out.println("======================================");
        System.out.println();
        System.out.println("           Enter '1' or '2'           ");
        System.out.println("       for the amount of players      ");

        String gameType = input.nextLine();
        System.out.println();
        if (gameType.equals("2")) {
            twoPlayerGame(gb, input);
        }
    }
}