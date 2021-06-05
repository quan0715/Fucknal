package Application.SingletonAndTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

import Application.Enum.*;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.effect.Lighting;

public class SnakeBody {
  SnakeBodyPlayer m_player;
  private ArrayList<Snake> Body;
  private Snake snakeInstance;
  private int HeadX;
  private int HeadY; 
  private Lighting l;
  private int EffectCount = 0;
  private final int HeightLimit = 600;
  private final int WidthLimit = 600;
  private Direction currentDirection;
  private int startSpeed;
  private double rate;
  private double FoodBuff;
  private int TextCount = 0;
  private String Skill = "";
  private String Id = "";
  private int score;
  public int woody=0;
  public SnakeBody(Snake instance,int startSpeed, int x,int y) {
    HeadX = x;
    HeadY = y;
    woody=0;
    rate = 1;
    EffectCount = 0;
    TextCount = 0;
    FoodBuff = 1 ;
    this.startSpeed=startSpeed;
    currentDirection=Direction.RIGHT;
    Body = new ArrayList<Snake>();
    snakeInstance=instance;
    for(int i=0;i<3;i++)AddNewBody();
    GameEntityCenter.addSnakeBody(this);
  }
  public void Move(Direction direction){
    if(direction!=null)currentDirection=direction;
    clearOnScreen();
    switch (currentDirection) {
      case UP :
        HeadY = (HeadY - Snake.SnakeWidth) % WidthLimit;
        break;
      case DOWN:
        HeadY = (HeadY + Snake.SnakeWidth) % WidthLimit;
        break;
      case RIGHT:
        HeadX = (HeadX + Snake.SnakeWidth) % HeightLimit;
        break;
      case LEFT:
        HeadX = (HeadX - Snake.SnakeWidth) % HeightLimit;
        break;
    }
    HeadX=(HeadX + WidthLimit) % WidthLimit;
    HeadY=(HeadY + HeightLimit) % HeightLimit;
    for (int i = Body.size() -1 ; i > 0; i--) {
      Body.get(i).ChangPosition(GetBodyPosition(i - 1));
    }
    Body.get(0).ChangPosition(new Point(HeadX,HeadY));
    showOnScreen();
  }
  public void RemoveBody(){
    if(Body.size()>0&&woody==0){
      GameCurrentChildrenArray.Instance.get().remove(Body.get(Body.size()-1).GetBody());
      Body.remove(Body.get(Body.size()-1));
    }
  }
  public void AddNewBody(){
    if(Body.size()==0){
      try{
        Snake bod=snakeInstance.getClass().getDeclaredConstructor().newInstance();
        bod.InitialSnakeBody(new Point(HeadX, HeadY));
        Body.add(bod);}
      catch(Exception e){
        System.out.println("fail adding new body when Body.size()==0");
      }
      return;
    }
    int x = GetBodyPosition(Body.size() - 1).getX();
    int y = GetBodyPosition(Body.size() - 1).getY();
    Move(currentDirection);
    try{
      Snake bod=snakeInstance.getClass().getDeclaredConstructor().newInstance();
      bod.InitialSnakeBody(new Point(x, y));
      bod.SnakeEffect(l);
      Body.add(bod);}
    catch(Exception e){
      System.out.println("fail adding new body");
    }
  }
  public Direction GetDirection() {
    return currentDirection;
  }
  public Point GetBodyPosition(int id){
    return Body.get(id).GetPosition();
  }
  public Point GetHead(){
    return new Point(HeadX,HeadY);
  }
  public double GetRate(){
    return rate;
  }
  public int GetSpeed(){return (int)(startSpeed*rate*FoodBuff);}
  public void clearOnScreen() {
    ObservableList<Node> children=GameCurrentChildrenArray.Instance.get();
    for (Snake snake : Body) children.remove(snake.GetBody());
  }
  public void showOnScreen() {
    ObservableList<Node> children=GameCurrentChildrenArray.Instance.get();
    for (Snake snake : Body) if(!children.contains(snake.GetBody()))children.add(snake.GetBody());
  }
  public List<SnakePart> whatPart(Point p){
    List<SnakePart> returnedList=new ArrayList<>();
    for(int i=1;i<Body.size();i++)
      if(p.getX()==GetBodyPosition(i).getX()&&p.getY()==GetBodyPosition(i).getY()){
        returnedList.add(SnakePart.BODY);
        break;
      }
    if(p.getX()==HeadX&&p.getY()==HeadY)returnedList.add(SnakePart.HEAD);
    return returnedList;
  }
  public void SnakeEffect(Lighting l){
    if(l == null ){
      EffectCount--;
      if(EffectCount<=0){
        this.l = null;
        EffectCount = 0;
        for (Snake s : Body) {
          s.SnakeEffect(this.l);
        }
      }
    }
    else{
      EffectCount++;
      this.l = l;
      for (Snake s : Body) {
        s.SnakeEffect(l);
      }
    }
  }
  public void RateBuff(double buff){
    FoodBuff /= buff;
  }
  public void RateNuff(double buff) {
    FoodBuff *= buff;
  }
  public void ScoreUp(){
    score+=10;
    rate=0.12 + rate * 0.97;
  }
  public void ScoreDown(){
    score-=10;
    rate=(rate - 0.12) / 0.97;
  }
  public int GetScore(){
    return score;
  }
  public void setSkill(int count, Callable<Void> skill){
    m_player.setSkill(count, skill);
  }
  public int getSize(){return Body.size();}
  public void SkillText(String text,String Id) {
    if(text == null){
      TextCount--;
      if(TextCount <= 0){
        TextCount = 0;
        this.Skill = "";
        this.Id = "";
      }
    }
    else{
      TextCount++;
      this.Skill = text;
      this.Id = Id;
    }
  }
  public String getSkillText(){
    return Skill;
  }
  public String getSkillTextId() {
    return Id;
  }
}
