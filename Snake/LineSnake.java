package SnakeGame.Snake;

import SnakeGame.Enum.Point;
import SnakeGame.SingletonAndTemplate.Snake;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;

public class LineSnake extends Snake {
  @Override
  public void InitialSnakeBody(Point position) {
    this.position = position;
    this.body = new Rectangle(position.getX(), position.getY(), SnakeWidth, SnakeWidth);
    this.image = new Image(getClass().getResource("../img/line.png").toString());
    this.body.setFill(new ImagePattern(image));
  }
}
