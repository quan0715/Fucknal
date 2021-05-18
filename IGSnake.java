package Application;

import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;

public class IGSnake extends Snake {
  public void InitialSnakeBody(Point position) {
    // TODO Auto-generated method stub
    this.position = position;
    this.body = new Rectangle(position.getX(), position.getY(), SnakeWidth, SnakeWidth);
    this.image = new Image(getClass().getResource("./img/ig.png").toString());
    this.body.setFill(new ImagePattern(image));
  }
}
