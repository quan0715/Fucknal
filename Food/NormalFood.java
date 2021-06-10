package SnakeGame.Food;

import SnakeGame.Enum.Point;
import SnakeGame.SingletonAndTemplate.*;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.util.Duration;

public class NormalFood extends Food{
    public NormalFood(Point p) {
      super(p);
    }

    @Override
    protected void FoodInit() {
      image = new Image(getClass().getResource("../img/avocado.png").toString());
      body.setFill(new ImagePattern(image));
    }

    @Override
    protected void OnSnakeHeadTouch(SnakeBody s) {
      s.AddNewBody();
      s.ScoreUp();
      s.SkillText("JACK","Normal");
      Timeline text = new Timeline(new KeyFrame(Duration.millis(2000), e -> {
        s.SkillText(null,null);
      }));
      text.setCycleCount(1);
      text.play();
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
