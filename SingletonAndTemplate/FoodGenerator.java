package Application.SingletonAndTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import Application.Food.BombFood;
import Application.Food.FireFood;
import Application.Food.IceFood;
import Application.Food.JackFood;
import Application.Food.NormalFood;
import Application.Food.StarFood;

public class FoodGenerator{
  private static FoodGenerator instance=new FoodGenerator();
  private List<Food> foods;
  private static List<Integer> FoodRate;
  private static Random random = new Random();
  private FoodGenerator(){
    foods=new ArrayList<>();
    FoodRate = new ArrayList<>();
    FoodRate.add(54); //n 54
    FoodRate.add(62); //b 8
    FoodRate.add(70); //f 8
    FoodRate.add(83); //i 13
    FoodRate.add(97); //j 14
    FoodRate.add(100); //s 3 
    foods.add(new NormalFood());
  }
  public static Food getFood(){return instance.foods.get(0);}
  public static void RefreshFood(){
    GameEntityCenter.removeFood(instance.foods.get(0));
    GetRandomFood(random.nextInt(100));
  }
  public static void GetRandomFood(int c){
    if(c < FoodRate.get(0)){
      instance.foods.set(0, new StarFood());
    }
    else if (c < FoodRate.get(1)) {
      instance.foods.set(0, new BombFood());
    }
    else if (c <= FoodRate.get(2)) {
      instance.foods.set(0, new FireFood());
    }
    else if(c < FoodRate.get(3)){
      instance.foods.set(0, new IceFood());
    }
    else if (c < FoodRate.get(4)) {
      instance.foods.set(0, new JackFood());
    }
    else if (c < FoodRate.get(5)) {
      instance.foods.set(0, new StarFood());
    }
  }
}
