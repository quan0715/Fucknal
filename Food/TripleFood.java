package SnakeGame.Food;

import SnakeGame.Enum.Point;
import SnakeGame.SingletonAndTemplate.*;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.effect.Lighting;
import javafx.scene.effect.Light.Distant;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.util.Duration;

public class TripleFood extends Food {
  private int Case = 0;
  private Distant lightW;
  private Lighting l;
  public TripleFood(Point p) {
    super(p);
  }
  @Override
  protected void FoodInit() {
    image = new Image(getClass().getResource("../img/bananas.png").toString());
    body.setFill(new ImagePattern(image));
    lightW = new Distant(45, 45, Color.WHITE);
    l = new Lighting(lightW);
    l.setSurfaceScale(0.0);
    l.setSpecularExponent(40.0);
    l.setSpecularConstant(2.0);
    l.setDiffuseConstant(2.0);
    Case = 0;
  }
  @Override
  protected void OnSnakeHeadTouch(SnakeBody s) {
    s.SkillText("BIG", "Normal");
    MusicController.GrowingUp();
    Timeline ef = new Timeline(new KeyFrame(Duration.millis(180), e -> {
      if(Case == 0) s.clearOnScreen();
      else s.showOnScreen();
        //l.setSpecularExponent((Case == 0 ? 40.0 : 20.0));
        Case = (Case + 1) % 2;
    }));
    Timeline add = new Timeline( new KeyFrame(Duration.millis(500) , e -> {
      s.ScoreUp();
      s.AddNewBody();
      s.ScoreUp();
    }));
    Timeline text = new Timeline(new KeyFrame(Duration.millis(1500), e -> {
      s.SkillText(null, null);
      ef.stop();
    }));
    ef.setCycleCount(-1);
    ef.play();
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
