package Application.Enum;

import java.util.Random;

public class Point {
  private int x;
  private int y;
  private static int windowWidth = 600;
  //private static int windowHeight = 600;
  public static int GridWidth = 20;
  public Point(int x, int y){
    this.x = x;
    this.y = y;
  }
  public void setX(int x){
    this.x = x;
  } 
  public void setY(int y) {
    this.y = y;
  }
  public int getX() {
    return x;
  }
  public int getY() {
    return y;
  }
  public int getGridX() {
    return x*GridWidth;
  }
  public int getGridY() {
    return y * GridWidth;
  }
  public static Point getrandompointGrid(){
    Random RandomPoint = new Random();
    int x = RandomPoint.nextInt(windowWidth / GridWidth);
    int y = RandomPoint.nextInt(windowWidth / GridWidth);
    return new Point(x*GridWidth,y*GridWidth);
  }
  public String toString() {
    return "( "+  x + " , " + y +" )"; 
  }
}
