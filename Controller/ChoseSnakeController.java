package SnakeGame.Controller;

import java.io.File;
import java.io.IOException;

import SnakeGame.App;
import SnakeGame.Enum.Direction;
import SnakeGame.SingletonAndTemplate.*;
import SnakeGame.Snake.*;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import javafx.util.Duration;
import javafx.util.Pair;

public class ChoseSnakeController{
  @FXML private GridPane table;
  @FXML private Pane P1select;
  @FXML private Pane P2select;
  @FXML private Pane P1Hover;
  @FXML private Pane P2Hover;
  @FXML private Pane Hover;
  @FXML private Pane playerOneExhibitPane;
  @FXML private Pane playerTwoExhibitPane;
  @FXML private Label SnakeType1;
  @FXML private Label SnakeType2;
  @FXML private AnchorPane GameTable1;
  private Snake[][] snakes = new Snake[5][4];
  private Pair<Integer,Integer> hover1 = new Pair<>(0,2);
  private Pair<Integer,Integer> hover2 = new Pair<>(0,3);
  private static Pair<Integer,Integer> select1=new Pair<>(0,2);
  private static Pair<Integer,Integer> select2=new Pair<>(0,3);
  private static String Snake1Name = "PythonSnake";
  private static String Snake2Name = "VscodeSnake";
  private String[][] name = new String[5][4];
  private boolean fixed1=false;
  private boolean fixed2=false;
  private Timeline player1Timeline;
  private Timeline player2Timeline;
  private SnakeBody player1Body;
  private SnakeBody player2Body;
  private Direction[][] exhibitDir=new Direction[2][8];
  private int[] nextDir=new int[2];
  public void BackToHomePage() throws IOException {
    MusicController.ButtonClickSound();
    player1Body.clearOnScreen();
    player2Body.clearOnScreen();
    player1Timeline.stop();
    player2Timeline.stop();
    if(fixed1)select1=hover1;
    if(fixed2)select2=hover2;
    HomeController.Player1 = snakes[select1.getKey()][select1.getValue()];
    HomeController.Player2 = snakes[select2.getKey()][select2.getValue()];
    FXMLLoader loader = new FXMLLoader(getClass().getResource("../Scene/Home.fxml"));
    Parent root = loader.load();
    Scene scene = new Scene(root);
    scene.setRoot(root);
    App.stage.setScene(scene);
  }
  public void init() {
    GameCurrentChildrenArray.Instance.set(GameTable1.getChildren());
    hover1=select1;
    hover2=select2;
    initializeArrays();
    nextDir[0]=nextDir[1]=0;
    table.getChildren().remove(P1select);
    table.getChildren().remove(P2select);
    table.getChildren().remove(Hover);
    GridPane.setColumnIndex(P1Hover, hover1.getValue());
    GridPane.setRowIndex(P1Hover, hover1.getKey());
    GridPane.setColumnIndex(P2Hover, hover2.getValue());
    GridPane.setRowIndex(P2Hover, hover2.getKey());
    Snake1Name = name[hover1.getKey()][hover1.getValue()];
    Snake2Name = name[hover2.getKey()][hover2.getValue()];
    SnakeType1.setText(Snake1Name);
    SnakeType2.setText(Snake2Name);
    playSnake1();
    playSnake2();
    table.setOnKeyPressed((e)->{
      e.consume();
      handle(e);
    });
    table.requestFocus();
  }
  private void playSnake1() {
    //start speed useless if SnakeBodyPlayer not used
    player1Body=new SnakeBody(snakes[hover1.getKey()][hover1.getValue()],0, 40,220);
    try {
      player1Body.AddNewBody();
    } catch (Exception e1) {
      // TODO Auto-generated catch block
      e1.printStackTrace();
    }
    player1Timeline=new Timeline(new KeyFrame(Duration.millis(200), e->{
      try {
        player1Body.Move(exhibitDir[0][nextDir[0]]);
        nextDir[0]=(nextDir[0]+1)%8;
      } catch (Exception e1) {
        // TODO Auto-generated catch block
        e1.printStackTrace();
      }
    }));
    player1Timeline.setCycleCount(-1);
    player1Timeline.play();
  }
  private void playSnake2() {
    player2Body=new SnakeBody(snakes[hover2.getKey()][hover2.getValue()],0,460,220);
    try {
      player2Body.AddNewBody();
    } catch (Exception e2) {
      // TODO Auto-generated catch block
      e2.printStackTrace();
    }
    player2Timeline=new Timeline(new KeyFrame(Duration.millis(200), e->{
      try {
        player2Body.Move(exhibitDir[1][nextDir[1]]);
        nextDir[1]=(nextDir[1]+1)%8;
      } catch (Exception e1) {
        // TODO Auto-generated catch block
        e1.printStackTrace();
      }
    }));
    player2Timeline.setCycleCount(-1);
    player2Timeline.play();
  }
  private void initializeArrays() {
    exhibitDir[0][0]=Direction.UP;
    exhibitDir[0][1]=Direction.UP;
    exhibitDir[0][2]=Direction.LEFT;
    exhibitDir[0][3]=Direction.LEFT;
    exhibitDir[0][4]=Direction.DOWN;
    exhibitDir[0][5]=Direction.DOWN;
    exhibitDir[0][6]=Direction.RIGHT;
    exhibitDir[0][7]=Direction.RIGHT;
    exhibitDir[1][0]=Direction.UP;
    exhibitDir[1][1]=Direction.UP;
    exhibitDir[1][2]=Direction.LEFT;
    exhibitDir[1][3]=Direction.LEFT;
    exhibitDir[1][4]=Direction.DOWN;
    exhibitDir[1][5]=Direction.DOWN;
    exhibitDir[1][6]=Direction.RIGHT;
    exhibitDir[1][7]=Direction.RIGHT;
    snakes[0][0]=new ClassicSnake();
    snakes[0][1]=new IGSnake();
    snakes[0][2]=new PythonSnake();
    snakes[0][3]=new VscodeSnake();
    snakes[1][0]=new NCUSnake();
    snakes[1][1]=new KobeSnake();
    snakes[1][2]=new RainbowSnake();
    snakes[1][3]=new AppleSnake();
    snakes[2][0]=new YoutubeSnake();
    snakes[2][1]=new ChromeSnake();
    snakes[2][2]=new DiscordSnake();
    snakes[2][3]=new LineSnake();
    snakes[3][0]=new JackSnake();
    snakes[3][1]=new QuanSnake();
    snakes[3][2]=new AlbertSnake();
    snakes[3][3]=new SBBSnake();
    snakes[4][0]=new QuestionSnake();
    snakes[4][1]=new QuestionSnake();
    snakes[4][2]=new QuestionSnake();
    snakes[4][3]=new QuestionSnake();
    name[0][0] = "ClassicSnake";
    name[0][1] = "IGSnake";
    name[0][2] = "PythonSnake";
    name[0][3] = "VscodeSnake";
    name[1][0] = "NCUSnake";
    name[1][1] = "KobeSnake";
    name[1][2] = "RainbowSnake";
    name[1][3] = "AppleSnake";
    name[2][0] = "YoutubeSnake";
    name[2][1] = "ChromeSnake";
    name[2][2] = "DiscordSnake";
    name[2][3] = "LineSnake";
    name[3][0] = "JackSnake";
    name[3][1] = "QuanSnake";
    name[3][2] = "AlbertSnake";
    name[3][3] = "SBBSnake";
    name[4][0] = "QuestSnake";
    name[4][1] = "QuestSnake";
    name[4][2] = "QuestSnake";
    name[4][3] = "QuestSnake";
  }
  private void handle(KeyEvent e) {
    table.getChildren().remove(P1select);
    table.getChildren().remove(P2select);
    table.getChildren().remove(Hover);
    table.getChildren().remove(P1Hover);
    table.getChildren().remove(P2Hover);
    int changedPlayer=0;
    if(e.getCode()==KeyCode.SPACE){
      fixed1=!fixed1;
      if(fixed1&&hover1.getKey()==hover2.getKey()&&hover1.getValue()==hover2.getValue()){
        hover2=new Pair<>((hover2.getKey())%5,(hover2.getValue()-1)%4);
      }
    }
    else if(e.getCode()==KeyCode.ENTER){
      fixed2=!fixed2;
      if(fixed2&&hover1.getKey()==hover2.getKey()&&hover1.getValue()==hover2.getValue()){
        hover1=new Pair<>((hover1.getKey())%5,(hover1.getValue()+1)%4);
      }
    }
    else{
      if(!fixed1)switch(e.getCode()){
        case W:
        changedPlayer=1;
        hover1=new Pair<>((hover1.getKey()+4)%5,(hover1.getValue())%4);
        if(fixed2&&hover1.getKey()==hover2.getKey()&&hover1.getValue()==hover2.getValue()){
          hover1=new Pair<>((hover1.getKey()+4)%5,(hover1.getValue())%4);
        }
        break;
        case S:
        changedPlayer=1;
        hover1=new Pair<>((hover1.getKey()+1)%5,(hover1.getValue())%4);
        if(fixed2&&hover1.getKey()==hover2.getKey()&&hover1.getValue()==hover2.getValue()){
          hover1=new Pair<>((hover1.getKey()+1)%5,(hover1.getValue())%4);
        }
        break;
        case A:
        changedPlayer=1;
        hover1=new Pair<>((hover1.getKey())%5,(hover1.getValue()+3)%4);
        if(fixed2&&hover1.getKey()==hover2.getKey()&&hover1.getValue()==hover2.getValue()){
          hover1=new Pair<>((hover1.getKey())%5,(hover1.getValue()+3)%4);
        }
        break;
        case D:
        changedPlayer=1;
        hover1=new Pair<>((hover1.getKey())%5,(hover1.getValue()+1)%4);
        if(fixed2&&hover1.getKey()==hover2.getKey()&&hover1.getValue()==hover2.getValue()){
          hover1=new Pair<>((hover1.getKey())%5,(hover1.getValue()+1)%4);
        }
        break;
        default:
        break;
        }
      if(!fixed2)switch(e.getCode()){
        case UP:
        changedPlayer=2;
        hover2=new Pair<>((hover2.getKey()+4)%5,(hover2.getValue())%4);
        if(fixed1&&hover1.getKey()==hover2.getKey()&&hover1.getValue()==hover2.getValue()){
          hover2=new Pair<>((hover2.getKey()+4)%5,(hover2.getValue())%4);
        }
        break;
        case DOWN:
        changedPlayer=2;
        hover2=new Pair<>((hover2.getKey()+1)%5,(hover2.getValue())%4);
        if(fixed1&&hover1.getKey()==hover2.getKey()&&hover1.getValue()==hover2.getValue()){
          hover2=new Pair<>((hover2.getKey()+1)%5,(hover2.getValue())%4);
        }
        break;
        case LEFT:
        changedPlayer=2;
        hover2=new Pair<>((hover2.getKey())%5,(hover2.getValue()+3)%4);
        if(fixed1&&hover1.getKey()==hover2.getKey()&&hover1.getValue()==hover2.getValue()){
          hover2=new Pair<>((hover2.getKey())%5,(hover2.getValue()+3)%4);
        }
        break;
        case RIGHT:
        changedPlayer=2;
        hover2=new Pair<>((hover2.getKey())%5,(hover2.getValue()+1)%4);
        if(fixed1&&hover1.getKey()==hover2.getKey()&&hover1.getValue()==hover2.getValue()){
          hover2=new Pair<>((hover2.getKey())%5,(hover2.getValue()+1)%4);
        }
        break;
        default:
        break;
      }
    }
    if(hover1.getKey()==hover2.getKey()&&hover1.getValue()==hover2.getValue()){
      table.getChildren().add(Hover);
      GridPane.setColumnIndex(Hover, hover1.getValue());
      GridPane.setRowIndex(Hover, hover1.getKey());
      
    }
    else{
      if(fixed1){
        table.getChildren().add(P1select);
        GridPane.setColumnIndex(P1select, hover1.getValue());
        GridPane.setRowIndex(P1select, hover1.getKey());
      }
      else{
        table.getChildren().add(P1Hover);
        GridPane.setColumnIndex(P1Hover, hover1.getValue());
        GridPane.setRowIndex(P1Hover, hover1.getKey());
      }
      if(fixed2){
        table.getChildren().add(P2select);
        GridPane.setColumnIndex(P2select, hover2.getValue());
        GridPane.setRowIndex(P2select, hover2.getKey());
      }
      else{
        table.getChildren().add(P2Hover);
        GridPane.setColumnIndex(P2Hover, hover2.getValue());
        GridPane.setRowIndex(P2Hover,hover2.getKey());
      }
    }
    Snake1Name = name[hover1.getKey()][hover1.getValue()];
    Snake2Name = name[hover2.getKey()][hover2.getValue()];
    SnakeType1.setText(Snake1Name);
    SnakeType2.setText(Snake2Name);
    if(changedPlayer==1){
      player1Body.clearOnScreen();
      nextDir[0]=0;
      player1Body=new SnakeBody(snakes[hover1.getKey()][hover1.getValue()],0,40,220);
      try {
        player1Body.AddNewBody();
      } catch (Exception e1) {
        e1.printStackTrace();
      }
    }
    else if(changedPlayer==2){
      player2Body.clearOnScreen();
      nextDir[1]=0;
      player2Body=new SnakeBody(snakes[hover2.getKey()][hover2.getValue()],0,460,220);
      try {
        player2Body.AddNewBody();
      } catch (Exception e2) {
        e2.printStackTrace();
      }
    }
  }
  public static String GetSnake1Name(){
    return Snake1Name;
  }
  public static String GetSnake2Name(){
    return Snake2Name;
  }
  private void setCostonmizePhoto(){
		FileChooser fileChooser = new FileChooser();
		fileChooser.getExtensionFilters().addAll(
			new FileChooser.ExtensionFilter("All Images", "*.*"),
			new FileChooser.ExtensionFilter("JPG", "*.jpg"),
			new FileChooser.ExtensionFilter("GIF", "*.gif"),
			new FileChooser.ExtensionFilter("BMP", "*.bmp"),
			new FileChooser.ExtensionFilter("PNG", "*.png"),
			new FileChooser.ExtensionFilter("JPNG", "*.jpng")
		);
		File selectedFile = fileChooser.showOpenDialog(App.stage);
		System.out.println(selectedFile.getAbsolutePath());
  }
}
