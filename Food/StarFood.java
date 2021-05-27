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

public class StarFood extends Food {
  private Distant light;
  private Lighting l;
  private double SpeedUp = 1.5;
  @Override
  protected void FoodInit() {
    light = new Distant(45, 45, Color.web("#ffee00"));
    l = new Lighting();
    l.setLight(light);
    l.setSpecularConstant(1.5);
    l.setDiffuseConstant(1.5);
    l.setSurfaceScale(0.0);
    image = new Image(getClass().getResource("../img/star.png").toString());
    body.setFill(new ImagePattern(image));
  }
  @Override
  protected void Ontouch(SnakeBody s) {
    s.AddNewBody();
    s.score += 10;
    s.SetRate(0.12 + s.GetRate() * 0.97);
    s.RateBuff(SpeedUp);
    s.SnakeEffect(l);
    Timeline speedup = new Timeline(new KeyFrame(Duration.millis(5000), e -> {
      s.SnakeEffect(null);
      s.RateNuff(SpeedUp);
    }));
    speedup.setCycleCount(1);
    speedup.play();
    MusicController.EatFoodPop();
    FoodGenerator.RefreshFood();
  }
  @Override
  protected void Cast(SnakeBody s) {  
  }
}
