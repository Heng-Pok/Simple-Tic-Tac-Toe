package views_controllers;

/**
 * Play TicTacToe the computer that can have different AIs to beat you. 
 * Select the Options menus to begin a new game, switch strategies for 
 * the computer player (BOT or AI), and to switch between the two views.
 * 
 * This class represents an event-driven program with a graphical user 
 * interface as a controller between the view and the model. It has 
 * event handlers to mediate between the view and the model.
 * 
 * This controller employs the Observer design pattern that updates two 
 * views every time the state of the Tic Tac Toe game changes:
 * 
 *  1) whenever you make a move by clicking a button or an area of either view
 *  2) whenever the computer AI makes a move
 *  3) whenever there is a win or a tie
 *    
 * You can also select two different strategies to play against from the menus
 * 
 * @author Rick Mercer and HENGSOCHEAT POK
 */
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import model.*;

public class TicTacToeGUI extends Application {

  public static void main(String[] args) {
    launch(args);
  }

  private TicTacToeGame theGame;
  private OurObserver currentView;
  private OurObserver textAreaView;
  private OurObserver drawingView;
  private OurObserver buttonView;
  private BorderPane window;
  private MenuBar menuBar;
  private Menu options;
  private Menu viewOptions;
  private Menu difficultyOptions;
  private MenuItem newGameOption;
  private MenuItem textViewOption;
  private MenuItem buttonViewOption;
  private MenuItem drawingViewOption;
  private MenuItem randomAIDifficulty;
  private MenuItem intermediateAIDifficulty;


  public static final int width = 400;
  public static final int height = 500;

  public void start(Stage stage) {
    stage.setTitle("Tic Tac Toe");
    window = new BorderPane();
    Scene scene = new Scene(window, width, height);
    initializeGameForTheFirstTime();
    textAreaView = new TextAreaView(theGame);
    buttonView = new ButtonView(theGame);
    drawingView = new DrawingView(theGame);
    menuBar = new MenuBar();
    options = new Menu("Options");
    newGameOption = new MenuItem("New Game");
    newGameOption.setOnAction(e -> resetTheGame(stage));
    options.getItems().add(newGameOption);                                         // add the 'New Game' option to the menu
    setUpMenu(menuBar);
    theGame.addObserver(textAreaView);
    theGame.addObserver(buttonView);
    theGame.addObserver(drawingView);
    setViewTo(textAreaView);
    stage.setScene(scene);
    stage.setMinWidth(400);
    stage.setMinHeight(500);
    stage.setResizable(false);
    stage.show();
  }

  /**
   * Set the game to the default of an empty board and the random AI.
   */
  public void initializeGameForTheFirstTime() {
    theGame = new TicTacToeGame();
    // This event driven program will always have
    // a computer player who takes the second turn
    theGame.setComputerPlayerStrategy(new RandomAI());
  }

  private void setViewTo(OurObserver newView) {
    window.setCenter(null);
    currentView = newView;
    window.setCenter((Node)currentView);
    window.setTop(menuBar);
  }

  private void setUpMenu(MenuBar menuBar) {

    viewOptions = new Menu("View");
    difficultyOptions = new Menu("Difficulty");

    randomAIDifficulty = new MenuItem("Easy (Random AI) (selected)");
    intermediateAIDifficulty = new MenuItem("Normal (Intermediate AI)");
    textViewOption = new MenuItem("Text Area View (selected)");
    buttonViewOption = new MenuItem("Button View");
    drawingViewOption = new MenuItem("Drawing View");

    randomAIDifficulty.setOnAction(new randomAIOptionListener());                 // register a listener for the random AI option
    intermediateAIDifficulty.setOnAction(new intermediateAIOptionListener());     // register a listener for the intermediate AI option
    buttonViewOption.setOnAction(new buttonViewOptionListener());                 // similar
    textViewOption.setOnAction(new textViewOptionListener());                     // similar
    drawingViewOption.setOnAction(new drawingViewOptionListener());                // similar

    difficultyOptions.getItems().add(randomAIDifficulty);                          // add the random AI option to the list of options
    difficultyOptions.getItems().add(intermediateAIDifficulty);                    // add the intermediate AI option to the list of options
    viewOptions.getItems().add(textViewOption);                                    // similar
    viewOptions.getItems().add(buttonViewOption);                                   // similar
    viewOptions.getItems().add(drawingViewOption);                                  // similar

    options.getItems().add(difficultyOptions);                                     // add the 'change difficulty' option to the menu
    options.getItems().add(viewOptions);                                           // add the option to change view to the menu
    menuBar.getMenus().add(options);                                               // add the menu to the top menu bar.
  }

