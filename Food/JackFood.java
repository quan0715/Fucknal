package Application.Food;


import Application.SingletonAndTemplate.*;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.util.Duration;

public class JackFood extends Food {
  private double SpeedUp = 2; 
  private double currentRate;
  @Override
  protected void FoodInit() {
    image = new Image(getClass().getResource("../img/Jack.png").toString());
    body.setFill(new ImagePattern(image));
  }
  @Override
  protected void Ontouch(SnakeBody s) {
    currentRate = s.GetRate();
    s.SetRate(currentRate * SpeedUp);
    Timeline speedup = new Timeline(new KeyFrame(Duration.millis(3000), e -> {
      s.SetRate(s.GetRate()/SpeedUp);
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
}
