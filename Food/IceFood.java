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

public class IceFood extends Food {
  public IceFood(Point p) {
    super(p);
  }
  private double SlowDown = 0.5;
  private Distant light ;
  private Lighting l;
  @Override
  protected void FoodInit() {
    light = new Distant(45, 45, Color.web("#009aff"));
    l = new Lighting();
    l.setLight(light);
    l.setSurfaceScale(0.0);
    l.setSpecularExponent(0.0);
    l.setSpecularConstant(2.0);
    l.setDiffuseConstant(2.0);
    image = new Image(getClass().getResource("../img/ice.png").toString());
    body.setFill(new ImagePattern(image));
  }

  @Override
  protected void OnSnakeHeadTouch(SnakeBody s) {
    s.AddNewBody();
    s.ScoreUp();
    MusicController.EatFoodPop();
    FoodGenerator.RefreshFood();
  }
  @Override
  protected void Cast(SnakeBody s) {
    s.RateBuff(SlowDown);
    s.SnakeEffect(l);
    s.SkillText("Frozen", "Ice");
    Timeline slowdown = new Timeline(new KeyFrame(Duration.millis(3000), e -> {
      s.RateNuff(SlowDown);
      s.SnakeEffect(null);
      s.SkillText(null, "");

    }));
    slowdown.setCycleCount(1);
    slowdown.play();
  }

  @Override
  protected void OnSnakeBodyTouch(SnakeBody s) {
    FoodGenerator.RefreshFood();
  }
}
