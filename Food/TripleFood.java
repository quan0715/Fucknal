package Application.Food;

import Application.Enum.Point;
import Application.SingletonAndTemplate.*;
import Application.SingletonAndTemplate.Food;
import Application.SingletonAndTemplate.SnakeBody;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;

public class TripleFood extends Food {
  public TripleFood(Point p) {
    super(p);
  }
  @Override
  protected void FoodInit() {
    image = new Image(getClass().getResource("../img/bananas.png").toString());
    body.setFill(new ImagePattern(image));
  }

  @Override
  protected void OnSnakeHeadTouch(SnakeBody s) {
    s.SetRate(0.12 + s.GetRate() * 0.97);
    s.AddNewBody();
    s.SetRate(0.12 + s.GetRate() * 0.97);
    s.AddNewBody();
    s.SetRate(0.12 + s.GetRate() * 0.97);
    s.AddNewBody();
    s.score += 30;
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
