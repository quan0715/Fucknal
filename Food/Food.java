package Application.Food;

import java.util.List;

import Application.Enum.Point;
import Application.Singleton.FoodEventCenter;
import Application.Singleton.GameCurrentChildrenArray;
import Application.Singleton.GameEntityCenter;
import Application.Snake.SnakeBody;
import Application.Template.FoodEvent;
import Application.Template.MyCallable;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.shape.Rectangle;

public abstract class Food{
  protected Image image;
  private Point FoodPosition;
  protected Rectangle body;
  private FoodEvent m_event;
  protected abstract void FoodInit();
  protected abstract void Ontouch(SnakeBody s);
  protected Food(){
    FoodPosition = Point.getrandompointGrid();
    body = new Rectangle(FoodPosition.getX(), FoodPosition.getY(), Point.GridWidth, Point.GridWidth);
    FoodInit();
    GameEntityCenter.addFood(this);
    m_event=new FoodEvent(this, new MyCallable(){
      @Override public void call(SnakeBody b){
        Ontouch(b);
      }
    });
  }
  public Point GetFoodPosition(){
    return FoodPosition;
  }
  public void showOnScreen() {
    List<Node> children=GameCurrentChildrenArray.Instance.get();
    if(children!=null&&!children.contains(body))
      GameCurrentChildrenArray.Instance.get().add(body);
  }
  public void clearOnScreen() {
    GameCurrentChildrenArray.Instance.get().remove(body);
  }
  public FoodEvent getEvent() {
    return m_event;
  }
}
