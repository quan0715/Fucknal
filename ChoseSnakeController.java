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
import javafx.scene.layout.Pane;
import javafx.util.Pair;

public class ChoseSnakeController implements Initializable{
  @FXML private GridPane table;
  private Snake[][] snakes = new Snake[4][5];
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
  @FXML Pane P1select;
  @FXML Pane P2select;
  @FXML Pane P1Hover;
  @FXML Pane P2Hover;
  @FXML Pane Hover;
  @Override
  public void initialize(URL location, ResourceBundle resources) {
    table.getChildren().remove(P1select);
    table.getChildren().remove(P2select);
    table.getChildren().remove(Hover);
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
    table.requestFocus();
    table.setOnKeyPressed((e)->{
      if(e.getCode()==KeyCode.ENTER)fixed1=!fixed1;
      if(e.getCode()==KeyCode.SPACE)fixed2=!fixed2;
      if(!fixed1)
        switch(e.getCode()){
          case UP:
            hover1=new Pair<>((hover1.getKey())%4,(hover1.getValue()-1)%5);
            break;
          case DOWN:
            hover1=new Pair<>((hover1.getKey())%4,(hover1.getValue()+1)%5);
            break;
          case LEFT:
            hover1=new Pair<>((hover1.getKey()-1)%4,(hover1.getValue())%5);
            break;
          case RIGHT:
            hover1=new Pair<>((hover1.getKey()+1)%4,(hover1.getValue())%5);
            break;
          default:
          break;
        }
      if(!fixed2)
        switch(e.getCode()){
          case W:
          hover2=new Pair<>((hover2.getKey())%4,(hover2.getValue()-1)%5);
          break;
          case S:
          hover2=new Pair<>((hover2.getKey())%4,(hover2.getValue()+1)%5);
          break;
          case A:
          hover2=new Pair<>((hover2.getKey()-1)%4,(hover2.getValue())%5);
          break;
          case D:
          hover2=new Pair<>((hover2.getKey()+1)%4,(hover2.getValue())%5);
          break;
          default:
          break;
        }
      System.out.println(hover2.getKey() + " " +hover2.getValue());
      
      GridPane.setColumnIndex(P1Hover, hover1.getKey());
      GridPane.setRowIndex(P1Hover, hover1.getValue());
      GridPane.setColumnIndex(P2Hover, hover2.getKey());
      GridPane.setRowIndex(P2Hover,hover2.getValue());
    });
  }
}
