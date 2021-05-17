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
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

public class GameTwoController<T extends Snake,U extends Snake>{
  private int windowWidth = 600;
  private int windowHeight = 600;
  private int GridWidth = 20;

  private int time = 150;
  private Timeline move1;
  private Timeline move2;
  private double rate1 = 1.0;
  private double rate2= 1.0;
  private String Username;
  private int score1 = 0;
  private int score2 = 0;

  private boolean PauseGame = false;
  private boolean CanPlayNewGame = true;
  private SnakeBody<T> snake1;
  private SnakeBody<U> snake2;
  private T snake1Instance;
  private U snake2Instance;
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
  public void init(T s1,U s2) {
    DrawLine();
    setAlertText("Tap  ENTER  to start new game", Color.WHITE);
    ScoreRefresh(0, 0);
    GameCurrentChildrenArray.Instance.set(GameTable.getChildren());
    directionController1 = new DirectionController();
    directionController2 = new DirectionController();
    snake1Instance=s1;
    snake2Instance=s2;
    snake1 = new SnakeBody<T>(snake1Instance, Color.WHITE);
    snake2 = new SnakeBody<U>(snake2Instance, Color.BLACK);
    apple = new NormalFood();
    foodGenerator = new FoodGenerator((NormalFood)apple);
    move1 = new Timeline(new KeyFrame(Duration.millis(time), (e) -> {
      GameOver(SnakeRun1(directionController1.NextDirection()));
    }));
    move2 = new Timeline(new KeyFrame(Duration.millis(time), (e) -> {
      GameOver(SnakeRun2(directionController2.NextDirection()));
    }));
  }

  // Game flow
  public void StartGame() {
    CanPlayNewGame = false;
    directionController1.init(Direction.UP);
    directionController2.init(Direction.DOWN);
    snake1 = new SnakeBody<T>(snake1Instance, Color.WHITE);
    snake2 = new SnakeBody<U>(snake2Instance, Color.BLACK);
    foodGenerator.RefreshFood();
    AlertText.setText("");
    rate1 = rate2 = 1.0;
    move1.setRate(rate1);
    move2.setRate(rate2);
    score1 = score2 = 0;
    ScoreRefresh(score1,score2);
    move1.setCycleCount(Animation.INDEFINITE);
    move2.setCycleCount(Animation.INDEFINITE);
    move1.play();
    move2.play();
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
  public int SnakeRun1(Direction direction) {
    try {
      if (snake1.SnakeMoving(direction, apple)) {
        foodGenerator.RefreshFood();
        ChangedScore(1);
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    return CheckGameOver(snake1,snake2);
  }
  
  public int SnakeRun2(Direction direction) {
    try {
      if (snake1.SnakeMoving(direction, apple)) {
        foodGenerator.RefreshFood();
        ChangedScore(2);
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    return CheckGameOver(snake1, snake2);
  }
  public int CheckGameOver(SnakeBody<T> snake1,SnakeBody<U> snake2){
    int x1 = snake1.GetHeadX();
    int x2 = snake2.GetHeadX();
    int y1 = snake1.GetHeadY();
    int y2 = snake2.GetHeadY();
    for(Snake Snake : snake1.getBody()){
      if(Snake.GetPosition().getX() == x2 && Snake.GetPosition().getY() == y2){
        return 1; 
      }
    }
    for (Snake Snake : snake2.getBody()) {
      if (Snake.GetPosition().getX() == x1 && Snake.GetPosition().getY() == y1) {
        return 2;
      }
    }
    return 0;
  }
  // score chang / rate chang
  public void ChangedScore(int id) {
    //score += 10;
    if(id==1) {
      score1+=10;
      rate1 = rate1 + (3 - rate1) * 0.03;
      move1.setRate(rate1);
    }
    else {
      score2 += 10;
      rate2 = rate2 + (3 - rate2) * 0.03;
      move2.setRate(rate2);
    } 
    ScoreRefresh(score1, score2);
  }
  
  public void ScoreRefresh(int score1 ,int score2){
    Score1.setText(Integer.toString(score1));
    Score2.setText(Integer.toString(score2));
  }
  // next game set
  public void GameOver(int w) {
    if(w!=0){
      move1.stop();
      move2.stop();
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
      move1.stop();
      move2.stop();
      BackToHomePage(event);
    }
    if (key == KeyCode.SPACE && !PauseGame){
      move1.pause();
      move2.pause();
      setAlertText("Tap Space --> continue the game\nTap H --> return Home Page", Color.WHITE);
      PauseGame = true;
    }
    else if (key == KeyCode.SPACE && PauseGame) {
      move1.play();
      move2.play();
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
    App.stage.setScene(new Scene(root));
  }
}