  private class randomAIOptionListener implements EventHandler<ActionEvent> {
    @Override
    public void handle(ActionEvent actionEvent) {
      theGame.setComputerPlayerStrategy(new RandomAI());
      randomAIDifficulty.setText("Easy (Random AI) (selected)");
      intermediateAIDifficulty.setText("Normal (Random AI)");

    }
  }

  private class intermediateAIOptionListener implements EventHandler<ActionEvent> {
    @Override
    public void handle(ActionEvent actionEvent) {
      theGame.setComputerPlayerStrategy(new IntermediateAI());
      intermediateAIDifficulty.setText("Normal (Random AI) (selected)");
      randomAIDifficulty.setText("Easy (Random AI)");
    }
  }

  private class buttonViewOptionListener implements EventHandler<ActionEvent> {
    @Override
    public void handle(ActionEvent actionEvent) {
      System.out.println("switched to button view!");
      textViewOption.setText("Text Area View");
      drawingViewOption.setText("Drawing View");
      buttonViewOption.setText("Button View (selected)");
      buttonView.update(theGame);
      textAreaView.update(theGame);
      setViewTo(buttonView);
    }
  }

  private class textViewOptionListener implements EventHandler<ActionEvent> {
    @Override
    public void handle(ActionEvent actionEvent) {
      System.out.println("switched to text view!");
      textViewOption.setText("Text Area View (selected)");
      drawingViewOption.setText("Drawing View");
      buttonViewOption.setText("Button View");
      buttonView.update(theGame);
      textAreaView.update(theGame);
      setViewTo(textAreaView);
    }
  }

  private class drawingViewOptionListener implements EventHandler<ActionEvent> {
    @Override
    public void handle(ActionEvent actionEvent) {
      System.out.println("switched to drawing view!");
      textViewOption.setText("Text Area View");
      drawingViewOption.setText("Drawing View (selected)");
      buttonViewOption.setText("Button View");
      buttonView.update(theGame);
      textAreaView.update(theGame);
      setViewTo(drawingView);
    }
  }

  private void resetTheGame(Stage stage) {
    OurObserver previousView = currentView;
    boolean isTextAreaView = previousView.equals(textAreaView);
    boolean isButtonView = previousView.equals(buttonView);
    boolean isDrawingView = previousView.equals(drawingView);
    boolean isRandom = randomAIDifficulty.getText().equals("Easy (Random AI) (selected)");

    start(stage);

    if (isTextAreaView) {
      textViewOption.setText("Text Area View (selected)");
      drawingViewOption.setText("Drawing View");
      buttonViewOption.setText("Button View");
      setViewTo(textAreaView);}
    else if (isButtonView) {
      textViewOption.setText("Text Area View");
      drawingViewOption.setText("Drawing View");
      buttonViewOption.setText("Button View (selected)");
      setViewTo(buttonView);}
    else if (isDrawingView) {
      textViewOption.setText("Text Area View");
      drawingViewOption.setText("Drawing View (selected)");
      buttonViewOption.setText("Button View");
      setViewTo(drawingView);}

    if (isRandom){
      theGame.setComputerPlayerStrategy(new RandomAI());
      intermediateAIDifficulty.setText("Normal (Intermediate AI)");
      randomAIDifficulty.setText("Easy (Random AI) (selected)");}
    else{
      theGame.setComputerPlayerStrategy(new IntermediateAI());
      randomAIDifficulty.setText("Easy (Random AI)");
      intermediateAIDifficulty.setText("Normal (Intermediate AI) (selected)");}
    }

}