/**
 * Rick suggests, the IntermediateAI first check to stop a win of the opponent, 
 * then look for its own win. If neither is found, select any other open
 * spot randomly. You may use any other strategy as long as it beats RandomAI.
 * 
 * @authors HENGSOCHEAT POK
 */
package model;

import java.util.Random;

public class IntermediateAI  implements TicTacToeStrategy {

  private Random generator = new Random();

  @Override
  public OurPoint desiredMove(TicTacToeGame theGame) {
    int row = -1;
    int col = -1;
    int[] bestPosition;
    boolean shouldDefendVertical = verticalDefense(theGame, theGame.getTicTacToeBoard().length, row, col)[0] != -1;
    boolean shouldDefendHorizontal = horizontalDefense(theGame, theGame.getTicTacToeBoard().length, row, col)[0] != -1;
    boolean shouldDefendDiagonal = diagonalDefense(theGame, theGame.getTicTacToeBoard().length, row, col)[0] != -1;
    boolean shouldAttackVertical = verticalAttack(theGame, theGame.getTicTacToeBoard().length, row, col)[0] != -1;
    boolean shouldAttackHorizontal = horizontalAttack(theGame, theGame.getTicTacToeBoard().length, row, col)[0] != -1;
    boolean shouldAttackDiagonal = diagonalAttack(theGame, theGame.getTicTacToeBoard().length, row, col)[0] != -1;
    boolean shouldNotDefend = !shouldDefendHorizontal && !shouldDefendVertical && !shouldDefendDiagonal;
    boolean shouldNotAttack = !shouldAttackHorizontal && !shouldAttackVertical && !shouldAttackDiagonal;

    if (shouldAttackDiagonal)
      bestPosition = diagonalAttack(theGame, theGame.getTicTacToeBoard().length, row, col);
    else if (shouldAttackHorizontal)
      bestPosition = horizontalAttack(theGame, theGame.getTicTacToeBoard().length, row, col);
    else if (shouldAttackVertical)
      bestPosition = verticalAttack(theGame, theGame.getTicTacToeBoard().length, row, col);

    else if (shouldDefendHorizontal){
      bestPosition = horizontalDefense(theGame, theGame.getTicTacToeBoard().length, row, col);
    }
    else if (shouldDefendVertical){
        bestPosition = verticalDefense(theGame, theGame.getTicTacToeBoard().length, row, col);
    }
    else if (shouldDefendDiagonal){
          bestPosition = diagonalDefense(theGame, theGame.getTicTacToeBoard().length, row, col);
    }
    else
      bestPosition = Attack(theGame);

    return new OurPoint(bestPosition[0], bestPosition[1]);
  }

  private int[] Attack(TicTacToeGame theGame) {
    int[] bestPosition = new int[]{-1, -1};
    int min = Integer.MIN_VALUE;
    int max = Integer.MAX_VALUE;
    char currentChar = 'O';
    int bestWinRate = Integer.MIN_VALUE;

    for (int i = 0; i < 3; i++) {
      for (int j = 0; j < 3; j++) {
        if (theGame.getTicTacToeBoard()[i][j] == '_') {
          theGame.getTicTacToeBoard()[i][j] = currentChar;
          int winRate = searchForTheBestSpot(theGame, 'X', min, max);
          theGame.getTicTacToeBoard()[i][j] = '_';
          if (winRate > bestWinRate) {
            bestWinRate = winRate;
            bestPosition[0] = i; bestPosition[1] = j;
          }
          min = Math.max(min, winRate);
          if (max <= min) {
            break;}}}
    }
    return bestPosition;
  }

  private int searchForTheBestSpot(TicTacToeGame theGame, char currentChar, int minInteger, int maxInteger) {
    if (theGame.didWin('X')) {
      return -1;
    } else if (theGame.didWin('O')) {
      return 1;
    } else if (theGame.tied()) {
      return 0;
    }

    int bestWinRate = Integer.MIN_VALUE;
    if (currentChar == 'X')
      bestWinRate = Integer.MAX_VALUE;

    for (int i = 0; i < 3; i++) {
      for (int j = 0; j < 3; j++) {
        if (theGame.getTicTacToeBoard()[i][j] == '_') {
          theGame.getTicTacToeBoard()[i][j] = currentChar;
          if (currentChar == 'X')
            currentChar = 'O';
          else
            currentChar = 'X';
          int winRate = searchForTheBestSpot(theGame, currentChar, minInteger, maxInteger);
          theGame.getTicTacToeBoard()[i][j] = '_';

          if (currentChar == 'O') {
            bestWinRate = Math.max(bestWinRate, winRate);
            minInteger = Math.max(minInteger, winRate);}
          else {
            bestWinRate = Math.min(bestWinRate, winRate);
            maxInteger = Math.min(maxInteger, winRate);}
          if (maxInteger <= minInteger) {
            break;}}}}

    return bestWinRate;
  }

