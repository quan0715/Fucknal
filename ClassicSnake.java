package Application;


import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;

public class ClassicSnake extends Snake{
    @Override
    public void InitialSnakeBody(Point position) {
        this.position = position;
        this.body = new Rectangle(position.getX(), position.getY(), SnakeWidth, SnakeWidth);
        //this.body.setFill(color);
    }
}
