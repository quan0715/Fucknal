package Application.Singleton;


import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

public class MusicController {
  private static MusicController instance=new MusicController();
  private MediaPlayer BackGround1MediaPlayer;
  private MediaPlayer BackGround2MediaPlayer;
  private MusicController(){
    BackGround1MediaPlayer = new MediaPlayer(new Media(getClass().getResource("../Sound/tok.mp3").toExternalForm()));
    BackGround2MediaPlayer = new MediaPlayer(new Media(getClass().getResource("../Sound/happymusic.mp3").toExternalForm()));
    BackGround1MediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
    BackGround2MediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
  }
  public static void PlayBackground1(){
    instance.BackGround1MediaPlayer.play();
  }
  public static void StopBackground1() {
    instance.BackGround1MediaPlayer.stop();
  }
  public static void PlayBackground2() {
    instance.BackGround2MediaPlayer.play();
  }
  public static void StopBackground2() {
    instance.BackGround2MediaPlayer.stop();
  }
  
}
