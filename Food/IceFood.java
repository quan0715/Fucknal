package Application.Food;


import Application.SingletonAndTemplate.*;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.effect.Lighting;
import javafx.scene.effect.Light.Distant;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.util.Duration;

public class IceFood extends Food {
  private double SlowDown = 0.5;
  private Distant light ;
  private Lighting l;
  @Override
  protected void FoodInit() {
    light = new Distant(45, 45, Color.web("#00ccff"));
    l = new Lighting();
    l.setLight(light);
    l.setSurfaceScale(0.0);
    l.setSpecularConstant(0.4);
    l.setDiffuseConstant(1.5);
    image = new Image(getClass().getResource("../img/ice.png").toString());
    body.setFill(new ImagePattern(image));
  }

  @Override
  protected void Ontouch(SnakeBody s) {
    s.AddNewBody();
    s.score+=10;
    s.SetRate(0.12 + s.GetRate() * 0.97);
    MusicController.EatFoodPop();
    FoodGenerator.RefreshFood();
  }
  @Override
  protected void Cast(SnakeBody s) {
    s.RateBuff(SlowDown);
    s.SnakeEffect(l);
    Timeline slowdown = new Timeline(new KeyFrame(Duration.millis(3000), e -> {
      s.RateNuff(SlowDown);
      s.SnakeEffect(null);
    }));
    slowdown.setCycleCount(1);
    slowdown.play();
  }
}
