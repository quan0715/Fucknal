package Application;
import javafx.scene.shape.Rectangle;
public abstract class Snake{
  public static final int SnakeWidth = Point.GridWidth;
  protected Rectangle body ;
  protected Point position ; 
  public abstract void InitialSnakeBody(Point position);
  public void ChangPosition(Point newPosition){
    this.position.setX(newPosition.getX());
    this.position.setY(newPosition.getY());
    this.body.setX(newPosition.getX());
    this.body.setY(newPosition.getY());
  }
  public Point GetPosition(){
    return this.position;
  }
  public Rectangle GetBody(){
    return this.body;
  }
}
