package Application;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.Node;
import javafx.scene.input.KeyEvent;


public class HomeController {
  @FXML Button ButtonOne;
  @FXML TextField GamePin;
  private Scene scene;
  public void SwitchOneManGame(ActionEvent event) throws IOException{
    FXMLLoader loader = new FXMLLoader(getClass().getResource("table.fxml"));
    String PinName = GamePin.getText();
    System.out.println(PinName);
    Parent root = loader.load();
    scene = (Scene)((Node) event.getSource()).getScene();
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
}
