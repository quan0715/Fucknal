package Application.Snake;

import Application.SingletonAndTemplate.SnakeBodyPlayer;
import javafx.scene.control.Label;

public class InputController{
  private boolean NewGame;
  private boolean GamePause;
  private Label AlertText;
  private SnakeBodyPlayer s1;
  private SnakeBodyPlayer s2;
  private DirectionController d1;
  private DirectionController d2;
  public InputController() {

  }
  public void init(){

  }
  public void NewGame(){

  }
  public boolean IsNewGame(){
    return NewGame;
  }
  public boolean IsPause(){
    return GamePause;
  }
}
