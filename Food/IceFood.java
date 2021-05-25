package Application.Food;

import Application.Singleton.FoodGenerator;
import Application.Singleton.GameEntityCenter;
import Application.Singleton.MusicController;
import Application.Snake.SnakeBody;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.util.Duration;

public class IceFood extends Food {
  private double SpeedUp = 0.5;
  private double currentRate;

  @Override
  protected void FoodInit() {
    image = new Image(getClass().getResource("../img/ice.png").toString());
    body.setFill(new ImagePattern(image));
  }

  @Override
  protected void Ontouch(SnakeBody s) {
    SnakeBody o = GameEntityCenter.GetAnotherSnake(s);
    currentRate = o.GetRate();
    o.SetRate(currentRate * SpeedUp);
    Timeline speedup = new Timeline(new KeyFrame(Duration.millis(3000), e -> {
      o.SetRate(o.GetRate() / SpeedUp);
    }));
    o.AddNewBody();
    speedup.setCycleCount(1);
    speedup.play();
    //s.SetRate(0.12 + s.GetRate() * 0.97);
    MusicController.EatFoodPop();
    FoodGenerator.RefreshFood();
  }
}
