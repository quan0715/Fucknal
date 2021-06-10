package SnakeGame.Food;

import SnakeGame.Enum.Point;
import SnakeGame.SingletonAndTemplate.Food;
import SnakeGame.SingletonAndTemplate.SnakeBody;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;

public class FireBullet extends Food {
  public FireBullet(Point p) {
    super(p);
  }
  @Override
  protected void FoodInit() {
    image = new Image(getClass().getResource("../img/FireBullet.png").toString());
    body.setFill(new ImagePattern(image));
  }

  @Override
  protected void Cast(SnakeBody s) {
  }
  @Override
  protected void OnSnakeHeadTouch(SnakeBody b) {
    OnSnakeBodyTouch(b);
  }
  @Override
  protected void OnSnakeBodyTouch(SnakeBody s) {
    s.RemoveBody();
    s.ScoreDown();
  }
}
