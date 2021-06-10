package SnakeGame.SingletonAndTemplate;

import java.util.LinkedList;
import java.util.Queue;

import SnakeGame.Enum.Direction;
import javafx.scene.input.KeyCode;

public class DirectionController {
  private Direction lastDirection;
  private Queue<Direction> direct;
  private boolean CanFire;
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
    CanFire = false;
  }
  public Direction NextDirection(){
    return direct.size() == 1 ? direct.peek() : direct.poll();
  }
  public void Direction1 (KeyCode key){
    if(key==KeyCode.W && lastDirection != Direction.UP&&lastDirection != Direction.DOWN){
      
      lastDirection = Direction.UP;
      direct.offer(Direction.UP);
    }
    else if(key == KeyCode.S && lastDirection!=Direction.UP &&lastDirection!=Direction.DOWN){
      
      lastDirection = Direction.DOWN;
      direct.offer(Direction.DOWN);
    }
    else if(key == KeyCode.A && lastDirection!= Direction.RIGHT&&lastDirection!=Direction.LEFT){
      
      lastDirection = Direction.LEFT;
      direct.offer(Direction.LEFT);
    }
    else if(key == KeyCode.D && lastDirection != Direction.RIGHT && lastDirection !=Direction.LEFT){
      
      lastDirection = Direction.RIGHT;
      direct.offer(Direction.RIGHT);
    }
  }
  public void Direction2(KeyCode key) {
    if (key == KeyCode.UP && lastDirection != Direction.UP && lastDirection != Direction.DOWN) {
      lastDirection = Direction.UP;
      direct.offer(Direction.UP);
    } else if (key == KeyCode.DOWN && lastDirection != Direction.UP && lastDirection != Direction.DOWN) {
      
      lastDirection = Direction.DOWN;
      direct.offer(Direction.DOWN);
    } else if (key == KeyCode.LEFT && lastDirection != Direction.RIGHT && lastDirection != Direction.LEFT) {
      
      lastDirection = Direction.LEFT;
      direct.offer(Direction.LEFT);
    } else if (key == KeyCode.RIGHT && lastDirection != Direction.RIGHT && lastDirection != Direction.LEFT) {
      
      lastDirection = Direction.RIGHT;
      direct.offer(Direction.RIGHT);
    }
  }
  public void Direction3(KeyCode key){
   Direction1(key);
   Direction2(key);
  }
  public void setCanFire(boolean t){
    CanFire = t;
  }
  public boolean GetCanFire() {
    return CanFire;
  }
}

