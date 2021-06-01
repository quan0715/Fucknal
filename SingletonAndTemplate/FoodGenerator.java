package Application.SingletonAndTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import Application.Food.TripleFood;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;
import Application.Enum.Direction;
import Application.Enum.Point;
import Application.Food.FireBullet;
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
    FoodRate.add(50); //n 50
    FoodRate.add(60); //b 10
    FoodRate.add(70); //f 10
    FoodRate.add(83); //i 13
    FoodRate.add(97); //j 14
    FoodRate.add(100); //s 3 
    foods.add(new NormalFood(Point.getrandompointGrid()));
  }
  public static Food getFood(){return instance.foods.get(0);}
  public static void RefreshFood(){
    GameEntityCenter.removeFood(instance.foods.get(0));
    GetRandomFood(random.nextInt(100));
  }
  public static void GetRandomFood(int c){
    if(c < FoodRate.get(0)){
      instance.foods.set(0, new FireFood(Point.getrandompointGrid()));
    }
    else if (c < FoodRate.get(1)) {
      instance.foods.set(0, new TripleFood(Point.getrandompointGrid()));
    }
    else if (c <= FoodRate.get(2)) {
      instance.foods.set(0, new FireFood(Point.getrandompointGrid()));
    }
    else if(c < FoodRate.get(3)){
      instance.foods.set(0, new IceFood(Point.getrandompointGrid()));
    }
    else if (c < FoodRate.get(4)) {
      instance.foods.set(0, new JackFood(Point.getrandompointGrid()));
    }
    else if (c < FoodRate.get(5)) {
      instance.foods.set(0, new StarFood(Point.getrandompointGrid()));
    }
  }
  public static void NewBullet(Direction d,Point p,int speed){
    final FireBullet bullet = new FireBullet(p);
    int X = p.getX();
    int Y = p.getY();
    switch (d) {
      case UP:
        Y = (Y - 20) % 600;
        break;
      case DOWN:
        Y = (Y + 20) % 600;
        break;
      case RIGHT:
        X = (X + 20) % 600;
        break;
      case LEFT:
        X = (X - 20) % 600;
        break;
    }
    bullet.ChangeFoodPosition(new Point(X, Y));
    Timeline move = new Timeline(new KeyFrame(Duration.millis(speed*0.4),e ->{
      int x = bullet.GetFoodPosition().getX();
      int y = bullet.GetFoodPosition().getY();
      switch (d) {
        case UP:
          y -= 20;
          break;
        case DOWN:
          y += 20;
          break;
        case RIGHT:
          x += 20;
          break;
        case LEFT:
          x -= 20;
          break;
      }
      if(x<0||x>=600||y<0||y>=600){
        GameEntityCenter.removeFood(bullet);
      }
      bullet.ChangeFoodPosition(new Point(x,y));
    }));
    move.setCycleCount(30);
    move.play();
  }
  public static void NewBomb(Point getHead) {
  }
}
