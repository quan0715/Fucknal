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
  private double SpeedUp = 0.5;
  private double currentRate;
  private Distant light ;
  private Lighting l;
  @Override
  protected void FoodInit() {
    light = new Distant(45, 45, Color.web("#00ccff"));
    l = new Lighting();
    l.setLight(light);
    l.setDiffuseConstant(1.5);
    image = new Image(getClass().getResource("../img/ice.png").toString());
    body.setFill(new ImagePattern(image));
  }

  @Override
  protected void Ontouch(SnakeBody s) {
    s.AddNewBody();
    s.SetRate(0.12 + s.GetRate() * 0.97);
    MusicController.EatFoodPop();
    FoodGenerator.RefreshFood();
  }
  @Override
  protected void Cast(SnakeBody s) {
    currentRate = s.GetRate();
    s.SetRate(currentRate * SpeedUp);
    s.SnakeEffect(l);
    Timeline speedup = new Timeline(new KeyFrame(Duration.millis(3000), e -> {
      s.SnakeEffect(null);
      s.SetRate(s.GetRate() / SpeedUp);
    }));
    speedup.setCycleCount(1);
    speedup.play();
  }
}
