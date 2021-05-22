package Application.Controller;
import java.io.IOException;

import Application.App;
import Application.Enum.Direction;
import Application.Enum.Point;
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

public class GameTwoController{
  private int windowWidth = 600;
  private int GridWidth = 20;

  private int time = 150;
  private Timeline move1;
  private Timeline move2;
  private double rate1 = 1.0;
  private double rate2= 1.0;
  private String Username;
  private int score1 = 0;
  private int score2 = 0;
  private int gamepoint1 = 0;
  private int gamepoint2 = 0;
  private boolean PauseGame = false;
  private boolean CanPlayNewGame = true;
  private SnakeBody snake1;
  private SnakeBody snake2;
  DirectionController directionController1;
  DirectionController directionController2;
  private Food apple;
  private FoodGenerator foodGenerator;
  
  @FXML  private AnchorPane GameTable;
  @FXML  private Label ScoreText;
  @FXML  private Label AlertText;
  @FXML  private Label UserName1;
  @FXML  private Label Score1;
  @FXML  private Label UserName2;
  @FXML  private Label Score2;
  @FXML  private Label GamePoint1;
  @FXML  private Label GamePoint2;
  @FXML  private Label SnakeName1;
  @FXML  private Label SnakeName2;
  public void init() {
    MusicController.PlayBackground1();
    DrawLine();
    setAlertText("TAP ENTER TO START NEW GAME","Normal");
    score1 = score2 = 0;
    gamepoint1 = gamepoint2 = 0;
    ScoreRefresh(score1, score2);
    GamePointRefresh(gamepoint1 ,gamepoint2);
    SnakeName1.setText(ChoseSnakeController.GetSnake1Name());
    SnakeName2.setText(ChoseSnakeController.GetSnake2Name());
    GameCurrentChildrenArray.Instance.set(GameTable.getChildren());
    directionController1 = new DirectionController();
    directionController2 = new DirectionController();
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
    if(snake1!= null){
      snake1.clearOnScreen();
      snake2.clearOnScreen();
    }
    snake1 = new SnakeBody(HomeController.Player1, 200,200);
    snake2 = new SnakeBody(HomeController.Player2, 400,400);
    CanPlayNewGame = false;
    directionController1.init(Direction.UP);
    directionController2.init(Direction.DOWN);
    snake1.clearOnScreen();
    snake2.clearOnScreen();
    foodGenerator.RefreshFood();
    AlertText.setText("");
    rate1 = rate2 = 1.0;
    move1.setRate(rate1);
    move2.setRate(rate2);
    score1 = score2 = 0;
    ScoreRefresh(score1,score2);
    GamePointRefresh(gamepoint1, gamepoint2);
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
      rows.setStrokeWidth(0.3);
      cols.setStrokeWidth(0.3);
      GameTable.getChildren().add(rows);
      GameTable.getChildren().add(cols);
    }
  }

  // moving event
  public int SnakeRun1(Direction direction) {
    snake1.Move(direction);
    if (snake1.whatPart(apple.GetFoodPosition())==SnakePart.HEAD) {
      snake1.AddNewBody();
      foodGenerator.RefreshFood();
      MusicController.EatFoodPop();
      ChangedScore(1);
    }
    return CheckGameOver(snake1,snake2);
  }
  
  public int SnakeRun2(Direction direction) {
    snake2.Move(direction);
    if (snake2.whatPart(apple.GetFoodPosition())==SnakePart.HEAD) {
      snake2.AddNewBody();
      MusicController.EatFoodPop();
      foodGenerator.RefreshFood();
      ChangedScore(2);
    }
    return CheckGameOver(snake1,snake2);
  }

  public int CheckGameOver(SnakeBody snake1,SnakeBody snake2){
    Point head1 = snake1.GetHead();
    Point head2 = snake2.GetHead();
    if (head1.getX()==head2.getX()&&head1.getY()==head2.getY())return 3;
    if(snake2.whatPart(head1)==SnakePart.BODY) return 2 ; 
    if(snake1.whatPart(head2)==SnakePart.BODY) return 1 ;
    return 0;
  }
  // score chang / rate chang
  public void ChangedScore(int id) {
    //score += 10;
    if(id==1) {
      score1+=10;
      rate1 = rate1 + (4 - rate1) * 0.025;
      move1.setRate(rate1);
    }
    else {
      score2 += 10;
      rate2 = rate2 + (4 - rate2) * 0.025;
      move2.setRate(rate2);
    } 
    ScoreRefresh(score1, score2);
  }
  
  public void ScoreRefresh(int score1 ,int score2){
    Score1.setText(Integer.toString(score1));
    Score2.setText(Integer.toString(score2));
  }
  
  public void GamePointRefresh(int score1, int score2) {
    GamePoint1.setText(Integer.toString(score1));
    GamePoint2.setText(Integer.toString(score2));
  }
  // next game set
  public void GameOver(int w) {
    if(w!=0){
      move1.stop();
      move2.stop();
      if(w==3){
        if(score1 == score2){
          setAlertText( "TIE \n\nTAP ENTER TO START NEW GAME","Alert");
        }
        else{
          if(score1 > score2){
            gamepoint1 +=1;
            setAlertText(Username + "1 Win\n\nTAP ENTER TO START NEW GAME", "Player1");
          }
          else{
            gamepoint2 += 1;
            setAlertText(Username + "2 Win\n\nTAP ENTER TO START NEW GAME", "Player2");
          }
        }
      }
      if(w==1){
        gamepoint1 += 1;
        setAlertText(Username + "1 Win\n\nTAP ENTER TO START NEW GAME", "Player1");
      }
      if(w==2){
        gamepoint2 += 1;
        setAlertText(Username + "2 Win\n\nTAP ENTER TO START NEW GAME","Player2");
      }
      GameTable.getChildren().remove(apple.GetFoodBody());
      MusicController.GameOverSound();
      GamePointRefresh(gamepoint1, gamepoint2);
      CanPlayNewGame = true;
    }
  }
  public void setAlertText(String text , String Id){
    GameTable.getChildren().remove(AlertText);
    AlertText.setText(text);
    AlertText.setAlignment(Pos.CENTER);
    AlertText.setId(Id);
    GameTable.getChildren().add(AlertText);
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
    if (key == KeyCode.SPACE && !PauseGame && !CanPlayNewGame){
      move1.pause();
      move2.pause();
      setAlertText("TAP SPACE --> CONTINUE THE GAME\n\nTAP H --> RETURN HOME PAGE", "Normal");
      PauseGame = true;
    }
    else if (key == KeyCode.SPACE && PauseGame && !CanPlayNewGame) {
      move1.play();
      move2.play();
      setAlertText("","Normal");
      PauseGame = false;
    }
    if (key == KeyCode.ENTER &&CanPlayNewGame) StartGame();
    //snake1
    if (!CanPlayNewGame && !PauseGame){
      directionController1.Direction1(event);
      directionController2.Direction2(event);
    }
  }
  public void BackToHomePage(KeyEvent e) throws IOException{
    MusicController.StopBackground1();
    FXMLLoader loader = new FXMLLoader(getClass().getResource("../Scene/Home.fxml"));
    Parent root = loader.load();
    App.stage.setScene(new Scene(root));
  }
}
