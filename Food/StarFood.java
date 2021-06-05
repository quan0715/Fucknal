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

public class StarFood extends Food {
  private Distant lightY;
  private Distant lightG;
  private Distant lightB;
  private Lighting l;
  private int spark = 0;
  private double SpeedUp = 1.5;
  public StarFood(Point p) {
    super(p);
  }
  @Override
  protected void FoodInit() {
    lightY = new Distant(45, 45, Color.web("#ffee00"));
    lightG = new Distant(45, 45, Color.web("#0fff6e"));
    lightB = new Distant(45, 45, Color.web("#0fc2ff"));
    l = new Lighting();
    l.setSpecularConstant(1.5);
    l.setDiffuseConstant(1.5);
    l.setSurfaceScale(0.0);
    image = new Image(getClass().getResource("../img/star.png").toString());
    body.setFill(new ImagePattern(image));
  }
  @Override
  protected void OnSnakeHeadTouch(SnakeBody s) {
    s.AddNewBody();
    s.ScoreUp();
    s.RateBuff(SpeedUp);
    s.woody++;
    s.SkillText("SUPER", "Star");
    Timeline SparkTimeline = new Timeline( new KeyFrame(Duration.millis(50),e ->{
      switch(spark){
        case 0:
          s.SnakeEffect(null);
          l.setLight(lightY);
          s.SnakeEffect(l);
          break;
        case 1:
          s.SnakeEffect(null);
          l.setLight(lightB);
          s.SnakeEffect(l);
          break;
        case 2:
          s.SnakeEffect(null);
          l.setLight(lightG);
          s.SnakeEffect(l);
          break;
        case 3:
          s.SnakeEffect(null);
          break;
      }
      spark = (spark+1) % 4;
    }));
    Timeline speedup = new Timeline(new KeyFrame(Duration.millis(4000), e -> {
      s.SnakeEffect(null);
      s.RateNuff(SpeedUp);
      MusicController.SuperStarFood(false);
      s.SkillText(null,"");
      s.woody--;
    }));
    SparkTimeline.setCycleCount(80);
    SparkTimeline.play();
    speedup.setCycleCount(1);
    speedup.play();
    MusicController.SuperStarFood(true);
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
