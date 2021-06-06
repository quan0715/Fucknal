package Application.Food;

import Application.Enum.Point;
import Application.SingletonAndTemplate.Food;
import Application.SingletonAndTemplate.GameEntityCenter;
import Application.SingletonAndTemplate.MusicController;
import Application.SingletonAndTemplate.SnakeBody;
import Application.SingletonAndTemplate.boomPlayer;
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
  private int speed = 400;
  private boomPlayer m_boomPlayer;
  private double duration;
  private int lifeCounter;
  public Bomb(Point p,double duration) {
    super(p);
    this.duration=duration;
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
      if(!GameEntityCenter.contain(this))ef.stop();
      count++;
      lifeCounter++;
      if(count>=speed*0.4)l.setSpecularExponent(40);
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
