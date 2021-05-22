package Application.Snake;

import java.util.ArrayList;

import Application.Enum.*;
import Application.Singleton.GameCurrentChildrenArray;
import Application.Singleton.GameEntityCenter;
import javafx.collections.ObservableList;
import javafx.scene.Node;

public class SnakeBody {
  private ArrayList<Snake> Body;
  private Snake snakeInstance;
  private int HeadX;
  private int HeadY;
  private final int HeightLimit = 600;
  private final int WidthLimit = 600;
  private Direction currentDirection;
  public SnakeBody(Snake instance,int x,int y) {
    HeadX = x;
    HeadY = y;
    currentDirection=Direction.RIGHT;
    Body = new ArrayList<Snake>();
    snakeInstance=instance;
    for(int i=0;i<3;i++)AddNewBody();
    GameEntityCenter.addSnakeBody(this);
  }
  public void Move(Direction direction){
    currentDirection=direction;
    clearOnScreen();
    switch (currentDirection) {
      case UP :
        HeadY = (HeadY - Snake.SnakeWidth) % WidthLimit;
        break;
      case DOWN:
        HeadY = (HeadY + Snake.SnakeWidth) % WidthLimit;
        break;
      case RIGHT:
        HeadX = (HeadX + Snake.SnakeWidth) % HeightLimit;
        break;
      case LEFT:
        HeadX = (HeadX - Snake.SnakeWidth) % HeightLimit;
        break;
    }
    HeadX=(HeadX + WidthLimit) % WidthLimit;
    HeadY=(HeadY + HeightLimit) % HeightLimit;
    for (int i = Body.size() -1 ; i > 0; i--) {
      Body.get(i).ChangPosition(GetBodyPosition(i - 1));
    }
    Body.get(0).ChangPosition(new Point(HeadX,HeadY));
    showOnScreen();
  }
  public void AddNewBody(){
    if(Body.size()==0){
      try{
        Snake bod=snakeInstance.getClass().getDeclaredConstructor().newInstance();
        bod.InitialSnakeBody(new Point(HeadX, HeadY));
        Body.add(bod);}
      catch(Exception e){
        System.out.println("fail adding new body when Body.size()==0");
      }
      return;
    }
    int x = GetBodyPosition(Body.size() - 1).getX();
    int y = GetBodyPosition(Body.size() - 1).getY();
    Move(currentDirection);
    try{
      Snake bod=snakeInstance.getClass().getDeclaredConstructor().newInstance();
      bod.InitialSnakeBody(new Point(x, y));
      Body.add(bod);}
    catch(Exception e){
      System.out.println("fail adding new body");
    }
  }
  public Point GetBodyPosition(int id){
    return Body.get(id).GetPosition();
  }
  public Point GetHead(){
    return new Point(HeadX,HeadY);
  }
  public void clearOnScreen() {
    ObservableList<Node> children=GameCurrentChildrenArray.Instance.get();
    for (Snake snake : Body) children.remove(snake.GetBody());
  }
  public void showOnScreen() {
    ObservableList<Node> children=GameCurrentChildrenArray.Instance.get();
    for (Snake snake : Body) children.add(snake.GetBody());
  }
  public SnakePart whatPart(Point p){
    for(int i=1;i<Body.size();i++)
      if(p.getX()==GetBodyPosition(i).getX()&&p.getY()==GetBodyPosition(i).getY())return SnakePart.BODY;
    if(p.getX()==HeadX&&p.getY()==HeadY)return SnakePart.HEAD;
    return SnakePart.NONE;
  }
}
