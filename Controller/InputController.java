package Application.Controller;

import java.io.IOException;

import Application.App;
import Application.SingletonAndTemplate.DirectionController;
import Application.SingletonAndTemplate.GameCurrentChildrenArray;
import Application.SingletonAndTemplate.MusicController;
import Application.SingletonAndTemplate.SnakeBodyPlayer;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class InputController{
  private boolean NewGame;
  private boolean GamePause;
  
  private Label AlertText;
  private SnakeBodyPlayer s1;
  private SnakeBodyPlayer s2;
  private DirectionController d1;
  private DirectionController d2;
  // game one 
  public InputController(Label AlertText,SnakeBodyPlayer s1,DirectionController d1) {
    this.AlertText = AlertText;
    this.s1 = s1;
    this.d1 = d1;
    init();
   }
  // game two
  public InputController(Label AlertText, SnakeBodyPlayer s1, SnakeBodyPlayer s2, DirectionController d1,DirectionController d2) {
    this.AlertText = AlertText;
    this.s1 = s1;
    this.d1 = d1;
    this.s2 = s2;
    this.d2 = d2;
    init();
  }
  public void init(){
    GamePause = false;
    NewGame = true;
  }
  public boolean GameTwoFlow(KeyEvent event) throws IOException{
    KeyCode key = event.getCode();
    if (key == KeyCode.H) {
      s1.stop();
      s2.stop();
      BackToHomePage();
    }
    if (key == KeyCode.SPACE) {
      if(GameContinue()){
        s1.pause();
        s2.pause();
        setAlertText("TAP SPACE --> CONTINUE THE GAME\n\nTAP H --> RETURN HOME PAGE", "Normal");
        GamePause = true;
      }
      else if(GameIsPause()){
        s1.play();
        s2.play();
        setAlertText("", "Normal");
        GamePause = false;
      }
    } 
    if (key == KeyCode.ENTER && NewGame){
      return true;
       //StartGame();
    }
    if (GameContinue()) {
      d1.Direction1(key);
      d2.Direction2(key);
    }
    if(key == KeyCode.Q){
      d1.setCanFire(true);
    }
    if (key == KeyCode.P) {
      d2.setCanFire(true);
    }
    return false;
  }

  public boolean GameOneFlow(KeyEvent event) throws IOException {
    KeyCode key = event.getCode();
    if (key == KeyCode.H) {
      s1.stop();
      BackToHomePage();
    }
    if (key == KeyCode.SPACE) {
      if (GameContinue()) {
        s1.pause();
        setAlertText("TAP SPACE --> CONTINUE THE GAME\n\nTAP H --> RETURN HOME PAGE", "Normal");
        GamePause = true;
      } else if (GameIsPause()) {
        s1.play();
        setAlertText("", "Normal");
        GamePause = false;
      }
    }
    if (key == KeyCode.ENTER && NewGame) {
      return true;
    }
    if (GameContinue()) {
      d1.Direction3(key);
    }
    if (key == KeyCode.Q) {
      d1.setCanFire(true);
    }
    return false;
  }
  private boolean GameContinue(){
    return !NewGame && !GamePause ;
  }
  private boolean GameIsPause(){
    return  GamePause && !NewGame;
  }
  public void setAlertText(String text, String Id) {
    ObservableList<Node> children = GameCurrentChildrenArray.Instance.get();
    children.remove(AlertText);
    AlertText.setText(text);
    AlertText.setAlignment(Pos.CENTER);
    AlertText.setId(Id);
    children.add(AlertText);
  }
  public void BackToHomePage() throws IOException{
    MusicController.StopBackground1();
    FXMLLoader loader = new FXMLLoader(getClass().getResource("../Scene/Home.fxml"));
    Parent root = loader.load();
    App.stage.setScene(new Scene(root));
  }
  public void SetNewGame(boolean n){
    this.NewGame = n;
  }
  public void SetGamePause(boolean n){
    this.GamePause = n;
  }
  public boolean IsNewGame(){
    return NewGame;
  }
  public boolean IsPause(){
    return GamePause;
  }
  public void welcome() {
    setAlertText("TAP ENTER TO START NEW GAME", "Normal");
  }
}