  private static int[] horizontalDefense (TicTacToeGame theGame, int size, int row, int col) {
    for (int r = 0; r < size; r++) {
      int countPlayerCharHorizontal = 0;
      for (int c = 0; c < size; c++) {

        char position = theGame.getTicTacToeBoard()[r][c];
        if (position == 'X'){
          countPlayerCharHorizontal++;
        }
      }
      if (countPlayerCharHorizontal == 2){
        if (theGame.available(r, 0)){
          row = r; col = 0;}
        else if (theGame.available(r, 1)){
          row = r; col = 1;}
        else if (theGame.available(r, 2)){
          row = r; col = 2;}
      }
    }
    return new int[]{row, col};
  }

  private static int[] verticalDefense (TicTacToeGame theGame, int size, int row, int col) {
    for (int c = 0; c < size; c++) {
      int countPlayerCharVertical = 0;
      for (int r = 0; r < size; r++) {
        char position = theGame.getTicTacToeBoard()[r][c];
        if (position == 'X')
          countPlayerCharVertical++;
      }
      if (countPlayerCharVertical == 2){
        col = c;
        if (theGame.available(0, c))
          row = 0;
        if (theGame.available(1, c))
          row = 1;
        if (theGame.available(2, c))
          row = 2;
      }
    }
    return new int[]{row, col};
  }

  private static int[] diagonalDefense (TicTacToeGame theGame, int size, int row, int col) {
    char[][] position = theGame.getTicTacToeBoard();
    int countXDiagonal = 0;
    if (position[0][0] == 'X')
      countXDiagonal++;
    if (position[1][1] == 'X')
      countXDiagonal++;
    if (position[2][2] == 'X')
      countXDiagonal++;

    if (countXDiagonal == 2){
      //System.out.println("Diagonal Defense Needed Diagonally");
      if (theGame.available(0,0)){
        row = 0; col = 0;}
      else if (theGame.available(1,1)){
        row = 1; col = 1;}
      else if (theGame.available(2,2)){
        row = 2; col = 2;}
    }

    countXDiagonal = 0;
    if (position[0][2] == 'X')
      countXDiagonal++;
    if (position[1][1] == 'X')
      countXDiagonal++;
    if (position[2][0] == 'X')
      countXDiagonal++;

    if (countXDiagonal == 2){
      //System.out.println("Diagonal Defense Needed Diagonally");
      if (theGame.available(0,2)){
        row = 0; col = 2;}
      else if (theGame.available(1,1)){
        row = 1; col = 1;}
      else if (theGame.available(2,0)){
        row = 2; col = 0;}
    }

    return new int[]{row, col};
  }

  private static int[] horizontalAttack (TicTacToeGame theGame, int size, int row, int col) {
    for (int r = 0; r < size; r++) {
      int countPlayerCharHorizontal = 0;
      for (int c = 0; c < size; c++) {

        char position = theGame.getTicTacToeBoard()[r][c];
        if (position == 'O'){
          countPlayerCharHorizontal++;
        }
      }
      if (countPlayerCharHorizontal == 2){
        if (theGame.available(r, 0)){
          row = r; col = 0;}
        else if (theGame.available(r, 1)){
          row = r; col = 1;}
        else if (theGame.available(r, 2)){
          row = r; col = 2;}
      }
    }
    return new int[]{row, col};
  }

  private static int[] verticalAttack (TicTacToeGame theGame, int size, int row, int col) {
    for (int c = 0; c < size; c++) {
      int countPlayerCharVertical = 0;
      for (int r = 0; r < size; r++) {
        char position = theGame.getTicTacToeBoard()[r][c];
        if (position == 'O')
          countPlayerCharVertical++;
      }
      if (countPlayerCharVertical == 2){
        col = c;
        if (theGame.available(0, c))
          row = 0;
        if (theGame.available(1, c))
          row = 1;
        if (theGame.available(2, c))
          row = 2;
      }
    }
    return new int[]{row, col};
  }

  private static int[] diagonalAttack (TicTacToeGame theGame, int size, int row, int col) {
    char[][] position = theGame.getTicTacToeBoard();
    int countXDiagonal = 0;
    if (position[0][0] == 'O')
      countXDiagonal++;
    if (position[1][1] == 'O')
      countXDiagonal++;
    if (position[2][2] == 'O')
      countXDiagonal++;

    if (countXDiagonal == 2){
      if (theGame.available(0,0))
        row = 0; col = 0;
      if (theGame.available(1,1))
        row = 1; col = 1;
      if (theGame.available(2,2))
        row = 2; col = 2;
    }

    countXDiagonal = 0;
    if (position[0][2] == 'O')
      countXDiagonal++;
    if (position[1][1] == 'O')
      countXDiagonal++;
    if (position[2][0] == 'O')
      countXDiagonal++;

    if (countXDiagonal == 2){
      if (theGame.available(0,2))
        row = 0; col = 0;
      if (theGame.available(1,1))
        row = 1; col = 1;
      if (theGame.available(2,0))
        row = 2; col = 2;
    }

    return new int[]{row, col};
  }

}
