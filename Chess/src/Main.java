import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Scanner;
import java.util.regex.Pattern;

public class Main {

    public static void main(String[] args){

        Game game = new Game();
        char[][][] board = game.getBoard();

        Scanner keyboard = new Scanner(System.in);
        System.out.println("Enter a player one name (white): ");
        Player player1 = new Player(keyboard.next(), 'w');
        System.out.println("Enter a player two name (black): ");
        Player player2 = new Player(keyboard.next(), 'b');


        ArrayList playerOne = new ArrayList();
        playerOne.add(player1.getName());
        playerOne.add(player1.getColor());

        ArrayList playerTwo = new ArrayList();
        playerTwo.add(player2.getName());
        playerTwo.add(player2.getColor());


        ArrayList playOrder = new ArrayList();
        playOrder.add(playerOne);
        playOrder.add(playerTwo);


        Boolean won = false;

        while (won == false){

            for (int i = 0; i < playOrder.size(); i++){
                ArrayList currentPlayer = (ArrayList) playOrder.get(i);
                String playerName = (String) currentPlayer.get(0);
                char playerColor = (char) currentPlayer.get(1);


                System.out.println(playerName + ", please enter your move: ");
                Scanner scanner = new Scanner(System.in);
                String userInput = scanner.nextLine();
                while (!Pattern.matches("(([QKNBR]([x][a-h][1-8]|[a-h][1-8]))|([x][a-h][1-8]|[a-h][1-8]))", userInput)){
                    System.out.println(playerName + ", please enter a valid move of the form Kxa6: ");
                    scanner = new Scanner(System.in);
                    userInput = scanner.nextLine();
                }

                ArrayList userCommand = game.translate(userInput);
                System.out.println(userCommand);


                int moveType = (int) userCommand.get(0);
                char figureType = (char) userCommand.get(1);
                int targetX  = (int) userCommand.get(2);
                int targetY  = (int) userCommand.get(3);

                ArrayList isValidMove = new ArrayList();


                if(moveType == 1){
                    if(board[targetX][targetY].length == 0 | board[targetX][targetY][1] == playerColor){
                        System.out.println("INVALID EAT: FIELD IS EMPTY OR THE FIGURE IS NOT BELONGING TO THE OPPOSITE PLAYER!");
                        //loop back to new user input
                    }else{
                        isValidMove = game.validateMove(playerColor, figureType, targetX, targetY);
                    }

                }else{
                    isValidMove = game.validateMove(playerColor, figureType, targetX, targetY);
                }

                //VALIDATE MOVE IS EITHER VALID, DISAMBIGUOUS OR INVALID, THEN UPDATE BOARD IF VALID.
                if(isValidMove.size() < 1){
                    System.out.println("INVALID MOVE! PLEASE ENTER A CORRECT MOVE: ");
                    //restart turn

                }else if(isValidMove.size() > 1){
                    System.out.println("DISAMBIGUOUS MOVE! PLEASE SPECIFY START FIELD: ");
                    //read starting pos again with position list

                }else{
                    System.out.println("VALID MOVE");

                    Object[] cell = Arrays.copyOf((Object[]) isValidMove.get(0), 2);
                    char[] figureData = Arrays.copyOf((char[]) cell[0], 2); //figure data
                    int[] figureXY = Arrays.copyOf((int[]) cell[1], 2); // figure coordinate

                    //!! GET THIS FROM TRANSLATION FUNCTION
                    int[] targetPosition = {targetX, targetY};

                    game.updateBoard(figureData, figureXY, targetPosition);

                    char[][][] boardAfterMove = game.getBoard();
                    System.out.println(Arrays.deepToString(boardAfterMove));
                }
            }
        }
    }
}


