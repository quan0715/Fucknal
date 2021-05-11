package Application;

import java.util.ArrayList;

public class SnakeBody {
  private ArrayList<Snake> Body;
  //private AnchorPane GameTable;
  private int HeadX;
  private int HeadY;
  private int HeightLimit = 600;
  private int WeightLimit = 600;
  private int size;
  public SnakeBody(){
    Body = new ArrayList<Snake>();
  }
  public void init(){
    if(Body.size()!=0){
      Body.clear();
    }
    HeadX = 300;
    HeadY = 300;
    Body.add(new Snake(new Point(this.HeadX, this.HeadY)));
    Body.add(new Snake(new Point(this.HeadX - Snake.SnakeWidth, this.HeadY)));
    Body.add(new Snake(new Point(this.HeadX - Snake.SnakeWidth * 2, this.HeadY)));
    size = 3;
  }
  public void SnakeMoving(Direction direction){
    for (int i = Body.size() - 1; i > 0; i--) {
      Body.get(i).ChangPosition(Body.get(i - 1).GetPosition());
    }
    switch (direction) {
      case UP -> HeadY -= Snake.SnakeWidth;
      case DOWN -> HeadY += Snake.SnakeWidth;
      case RIGHT -> HeadX += Snake.SnakeWidth;
      case LEFT -> HeadX -= Snake.SnakeWidth;
    }
    if(HeadX >= WeightLimit){
      ChangBodyPosition(0, new Point(0, HeadY));
    }
    else if(HeadX < 0){
      ChangBodyPosition(0, new Point(WeightLimit- Snake.SnakeWidth, HeadY));
    }
    else if (HeadY >= HeightLimit) {
      ChangBodyPosition(0, new Point(HeadX, 0));
    }
    else if (HeadY < 0) {
      ChangBodyPosition(0, new Point(HeadX,HeightLimit - Snake.SnakeWidth));
    }
    if (HeadX >= WeightLimit || HeadX < 0 || HeadY >= HeightLimit || HeadY < 0) {
      System.out.println("Out of Bound");
      ChangBodyPosition(0, new Point(HeadX, HeadY));
    }
    else
      ChangBodyPosition(0,new Point(HeadX, HeadY));
  }
  public void AddNewBody(){
    int x = GetBodyPosition(size - 1).getX();
    int px = GetBodyPosition(size - 2).getX();
    int y = GetBodyPosition(size - 1).getY();
    int py = GetBodyPosition(size - 2).getY();
    if (x - Snake.SnakeWidth >= 0 && x - Snake.SnakeWidth != px)
      AddNewBody(x - Snake.SnakeWidth, y);
    else if (x + Snake.SnakeWidth < WeightLimit && x + Snake.SnakeWidth != px)
      AddNewBody(x + Snake.SnakeWidth, y);
    else if (y - Snake.SnakeWidth >= 0 && y - Snake.SnakeWidth != py)
      AddNewBody(x, y - Snake.SnakeWidth);
    else if (y + Snake.SnakeWidth < HeightLimit && y + Snake.SnakeWidth != py)
      AddNewBody(x, y + Snake.SnakeWidth);
  }
  public void AddNewBody(int x,int y){
    Body.add(new Snake( new Point(x,y)));
    size++;
  }
  public Point GetBodyPosition(int id){
    return Body.get(id).GetPosition();
  }
  public void ChangBodyPosition(int id,Point point){
    if(id == 0){
      HeadX = point.getX();
      HeadY = point.getY();
    }
    Body.get(id).ChangPosition(point);
  }
  public boolean CheckGameOver(){
    HeadX = GetBodyPosition(0).getX();
    HeadY = GetBodyPosition(0).getY();
    for (int i = 1; i < size ; i++) {
      int x = GetBodyPosition(i).getX();
      int y = GetBodyPosition(i).getY();
      if (HeadX == x && HeadY == y) {
        System.out.println(Body.get(i) + " id = " + i);
        return true;
      }
    }
    return false;
  }
  public boolean CheckEating(Food apple){
    int foodX = apple.GetFoodPosition().getX();
    int foodY = apple.GetFoodPosition().getY();
    if(HeadX ==  foodX && HeadY == foodY){
      return true;
    }
    return false;
  }
  public int GetHeadX(){
    return HeadX;
  }
  public int GetHeadY() {
    return HeadY;
  }
  public ArrayList<Snake> getBody() {
    return Body;
  }
  public Snake getSnake(int id) {
    return Body.get(id);
  }
  public int getSize(){
    return size;
  }
}
