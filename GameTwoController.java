package Application;
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

public class GameTwoController implements Initializable {
  private int windowWidth = 600;
  private int windowHeight = 600;
  private int GridWidth = 20;

  private int time = 150;
  private Timeline move;
  private double rate = 1.0;
  private String Username;
  private int score1 = 0;
  private int score2 = 0;

  private boolean PauseGame = false;
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
  @FXML  private Label UserName1;
  @FXML  private Label Score1;
  @FXML private Label UserName2;
  @FXML private Label Score2;
  @Override
  public void initialize(URL q, ResourceBundle p) {
    DrawLine();
    setAlertText("Tap  ENTER  to start new game", Color.WHITE);
    score1 = 0;
    Score1.setText(Integer.toString(score1));
    score2 = 0;
    Score2.setText(Integer.toString(score2));
    directionController1 = new DirectionController();
    directionController2 = new DirectionController();
    snake1 = new SnakeBody<ClassicSnake>(new ClassicSnake(), Color.WHITE);
    snake2 = new SnakeBody<ClassicSnake>(new ClassicSnake(), Color.BLACK);
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
    snake1 = new SnakeBody<ClassicSnake>(new ClassicSnake(), Color.WHITE);
    snake2 = new SnakeBody<ClassicSnake>(new ClassicSnake(), Color.BLACK);
    foodGenerator.RefreshFood();
    AlertText.setText("");
    rate = 1.0;
    move.setRate(rate);
    score1 = 0;
    Score1.setText(Integer.toString(score1));
    score2 = 0;
    Score2.setText(Integer.toString(score2));
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

  // score chang / rate chang
  public void ChangedScore() {
    //score += 10;
    rate = rate + (3 - rate) * 0.03;
    move.setRate(rate);
  }

  // next game set
  public void GameOver() {
    for (Snake snake : snake1.getBody())
      GameTable.getChildren().remove(snake.GetBody());
    setAlertText("Game Over\n(Tap Enter to start a new game)",Color.RED);
    CanPlayNewGame = true;
  }
  public void setAlertText(String text , Color color){
    GameTable.getChildren().remove(AlertText);
    AlertText.setText(text);
    AlertText.setAlignment(Pos.CENTER);
    AlertText.setTextFill(color);
    GameTable.getChildren().add(AlertText);
  }

  // get button click or not
  public boolean NewGame() {
    return CanPlayNewGame;
  }

  public void GetPinName(String name) {
    String DefaultName = "JACK";
    this.Username = name;
    if (Username.length() != 0){
      UserName1.setText(Username+"1");
      UserName2.setText(Username+"1");
    }
    else{
      UserName1.setText(DefaultName + "2");
      UserName2.setText(DefaultName + "2");
    }
  }

  public void KeyEven(KeyEvent event) throws IOException {
    KeyCode key = event.getCode();
    if (key == KeyCode.H ){
      move.stop();
      BackToHomePage(event);
    }
    if (key == KeyCode.SPACE && !PauseGame){
      move.pause();
      setAlertText("Tap Space --> continue the game\nTap H --> return Home Page", Color.WHITE);
      PauseGame = true;
    }
    else if (key == KeyCode.SPACE && PauseGame) {
      move.play();
      setAlertText("", Color.BLACK);
      PauseGame = false;
    }
    if (key == KeyCode.ENTER && NewGame()) StartGame();
    //snake1
    if (!NewGame() && !PauseGame){
      directionController1.Direction1(event);
      directionController2.Direction2(event);
    }
  }
  public void BackToHomePage(KeyEvent e) throws IOException{
    FXMLLoader loader = new FXMLLoader(getClass().getResource("Home.fxml"));
    Parent root = loader.load();
    Scene scene = (Scene)GameTable.getScene();
    scene.setRoot(root);
  }
}
