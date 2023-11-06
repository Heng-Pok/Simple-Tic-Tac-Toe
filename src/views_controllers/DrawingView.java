package views_controllers;

import javafx.event.EventHandler;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.scene.text.TextFlow;
import model.OurObserver;
import model.TicTacToeGame;

public class DrawingView extends BorderPane implements OurObserver {
    private TicTacToeGame theGame;
    private BorderPane playerArea;
    private Text prompt;
    private TextFlow promptArea;
    private Canvas canvas;
    private GraphicsContext gc;

    Image X = new Image("file:images/X.png", false);
    Image O = new Image("file:images/O.png", false);

    public DrawingView(TicTacToeGame theModel){
        theGame = theModel;
        initializePanel();
    }

    private void initializePanel() {
        playerArea = new BorderPane();
        canvas = new Canvas(250, 250);
        playerArea.setCenter(canvas);
        gc = canvas.getGraphicsContext2D();
        prompt = new Text("\nMake a move!");
        prompt.setFont(new Font("Courier", 20));
        promptArea = new TextFlow(prompt);
        promptArea.setTextAlignment(TextAlignment.CENTER);
        drawSquares(gc);
        registerHandlers();
        this.setCenter(playerArea);
        this.setTop(promptArea);
    }

    private void drawSquares(GraphicsContext gc) {
        // Draw the outer border as a square
        gc.strokeRect(5,5,gc.getCanvas().getWidth() - 10 ,gc.getCanvas().getHeight() - 10);
        // Draw two vertical lines
        gc.strokeLine(80, 5, 80, gc.getCanvas().getHeight() - 5);
        gc.strokeLine(2 * 80, 5, 2 * 80, gc.getCanvas().getHeight() - 5);
        // Draw two horizontal lines
        gc.strokeLine(5, 80, gc.getCanvas().getWidth() - 5, 80);
        gc.strokeLine(5, 2 * 80, gc.getCanvas().getWidth() - 5, 2 * 80);
    }

