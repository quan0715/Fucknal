package Application.Food;

import Application.Enum.Point;
import Application.SingletonAndTemplate.Food;
import Application.SingletonAndTemplate.SnakeBody;
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
    
  }
  @Override
  protected void OnSnakeBodyTouch(SnakeBody s) {
    
  }
}
