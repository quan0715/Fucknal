package Application;

import javafx.scene.layout.AnchorPane;

public class FoodGenerator{
  private Food food;
  //private Class currentClass;
  public FoodGenerator(NormalFood food){
    this.food = food;
  }
  
  public void RefreshFood(){
    GameCurrentChildrenArray.Instance.get().remove(food.GetFoodBody());
    food.FoodInit();
    GameCurrentChildrenArray.Instance.get().add(food.GetFoodBody());
  }
  public Food getFood(){
    return food;
  }
}
