package Application;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

public abstract class Food{
  private Timeline foodTimeline;
  private static ArrayList<EatEvent> eatEvents=new ArrayList<>();
  protected Point FoodPosition;
  protected Rectangle body;
  public abstract void FoodInit();
  protected Food(){
    FoodPosition = Point.getrandompointGrid();
    body = new Rectangle(FoodPosition.getX(), FoodPosition.getY(), Point.GridWidth, Point.GridWidth);
    foodTimeline=new Timeline(new KeyFrame(Duration.millis(1),e->{
      for(EatEvent es:eatEvents)
        try {
          es.Call(this);
        } catch (Exception e1) {
          // TODO Auto-generated catch block
          e1.printStackTrace();
        }
    }));
    foodTimeline.setCycleCount(-1);
    foodTimeline.play();
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
    List<EatEvent> removeList=new ArrayList<>();
    for(EatEvent e:eatEvents)if(e.body==null)removeList.add(e);
    for(EatEvent e:removeList)eatEvents.remove(e);
    eatEvents.add(new EatEvent(body, calledFunction));
  }
}
