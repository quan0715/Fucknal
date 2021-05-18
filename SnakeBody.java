package Application;

import java.util.ArrayList;

import javafx.collections.ObservableList;
import javafx.scene.Node;

public class SnakeBody {
  private ArrayList<Snake> Body;
  private Snake snakeInstance;
  //private AnchorPane GameTable;
  private int HeadX = 300;
  private int HeadY = 300;
  private int HeightLimit = 600;
  private int WeightLimit = 600;

  public SnakeBody(Snake instance){
    Body = new ArrayList<Snake>();
    snakeInstance=instance;
    init();
  }
  
  public SnakeBody(Snake instance,int x,int y) {
    HeadX = x;
    HeadY = y;
    Body = new ArrayList<Snake>();
    snakeInstance=instance;
    init();
  }
  private void init(){
    if(Body.size()!=0)Body.clear();
    for(int i=0;i<3;i++){
      try{
        Snake bod=snakeInstance.getClass().getDeclaredConstructor().newInstance();
        bod.InitialSnakeBody(new Point(this.HeadX - Snake.SnakeWidth * i, this.HeadY));
        Body.add(bod);
      }
      catch(Exception e){
        System.out.println(e);
      }
    }
  }
  public boolean SnakeMoving(Direction direction,Food apple) throws Exception{
    clearOnScreen();
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
    showOnScreen();
    return check;
  }
  public void AddNewBody(int x,int y) throws Exception{
    Snake bod=snakeInstance.getClass().getDeclaredConstructor().newInstance();
    bod.InitialSnakeBody(new Point(x, y));
    Body.add(bod);
  }
  public void AddNewBody(Point position) throws Exception {
    int x = position.getX();
    int y = position.getY();
    AddNewBody(x,y);
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
  public void clearOnScreen() {
    ObservableList<Node> children=GameCurrentChildrenArray.Instance.get();
    for (Snake snake : Body) children.remove(snake.GetBody());
  }
  public void showOnScreen() {
    ObservableList<Node> children=GameCurrentChildrenArray.Instance.get();
    for (Snake snake : Body) children.add(snake.GetBody());
  }
  public ArrayList<Snake> getBody(){
    return Body ;
  }
  
}
