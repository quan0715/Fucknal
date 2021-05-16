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
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.util.Duration;

public class GameTwoController implements Initializable {
  private int windowWidth = 600;
  private int windowHeight = 600;
  private int GridWidth = 20;
  private int time = 150;
  private Timeline move;
  private double rate = 1.0;
  private String Username;
  //private int score = 0;
  private boolean CanPlayNewGame = true;
  private SnakeBody<ClassicSnake> snake1;
  private SnakeBody<ClassicSnake> snake2;
  DirectionController directionController1;
  DirectionController directionController2;
  private Food apple;
  private FoodGenerator foodGenerator;
  
  @FXML  private AnchorPane GameTable;
  @FXML  private Label ScoreText;
  @FXML  private Label AlertText;
  @FXML  private Label UserName;
  @FXML  private Label RecordS;
  @Override
  public void initialize(URL q, ResourceBundle p) {
    DrawLine();
    directionController1 = new DirectionController();
    directionController2 = new DirectionController();
    snake1 = new SnakeBody<ClassicSnake>(Color.WHITE);
    snake2 = new SnakeBody<ClassicSnake>(Color.BLACK);
    apple = new NormalFood();
    foodGenerator = new FoodGenerator(GameTable,(NormalFood)apple);
    move = new Timeline(new KeyFrame(Duration.millis(time), (e) -> {
      SnakeRun(directionController1.NextDirection(),directionController2.NextDirection());
    }));
  }

  // Game flow
  public void StartGame() {
    CanPlayNewGame = false;
    directionController1.init(Direction.UP);
    directionController2.init(Direction.DOWN);
    snake1.init((Class<ClassicSnake>)(new ClassicSnake()).getClass());
    snake2.init((Class<ClassicSnake>)(new ClassicSnake()).getClass());
    foodGenerator.RefreshFood();
    AlertText.setText("");
    rate = 1.0;
    move.setRate(rate);
    //score = 0;
    //ScoreText.setText("Score : " + score);
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

  // moving event
  public void SnakeRun(Direction direction1, Direction direction2) {
    for (ClassicSnake snake : snake1.getBody())
      GameTable.getChildren().remove(snake.GetBody());
    for (Snake snake : snake2.getBody())
      GameTable.getChildren().remove(snake.GetBody());    
    try {
      if (snake1.SnakeMoving(direction1, apple) || snake2.SnakeMoving(direction2, apple)) {
        // snake1.ChangHead(apple.GetFoodPosition());
        foodGenerator.RefreshFood();
        ChangedScore();
      }
    } catch (Exception e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    for (Snake snake : snake1.getBody())
      GameTable.getChildren().add(snake.GetBody());
    for (Snake snake : snake2.getBody())
      GameTable.getChildren().add(snake.GetBody());
    //return snake1.CheckGameOver();
  }

  public void CheckScoreRecord(int CurrentScore) throws IOException {
    File Score = new File("src/Application/RecordScore.txt");
    Score.createNewFile();
    FileReader ScoreReader = new FileReader(Score);
    BufferedReader br = new BufferedReader(ScoreReader);
    /*
    record = Integer.valueOf(br.readLine());
    br.close();
    if (CurrentScore > record) {
      FileWriter ScoreWriter = new FileWriter(Score);
      ScoreWriter.write(Integer.toString(CurrentScore));
      ScoreWriter.flush();
      ScoreWriter.close();
      record = CurrentScore;
    }
    */
  }

  // score chang / rate chang
  public void ChangedScore() {
    //score += 10;
    rate = rate + (4 - rate) * 0.03;
    /*
    try {
      CheckScoreRecord(score);
    } catch (IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    */
    move.setRate(rate);
    //System.out.println(score / 10 + " " + rate);
    //ScoreText.setText("Score : " + score);
    //RecordS.setText("Record : " + record);
  }

  // next game set
  public void GameOver() {
    for (Snake snake : snake1.getBody())
      GameTable.getChildren().remove(snake.GetBody());
    GameTable.getChildren().remove(apple.GetFoodBody());
    GameTable.getChildren().remove(AlertText);
    AlertText.setText("Game Over\n(Tap Enter to start a new game)");
    AlertText.setAlignment(Pos.CENTER);
    AlertText.setTextFill(Color.RED);
    GameTable.getChildren().add(AlertText);
    CanPlayNewGame = true;
  }

  // get button click or not
  public boolean NewGame() {
    return CanPlayNewGame;
  }

  public void GetPinName(String name) {
    String DefaultName = "JACK";
    this.Username = name;
    if (Username.length() != 0)
      UserName.setText(Username);
    else
      UserName.setText(DefaultName);
  }

  public void KeyEven(KeyEvent event) {
    KeyCode key = event.getCode();
    if (key == KeyCode.ENTER && NewGame()) StartGame();
    //snake1
    directionController1.Direction1(event);
    directionController2.Direction2(event);
  }
}
