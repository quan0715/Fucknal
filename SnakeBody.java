package Application;

import java.util.ArrayList;

public class SnakeBody<T extends Snake> {
  private ArrayList<T> Body;
  private Class<T> classInstance;
  //private AnchorPane GameTable;
  private int HeadX;
  private int HeadY;
  private int HeightLimit = 600;
  private int WeightLimit = 600;
  private int size;
  private Color color = Color.GREEN;
  public SnakeBody(){
    Body = new ArrayList<T>();
  }
  public void init(Class<? extends ClassicSnake> snakeType){
    classInstance=(Class<T>) snakeType;
    if(Body.size()!=0)Body.clear();
    HeadX = HeadY = 300;
    for(int i=0;i<3;i++){
      try{
        T bod=classInstance.getDeclaredConstructor().newInstance();
        bod.InitialSnakeBody(new Point(this.HeadX - Snake.SnakeWidth * i, this.HeadY));
        Body.add(bod);
      }
      catch(Exception e){
        System.out.println(e);
      }
    }
    size = 3;
  }
  public boolean SnakeMoving(Direction direction,Food apple) throws Exception{
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
  public void AddNewBody(int x,int y) throws Exception{
    T bod=classInstance.getDeclaredConstructor().newInstance();
    bod.InitialSnakeBody(new Point(x, y));
    Body.add(bod);
    size++;
  }
  public void AddNewBody(Point position) throws Exception {
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
  public ArrayList<T> getBody() {
    return Body;
  }
  public Snake getSnake(int id) {
    return Body.get(id);
  }
  public int getSize(){
    return size;
  }
}
