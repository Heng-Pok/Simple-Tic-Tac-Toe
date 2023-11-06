package views_controllers;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;

import javafx.scene.paint.Color;
import javafx.scene.text.Font;

import model.OurObserver;
import model.TicTacToeGame;

public class ButtonView extends BorderPane implements OurObserver {
    private TicTacToeGame theGame;
    private Button[][] buttonArray = new Button[3][3];
    private AnchorPane playerArea;
    private Label prompt;
    public ButtonView(TicTacToeGame theModel) {
        theGame = theModel;
        initializePanel();
    }

    private void initializePanel() {
        playerArea = new AnchorPane();
        for (int i = 0; i < buttonArray.length; i++){
            for (int j = 0; j < buttonArray.length; j++){
                char characterForEachButton = theGame.getTicTacToeBoard()[i][j];
                Button eachButton = new Button("" + characterForEachButton);
                eachButton.setFont(new Font("Courier New", 32));
                buttonArray[i][j] = eachButton;
                buttonArray[i][j].setOnAction(new buttonListener());
            }
        }

        setUpButtons();
        prompt = new Label("Make a Move");
        prompt.setFont(new Font("Courier", 20));
        prompt.setTextFill(Color.BLUE);
        AnchorPane.setBottomAnchor(prompt, 60.00);
        AnchorPane.setLeftAnchor(prompt, 110.00);
        playerArea.getChildren().addAll(prompt);
        this.setCenter(playerArea);
    }

    private void setUpButtons() {
        //Button 1
        buttonArray[0][0].setPrefSize(80,80);
        AnchorPane.setTopAnchor(buttonArray[0][0], 40.00);
        AnchorPane.setLeftAnchor(buttonArray[0][0], 60.00);
        playerArea.getChildren().addAll(buttonArray[0][0]);
        //Button 2
        buttonArray[0][1].setPrefSize(80,80);
        AnchorPane.setTopAnchor(buttonArray[0][1], 40.00);
        AnchorPane.setLeftAnchor(buttonArray[0][1],  2 * 60.00 + 30);
        playerArea.getChildren().addAll(buttonArray[0][1]);
        //Button 3
        buttonArray[0][2].setPrefSize(80,80);
        AnchorPane.setTopAnchor(buttonArray[0][2], 40.00);
        AnchorPane.setLeftAnchor(buttonArray[0][2],  (3 * 60.00 + 60));
        playerArea.getChildren().addAll(buttonArray[0][2]);
        //Button 4
        buttonArray[1][0].setPrefSize(80,80);
        AnchorPane.setTopAnchor(buttonArray[1][0], 3 * 40.00 + 10);
        AnchorPane.setLeftAnchor(buttonArray[1][0], 60.00);
        playerArea.getChildren().addAll(buttonArray[1][0]);
        //Button 5
        buttonArray[1][1].setPrefSize(80,80);
        AnchorPane.setTopAnchor(buttonArray[1][1], 3 * 40.00 + 10);
        AnchorPane.setLeftAnchor(buttonArray[1][1],  2 * 60.00 + 30);
        playerArea.getChildren().addAll(buttonArray[1][1]);
        //Button 6
        buttonArray[1][2].setPrefSize(80,80);
        AnchorPane.setTopAnchor(buttonArray[1][2], 3 * 40.00 + 10);
        AnchorPane.setLeftAnchor(buttonArray[1][2],  (3 * 60.00 + 60));
        playerArea.getChildren().addAll(buttonArray[1][2]);
        //Button 7
        buttonArray[2][0].setPrefSize(80,80);
        AnchorPane.setTopAnchor(buttonArray[2][0], 5 * 40.00 + 20);
        AnchorPane.setLeftAnchor(buttonArray[2][0], 60.00);
        playerArea.getChildren().addAll(buttonArray[2][0]);
        //Button 8
        buttonArray[2][1].setPrefSize(80,80);
        AnchorPane.setTopAnchor(buttonArray[2][1], 5 * 40.00 + 20);
        AnchorPane.setLeftAnchor(buttonArray[2][1],  2 * 60.00 + 30);
        playerArea.getChildren().addAll(buttonArray[2][1]);
        //Button 9
        buttonArray[2][2].setPrefSize(80,80);
        AnchorPane.setTopAnchor(buttonArray[2][2], 5 * 40.00 + 20);
        AnchorPane.setLeftAnchor(buttonArray[2][2],  (3 * 60.00 + 60));
        playerArea.getChildren().addAll(buttonArray[2][2]);
    }

    private class buttonListener implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent arg0){
            if (theGame.stillRunning()){
                prompt.setText("");
                Button buttonClicked = (Button) arg0.getSource();
                for (int i = 0; i < buttonArray.length; i++){
                    for (int j = 0; j < buttonArray.length; j++){
                        if (buttonArray[i][j] == buttonClicked){
                            //buttonArray[i][j].setText("X");
                            if (!theGame.available(i,j)){
                                System.out.println("     Invalid Move");
                                prompt.setText("     Invalid Move!");
                            }
                            theGame.humanMove(i, j, false);
                            update(theGame);}}}}}
    }

    @Override
    public void update(Object theObserved) {
        //System.out.println("update called from OurObservable TicTacToeGame \n" + theGame);
        for (int i = 0; i < buttonArray.length; i++){
            for (int j = 0; j < buttonArray.length; j++){
                char characterForEachButton = theGame.getTicTacToeBoard()[i][j];
                buttonArray[i][j].setText("" + characterForEachButton);}}

        if (!theGame.stillRunning()){
            System.out.println("Game over!");
            if (theGame.didWin('X'))
                prompt.setText("     X wins");
            else if (theGame.didWin('O'))
                prompt.setText("O wins");
            else if (theGame.tied())
                prompt.setText("     Tie");}
    }
}
