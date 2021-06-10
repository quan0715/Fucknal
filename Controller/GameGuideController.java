package SnakeGame.Controller;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import SnakeGame.App;
import SnakeGame.SingletonAndTemplate.*;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

public class GameGuideController implements Initializable {
  private List<AnchorPane> topic;
  @FXML private AnchorPane Home;
  @FXML private AnchorPane Food;
  @FXML private AnchorPane GameOne;
  @FXML private AnchorPane GameTwo;
  @FXML private AnchorPane Keyboard;
  @FXML private AnchorPane Developer;
  @FXML private Label TitleLabel;
  private int Case = 0;
  @Override
  public void initialize(URL location, ResourceBundle resources) {
   topic = new ArrayList<AnchorPane>();
   topic.add(Home);
   topic.add(Food);
   topic.add(Keyboard);
   topic.add(Developer);
   //topic.add(GameTwo);
   Case = 0;
   TitleLabel.setText("Home Page");
   Setvis(Case);
   //GameOne.setVisible(false);
   //GameTwo.setVisible(false);
  }
  public void BackToHomePage() throws IOException {
    MusicController.ButtonClickSound();
    FXMLLoader loader = new FXMLLoader(getClass().getResource("../Scene/Home.fxml"));
    Parent root = loader.load();
    Scene scene = new Scene(root);
    scene.setRoot(root);
    App.stage.setScene(scene);
  }
  public void RightShift(){
    MusicController.ButtonClickSound();
    if (Case < topic.size()-1) Case+=1;
    Setvis(Case);
  }
  
  public void LeftShift() {
    MusicController.ButtonClickSound();
    if (Case > 0) Case-=1;
    Setvis(Case);
  }
  public void setTitle(int Case){
    
  }
  public void Setvis(int id){
    if (Case == 0)
      TitleLabel.setText("HomePage");
    if (Case == 1)
      TitleLabel.setText("Food");
    if (Case == 2)
      TitleLabel.setText("Keyboard");
    if (Case == 3)
      TitleLabel.setText("Developer");
    for(int i=0;i<topic.size();i++){
      if(i != id) ((AnchorPane)topic.get(i)).setVisible(false);
    }
    ((AnchorPane)topic.get(id)).setVisible(true);
  }
}
