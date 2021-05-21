package Application;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

public abstract class Food{
  private static ArrayList<EatEvent> eatEvents=new ArrayList<>();
  protected Point FoodPosition;
  protected Rectangle body;
  protected Image image;
  public abstract void FoodInit();
  protected Food(){
    FoodPosition = Point.getrandompointGrid();
    body = new Rectangle(FoodPosition.getX(), FoodPosition.getY(), Point.GridWidth, Point.GridWidth);
    FoodInit();
  }
  public void Refresh(){
    FoodPosition = Point.getrandompointGrid();
    body = new Rectangle(FoodPosition.getX(), FoodPosition.getY(), Point.GridWidth, Point.GridWidth);
  }
  //public abstract void Eaten();
  public void setColor(Color color){
    body.setFill(color);
  }
  public Point GetFoodPosition(){
    return FoodPosition;
  }
  public Rectangle GetFoodBody(){
    return body;
  }
  public static void addFoodHandler(SnakeBody body, Callable<Void> calledFunction){
    eatEvents.add(new EatEvent(body, calledFunction));
  }
  
  public static void removeFoodHandler(SnakeBody body) {
    List<EatEvent> removeList = new ArrayList<>();
    for (EatEvent e : eatEvents)
    if (e.body == body)removeList.add(e);
    for (EatEvent e : removeList)eatEvents.remove(e);
  }
  public void listen() {
    for (EatEvent es : eatEvents)
      try {
        es.Call(this);
      } catch (Exception e1) {
        e1.printStackTrace();
      }
  }
}
