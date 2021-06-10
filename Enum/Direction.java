package SnakeGame.Enum;

public enum Direction {
  UP,DOWN,RIGHT,LEFT;

  public static Direction RandomDirection(int c){
    Direction direction = Direction.UP;
    if(c==1) {
      direction = Direction.UP;
    }
    if (c == 0) {
      direction = Direction.DOWN;
    }
    if (c == 2) {
      direction = Direction.LEFT;
    }
    if (c == 3) {
      direction = Direction.RIGHT;
    }
    return direction;
  }
}
