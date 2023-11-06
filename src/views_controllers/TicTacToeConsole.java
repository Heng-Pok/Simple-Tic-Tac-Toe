package views_controllers;

import model.IntermediateAI;
import model.TicTacToeGame;

import java.io.IOException;
import java.util.Scanner;

/**
 * This class mainly utilizes the TicTacToeGame class to run the game on console.
 * Enables player to play with the computer opponent by typing in coordinates in the console
 *
 * @author HENGSOCHEAT POK
 *
 */

public class TicTacToeConsole {

    public static void main(String[] args) {
        TicTacToeGame theGame = new TicTacToeGame();
        theGame.setComputerPlayerStrategy(new IntermediateAI());
        while (theGame.stillRunning()){
            play(theGame);
            System.out.println(theGame);
            System.out.println();}

        checkWhoWins(theGame);
    }

    private static void checkWhoWins(TicTacToeGame theGame) {
        if (theGame.didWin('X'))
            System.out.println("X wins");
        else if (theGame.didWin('O'))
            System.out.println("O wins");
        else if (theGame.tied())
            System.out.println("Tie");
    }


    private static void play(TicTacToeGame theGame) {
        System.out.print("Enter row and column: ");
        String[] coordinates = getEnteredCoordinates();
        int rowToBeUsed = Integer.parseInt(coordinates[0]);
        int columnToBeUsed = Integer.parseInt(coordinates[1]);
        if (!theGame.available(rowToBeUsed, columnToBeUsed)){
            while (!theGame.available(rowToBeUsed, columnToBeUsed)) {
                System.out.println("Square taken, try again");
                System.out.print("Enter row and column: ");
                coordinates = getEnteredCoordinates();
                rowToBeUsed = Integer.parseInt(coordinates[0]);
                columnToBeUsed = Integer.parseInt(coordinates[1]);
            }
        }
        theGame.humanMove(rowToBeUsed, columnToBeUsed, false);
    }

    private static String[] getEnteredCoordinates() {
        Scanner enteredCoordinates = new Scanner(System.in);
        String playerCoordinates = enteredCoordinates.nextLine();
        String[] coordinates = playerCoordinates.split(" ");
        boolean outOfBounds = Integer.parseInt(coordinates[0]) < 0 || Integer.parseInt(coordinates[0]) > 2 || Integer.parseInt(coordinates[1]) < 0 || Integer.parseInt(coordinates[1]) > 2;
        while (outOfBounds) {
            System.out.println("Coordinates out of bounds! Please re-enter:");
            //enteredCoordinates = new Scanner(System.in);
            playerCoordinates = enteredCoordinates.nextLine();
            coordinates = playerCoordinates.split(" ");
            if (!(Integer.parseInt(coordinates[0]) < 0 || Integer.parseInt(coordinates[0]) > 2 || Integer.parseInt(coordinates[1]) < 0 || Integer.parseInt(coordinates[1]) > 2))
                break;
        }
        return coordinates;
    }
}
