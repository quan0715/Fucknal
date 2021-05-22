package Application.Controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import Application.App;
import Application.Singleton.MusicController;
import Application.Snake.PythonSnake;
import Application.Snake.Snake;
import Application.Snake.VscodeSnake;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;


public class HomeController implements Initializable{
  @FXML Button ButtonOne;
  @FXML public TextField GamePin;
  public static Snake Player1=new PythonSnake();
  public static Snake Player2=new VscodeSnake();
  public void SwitchOneManGame() throws IOException{
    MusicController.StopBackground2();
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
  
}
