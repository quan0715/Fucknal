package Application.Snake;

import Application.Enum.Point;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;

public class IGSnake extends Snake {
    @Override
    public void InitialSnakeBody(Point position) {
    this.position = position;
    this.body = new Rectangle(position.getX(), position.getY(), SnakeWidth, SnakeWidth);
    this.image = new Image(getClass().getResource("../img/ig.png").toString());
    this.body.setFill(new ImagePattern(image));
  }
}
