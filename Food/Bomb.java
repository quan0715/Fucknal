package SnakeGame.Food;

import SnakeGame.Enum.Point;
import SnakeGame.SingletonAndTemplate.Food;
import SnakeGame.SingletonAndTemplate.GameEntityCenter;
import SnakeGame.SingletonAndTemplate.MusicController;
import SnakeGame.SingletonAndTemplate.SnakeBody;
import SnakeGame.SingletonAndTemplate.boomPlayer;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.effect.Lighting;
import javafx.scene.effect.Light.Distant;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.util.Duration;

public class Bomb extends Food {
  private Distant lightW;
  private Lighting l;
  private Timeline ef;
  private int count = 0;
  private int speed;
  private boomPlayer m_boomPlayer;
  private double duration;
  private int lifeCounter;
  public Bomb(Point p,double duration) {
    super(p);
    this.duration=duration;
    speed=(int)(50*(540+duration)/540);
  }
  @Override
  protected void FoodInit() {
    lightW = new Distant(45,45,Color.WHITE);
    image = new Image(getClass().getResource("../img/TNT.png").toString());
    body.setFill(new ImagePattern(image));
    count = 0;
    l = new Lighting(lightW);
    l.setSurfaceScale(0.0);
    l.setSpecularExponent(0.0);
    l.setSpecularConstant(2.0);
    l.setDiffuseConstant(2.0);
    body.setEffect(l);
    m_boomPlayer=MusicController.newboom();
    ef = new Timeline(new KeyFrame(Duration.millis(1),e ->{
      if(!GameEntityCenter.contain(this)){
        m_boomPlayer.stop();
        ef.stop();
      }
      count++;
      lifeCounter++;
      if(lifeCounter>=duration-4000)m_boomPlayer.preboom();
      if(count>=speed*0.4||count>=200)l.setSpecularExponent(40);
      if(count >= speed && lifeCounter <= duration){
        l.setSpecularExponent(0);
        speed*=0.9;
        if(speed<=60) speed = 60;
        count = 0;
      }
      if(lifeCounter>=duration){
        ef.stop();
        m_boomPlayer.boom();
      }
    }));
    ef.setCycleCount(-1);
    ef.play();
  }
  
  @Override
  protected void Cast(SnakeBody s) {
  }
  @Override
  protected void OnSnakeHeadTouch(SnakeBody b) {
    OnSnakeBodyTouch(b);
  }
  @Override
  protected void OnSnakeBodyTouch(SnakeBody s) {
   m_boomPlayer.boom();
    for(int i=0;i<3;i++){
      s.RemoveBody();
      s.ScoreDown();
    }
  }
}
