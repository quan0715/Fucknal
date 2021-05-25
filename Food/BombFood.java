package Application.Food;

import Application.Singleton.FoodGenerator;
import Application.Singleton.MusicController;
import Application.Snake.SnakeBody;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;

public class BombFood extends Food {
  @Override
  protected void FoodInit() {
    image = new Image(getClass().getResource("../img/bomb.png").toString());
    body.setFill(new ImagePattern(image));
  }

  @Override
  protected void Ontouch(SnakeBody s) {
    s.AddNewBody();
    s.score += 10;
    s.SetRate(0.12 + s.GetRate() * 0.97);
    MusicController.EatFoodPop();
    FoodGenerator.RefreshFood();
  }
}
