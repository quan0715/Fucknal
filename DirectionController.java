package Application;

import java.util.LinkedList;
import java.util.Queue;

import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class DirectionController {
  private Direction lastDirection;
  private Queue<Direction> direct;
  public DirectionController (){
    lastDirection = Direction.LEFT;
    this.direct = new LinkedList<Direction>();
  }
  public void init(Direction direction){
    if(direct.size()!=0){
      direct.clear();
    }
    lastDirection = direction;
    direct.offer(direction);
  }
  public Direction NextDirection(){
    return direct.size() == 1 ? direct.peek() : direct.poll();
  }
  public void Direction1 (KeyEvent event){
    KeyCode key = event.getCode();
    if(key==KeyCode.W && lastDirection != Direction.UP&&lastDirection != Direction.DOWN){
      System.out.println("W");
      lastDirection = Direction.UP;
      direct.offer(Direction.UP);
    }
    else if(key == KeyCode.S && lastDirection!=Direction.UP &&lastDirection!=Direction.DOWN){
      System.out.println("S");
      lastDirection = Direction.DOWN;
      direct.offer(Direction.DOWN);
    }
    else if(key == KeyCode.A && lastDirection!= Direction.RIGHT&&lastDirection!=Direction.LEFT){
      System.out.println("A");
      lastDirection = Direction.LEFT;
      direct.offer(Direction.LEFT);
    }
    else if(key == KeyCode.D && lastDirection != Direction.RIGHT && lastDirection !=Direction.LEFT){
      System.out.println("D");
      lastDirection = Direction.RIGHT;
      direct.offer(Direction.RIGHT);
    }
  }
  
  public void Direction2(KeyEvent event) {
    KeyCode key = event.getCode();
    if (key == KeyCode.UP && lastDirection != Direction.UP && lastDirection != Direction.DOWN) {
      System.out.println("UP");
      lastDirection = Direction.UP;
      direct.offer(Direction.UP);
    } else if (key == KeyCode.DOWN && lastDirection != Direction.UP && lastDirection != Direction.DOWN) {
      System.out.println("DOWN");
      lastDirection = Direction.DOWN;
      direct.offer(Direction.DOWN);
    } else if (key == KeyCode.LEFT && lastDirection != Direction.RIGHT && lastDirection != Direction.LEFT) {
      System.out.println("LEFT");
      lastDirection = Direction.LEFT;
      direct.offer(Direction.LEFT);
    } else if (key == KeyCode.RIGHT && lastDirection != Direction.RIGHT && lastDirection != Direction.LEFT) {
      System.out.println("RIGHT");
      lastDirection = Direction.RIGHT;
      direct.offer(Direction.RIGHT);
    }
  }
}

