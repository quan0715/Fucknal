package Application;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
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
public class GameOneController implements Initializable {
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
  private SnakeBody<? extends Snake> snake1;
  private int record;
  @FXML private AnchorPane GameTable;
  @FXML private Label ScoreText;
  @FXML private Label AlertText;
  @FXML private Label UserName;
  @FXML private Label RecordS;
  @Override
  public void initialize(URL q, ResourceBundle p) {
    DrawLine();
    setAlertText("Tap  ENTER  to start new game", Color.WHITE);
    RecordS.setText("Record : ");
    GameCurrentChildrenArray.Instance.set(GameTable.getChildren());
    snake1 = new SnakeBody<IGSnake>(new IGSnake());
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
    snake1 = new SnakeBody<IGSnake>(new IGSnake());
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
      
      GameTable.getChildren().add(rows);
      GameTable.getChildren().add(cols);
    }
  }

  //moving event
  public boolean SnakeRun(Direction direction) throws Exception {
    if (snake1.SnakeMoving(direction,apple)) {
      foodGenerator.RefreshFood();
      ChangedScore();
    }
    return snake1.CheckGameOver();
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
    setAlertText("Game Over\n(Tap Enter to start a new game)", Color.web("#d82909"));
    CanPlayNewGame = true;
  }
  //get button click or not
  public boolean NewGame() {
    return CanPlayNewGame;
  }
  public void GetPinName(String name){
    String DefaultName = "JACK";
    this.Username = name;
    if(Username.length() != 0) UserName.setText(Username);
    else UserName.setText(DefaultName);
  }
  
  public void setAlertText(String text, Color color) {
    GameTable.getChildren().remove(AlertText);
    AlertText.setText(text);
    AlertText.setAlignment(Pos.CENTER);
    AlertText.setTextFill(color);
    GameTable.getChildren().add(AlertText);
  }

  public void KeyEven(KeyEvent event) throws IOException{
    KeyCode key = event.getCode();
    if (key == KeyCode.H) {
      move.stop();
      BackToHomePage(event);
    }
    if (key == KeyCode.ENTER && NewGame()) StartGame();
      if (key == KeyCode.SPACE && !PauseGame) {
      move.pause();
      setAlertText("Tap Space --> continue the game\nTap H --> return Home Page", Color.WHITE);
      PauseGame = true;
    } 
    else if (key == KeyCode.SPACE && PauseGame) {
      move.play();
      setAlertText("", Color.BLACK);
      PauseGame = false;
    }
    // snake1
    if (!NewGame() && !PauseGame) {
      directionController.Direction2(event);
    }
  }
  
  public void BackToHomePage(KeyEvent e) throws IOException {
    FXMLLoader loader = new FXMLLoader(getClass().getResource("./Scene/Home.fxml"));
    Parent root = loader.load();
    App.stage.setScene(new Scene(root));
  }
}
