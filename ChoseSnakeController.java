package Application;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;

public class ChoseSnakeController {
  
  
  public void BackToHomePage() throws IOException {
    FXMLLoader loader = new FXMLLoader(getClass().getResource("./Scene/Home.fxml"));
    Parent root = loader.load();
    Scene scene = new Scene(root);
    scene.setRoot(root);
    App.stage.setScene(scene);
  }
}
