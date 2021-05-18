package Application;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.GridPane;
import javafx.util.Pair;

public class ChoseSnakeController implements Initializable{
  @FXML private GridPane board;
  private Snake[][] snakes = new Snake[4][3];
  private Pair<Integer,Integer> hover1=new Pair<>(0,0);
  private Pair<Integer,Integer> hover2=new Pair<>(3,2);
  private boolean fixed1=false;
  private boolean fixed2=false;
  public void BackToHomePage() throws IOException {
    FXMLLoader loader = new FXMLLoader(getClass().getResource("./Scene/Home.fxml"));
    Parent root = loader.load();
    Scene scene = new Scene(root);
    scene.setRoot(root);
    App.stage.setScene(scene);
  }
  @Override
  public void initialize(URL location, ResourceBundle resources) {
    snakes[0][0]=new ClassicSnake();
    snakes[0][1]=new IGSnake();
    snakes[0][2]=new PythonSnake();
    snakes[1][0]=new VscodeSnake();
    snakes[1][1]=new ClassicSnake();
    snakes[1][2]=new ClassicSnake();
    snakes[2][0]=new ClassicSnake();
    snakes[2][1]=new ClassicSnake();
    snakes[2][2]=new ClassicSnake();
    snakes[3][0]=new ClassicSnake();
    snakes[3][1]=new ClassicSnake();
    snakes[3][2]=new ClassicSnake();
    board.requestFocus();
    board.setOnKeyPressed(e->{
      if(e.getCode()==KeyCode.ENTER)fixed1=!fixed1;
      if(e.getCode()==KeyCode.SPACE)fixed2=!fixed2;
      if(!fixed1)
        switch(e.getCode()){
          case UP:
            hover1=new Pair<>((hover1.getKey())%4,(hover1.getValue()-1)%3);
            break;
          case DOWN:
            hover1=new Pair<>((hover1.getKey())%4,(hover1.getValue()+1)%3);
            break;
          case LEFT:
            hover1=new Pair<>((hover1.getKey()-1)%4,(hover1.getValue())%3);
            break;
          case RIGHT:
            hover1=new Pair<>((hover1.getKey()+1)%4,(hover1.getValue())%3);
            break;
          default:
          break;
        }
      if(!fixed2)
        switch(e.getCode()){
          case W:
          hover2=new Pair<>((hover2.getKey())%4,(hover2.getValue()-1)%3);
          break;
          case S:
          hover2=new Pair<>((hover2.getKey())%4,(hover2.getValue()+1)%3);
          break;
          case A:
          hover2=new Pair<>((hover2.getKey()-1)%4,(hover2.getValue())%3);
          break;
          case D:
          hover2=new Pair<>((hover2.getKey()+1)%4,(hover2.getValue())%3);
          break;
          default:
          break;
        }
      //GridPane.setColumnIndex(, arg1);
    });
  }
}
