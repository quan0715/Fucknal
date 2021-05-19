package Application.Snake;

import Application.Point;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;

public class VscodeSnake extends Snake{
    @Override
    public void InitialSnakeBody(Point position) {
    this.position = position;
    this.body = new Rectangle(position.getX(), position.getY(), SnakeWidth, SnakeWidth);
    this.image = new Image(getClass().getResource("../img/vscode.png").toString());
    this.body.setFill(new ImagePattern(image));
  }
}
