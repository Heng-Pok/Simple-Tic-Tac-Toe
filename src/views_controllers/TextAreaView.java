package views_controllers;

/**
 * This is the beginning of one view of a Tic Tac Toe game using
 * two TextField objects and one TextArea. The other two views
 * of ButtonView and DrawingView follow the same structure as this.
 * 
 * @author Rick Mercer and HENGSOCHEAT POK
 */
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.scene.text.*;
import model.OurObserver;
import model.TicTacToeGame;

public class TextAreaView extends BorderPane implements OurObserver {

  private TicTacToeGame theGame;
  private TextFlow theMainPlayGround;
  private Text gameBoardText;
  private AnchorPane playerArea;
  private TextField rowOfChoice;
  private TextField columnOfChoice;
  private Button confirmButton;
  private Label difficultyLabel;
  
  public TextAreaView(TicTacToeGame theModel) {
    theGame = theModel;
    initializePanel();
  }

  private void initializePanel() {
    theMainPlayGround = new TextFlow();         // the main display of the game (a table consisting of Xs and Os).
    rowOfChoice = new TextField();              // where we type in the row number
    columnOfChoice = new TextField();           // where we type in the column number
    confirmButton = new Button("Make Move"); // where we press to confirm the coordinates.
    playerArea = new AnchorPane();              // the place where player interacts with the GUI
    playerArea.setBackground(Background.fill(Color.LIGHTGRAY));

    // set up texts that show the gameplay and the status of it.
    gameBoardText = new Text("\n\n" + theGame + "\n");
    gameBoardText.setFont(new Font("Courier New", 32));


    // design the player's area (the place where player interacts with the GUI)
    Label rowLabel = new Label("row");
    Label colLabel = new Label("column");
    difficultyLabel = new Label();
    difficultyLabel.setFont(new Font("Courier", 12));
    rowOfChoice.setMaxWidth(50);
    columnOfChoice.setMaxWidth(50);

    AnchorPane.setTopAnchor(rowOfChoice, 40.00);
    AnchorPane.setLeftAnchor(rowOfChoice, 130.00);

    AnchorPane.setTopAnchor(rowLabel, 45.00);
    AnchorPane.setRightAnchor(rowLabel, 170.00);

    AnchorPane.setTopAnchor(colLabel, 85.00);
    AnchorPane.setRightAnchor(colLabel, 150.00);

    AnchorPane.setTopAnchor(columnOfChoice, 80.00);
    AnchorPane.setLeftAnchor(columnOfChoice, 130.00);

    AnchorPane.setTopAnchor(confirmButton, 120.00);
    AnchorPane.setLeftAnchor(confirmButton, 130.00);

    AnchorPane.setTopAnchor(difficultyLabel, 160.00);
    AnchorPane.setLeftAnchor(difficultyLabel, 130.00);

    // set action for the confirmation button
    confirmButton.setOnAction(new confirmButtonListener());

    playerArea.getChildren().addAll(rowOfChoice);
    playerArea.getChildren().addAll(rowLabel);
    playerArea.getChildren().addAll(colLabel);
    playerArea.getChildren().addAll(columnOfChoice);
    playerArea.getChildren().addAll(confirmButton);
    playerArea.getChildren().addAll(difficultyLabel);
    playerArea.setMinHeight(190);

    // add the texts representing the gameplay to the view
    theMainPlayGround.setStyle("-fx-border-color: blue; border-width: 10;");
    theMainPlayGround.getChildren().add(gameBoardText);
    theMainPlayGround.setTextAlignment(TextAlignment.CENTER);
    theMainPlayGround.setMinHeight(300);

    this.setCenter(playerArea);
    this.getCenter().minHeight(190);
    this.setBottom(theMainPlayGround);
    this.getBottom().minHeight(300);
  }

  // This method is called by Observable's notifyObservers()
  @Override
  public void update(Object observable) {
    //System.out.println("update called from OurObservable TicTacToeGame \n" + theGame);
    if (theGame.stillRunning())
      gameBoardText.setText("\n\n" + theGame.toString() + "\n");
    else{
      System.out.println("game over!");
      gameBoardText.setText("\n\n" + theGame.toString() + "\n");
      if (theGame.didWin('X'))
        confirmButton.setText("X wins");
      else if (theGame.didWin('O'))
        confirmButton.setText("O wins");
      else if (theGame.tied())
        confirmButton.setText("Tie");}
  }

  private class confirmButtonListener implements EventHandler<ActionEvent> {
    @Override
    public void handle(ActionEvent actionEvent) {
      if (theGame.stillRunning()){
        String textRowEntered = rowOfChoice.getText();
        String textColumnEntered = columnOfChoice.getText();
        if ((textRowEntered.equals("0") || textRowEntered.equals("1") || textRowEntered.equals("2")) &&
                (textColumnEntered.equals("0") || textColumnEntered.equals("1") || textColumnEntered.equals("2"))){
          int rowToBeUsed = Integer.parseInt(textRowEntered);
          int colToBeUsed = Integer.parseInt(textColumnEntered);
          if (!theGame.available(rowToBeUsed, colToBeUsed)){
            confirmButton.setText("Invalid Move");
            System.out.println("Invalid Move!");}
          else{
            theGame.humanMove(rowToBeUsed, colToBeUsed, false);
            update(theGame);

          }}
        else
          confirmButton.setText("Invalid Move");}
      }
  }

}