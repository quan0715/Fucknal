package Application.SingletonAndTemplate;
import Application.Enum.Point;
import javafx.scene.effect.Lighting;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public abstract class Snake{
  public static final int SnakeWidth = Point.GridWidth;
  protected Rectangle body ;
  protected Point position ; 
  protected Image image;
  public abstract void InitialSnakeBody(Point position);
  public void ChangPosition(Point newPosition){
    this.position.setX(newPosition.getX());
    this.position.setY(newPosition.getY());
    this.body.setX(newPosition.getX());
    this.body.setY(newPosition.getY());
  }
  public void SetColor(Color color){
    body.setFill(color);
  }
  public Point GetPosition(){
    return this.position;
  }
  public Rectangle GetBody(){
    return this.body;
  }
  public void SnakeEffect(Lighting l) {
    body.setEffect(l);
  }
}
