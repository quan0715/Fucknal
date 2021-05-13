package Application;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
public class Snake {
  public static final int SnakeWidth = Point.GridWidth;
  private Rectangle body ;
  private Point position ; 
  private Color color = Color.GREEN;
  public Snake(Point position){
    this.position = position;
    this.body = new Rectangle(position.getX(),position.getY(),SnakeWidth, SnakeWidth);
    this.body.setFill(color);
  }
  
  public Snake(Point position,Color col) {
    this.position = position;
    this.body = new Rectangle(position.getX(), position.getY(), SnakeWidth, SnakeWidth);
    this.body.setFill(col);
  }
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
}
