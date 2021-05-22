package Application.Food;

import Application.Enum.Point;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;

public class NormalFood extends Food{
    @Override
    public void FoodInit() {
      FoodPosition = Point.getrandompointGrid();
      body = new Rectangle(FoodPosition.getX(), FoodPosition.getY(), Point.GridWidth, Point.GridWidth);
      image = new Image(getClass().getResource("../img/avocado.png").toString());
      body.setFill(new ImagePattern(image));
      System.out.println(FoodPosition.toString());
    }
}
