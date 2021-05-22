package Application.Food;

import Application.Enum.Point;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class NormalFood extends Food{
    @Override
    public void FoodInit() {
      FoodPosition = Point.getrandompointGrid();
      body = new Rectangle(FoodPosition.getX(), FoodPosition.getY(), Point.GridWidth, Point.GridWidth);
      body.setFill(Color.RED);
      System.out.println(FoodPosition.toString());
    }
}
