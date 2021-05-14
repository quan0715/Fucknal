package Application;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.Node;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;


public class HomeController implements Initializable{
  @FXML Button ButtonOne;
  @FXML public TextField GamePin;
  private Scene scene;
  public void SwitchOneManGame() throws IOException{
    FXMLLoader loader = new FXMLLoader(getClass().getResource("table.fxml"));
    String PinName = GamePin.getText();
    System.out.println(PinName);
    Parent root = loader.load();
    scene = ButtonOne.getScene();
    scene.setRoot(root);
    GameOneController controller = loader.getController();
    controller.GetPinName(PinName);
    scene.setOnKeyPressed(new javafx.event.EventHandler<KeyEvent>() {
      @Override
      public void handle(KeyEvent event) {
        controller.KeyEven(event);
      }
    });
  }
  
  public void SwitchTwoManGame(ActionEvent event) throws IOException {
    FXMLLoader loader = new FXMLLoader(getClass().getResource("table2.fxml"));
    String PinName = GamePin.getText();
    System.out.println(PinName);
    Parent root = loader.load();
    scene = (Scene) ((Node) event.getSource()).getScene();
    scene.setRoot(root);
    GameTwoController controller = loader.getController();
    controller.GetPinName(PinName);
    scene.setOnKeyPressed(new javafx.event.EventHandler<KeyEvent>() {
      @Override
      public void handle(KeyEvent event) {
        controller.KeyEven(event);
      }
    });
  }

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    scene = ButtonOne.getScene();
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
