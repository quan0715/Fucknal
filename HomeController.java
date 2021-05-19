package Application;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

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
<<<<<<< HEAD
  private static Scene controllerScene=null;
  
=======
  public static MusicController player = new MusicController();
>>>>>>> aad5e4c5be447a432fb1a89e9ad711eb07a7d16d
  public void SwitchOneManGame() throws IOException{
    player.StopBackground2();
    FXMLLoader loader = new FXMLLoader(getClass().getResource("./Scene/table.fxml"));
    String PinName = GamePin.getText();
    System.out.println(PinName);
    Parent root = loader.load();
    Scene scene = new Scene(root);
    App.stage.setScene(scene);
    GameOneController controller = loader.getController();
    controller.init(Player1);
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
  
  public void SwitchChoseSnake() throws IOException {
    //player.StopBackground2();
    FXMLLoader loader = new FXMLLoader(getClass().getResource("./Scene/SnakeControl.fxml"));
    String PinName = GamePin.getText();
    System.out.println(PinName);
    Parent root = loader.load();
    Scene scene = new Scene(root);
    ChoseSnakeController controller=loader.getController();
    controller.init();
    App.stage.setScene(scene);
  }
  public void SwitchTwoManGame(ActionEvent event) throws IOException {
    player.StopBackground2();
    FXMLLoader loader = new FXMLLoader(getClass().getResource("./Scene/table2.fxml"));
    String PinName = GamePin.getText();
    System.out.println(PinName);
    Parent root = loader.load();
    Scene scene = new Scene(root);
    App.stage.setScene(scene);
    GameTwoController controller = loader.getController();
    controller.init(Player1, Player2);
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
    player.PlayBackground2();
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
