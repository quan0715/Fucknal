package Application;


import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

public class MusicController {
  private MediaPlayer BackGround1MediaPlayer;
  private MediaPlayer BackGround2MediaPlayer;
  public MusicController(){
    this.BackGround1MediaPlayer = new MediaPlayer(new Media(getClass().getResource("tok.mp3").toExternalForm()));
    this.BackGround2MediaPlayer = new MediaPlayer(new Media(getClass().getResource("happymusic.mp3").toExternalForm()));
    BackGround1MediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
    BackGround2MediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
  }
  public void PlayBackground1(){
    this.BackGround1MediaPlayer.play();
  }
  public void StopBackground1() {
    this.BackGround1MediaPlayer.stop();
  }
  public void PlayBackground2() {
    this.BackGround2MediaPlayer.play();
  }

  public void StopBackground2() {
    this.BackGround2MediaPlayer.stop();
  }
  
}
