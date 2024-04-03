// Ethan Sylvester | 101479568   |   Taylor Martin | 100849882   |   Amanda Gurney | 101443253
// Data Structures and Algorithms | COMP 2080
// Assignment 2 : Connect 4
// Data of last update: 4/3/2024

import java.util.Scanner;
public class Main {
    static boolean initialTurn = true;
    static boolean player1Turn = false;
    static String player1Name;
    static String player2Name;
    static int player1Code;
    static int player2Code;
    public static void main(String[] args) {
        selectGame();
    }

    static void twoPlayerGame(GameBoard gb, Scanner input) {
        player1Turn = true;
        player1Code = 1;
        player2Code = 2;
        System.out.print("'R' Player, please enter your name: ");
        player1Name = input.nextLine();
        System.out.print("'Y' Player, please enter your name: ");
        player2Name = input.nextLine();
        gb.printBoard();

        while (gb.getWinner() == -1) {
            if (player1Turn) {
                playerMove(input, gb);
                player1Turn = false;
            }
            else {
                playerMove(input, gb);
                player1Turn = true;
            }
            gb.printBoard();
        }
        selectWinner(gb, input);
    }

    static void onePlayerGame(GameBoard gb, Scanner input) {
        player2Name = "Your friendly neighbourhood AI";
        // GAME SETUP
        System.out.print("Please enter your name: ");
        player1Name = input.nextLine();
        System.out.println(player1Name + ", do you want to be 'R', or 'Y'? ('R' MOVES FIRST!)");
        char playerPiece = ' ';
        while (playerPiece != 'R' && playerPiece != 'Y') {
            try {
                playerPiece = input.nextLine().toUpperCase().charAt(0);
            }
            catch (Exception e) {
                System.out.println();
                System.out.println("### Please enter ONLY 'R' or 'Y' ###");
            }

            if (playerPiece == 'R') {
                player1Code = 1;
                // player2Code used for AI
                player2Code = 2;
                player1Turn = true;
            }
            else if (playerPiece == 'Y') {
                player1Code = 2;
                player2Code = 1;
            }
            else {
                System.out.println();
                System.out.println("### Please enter ONLY 'R' or 'Y' ###");
            }
        }

        int activePlayer = 0;
        gb.printBoard();

        // GAME LOOP
        while (gb.getWinner() == -1) {
            if (player1Turn) {
                playerMove(input, gb);
                player1Turn = false;
            }
            else {
                computerMove(gb, player2Code);
                player1Turn = true;
            }
            gb.printBoard();

        }
        selectWinner(gb, input);
    }

    static void computerMove(GameBoard gb, int AICode) {
        // force the AI to move on 3 before initializing minimax. For some reason
        // there was an issue with it not stopping you if you went in a straight line
        // on 3. There was no conceivable reason for this, through debugging, it just didn't percieve this
        // one specific case as a win. It will block you on literally every other move.
        if (initialTurn)  {
            gb.dropPiece(3, AICode, false);
            initialTurn = false;
        }
        else gb.dropPiece(gb.chooseBestMove(), AICode, false);
    }

    static void playerMove(Scanner input, GameBoard gb) {
        int moveSuccess = -1;
        String activePlayerName;
        int activePlayer = -1;

        // checks to make sure player can make that play
        while (moveSuccess == -1) {
            // establishes player variables
            if (player1Turn) {
                activePlayerName = player1Name;
                activePlayer = player1Code;
            } else {
                activePlayerName = player2Name;
                activePlayer = player2Code;
            }
            System.out.println(activePlayerName + ", please make a move (0-6)");
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
        String playerName = "";
        int winnerCode = gb.getWinner();
        if (winnerCode != 0) {
            if (winnerCode == player1Code) {
                playerName = player1Name;
            } else {
                playerName = player2Name;
            }

            System.out.println("WINNER!!");
            System.out.println(playerName + " WINS!");
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