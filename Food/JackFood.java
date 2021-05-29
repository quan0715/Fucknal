package Application.Food;


import Application.Enum.Point;
import Application.SingletonAndTemplate.*;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.effect.Lighting;
import javafx.scene.effect.Light.Distant;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.util.Duration;

public class JackFood extends Food {
  public JackFood(Point p) {
    super(p);
  }
  private double SpeedUp = 2; 
  private Distant light;
  private Lighting l;
  @Override
  protected void FoodInit() {
    light = new Distant(45, 45, Color.web("#575757"));
    l = new Lighting();
    l.setLight(light);
    l.setSpecularConstant(0.0);
    l.setSurfaceScale(0.0);
    l.setDiffuseConstant(0.4);
    image = new Image(getClass().getResource("../img/Jack.png").toString());
    body.setFill(new ImagePattern(image));
  }
  @Override
  protected void OnSnakeHeadTouch(SnakeBody s) {
    s.RateBuff(SpeedUp);
    s.SnakeEffect(l);
    Timeline speedup = new Timeline(new KeyFrame(Duration.millis(3000), e -> {
      s.SnakeEffect(null);
      s.RateNuff(SpeedUp);
    }));
    speedup.setCycleCount(1);
    speedup.play();
    //s.AddNewBody();
    //s.score += 10;
    //s.SetRate(0.12 + s.GetRate() * 0.97);
    MusicController.EatFoodPop();
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
