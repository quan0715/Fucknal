package Application;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;

public class FoodGenerator{
  private Food food;
  private Timeline listenerTimeline;
  //private Class currentClass;
  public FoodGenerator(NormalFood food){
    this.food = food;
    listenerTimeline = new Timeline(new KeyFrame(Duration.millis(1), e -> {this.food.listen();}));
    listenerTimeline.setCycleCount(-1);
    listenerTimeline.play();
  }
  
  public void RefreshFood(){
    GameCurrentChildrenArray.Instance.get().remove(food.GetFoodBody());
    food=new NormalFood();
    GameCurrentChildrenArray.Instance.get().add(food.GetFoodBody());
  }
  public Food getFood(){
    return food;
  }
}
