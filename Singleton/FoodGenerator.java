package Application.Singleton;

import Application.Food.Food;
import Application.Food.NormalFood;

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
