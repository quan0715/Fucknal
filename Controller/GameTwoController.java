package SnakeGame.Controller;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.Callable;

import SnakeGame.Enum.Direction;
import SnakeGame.Enum.SnakePart;
import SnakeGame.SingletonAndTemplate.*;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.util.Duration;

public class GameTwoController{
  private int windowWidth = 600;
  private int GridWidth = 20;
  private final int time = 170;
  private String Username;
  private int score1 = 0;
  private int score2 = 0;
  private int gamepoint1 = 0;
  private int gamepoint2 = 0;
  private SnakeBodyPlayer snakePlayer1;
  private SnakeBodyPlayer snakePlayer2;
  DirectionController directionController1;
  DirectionController directionController2;
  public InputController input;
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
  @FXML  private Label Skill1;
  @FXML  private Label Skill2;
  private Timeline checkScoreTimeline;
  private Timeline SkillTextRefresh;
  public void init() {
    MusicController.PlayBackground1();
    DrawLine();
    score1 = score2 = 0;
    gamepoint1 = gamepoint2 = 0;
    ScoreRefresh(score1, score2);
    GamePointRefresh(gamepoint1 ,gamepoint2);
    SnakeName1.setText(ChoseSnakeController.GetSnake1Name());
    SnakeName2.setText(ChoseSnakeController.GetSnake2Name());
    GameCurrentChildrenArray.Instance.set(GameTable.getChildren());
    directionController1 = new DirectionController();
    directionController2 = new DirectionController();
    snakePlayer1=new SnakeBodyPlayer(
      directionController1,
      time,
      new Callable<Boolean>(){
        @Override
        public Boolean call() throws Exception {
          SnakeBody snake1=snakePlayer1.getSnakeBody();
          SnakeBody snake2=snakePlayer2.getSnakeBody();
          int t=CheckGameOver(snake1,snake2);
          if(t!=0)GameOver(t);
          else return false;
          return true;
        }
      }
    );
    snakePlayer2=new SnakeBodyPlayer(
      directionController2, 
      time,
      new Callable<Boolean>(){
        @Override
        public Boolean call() throws Exception {
          SnakeBody snake1=snakePlayer1.getSnakeBody();
          SnakeBody snake2=snakePlayer2.getSnakeBody();
          int t=CheckGameOver(snake1,snake2);
          if(t!=0)GameOver(t);
          else return false;
          return true;
        }
      }
    );
    input = new InputController(
      AlertText,
      snakePlayer1,
      snakePlayer2,
      directionController1,
      directionController2
    );
    input.welcome();
  }

  public int CheckGameOver(SnakeBody snake1,SnakeBody snake2){
    if(snake1.woody!=0&&snake2.woody!=0)return 0;
    if(snake1.getSize()==0)return 2;
    if(snake2.getSize()==0)return 1;
    List<SnakePart> s1=snake1.whatPart(snake2.GetHead());
    List<SnakePart> s2=snake2.whatPart(snake1.GetHead());
    if (s1.contains(SnakePart.HEAD)&&s2.contains(SnakePart.HEAD)){
      if(snake1.woody!=0)return 1;
      if(snake2.woody!=0)return 2;
      return 3;
    }
    if (s2.contains(SnakePart.BODY)) {
      if(snake1.woody!=0)return 0;
      return 2 ; 
    }
    if (s1.contains(SnakePart.BODY)) {
      if(snake2.woody!=0)return 0;
      return 1 ; 
    }
    return 0;
  }
  // Game flow
  public void StartGame() {
    input.SetNewGame(false);
    snakePlayer1.SetSnakeBody(new SnakeBody(HomeController.Player1, time, 200,200));
    snakePlayer2.SetSnakeBody(new SnakeBody(HomeController.Player2, time, 400,400));
    FoodGenerator.RefreshFood();
    AlertText.setText("");
    score1 = score2 = 0;
    ScoreRefresh(score1,score2);
    GamePointRefresh(gamepoint1, gamepoint2);
    directionController1.init(Direction.UP);
    directionController2.init(Direction.DOWN);
    SkillTextRefresh = new Timeline(new KeyFrame(Duration.millis(10), e -> {
      SkillRefresh();
    }));
    checkScoreTimeline=new Timeline(new KeyFrame(Duration.millis(10),e->{
      if(score1!=snakePlayer1.getSnakeBody().GetScore())
        ChangedScore(1);
      if(score2!=snakePlayer2.getSnakeBody().GetScore())
        ChangedScore(2);
    }));
    checkScoreTimeline.setCycleCount(Timeline.INDEFINITE);
    checkScoreTimeline.play();
    SkillTextRefresh.setCycleCount(Timeline.INDEFINITE);
    SkillTextRefresh.play();
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
  // score chang / rate chang
  public void ChangedScore(int id) {
    if(id==1) {
      score1=snakePlayer1.getSnakeBody().GetScore();
    }
    else {
      score2=snakePlayer2.getSnakeBody().GetScore();
    } 
    ScoreRefresh(score1, score2);
  }
  public void SkillRefresh(){
    Skill1.setText(snakePlayer1.getSnakeBody().getSkillText());
    Skill1.setId(snakePlayer1.getSnakeBody().getSkillTextId());
    Skill2.setText(snakePlayer2.getSnakeBody().getSkillText());
    Skill2.setId(snakePlayer2.getSnakeBody().getSkillTextId());
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
    MusicController.GameOver();
    if(w!=0){
      snakePlayer1.stop();
      snakePlayer2.stop();
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
      MusicController.GameOverSound();
      GamePointRefresh(gamepoint1, gamepoint2);
      FoodGenerator.RemoveAllExtraFood();
      FoodGenerator.getFood().clearOnScreen();
      input.SetNewGame(true);
    }
  }
  public void setAlertText(String text , String Id){
    ObservableList<Node> children = GameCurrentChildrenArray.Instance.get();
    children.remove(AlertText);
    AlertText.setText(text);
    AlertText.setAlignment(Pos.CENTER);
    AlertText.setId(Id);
    children.add(AlertText);
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
    if(input.GameTwoFlow(event)){
      StartGame();
    }
  }
}
