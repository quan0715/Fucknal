package Application.Food;

import java.util.concurrent.Callable;

import Application.Enum.Point;
import Application.SingletonAndTemplate.Food;
import Application.SingletonAndTemplate.FoodGenerator;
import Application.SingletonAndTemplate.MusicController;
import Application.SingletonAndTemplate.SnakeBody;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.util.Duration;

public class BombFood extends Food {
    public BombFood(Point p) {
      super(p);
    }
    @Override
    protected void FoodInit() {
        image = new Image(getClass().getResource("../img/Bomb.png").toString());
        body.setFill(new ImagePattern(image));
    }
  
    @Override
    protected void Cast(SnakeBody s) {
    }
    @Override
    protected void OnSnakeHeadTouch(SnakeBody s) {
        s.AddNewBody();
        s.ScoreUp();
        MusicController.EatFoodPop();
        FoodGenerator.RefreshFood();
        s.setSkill(3, new Callable<Void>(){
            @Override
            public Void call() throws Exception {
                FoodGenerator.NewBomb(s);
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
    protected void OnSnakeBodyTouch(SnakeBody s) {
      FoodGenerator.RefreshFood();
    }
  }
