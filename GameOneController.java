package Application;
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
public class GameOneController implements Initializable {
  private int windowWidth = 600;
  private int windowHeight = 600;
  private int GridWidth = 20;
  private int time = 150;
  private Timeline move;
  private double rate = 1.0;
  private String Username;
  private Food apple;
  private int score = 0;
  private boolean CanPlayNewGame = true;
  private ClassicSnakeBody snake1;
  private Queue<Direction> direct;
  private Direction LastDirection;
  @FXML private AnchorPane GameTable;
  @FXML private Label ScoreText;
  @FXML private Label AlertText;
  @FXML private Label UserName;

  @Override
  public void initialize(URL q, ResourceBundle p) {
    DrawLine();
    direct = new LinkedList<Direction>();
    apple = new Food();
    snake1 = new ClassicSnakeBody();
    LastDirection =Direction.RIGHT;
    ScoreText.setText("Score : 0");
    move = new Timeline(new KeyFrame(Duration.millis(time), (e) -> {
      //ChangDirection = false;
      if (SnakeRun((direct.size() == 1 ? direct.peek() :direct.poll()))) {
        move.stop();
        GameOver();
      }
      //ChangDirection = true;
    }));
  }
  // Game flow
  public void StartGame() {
    CanPlayNewGame = false;
    direct.clear();
    LastDirection = Direction.RIGHT;
    direct.add(Direction.RIGHT);
    snake1.init();
    NewFood(apple);
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
  public void NewFood(Food apple) {
    GameTable.getChildren().remove(apple.GetFoodBody());
    apple.init();
    GameTable.getChildren().add(apple.GetFoodBody());
  }
  //moving event
  public boolean SnakeRun(Direction direction) {
    for (Snake snake : snake1.getBody()) GameTable.getChildren().remove(snake.GetBody());
    snake1.SnakeMoving(direction);
    if (snake1.CheckEating(apple)) {
      NewFood(apple);
      snake1.AddNewBody();
      ChangedScore();
    }
    for (Snake snake : snake1.getBody()) GameTable.getChildren().add(snake.GetBody());
    return snake1.CheckGameOver();
  }
  // score chang / rate chang
  public void ChangedScore() {
    score += 10;
    rate = rate+(4-rate)*0.03;
    move.setRate(rate);
    System.out.println(score / 10 + " " + rate);
    ScoreText.setText("Score : " + score);
  }
  //next game set
  public void GameOver() {
    for (Snake snake : snake1.getBody()) 
      GameTable.getChildren().remove(snake.GetBody());
    rate = 1.0;
    move.setRate(rate);
    GameTable.getChildren().remove(apple.GetFoodBody());
    AlertText.setText("Game Over\n(Tap Enter to start a new game)");
    AlertText.setAlignment(Pos.CENTER);
    AlertText.setTextFill(Color.RED);
    LastDirection = Direction.RIGHT;
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
  public void KeyEven(KeyEvent event){
    KeyCode key = event.getCode();
    if (key.equals(KeyCode.ENTER) && NewGame()) StartGame();
    if (key.equals(KeyCode.UP) && !LastDirection.equals(Direction.DOWN) ) {
      LastDirection = Direction.UP;
      System.out.println("UP");
      direct.offer(Direction.UP);
    }
    else if (key.equals(KeyCode.DOWN) && !LastDirection.equals(Direction.UP) ) {
      System.out.println("DOWN");
      LastDirection = Direction.DOWN;
        direct.offer(Direction.DOWN);
    }
    else if (key.equals(KeyCode.RIGHT) && !LastDirection.equals(Direction.LEFT)) {
      System.out.println("RIGHT");
      LastDirection = Direction.RIGHT;
        direct.offer(Direction.RIGHT);
    }
    else if (key.equals(KeyCode.LEFT) && !LastDirection.equals(Direction.RIGHT)) {
      System.out.println("LEFT");
      LastDirection = Direction.LEFT;
        direct.offer(Direction.LEFT);
    }
  }
}