    public void drawXsAndOs(GraphicsContext gc, double detectedX, double detectedY, boolean playersTurn, boolean computersTurn, boolean justUpdate) {

        if (playersTurn && theGame.stillRunning()){ // when player presses the mouse
            prompt.setText("\n"); // clear the prompt text of any previous text
            if ((79.0 < detectedX && detectedX < 153.0) && ( 169.0 < detectedY && detectedY < 249.0)) {
                //gc.drawImage(X, 25, 25); // row 1 column 1
                if (!theGame.available(0, 0)){ // if the spot is taken
                    prompt.setText("\nInvalid Move!"); // tell the player "invalid move"
                    System.out.println("Invalid Move");
                }
                theGame.humanMove(0,0, false);
            }
            if ((153.0 <= detectedX && detectedX < 234.0) && (169.0 < detectedY && detectedY < 249.0)){
                //gc.drawImage(X, 25 + 80, 25); // row 1 column 2
                if (!theGame.available(0, 1)){ // if the spot is taken
                    prompt.setText("\nInvalid Move!"); // tell the player "invalid move"
                    System.out.println("Invalid Move");
                }
                theGame.humanMove(0,1, false);
            }
            if ((234.0 <= detectedX && detectedX < 319.0) && (169.0 < detectedY && detectedY < 249.0)){
                //gc.drawImage(X, 25 + 80 * 2, 25); // row 1 column 3
                if (!theGame.available(0, 2)){ // if the spot is taken
                    prompt.setText("\nInvalid Move!"); // tell the player "invalid move"
                    System.out.println("Invalid Move");
                }
                theGame.humanMove(0,2, false);
            }
            if ((79.0 < detectedX && detectedX < 153.0) && ( 249.0 <= detectedY && detectedY < 324.0)){
                //gc.drawImage(X, 25, 80 + 25); // row 2 column 1
                if (!theGame.available(1, 0)){ // if the spot is taken
                    prompt.setText("\nInvalid Move!"); // tell the player "invalid move"
                    System.out.println("Invalid Move");
                }
                theGame.humanMove(1,0, false);
            }
            if ((153.0 <= detectedX && detectedX < 234.0) && (249.0 <= detectedY && detectedY < 324.0)){
                //gc.drawImage(X, 25 + 80, 25 + 80); // row 2 column 2
                if (!theGame.available(1, 1)){ // if the spot is taken
                    prompt.setText("\nInvalid Move!"); // tell the player "invalid move"
                    System.out.println("Invalid Move");
                }
                theGame.humanMove(1,1, false);
            }
            if ((234.0 <= detectedX && detectedX < 319.0) && (249.0 <= detectedY && detectedY < 324.0)){
                //gc.drawImage(X, 25 + 80 * 2, 25 + 80); // row 2 column 3
                if (!theGame.available(1, 2)){ // if the spot is taken
                    prompt.setText("\nInvalid Move!"); // tell the player "invalid move"
                    System.out.println("Invalid Move");
                }
                theGame.humanMove(1,2, false);
            }
            if ((79.0 < detectedX && detectedX < 153.0) && ( 324.0 <= detectedY && detectedY < 409.0)){
                //gc.drawImage(X, 25, 80 * 2 + 25); // row 3 column 1
                if (!theGame.available(2, 0)){ // if the spot is taken
                    prompt.setText("\nInvalid Move!"); // tell the player "invalid move"
                    System.out.println("Invalid Move");
                }
                theGame.humanMove(2,0, false);
            }
            if ((153.0 <= detectedX && detectedX < 234.0) && (324.0 <= detectedY && detectedY < 409.0)){
                //gc.drawImage(X, 25 + 80, 25 + 80 * 2); // row 3 column 2
                if (!theGame.available(2, 1)){ // if the spot is taken
                    prompt.setText("\nInvalid Move!"); // tell the player "invalid move"
                    System.out.println("Invalid Move");
                }
                theGame.humanMove(2,1, false);
            }
            if ((234.0 <= detectedX && detectedX < 319.0) && (324.0 <= detectedY && detectedY < 409.0)){
                //gc.drawImage(X, 25 + 80 * 2, 80 * 2 + 25); // row 3 column 3
                if (!theGame.available(2, 2)){ // if the spot is taken
                    prompt.setText("\nInvalid Move!"); // tell the player "invalid move"
                    System.out.println("Invalid Move!");
                }
                theGame.humanMove(2,2, false);
            }
            update(theGame);
        }

        if (computersTurn){ // when it is the computer's turn
            if ((detectedX == 0) && ( detectedY == 0))
                gc.drawImage(O, 25, 25); // row 1 column 1
            if ((detectedX == 0) && ( detectedY == 1))
                gc.drawImage(O, 25 + 80, 25); // row 1 column 2
            if ((detectedX == 0) && ( detectedY == 2))
                gc.drawImage(O, 25 + 80 * 2, 25); // row 1 column 3
            if ((detectedX == 1) && ( detectedY == 0))
                gc.drawImage(O, 25, 80 + 25); // row 2 column 1
            if ((detectedX == 1) && ( detectedY == 1))
                gc.drawImage(O, 25 + 80, 25 + 80); // row 2 column 2
            if ((detectedX == 1) && ( detectedY == 2))
                gc.drawImage(O, 25 + 80 * 2, 25 + 80); // row 2 column 3
            if ((detectedX == 2) && ( detectedY == 0))
                gc.drawImage(O, 25, 80 * 2 + 25); // row 3 column 1
            if ((detectedX == 2) && ( detectedY == 1))
                gc.drawImage(O, 25 + 80, 25 + 80 * 2); // row 3 column 2
            if ((detectedX == 2) && ( detectedY == 2))
                gc.drawImage(O, 25 + 80 * 2, 80 * 2 + 25); // row 3 column 3
        }

        if (justUpdate){ // just updating when switched back from another view
            if (prompt.getText().equals("\nWelcome!"))
                prompt.setText("\n");
            if ((detectedX == 0) && ( detectedY == 0) ) {
                gc.drawImage(X, 25, 25); // row 1 column 1
            }
            if ((detectedX == 0) && ( detectedY == 1) ){
                gc.drawImage(X, 25 + 80, 25); // row 1 column 2
            }
            if ((detectedX == 0) && ( detectedY == 2) ){
                gc.drawImage(X, 25 + 80 * 2, 25); // row 1 column 3
            }
            if ((detectedX == 1) && ( detectedY == 0) ){
                gc.drawImage(X, 25, 80 + 25); // row 2 column 1
            }
            if ((detectedX == 1) && ( detectedY == 1) ){
                gc.drawImage(X, 25 + 80, 25 + 80); // row 2 column 2
            }
            if ((detectedX == 1) && ( detectedY == 2) ){
                gc.drawImage(X, 25 + 80 * 2, 25 + 80); // row 2 column 3
            }
            if ((detectedX == 2) && ( detectedY == 0)){
                gc.drawImage(X, 25, 80 * 2 + 25); // row 3 column 1
            }
            if ((detectedX == 2) && ( detectedY == 1) ){
                gc.drawImage(X, 25 + 80, 25 + 80 * 2); // row 3 column 2
            }
            if ((detectedX == 2) && ( detectedY == 2) ){
                gc.drawImage(X, 25 + 80 * 2, 80 * 2 + 25); // row 3 column 3
            }
        }
    }

    private void registerHandlers() {
        canvas.setOnMousePressed(new MousePressed());
    }

    public class MousePressed implements EventHandler<MouseEvent> {

        @Override

        public void handle(MouseEvent event) {
            double detectedX = event.getSceneX();
            double detectedY = event.getSceneY();
            System.out.println("coordinates clicked: (" + detectedX + ", " + detectedY + ")");
            drawXsAndOs(gc, detectedX, detectedY, true, false, false);
        }
    }

    @Override
    public void update(Object theObserved) {
       System.out.println("update called from OurObservable TicTacToeGame \n" + theGame);
        int boardSize = theGame.getTicTacToeBoard().length;
        char[][] theBoard = theGame.getTicTacToeBoard();
        for (int row = 0; row < boardSize; row++){
            for (int col = 0; col < boardSize; col++){
                if (theBoard[row][col] == 'O')
                    drawXsAndOs(gc, row, col, false, true, false);
                else if (theBoard[row][col] == 'X')
                    drawXsAndOs(gc, row, col, false, false, true);
            }
        }
        if (!theGame.stillRunning()){
            System.out.println("Game over");
            if (theGame.tied()){
                System.out.println("Tie");
                prompt.setText("\nTie");
            }
            else if (theGame.didWin('X')){
                System.out.println("X wins");
                prompt.setText("\nX wins");
            }
            else if (theGame.didWin('O')){
                System.out.println("O wins");
                prompt.setText("\nO wins");
            }
        }
    }
}
