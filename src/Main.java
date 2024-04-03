import org.w3c.dom.ls.LSOutput;

import java.util.Scanner;
public class Main {
    public static void main(String[] args) {
        selectGame();
    }

    static void twoPlayerGame(GameBoard gb, Scanner input) {
        int activePlayer = 0;
        gb.printBoard();
        while(gb.getWinner() == -1) {

            if (activePlayer == 1) activePlayer = 2;
            else activePlayer = 1;

            boolean moveSuccess = false;
            playerMove(activePlayer, input, gb);
            gb.printBoard();
        }

        selectWinner(gb, input);
    }

    static void onePlayerGame(GameBoard gb, Scanner input) {
        int activePlayer = 0;
        gb.printBoard();
        while (gb.getWinner() == -1) {

            if (activePlayer == 1)  activePlayer = 2;
            else  activePlayer = 1;

            if (activePlayer == 1) {
                playerMove(activePlayer, input, gb);
            }
            else {
                computerMove(gb);
            }
            gb.printBoard();
        }

        selectWinner(gb, input);
    }

    // Could have not written this as a function, but it was easier to read in the game function
    static void computerMove(GameBoard gb) {
        gb.dropPiece(gb.chooseBestMove(), 2, false);
    }

    static void playerMove(int activePlayer, Scanner input, GameBoard gb) {
        int moveSuccess = -1;
        while (moveSuccess == -1) {
            System.out.println("Player " + activePlayer + ", please make a move (0-6)");
            String selection = input.nextLine();
            System.out.println();
            try {
                int lane = Integer.parseInt(selection);
                if (lane >= 0 &&  lane <= 6) {
                    moveSuccess = gb.dropPiece(lane, activePlayer, false);
                }
            }
            catch (Exception e) {
                System.out.println("ERROR: That move is out of bounds!");
            }
            System.out.println();

            if (moveSuccess == -1) {
                System.out.println("### INVALID MOVE, THAT LANE IS FULL ###");
            }
        }
    }

    static void selectWinner(GameBoard gb, Scanner input) {
        if (gb.getWinner() != 0) {
            System.out.println("WINNER!!");
            System.out.println("PLAYER " + gb.getWinner() + " WINS!");
            System.out.println();
        } else {
            System.out.println("THE GAME ENDED IN A TIE!");
        }

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

        String gameType = "NOPE";
        while (gameType.equals("NOPE")) {

            gameType = input.nextLine();
            System.out.println();
            if (gameType.equals("2")) {
                twoPlayerGame(gb, input);
            } else if (gameType.equals("1")) {
                onePlayerGame(gb, input);
            } else {
                System.out.println("###      ERROR, INVALID ENTRY      ###");
                System.out.println();
                System.out.println("           Enter '1' or '2'           ");
                System.out.println("       for the amount of players      ");
                gameType = "NOPE";
            }
        }
    }




}