package Application;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
public class Snake {
  public static final int SnakeWidth = Point.GridWidth;
  private Rectangle body ;
  private Point position ; 
  public Snake(Point position){
    this.position = position;
    this.body = new Rectangle(position.getX(),position.getY(),SnakeWidth, SnakeWidth);
    this.body.setFill(Color.GREEN);
  }
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
