package Application.Controller;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.concurrent.Callable;

import Application.Enum.Direction;
import Application.Enum.SnakePart;
import Application.SingletonAndTemplate.*;
import Application.Snake.DirectionController;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.util.Duration;
public class GameOneController{
  private int windowWidth = 600;
  //private int windowHeight = 600;
  private int GridWidth = 20;
  private int time = 170;
  private double rate = 1.0;
  private String Username;
  private int score = 0;
  private SnakeBodyPlayer snakeBodyPlayer1;
  private DirectionController directionController;
  private Timeline checkScoreTimeline;
  private InputController input;
  private int record;
  @FXML private AnchorPane GameTable;
  @FXML private Label ScoreText;
  @FXML private Label AlertText;
  @FXML private Label UserName;
  @FXML private Label RecordS;
  public void init() {
    MusicController.PlayBackground1();
    DrawLine();
    setAlertText("TAP ENTER TO START NEW GAME","Normal");
    RecordS.setText("Record : ");
    GameCurrentChildrenArray.Instance.set(GameTable.getChildren());
    directionController = new DirectionController();
    DrawLine();
    RecordS.setText("Record : ");
    try {
      CheckScoreRecord(score);
      RecordS.setText("Record : " + record);
    } catch (IOException e1) {
      e1.printStackTrace();
    }
    ScoreText.setText("Score : 0");
    snakeBodyPlayer1=new SnakeBodyPlayer(
      directionController, 
      time, 
      new Callable<Boolean>(){
        @Override
        public Boolean call() throws Exception {
          SnakeBody tem=snakeBodyPlayer1.getSnakeBody();
          if(tem.woody!=0)return false;
          if(tem.whatPart(tem.GetHead()).contains(SnakePart.BODY)){
            GameOver();
            return true;
          }
          return false;
        }
      }
    );
    checkScoreTimeline=new Timeline(new KeyFrame(Duration.millis(10),e->{
      if(score!=snakeBodyPlayer1.getSnakeBody().score)ChangedScore();
    }));
    checkScoreTimeline.setCycleCount(Timeline.INDEFINITE);
    checkScoreTimeline.play();
    input = new InputController(AlertText,snakeBodyPlayer1,directionController);
  }
  // Game flow
  public void StartGame(){
    snakeBodyPlayer1.SetSnakeBody(new SnakeBody(HomeController.Player1, time, 300, 300));
    directionController.init(Direction.RIGHT);
    input.SetNewGame(false);
    AlertText.setText("");
    score = 0;
    ScoreText.setText("Score : " + score);
    FoodGenerator.RefreshFood();
  }
  /// initializable method
  public void DrawLine() {
    for (int i = 0; i <= windowWidth; i += GridWidth) {
      Line rows = new Line(0, i, windowWidth, i);
      Line cols = new Line(i, 0, i, windowWidth);
      rows.setStroke(Color.web("#D6D6AD"));
      cols.setStroke(Color.web("#D6D6AD"));
      rows.setStrokeWidth(0.3);
      cols.setStrokeWidth(0.3);
      GameTable.getChildren().add(rows);
      GameTable.getChildren().add(cols);
    }
  }
  public void CheckScoreRecord(int CurrentScore) throws IOException{
      File Score = new File("src/Application/RecordScore.txt");
      Score.createNewFile();
      FileReader ScoreReader = new FileReader(Score);
      BufferedReader br = new BufferedReader(ScoreReader);
      record = Integer.valueOf(br.readLine());
      br.close();
      if(CurrentScore > record){
        FileWriter ScoreWriter = new FileWriter(Score);
        ScoreWriter.write(Integer.toString(CurrentScore));
        ScoreWriter.flush();
        ScoreWriter.close();
        record = CurrentScore;
      }
  }
  // score chang / rate chang
  public void ChangedScore() {
    score=snakeBodyPlayer1.getSnakeBody().score;
    try {
      CheckScoreRecord(score);
    } catch (IOException e) {
      e.printStackTrace();
    }
    System.out.println(score / 10 + " " + rate);
    ScoreText.setText("Score : " + score);
    RecordS.setText("Record : "+ record);
  }
  //next game set
  public void GameOver() {
    snakeBodyPlayer1.getSnakeBody().clearOnScreen();
    rate = 1.0;
    FoodGenerator.getFood().clearOnScreen();
    setAlertText("Game Over\n\nTAP ENTER TO START NEW GAME", "Alert");
    MusicController.GameOverSound();
    input.SetNewGame(true);

  }
  public void GetPinName(String name){
    String DefaultName = "JACK";
    this.Username = name;
    if(Username.length() != 0) UserName.setText(Username);
    else UserName.setText(DefaultName);
  }
  
  public void setAlertText(String text, String Id) {
    GameTable.getChildren().remove(AlertText);
    AlertText.setText(text);
    AlertText.setAlignment(Pos.CENTER);
    AlertText.setId(Id);
    GameTable.getChildren().add(AlertText);
  }

  public void KeyEven(KeyEvent event) throws IOException{
    if(input.GameOneFlow(event)){
      StartGame();
    }
  }
}
