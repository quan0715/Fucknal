package Application.Food;

import Application.Snake.SnakeBody;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;

public class NormalFood extends Food{
    @Override
    protected void FoodInit() {
      image = new Image(getClass().getResource("../img/avocado.png").toString());
      body.setFill(new ImagePattern(image));
    }

    @Override
    protected void Ontouch(SnakeBody s) {
      s.AddNewBody();
    }
}
