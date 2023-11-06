package Draft;

import java.util.Scanner;

public class TTTDraft {
    public static void main(String[] args) {
        char[][] board = {{' ', ' ', ' '}, {' ', ' ', ' '}, {' ', ' ', ' '}};
        boolean playerTurn = true; // true for player, false for AI

        Scanner scanner = new Scanner(System.in);

        while (true) {
            printBoard(board);

            if (playerTurn) {
                playerMove(board, scanner);
                if (checkWin(board, 'X')) {
                    printBoard(board);
                    System.out.println("Player wins!");
                    break;
                }
            } else {
                aiMove(board);
                if (checkWin(board, 'O')) {
                    printBoard(board);
                    System.out.println("AI wins!");
                    break;
                }
            }

            if (isBoardFull(board)) {
                printBoard(board);
                System.out.println("It's a draw!");
                break;
            }

            playerTurn = !playerTurn;
        }

        scanner.close();
    }

    // Print the current state of the board
    private static void printBoard(char[][] board) {
        for (char[] row : board) {
            for (char cell : row) {
                System.out.print(cell + " | ");
            }
            System.out.println();
        }
    }

    // Check if the board is full
    private static boolean isBoardFull(char[][] board) {
        for (char[] row : board) {
            for (char cell : row) {
                if (cell == ' ') {
                    return false;
                }
            }
        }
        return true;
    }

    // Check if a player has won
    private static boolean checkWin(char[][] board, char symbol) {
        // Check rows, columns, and diagonals
        for (int i = 0; i < 3; i++) {
            if (board[i][0] == symbol && board[i][1] == symbol && board[i][2] == symbol) {
                return true; // row
            }
            if (board[0][i] == symbol && board[1][i] == symbol && board[2][i] == symbol) {
                return true; // column
            }
        }
        if (board[0][0] == symbol && board[1][1] == symbol && board[2][2] == symbol) {
            return true; // diagonal
        }
        if (board[0][2] == symbol && board[1][1] == symbol && board[2][0] == symbol) {
            return true; // diagonal
        }
        return false;
    }

    // Make a move for the player
    private static void playerMove(char[][] board, Scanner scanner) {
        int row, col;
        do {
            System.out.print("Enter row and column separated by a space: ");
            row = scanner.nextInt();
            col = scanner.nextInt();
        } while (row < 0 || row > 2 || col < 0 || col > 2 || board[row][col] != ' ');

        board[row][col] = 'X';
    }

    // Make a move for the AI using minimax with alpha-beta pruning
    private static void aiMove(char[][] board) {
        int[] bestMove = minimax(board, 'O', Integer.MIN_VALUE, Integer.MAX_VALUE);
        board[bestMove[0]][bestMove[1]] = 'O';
    }

    // Minimax algorithm with alpha-beta pruning
    private static int[] minimax(char[][] board, char player, int alpha, int beta) {
        int[] bestMove = new int[]{-1, -1};
        int bestScore = (player == 'O') ? Integer.MIN_VALUE : Integer.MAX_VALUE;

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[i][j] == ' ') {
                    board[i][j] = player;
                    int score = minimaxHelper(board, 'X', alpha, beta);
                    board[i][j] = ' ';

                    if ((player == 'O' && score > bestScore) || (player == 'X' && score < bestScore)) {
                        bestScore = score;
                        bestMove[0] = i;
                        bestMove[1] = j;
                    }

                    if (player == 'O') {
                        alpha = Math.max(alpha, score);
                    } else {
                        beta = Math.min(beta, score);
                    }

                    if (beta <= alpha) {
                        break;
                    }
                }
            }
        }

        return bestMove;
    }

    private static int minimaxHelper(char[][] board, char player, int alpha, int beta) {
        if (checkWin(board, 'X')) {
            return -1;
        } else if (checkWin(board, 'O')) {
            return 1;
        } else if (isBoardFull(board)) {
            return 0;
        }

        int bestScore = (player == 'O') ? Integer.MIN_VALUE : Integer.MAX_VALUE;

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[i][j] == ' ') {
                    board[i][j] = player;
                    int score = minimaxHelper(board, (player == 'X') ? 'O' : 'X', alpha, beta);
                    board[i][j] = ' ';

                    if (player == 'O') {
                        bestScore = Math.max(bestScore, score);
                        alpha = Math.max(alpha, score);
                    } else {
                        bestScore = Math.min(bestScore, score);
                        beta = Math.min(beta, score);
                    }

                    if (beta <= alpha) {
                        break;
                    }
                }
            }
        }

        return bestScore;
    }
}
