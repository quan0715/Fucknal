package Application.Food;

import Application.Enum.Point;
import Application.Singleton.GameCurrentChildrenArray;
import Application.Singleton.GameEntityCenter;
import javafx.scene.image.Image;
import javafx.scene.shape.Rectangle;

public abstract class Food{
  protected Image image;
  protected Point FoodPosition;
  protected Rectangle body;
  public abstract void FoodInit();
  protected Food(){
    GameEntityCenter.addFood(this);
    FoodPosition = Point.getrandompointGrid();
    body = new Rectangle(FoodPosition.getX(), FoodPosition.getY(), Point.GridWidth, Point.GridWidth);
    System.out.println(FoodPosition.toString());
  }
  public void Refresh(){
    clearOnScreen();
    FoodPosition = Point.getrandompointGrid();
    body = new Rectangle(FoodPosition.getX(), FoodPosition.getY(), Point.GridWidth, Point.GridWidth);
    System.out.println(FoodPosition.toString());
    showOnScreen();
  }
  public Point GetFoodPosition(){
    return FoodPosition;
  }
  public void showOnScreen() {
    GameCurrentChildrenArray.Instance.get().add(body);
  }
  public void clearOnScreen() {
    GameCurrentChildrenArray.Instance.get().remove(body);
  }
}
