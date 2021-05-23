package Application.Controller;

import java.io.IOException;
import java.net.URL;
import java.util.Random;
import java.util.ResourceBundle;

import Application.App;
import Application.Enum.Direction;
import Application.Singleton.GameCurrentChildrenArray;
import Application.Singleton.MusicController;
import Application.Snake.PythonSnake;
import Application.Snake.Snake;
import Application.Snake.SnakeBody;
import Application.Snake.VscodeSnake;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;


public class HomeController implements Initializable{
  @FXML Button ButtonOne;
  @FXML public TextField GamePin;
  @FXML private ImageView Sound;
  @FXML AnchorPane Jack;
  private static boolean SoundOff = false;
  private Image sound = new Image(getClass().getResource("../img/sound.png").toString());
  private Image nosound = new Image(getClass().getResource("../img/no-sound.png").toString());
  public static Snake Player1=new PythonSnake();
  public static Snake Player2=new VscodeSnake();
  public void SwitchOneManGame() throws IOException{
    MusicController.StopBackground2();
    MusicController.ButtonClickSound();
    FXMLLoader loader = new FXMLLoader(getClass().getResource("../Scene/table.fxml"));
    String PinName = GamePin.getText();
    System.out.println(PinName);
    Parent root = loader.load();
    Scene scene = new Scene(root);
    App.stage.setScene(scene);
    GameOneController controller = loader.getController();
    controller.init();
    controller.GetPinName(PinName);
    scene.setOnKeyPressed(new javafx.event.EventHandler<KeyEvent>() {
      @Override
      public void handle(KeyEvent event) {
        try {
          controller.KeyEven(event);
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
    });
  }
  
  public void SwitchChoseSnake() throws IOException {
    MusicController.ButtonClickSound();
    FXMLLoader loader = new FXMLLoader(getClass().getResource("../Scene/SnakeControl.fxml"));
    String PinName = GamePin.getText();
    System.out.println(PinName);
    Parent root = loader.load();
    Scene scene = new Scene(root);
    ChoseSnakeController controller=loader.getController();
    controller.init();
    App.stage.setScene(scene);
  }
  public void SwitchTwoManGame(ActionEvent event) throws IOException {
    MusicController.StopBackground2();
    MusicController.ButtonClickSound();
    FXMLLoader loader = new FXMLLoader(getClass().getResource("../Scene/table2.fxml"));
    String PinName = GamePin.getText();
    System.out.println(PinName);
    Parent root = loader.load();
    Scene scene = new Scene(root);
    App.stage.setScene(scene);
    GameTwoController controller = loader.getController();
    controller.init();
    controller.GetPinName(PinName);
    scene.setOnKeyPressed(new javafx.event.EventHandler<KeyEvent>() {
      @Override
      public void handle(KeyEvent event) {
        try {
          controller.KeyEven(event);
        } catch (IOException e) {
          // TODO Auto-generated catch block
          e.printStackTrace();
        }
      }
    });
  }
  @Override
  public void initialize(URL location, ResourceBundle resources) {
    MusicController.PlayBackground2();
    SwitchSoundIcon(SoundOff);
    ButtonOne.getScene();
    ButtonOne.setOnKeyPressed((e) -> {
      if(e.getCode() == KeyCode.ENTER){
        e.consume();
        try {
          SwitchOneManGame();
        } catch (IOException e1) {
          // TODO Auto-generated catch block
          e1.printStackTrace();
        }
      }
    });
    GamePin.setOnAction(e->{
      e.consume();
      ButtonOne.requestFocus();
    });
  }
  public void SetMute(){
    SoundOff = !SoundOff;
    SwitchSoundIcon(SoundOff);
    MusicController.SetMute(SoundOff);
  }
  private void SwitchSoundIcon (boolean SoundOff){
    if(SoundOff){
      Sound.setImage(nosound);
    }
    else Sound.setImage(sound);
  }
  
}
