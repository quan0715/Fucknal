package SnakeGame.Snake;


import SnakeGame.Enum.Point;
import SnakeGame.SingletonAndTemplate.Snake;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class ClassicSnake extends Snake{
    @Override
    public void InitialSnakeBody(Point position) {
        this.position = position;
        this.body = new Rectangle(position.getX(), position.getY(), SnakeWidth, SnakeWidth);
        SetColor(Color.GREEN);
    }
}
