package Application.Food;

import Application.Enum.Point;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public abstract class Food{
  protected Point FoodPosition;
  protected Rectangle body;
  public abstract void FoodInit();
  protected Food(){
    FoodPosition = Point.getrandompointGrid();
    body = new Rectangle(FoodPosition.getX(), FoodPosition.getY(), Point.GridWidth, Point.GridWidth);
    System.out.println(FoodPosition.toString());
  }
  public void Refresh(){
    FoodPosition = Point.getrandompointGrid();
    body = new Rectangle(FoodPosition.getX(), FoodPosition.getY(), Point.GridWidth, Point.GridWidth);
    System.out.println(FoodPosition.toString());
  }
  //public abstract void Eaten();
  public void setColor(Color color){
    body.setFill(color);
  }
  public Point GetFoodPosition(){
    return FoodPosition;
  }
  public Rectangle GetFoodBody(){
    return body;
  }
}
