package Application.Food;

import java.util.concurrent.Callable;

import Application.Enum.Direction;
import Application.Enum.Point;
import Application.SingletonAndTemplate.*;
import Application.SingletonAndTemplate.Food;
import Application.SingletonAndTemplate.SnakeBody;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.effect.Lighting;
import javafx.scene.effect.Light.Distant;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.util.Duration;

public class FireFood extends Food {
  private Distant light;
  private Lighting l;
  
  public FireFood(Point p) {
    super(p);
  }

  @Override
  protected void FoodInit() {
    light = new Distant(45, 45, Color.web("#ff7700"));
    l = new Lighting();
    l.setLight(light);
    l.setSurfaceScale(0.0);
    l.setSpecularExponent(0.0);
    l.setSpecularConstant(2.0);
    l.setDiffuseConstant(2.0);
    image = new Image(getClass().getResource("../img/fire.png").toString());
    body.setFill(new ImagePattern(image));
  }

  @Override
  protected void OnSnakeHeadTouch(SnakeBody s) {
    s.AddNewBody();
    s.score += 10;
    s.SetRate(0.12 + s.GetRate() * 0.97);
    s.SnakeEffect(l);
    FoodGenerator.RefreshFood();
    MusicController.EatFoodPop();
    s.setSkill(-1, new Callable<Void>(){
      @Override
      public Void call() throws Exception {
        Direction d = s.GetDirection();
        Point p = s.GetHead();
        int sp = (int)(s.GetSpeed() * s.GetFoodBuff());
        FoodGenerator.NewBullet(d, p, sp);
        return null;
      }
    });
    Timeline cancelTimeline=new Timeline(new KeyFrame(Duration.millis(5000), e->{
      s.SnakeEffect(null);
      s.setSkill(0, null);
    }));
    cancelTimeline.setCycleCount(1);
    cancelTimeline.play();
  }
  @Override
  protected void Cast(SnakeBody s) {  
    
  }
  @Override
  protected void OnSnakeBodyTouch(SnakeBody s) {
    FoodGenerator.RefreshFood();
  }
}
