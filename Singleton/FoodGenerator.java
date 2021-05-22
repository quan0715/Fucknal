package Application.Singleton;

import java.util.ArrayList;
import java.util.List;

import Application.Food.Food;
import Application.Food.NormalFood;

public class FoodGenerator{
  private static FoodGenerator instance=new FoodGenerator();
  private List<Food> foods;
  private FoodGenerator(){
    foods=new ArrayList<>();
    foods.add(new NormalFood());
  }
  public static Food getFood(){return instance.foods.get(0);}
  public static void RefreshFood(){
    GameEntityCenter.removeFood(instance.foods.get(0));
    instance.foods.set(0,new NormalFood());
    GameEntityCenter.addFood(instance.foods.get(0));
  }
}
