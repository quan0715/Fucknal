package Application.SingletonAndTemplate;

import java.util.List;

import Application.Enum.Point;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.shape.Rectangle;

public abstract class Food{
  protected Image image;
  protected Point FoodPosition;
  protected Rectangle body;
  private FoodEvent m_event;
  protected abstract void FoodInit();
  protected abstract void OnSnakeHeadTouch(SnakeBody b);
  protected abstract void OnSnakeBodyTouch(SnakeBody s);
  protected abstract void Cast(SnakeBody s);
  protected Food(Point p){
    FoodPosition = p;
    body = new Rectangle(FoodPosition.getX(), FoodPosition.getY(), Point.GridWidth, Point.GridWidth);
    GameEntityCenter.addFood(this);
    FoodInit();
    m_event=new FoodEvent(
      this, 
      new MyCallable(){
        @Override public void call(SnakeBody b){
          OnSnakeHeadTouch(b);
        }
      }, new MyCallable() {
          @Override
          public void call(SnakeBody b) {
            OnSnakeBodyTouch(b);
          }
        },
      new MyCallable(){
        @Override public void call(SnakeBody b){
          Cast(b);
        }
      }
    );
  }
  protected void ChangeFoodPosition(Point point){
    FoodPosition = point;
    body.setX(point.getX());
    body.setY(point.getY());
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
