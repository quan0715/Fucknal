package Application.Singleton;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import Application.Food.Food;
import Application.Food.IceFood;
import Application.Food.JackFood;
import Application.Food.NormalFood;

public class FoodGenerator{
  private static FoodGenerator instance=new FoodGenerator();
  private List<Food> foods;
  private static Random random = new Random();
  private FoodGenerator(){
    foods=new ArrayList<>();
    foods.add(new NormalFood());
  }
  public static Food getFood(){return instance.foods.get(0);}
  public static void RefreshFood(){
    GameEntityCenter.removeFood(instance.foods.get(0));
    GetRandomFood(random.nextInt(6));
    GameEntityCenter.addFood(instance.foods.get(0));
  }
  public static void GetRandomFood(int c){
    switch(c){
      case 0:
        instance.foods.set(0, new NormalFood());
        break;
      case 1:
        instance.foods.set(0, new JackFood());
        break;
      case 2:
        instance.foods.set(0, new IceFood());
        break;
      case 3:
        instance.foods.set(0, new IceFood());
        break;
      case 4:
        instance.foods.set(0, new NormalFood());
        break;
      case 5:
        instance.foods.set(0, new JackFood());
        break;
      default:
        break;
    }
  }
}
