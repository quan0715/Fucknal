package Application;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.LinkedList;
import java.util.Queue;
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
  private Food apple;
  //private int score = 0;
  private boolean CanPlayNewGame = true;
  private SnakeBody snake1;
  private SnakeBody snake2;
  private Queue<Direction> direct1 ;
  private Queue<Direction> direct2 ;
  private Direction LastDirection1;
  private Direction LastDirection2;
  //private int record;
  @FXML
  private AnchorPane GameTable;
  @FXML
  private Label ScoreText;
  @FXML
  private Label AlertText;
  @FXML
  private Label UserName;
  @FXML
  private Label RecordS;

  @Override
  public void initialize(URL q, ResourceBundle p) {
    DrawLine();
    //RecordS.setText("Record : ");
    direct1 = new LinkedList<Direction>();
    direct2 = new LinkedList<Direction>();
    apple = new Food();
    snake1 = new ClassicSnakeBody();
    snake2 = new ClassicSnakeBody();
    LastDirection1 = Direction.UP;
    LastDirection2 = Direction.DOWN;
    direct1.add(LastDirection1);
    direct2.add(LastDirection2);
    /*
    try {
      CheckScoreRecord(score);
      RecordS.setText("Record : " + record);
    } catch (IOException e1) {
      // TODO Auto-generated catch block
      e1.printStackTrace();
    }
    ScoreText.setText("Score : 0");
    */
    move = new Timeline(new KeyFrame(Duration.millis(time), (e) -> {
      // ChangDirection = false;
      SnakeRun((direct1.size() == 1 ? direct1.peek() : direct1.poll()),
          (direct2.size() == 1 ? direct2.peek() : direct2.poll()));
    }));
  }

  // Game flow
  public void StartGame() {
    CanPlayNewGame = false;
    if(direct1.size() != 0){
      direct1.clear();
    }
    if(direct2.size() != 0){
      direct2.clear();
    }
    LastDirection1 = Direction.UP;
    LastDirection2 = Direction.DOWN;
    direct1.add(LastDirection1);
    direct2.add(LastDirection2);
    snake1.SetSnakeColor(Color.web("D2D8B3"));
    snake2.SetSnakeColor(Color.web("90A987"));
    snake1.init();
    snake2.init();
    NewFood(apple);
    AlertText.setText("");
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

  public void NewFood(Food apple) {
    GameTable.getChildren().remove(apple.GetFoodBody());
    apple.init();
    GameTable.getChildren().add(apple.GetFoodBody());
  }

  // moving event
  public void SnakeRun(Direction direction1, Direction direction2) {
    for (Snake snake : snake1.getBody())
      GameTable.getChildren().remove(snake.GetBody());
    for (Snake snake : snake2.getBody())
      GameTable.getChildren().remove(snake.GetBody());    
    if (snake1.SnakeMoving(direction1, apple) || snake2.SnakeMoving(direction2, apple)) {
      // snake1.ChangHead(apple.GetFoodPosition());
      NewFood(apple);
      ChangedScore();
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
    rate = 1.0;
    move.setRate(rate);
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
    if (key.equals(KeyCode.ENTER) && NewGame()) StartGame();
    //snake1
    if (key.equals(KeyCode.W) && !LastDirection1.equals(Direction.UP) && !LastDirection1.equals(Direction.DOWN)) {
      System.out.println("W");
      LastDirection1 = Direction.UP;
      direct1.offer(Direction.UP);
    } 
    else if (key.equals(KeyCode.S) && !LastDirection1.equals(Direction.UP) && !LastDirection1.equals(Direction.DOWN)) {
      System.out.println("S");
      LastDirection1 = Direction.DOWN;
      direct1.offer(Direction.DOWN);
    } 
    else if (key.equals(KeyCode.A) && !LastDirection1.equals(Direction.RIGHT) && !LastDirection1.equals(Direction.LEFT)) {
      System.out.println("A");
      LastDirection1 = Direction.LEFT;
      direct1.offer(Direction.LEFT);
    } 
    else if (key.equals(KeyCode.D) && !LastDirection1.equals(Direction.RIGHT) && !LastDirection1.equals(Direction.LEFT)) {
      System.out.println("D");
      LastDirection1 = Direction.RIGHT;
      direct1.offer(Direction.RIGHT);
    }
    // snake2
    if (key.equals(KeyCode.UP) && !LastDirection2.equals(Direction.DOWN) && !LastDirection2.equals(Direction.UP)) {
      LastDirection2 = Direction.UP;
      System.out.println("UP");
      direct2.offer(Direction.UP);
    } 
    else if (key.equals(KeyCode.DOWN) && !LastDirection2.equals(Direction.UP) && !LastDirection2.equals(Direction.DOWN)) {
      System.out.println("DOWN");
      LastDirection2 = Direction.DOWN;
      direct2.offer(Direction.DOWN);
    } 
    else if (key.equals(KeyCode.RIGHT) && !LastDirection2.equals(Direction.LEFT) && !LastDirection2.equals(Direction.RIGHT)) {
      System.out.println("RIGHT");
      LastDirection2 = Direction.RIGHT;
      direct2.offer(Direction.RIGHT);
    } 
    else if (key.equals(KeyCode.LEFT) && !LastDirection2.equals(Direction.RIGHT) && !LastDirection2.equals(Direction.LEFT)) {
      System.out.println("LEFT");
      LastDirection2 = Direction.LEFT;
      direct2.offer(Direction.LEFT);
    }
  }
}
