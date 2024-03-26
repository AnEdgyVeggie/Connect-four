import java.util.Scanner;
public class Main {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        GameBoard gb = new GameBoard();
        int activePlayer = 0;
        System.out.println();
        gb.printBoard();

        while(gb.getWinner() == -1) {

            if (activePlayer == 1) activePlayer = 2;
            else activePlayer = 1;

            int moveSuccess = -1;
            while (moveSuccess == -1) {

                System.out.print("Player " + activePlayer + ", please make a move (0 - 6)    ");
                String selection =  input.nextLine();
                System.out.println();
                int move = Integer.parseInt(selection);

                // need try / catch block
                moveSuccess = gb.dropPiece(move, activePlayer);
                System.out.println();

                gb.printBoard();
            }


        }

        System.out.println("WINNER!!");
        System.out.println("PLAYER " + gb.getWinner() + " WINS!");
    }


}