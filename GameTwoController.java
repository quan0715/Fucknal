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
    ScoreRefresh(0, 0);
    GameCurrentChildrenArray.Instance.set(GameTable.getChildren());
    directionController1 = new DirectionController();
    directionController2 = new DirectionController();
    snake1 = new SnakeBody<ClassicSnake>(new ClassicSnake(), Color.WHITE);
    snake2 = new SnakeBody<ClassicSnake>(new ClassicSnake(), Color.BLACK);
    apple = new NormalFood();
    foodGenerator = new FoodGenerator((NormalFood)apple);
    move = new Timeline(new KeyFrame(Duration.millis(time), (e) -> {
      GameOver(SnakeRun(directionController1.NextDirection(),directionController2.NextDirection()));
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
    score1 = score2 = 0;
    ScoreRefresh(score1,score2);
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
  public int SnakeRun(Direction direction1, Direction direction2) {
    try {
      if (snake1.SnakeMoving(direction1, apple)) {
        foodGenerator.RefreshFood();
        ChangedScore(1);
      }
      if(snake2.SnakeMoving(direction2, apple)){
        foodGenerator.RefreshFood();
        ChangedScore(2);
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    return CheckGameOver(snake1,snake2);
  }
  public int CheckGameOver(SnakeBody<ClassicSnake> snake1,SnakeBody<ClassicSnake> snake2){
    int x1 = snake1.GetHeadX();
    int x2 = snake2.GetHeadX();
    int y1 = snake1.GetHeadY();
    int y2 = snake2.GetHeadY();
    for(ClassicSnake Snake : snake1.getBody()){
      if(Snake.GetPosition().getX() == x2 && Snake.GetPosition().getY() == y2){
        return 1; 
      }
    }
    for (ClassicSnake Snake : snake2.getBody()) {
      if (Snake.GetPosition().getX() == x1 && Snake.GetPosition().getY() == y1) {
        return 2;
      }
    }
    return 0;
  }
  // score chang / rate chang
  public void ChangedScore(int id) {
    //score += 10;
    if(id==1) score1+=10;
    else score2 += 10;
    ScoreRefresh(score1, score2);
    rate = rate + (3 - rate) * 0.03;
    move.setRate(rate);
  }
  public void ScoreRefresh(int score1 ,int score2){
    Score1.setText(Integer.toString(score1));
    Score2.setText(Integer.toString(score2));
  }
  // next game set
  public void GameOver(int w) {
    if(w!=0){
      move.stop();
      snake1.clearOnScreen();
      snake2.clearOnScreen();
      setAlertText(Username + w + " Win\n(Tap Enter to start a new game)", Color.RED);
      GameTable.getChildren().remove(apple.GetFoodBody());
      CanPlayNewGame = true;
    }
    
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
    this.Username = name;
    if (Username.length() == 0){
      Username = "Jack" ;
    }
    UserName1.setText(Username+"1");
    UserName2.setText(Username+"2");
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
