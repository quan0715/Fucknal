package Application;

import java.util.ArrayList;

import javafx.scene.paint.Color;

public abstract class SnakeBody {
  private ArrayList<Snake> Body;
  //private AnchorPane GameTable;
  private int HeadX;
  private int HeadY;
  private int HeightLimit = 600;
  private int WeightLimit = 600;
  private int size;
  private Color color = Color.GREEN;
  public SnakeBody(){
    Body = new ArrayList<Snake>();
    HeadX = 300;
    HeadY = 300;
  }
  public SnakeBody(int x,int y) {
    Body = new ArrayList<Snake>();
    HeadX = x;
    HeadY = y;
  }
  public void init(){
    if(Body.size()!=0)Body.clear();
    HeadX = HeadY = 300;
    Body.add(new Snake(new Point(this.HeadX, this.HeadY),color));
    Body.add(new Snake(new Point(this.HeadX - Snake.SnakeWidth, this.HeadY),color));
    Body.add(new Snake(new Point(this.HeadX - Snake.SnakeWidth * 2, this.HeadY),color));
    size = 3;
  }
  public void SetSnakeColor(Color color){
    this.color =color;
  }
  public void init(int x,int y) {
    if (Body.size() != 0)
      Body.clear();
    HeadX = x;
    HeadY = y;
    Body.add(new Snake(new Point(this.HeadX, this.HeadY)));
    Body.add(new Snake(new Point(this.HeadX - Snake.SnakeWidth, this.HeadY)));
    Body.add(new Snake(new Point(this.HeadX - Snake.SnakeWidth * 2, this.HeadY)));
    size = 3;
  }
  public boolean SnakeMoving(Direction direction,Food apple){
    switch (direction) {
      case UP :
        HeadY = (HeadY - Snake.SnakeWidth) % WeightLimit;
        break;
      case DOWN:
        HeadY = (HeadY + Snake.SnakeWidth) % WeightLimit;
        break;
      case RIGHT:
        HeadX = (HeadX + Snake.SnakeWidth) % HeightLimit;
        break;
      case LEFT:
        HeadX = (HeadX - Snake.SnakeWidth) % HeightLimit;
        break;
    }
    HeadX=(HeadX + WeightLimit) % WeightLimit;
    HeadY=(HeadY + HeightLimit) % HeightLimit;
    boolean check = CheckEating(apple);
    if(check){
      AddNewBody(GetBodyPosition(Body.size() - 1));
      for (int i = Body.size() - 2; i > 0; i--) {
        ChangBodyPosition(i, GetBodyPosition(i - 1));
      }
    }
    else {
      for (int i = Body.size() -1 ; i > 0; i--) {
        ChangBodyPosition(i, GetBodyPosition(i - 1));
      }
    }
    ChangBodyPosition(0,new Point(HeadX,HeadY));
    return check;
  }
  public void AddNewBody(int x,int y){
    Body.add(new Snake( new Point(x,y)));
    size++;
  }
  
  public void AddNewBody(int x, int y,Color color) {
    Body.add(new Snake(new Point(x, y),color));
    size++;
  }

  public void AddNewBody(Point position) {
    int x = position.getX();
    int y = position.getY();
    AddNewBody(x,y,color);
  }
  public Point GetBodyPosition(int id){
    return Body.get(id).GetPosition();
  }
  public void ChangBodyPosition(int id,Point point){
    Body.get(id).ChangPosition(point);
  }
  public boolean CheckGameOver(){
    for (int i = 1; i < Body.size() ; i++) {
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
