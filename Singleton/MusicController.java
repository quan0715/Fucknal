package Application.Singleton;


import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;

public class MusicController {
  private static MusicController instance=new MusicController();
  private MediaPlayer BackGround1MediaPlayer;
  private MediaPlayer BackGround2MediaPlayer;
  private MediaPlayer EatFood;
  private MediaPlayer GameOver;
  private MediaPlayer Click;
  private MusicController(){
    BackGround1MediaPlayer = new MediaPlayer(new Media(getClass().getResource("../Music/background2.mp3").toExternalForm()));
    BackGround2MediaPlayer = new MediaPlayer(new Media(getClass().getResource("../Music/background1.mp3").toExternalForm()));
    EatFood = new MediaPlayer(new Media(getClass().getResource("../Music/Pop.mp3").toExternalForm()));
    GameOver = new MediaPlayer(new Media(getClass().getResource("../Music/GameOver.mp3").toExternalForm()));
    Click = new MediaPlayer(new Media(getClass().getResource("../Music/Click.mp3").toExternalForm()));
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
  public static void EatFoodPop(){
    instance.EatFood.seek(Duration.millis(100));
    instance.EatFood.play();
  }
  public static void GameOverSound() {
    instance.GameOver.seek(Duration.millis(0));
    instance.GameOver.play();
  }
  
  public static void ButtonClickSound() {
    instance.Click.seek(Duration.millis(400) );
    instance.Click.play();
  }
  public static void SetMute(boolean SoundOff){
      instance.BackGround1MediaPlayer.setMute(SoundOff);
      instance.BackGround2MediaPlayer.setMute(SoundOff);
  }
}
