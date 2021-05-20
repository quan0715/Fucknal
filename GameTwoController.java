package Application;
import java.io.IOException;
import java.util.concurrent.Callable;

import Application.Snake.Snake;
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
  private MusicController player = HomeController.player;
  private boolean PauseGame = false;
  private boolean CanPlayNewGame = true;
  private SnakeBody snake1;
  private SnakeBody snake2;
  private Snake snake1Instance;
  private Snake snake2Instance;
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
  public void init(Snake s1,Snake s2) {
    player.PlayBackground1();
    DrawLine();
    setAlertText("TAP ENTER TO START NEW GAME", Color.WHITE);
    score1 = score2 = 0;
    gamepoint1 = gamepoint2 = 0;
    ScoreRefresh(score1, score2);
    GamePointRefresh(gamepoint1 ,gamepoint2);
    GameCurrentChildrenArray.Instance.set(GameTable.getChildren());
    directionController1 = new DirectionController();
    directionController2 = new DirectionController();
    snake1Instance=s1;
    snake2Instance=s2;
    snake1 = new SnakeBody(snake1Instance,200,200);
    snake2 = new SnakeBody(snake2Instance,400,400);
    apple = new NormalFood();
    foodGenerator = new FoodGenerator((NormalFood)apple);
    move1 = new Timeline(new KeyFrame(Duration.millis(time), (e) -> {
      GameOver(SnakeRun1(directionController1.NextDirection()));
    }));
    move2 = new Timeline(new KeyFrame(Duration.millis(time), (e) -> {
      GameOver(SnakeRun2(directionController2.NextDirection()));
    }));
    Food.addFoodHandler(snake1, new Callable<Void>(){
      @Override
      public Void call() throws Exception {
        foodGenerator.RefreshFood();
        ChangedScore(1);
        return null;
      }
    });
    Food.addFoodHandler(snake2, new Callable<Void>(){
      @Override
      public Void call() throws Exception {
        foodGenerator.RefreshFood();
        ChangedScore(2);
        return null;
      }
    });
  }

  // Game flow
  public void StartGame() {
    CanPlayNewGame = false;
    directionController1.init(Direction.UP);
    directionController2.init(Direction.DOWN);
    snake1.clearOnScreen();
    snake2.clearOnScreen();
    snake1 = new SnakeBody(snake1Instance, 200,200);
    snake2 = new SnakeBody(snake2Instance, 400,400);
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
  public int SnakeRun1(Direction direction)  {
    try {
      snake1.SnakeMoving(direction);
    } catch (Exception e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    return CheckGameOver(snake1,snake2);
  }
  
  public int SnakeRun2(Direction direction) {
    try {
      snake2.SnakeMoving(direction);
    } catch (Exception e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    return CheckGameOver(snake1, snake2);
  }

  public int CheckGameOver(SnakeBody snake1,SnakeBody snake2){
    int x1 = snake1.GetHeadX();
    int x2 = snake2.GetHeadX();
    int y1 = snake1.GetHeadY();
    int y2 = snake2.GetHeadY();
    if (x1 == x2 && y1 ==y2){
      return 3;
    }
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
          setAlertText( "TIE \n\nTAP ENTER TO START NEW GAME", Color.web("#d82909"));
        }
        else{
          if(score1 > score2){
            gamepoint1 +=1;
            setAlertText(Username + "1 Win\n\nTAP ENTER TO START NEW GAME", Color.web("#d82909"));
          }
          else{
            gamepoint2 += 1;
            setAlertText(Username + "2 Win\n\nTAP ENTER TO START NEW GAME", Color.web("#d82909"));
          }
        }
      }
      if(w==1){
        gamepoint1 += 1;
        setAlertText(Username + "1 Win\n\nTAP ENTER TO START NEW GAME", Color.web("#d82909"));
      }
      if(w==2){
        gamepoint2 += 1;
        setAlertText(Username + "2 Win\n\nTAP ENTER TO START NEW GAME", Color.web("#d82909"));
      }
      GameTable.getChildren().remove(apple.GetFoodBody());
      GamePointRefresh(gamepoint1, gamepoint2);
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
      setAlertText("TAP SPACE --> CONTINUE THE GAME\n\nTAP H --> RETURN HOME PAGE", Color.WHITE);
      PauseGame = true;
    }
    else if (key == KeyCode.SPACE && PauseGame && !CanPlayNewGame) {
      move1.play();
      move2.play();
      setAlertText("", Color.BLACK);
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
    player.StopBackground1();
    FXMLLoader loader = new FXMLLoader(getClass().getResource("./Scene/Home.fxml"));
    Parent root = loader.load();
    App.stage.setScene(new Scene(root));
  }
}
