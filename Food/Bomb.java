package Application.Food;

import Application.Enum.Point;
import Application.SingletonAndTemplate.Food;
import Application.SingletonAndTemplate.MusicController;
import Application.SingletonAndTemplate.SnakeBody;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;

public class Bomb extends Food {
    public Bomb(Point p) {
      super(p);
    }
    @Override
    protected void FoodInit() {
      image = new Image(getClass().getResource("../img/TNT.png").toString());
      body.setFill(new ImagePattern(image));
      MusicController.Boom();
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
        s.score-=10;
        s.SetRate((s.GetRate() - 0.12) / 0.97);
      }
    }
  }
