package Application;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public abstract class Food{
  private Point FoodPosition ;
  private Rectangle body;
  protected Food(){
    init();
  }
  public void init(){
    this.FoodPosition = Point.getrandompointGrid();
    body = new Rectangle(FoodPosition.getX(),FoodPosition.getY(),Point.GridWidth, Point.GridWidth);
    body.setFill(Color.web("272727"));
    System.out.println(FoodPosition.toString());
  }
  public Point GetFoodPosition(){
    return FoodPosition;
  }
  public Rectangle GetFoodBody(){
    return body;
  }
}
