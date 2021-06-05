package Application.Food;

import Application.Enum.Point;
import Application.SingletonAndTemplate.*;
import Application.SingletonAndTemplate.Food;
import Application.SingletonAndTemplate.SnakeBody;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.util.Duration;

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
    s.SkillText("BIG", "Normal");
    MusicController.GrowingUp();
    Timeline add = new Timeline( new KeyFrame(Duration.millis(500) , e -> {
      s.SetRate(0.12 + s.GetRate() * 0.97);
      s.AddNewBody();
      s.score += 10;
    }));
    Timeline text = new Timeline(new KeyFrame(Duration.millis(1500), e -> {
      s.SkillText(null, null);
    }));
    text.setCycleCount(1);
    text.play();
    add.setCycleCount(3);
    add.play();
    //MusicController.EatFoodPop();
    FoodGenerator.RefreshFood();
  }

  @Override
  protected void Cast(SnakeBody s) {  
  }
  @Override
  protected void OnSnakeBodyTouch(SnakeBody s) {
    FoodGenerator.RefreshFood();
  }
}
