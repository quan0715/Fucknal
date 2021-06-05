package Application.Food;

import Application.Enum.Point;
import Application.SingletonAndTemplate.Food;
import Application.SingletonAndTemplate.MusicController;
import Application.SingletonAndTemplate.SnakeBody;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.effect.Lighting;
import javafx.scene.effect.Light.Distant;
import javafx.scene.image.Image;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.util.Duration;

public class Bomb extends Food {
  private Distant lightW;
  private Lighting l;
  private Timeline ef;
  private int Case = 0;
  private int count = 0;
  private int speed = 200;
  private MediaPlayer tnt;
    public Bomb(Point p) {
      super(p);
    }
    @Override
    protected void FoodInit() {
      lightW = new Distant(45,45,Color.WHITE);
      image = new Image(getClass().getResource("../img/TNT.png").toString());
      tnt = new MediaPlayer(new Media(getClass().getResource("../Music/TnT.mp3").toExternalForm()));
      body.setFill(new ImagePattern(image));
      Case = 0;
      count = 0;
      l = new Lighting(lightW);
      l.setSurfaceScale(0.0);
      l.setSpecularExponent(0.0);
      l.setSpecularConstant(2.0);
      l.setDiffuseConstant(2.0);
      body.setEffect(l);
      MusicController.boomSoundPlay(tnt);
      ef = new Timeline(new KeyFrame(Duration.millis(1),e ->{
        if(count == speed){
          l.setSpecularExponent((Case==0 ? 40.0 : 0.0));
          Case = (Case + 1) % 2;
          speed -= 5;
          if(speed<=30) speed = 30;
          count = 0;
        }
        count++;
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
      MusicController.boom(tnt);
    }
    @Override
    protected void OnSnakeBodyTouch(SnakeBody s) {
      for(int i=0;i<3;i++){
        s.RemoveBody();
        s.ScoreDown();
      }
    }
  }
