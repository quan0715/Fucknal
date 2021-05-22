package Application.Controller;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import Application.App;
import Application.Enum.Direction;
import Application.Enum.SnakePart;
import Application.Food.Food;
import Application.Food.NormalFood;
import Application.Singleton.FoodGenerator;
import Application.Singleton.GameCurrentChildrenArray;
import Application.Singleton.MusicController;
import Application.Snake.DirectionController;
import Application.Snake.SnakeBody;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.util.Duration;
public class GameOneController{
  private int windowWidth = 600;
  //private int windowHeight = 600;
  private int GridWidth = 20;
  private int time = 150;
  private Timeline move;
  private double rate = 1.0;
  private String Username;
  private Food apple;
  private int score = 0;
  private boolean CanPlayNewGame = true;
  private boolean PauseGame = false;
  private FoodGenerator foodGenerator;
  private DirectionController directionController;
  private SnakeBody snake1;
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
    apple = new NormalFood();
    foodGenerator = new FoodGenerator((NormalFood)apple);
    DrawLine();
    RecordS.setText("Record : ");
    try {
      CheckScoreRecord(score);
      RecordS.setText("Record : " + record);
    } catch (IOException e1) {
      e1.printStackTrace();
    }
    ScoreText.setText("Score : 0");
    move = new Timeline(new KeyFrame(Duration.millis(time), (e) -> {
      try {
        if (SnakeRun(directionController.NextDirection())) {
          move.stop();
          GameOver();
        }
      } catch (Exception e1) {
        e1.printStackTrace();
      }
    }));
  }
  // Game flow
  public void StartGame(){
    snake1 = new SnakeBody(HomeController.Player1, 300, 300);
    directionController.init(Direction.RIGHT);
    CanPlayNewGame = false;
    foodGenerator.RefreshFood();
    AlertText.setText("");
    score = 0;
    ScoreText.setText("Score : " + score);
    move.setCycleCount(Animation.INDEFINITE);
    move.play();
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

  //moving event
  public boolean SnakeRun(Direction direction){
    snake1.Move(direction);
    if(snake1.whatPart(apple.GetFoodPosition())==SnakePart.HEAD){
      snake1.AddNewBody();
      foodGenerator.RefreshFood();
      MusicController.EatFoodPop();
      ChangedScore();
    }
    if(snake1.whatPart(snake1.GetHead())==SnakePart.BODY)return true;
    return false;
  }
  public void CheckScoreRecord(int CurrentScore) throws IOException{
      File Score = new File("../RecordScore.txt");
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
    score += 10;
    rate = rate+(4-rate)*0.03;
    try {
      CheckScoreRecord(score);
    } catch (IOException e) {
      e.printStackTrace();
    }
    move.setRate(rate);
    System.out.println(score / 10 + " " + rate);
    ScoreText.setText("Score : " + score);
    RecordS.setText("Record : "+ record);
  }
  //next game set
  public void GameOver() {
    snake1.clearOnScreen();
    rate = 1.0;
    move.setRate(rate);
    GameTable.getChildren().remove(apple.GetFoodBody());
    setAlertText("Game Over\nTAP ENTER TO START NEW GAME", "Alert");
    MusicController.GameOverSound();
    CanPlayNewGame = true;
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
    KeyCode key = event.getCode();
    if (key == KeyCode.H) {
      move.stop();
      BackToHomePage(event);
    }
    if (key == KeyCode.ENTER && CanPlayNewGame) StartGame();
      if (key == KeyCode.SPACE && !PauseGame && !CanPlayNewGame) {
      move.pause();
      setAlertText("TAP SPACE --> CONTINUE THE GAME\n\nTAP H --> RETURN HOME PAGE", "Normal");
      PauseGame = true;
    } 
    else if (key == KeyCode.SPACE && PauseGame && !CanPlayNewGame) {
      move.play();
      setAlertText("", "Normal");
      PauseGame = false;
    }
    // snake1
    if (!CanPlayNewGame && !PauseGame) {
      directionController.Direction2(event);
    }
  }
  
  public void BackToHomePage(KeyEvent e) throws IOException {
    MusicController.StopBackground1();
    FXMLLoader loader = new FXMLLoader(getClass().getResource("../Scene/Home.fxml"));
    Parent root = loader.load();
    App.stage.setScene(new Scene(root));
  }
}
