package Application.Food;

import Application.Enum.Point;
import Application.SingletonAndTemplate.Food;
import Application.SingletonAndTemplate.SnakeBody;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;

public class Bomb extends Food {
    public Bomb(Point p) {
      super(p);
    }
    @Override
    protected void FoodInit() {
    //   image = new Image(getClass().getResource("../img/FireBullet.png").toString());
    //   body.setFill(new ImagePattern(image));
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
      for(int i=0;i<3;i++){
        s.RemoveBody();
        s.ScoreDown();
      }
    }
  }