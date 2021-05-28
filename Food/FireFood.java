package Application.Food;

import Application.Enum.Direction;
import Application.Enum.Point;
import Application.SingletonAndTemplate.*;
import Application.SingletonAndTemplate.Food;
import Application.SingletonAndTemplate.SnakeBody;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;

public class FireFood extends Food {
  public FireFood(Point p) {
    super(p);
  }

  @Override
  protected void FoodInit() {
    
    image = new Image(getClass().getResource("../img/fire.png").toString());
    body.setFill(new ImagePattern(image));
  }

  @Override
  protected void OnSnakeHeadTouch(SnakeBody s) {
    s.AddNewBody();
    s.score += 10;
    s.SetRate(0.12 + s.GetRate() * 0.97);
    Direction d = s.GetDirection();
    Point p = s.GetHead();
    int sp = (int)(s.GetSpeed() * s.GetFoodBuff());
    FoodGenerator.NewBullet(d, p, sp);
    MusicController.EatFoodPop();
    FoodGenerator.RefreshFood();
  }
  @Override
  protected void Cast(SnakeBody s) {  
    
  }
  @Override
  protected void OnSnakeBodyTouch(SnakeBody s) {
    
  }
}
