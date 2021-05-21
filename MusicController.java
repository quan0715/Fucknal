package Application;


import javafx.event.ActionEvent;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;

public class MusicController {
  private MediaPlayer BackGround1MediaPlayer;
  private MediaPlayer BackGround2MediaPlayer;
  private MediaPlayer GameOver;
  private MediaPlayer Eat;
  public MusicController(){
    this.BackGround1MediaPlayer = new MediaPlayer(new Media(getClass().getResource("./music/tok.mp3").toExternalForm()));
    this.BackGround2MediaPlayer = new MediaPlayer(new Media(getClass().getResource("./music/happymusic.mp3").toExternalForm()));
    this.GameOver = new MediaPlayer(new Media(getClass().getResource("./music/GameOver.mp3").toExternalForm()));
    this.Eat = new MediaPlayer(new Media(getClass().getResource("./music/Pop.mp3").toExternalForm()));
    BackGround1MediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
    BackGround2MediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
  }
  public void PlayGameOver(){
    GameOver.seek(Duration.millis(0));
    GameOver.play();
    
  }
  public void StopGameOver() {
    BackGround1MediaPlayer.setMute(false);
    GameOver.stop();
  }
  
  
  public void PlayEat() {
    Eat.seek(Duration.millis(0));
    Eat.play();
  }

  public void StopEat() {
    Eat.stop();
    
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
   public void setVolume(boolean SoundOff) {
     if(SoundOff){
       BackGround1MediaPlayer.setMute(true);
       BackGround2MediaPlayer.setMute(true);
       //GameOver.setVolume(0);
       //Eat.setVolume(0);
     }
     else{
       BackGround1MediaPlayer.setMute(false);
       BackGround2MediaPlayer.setMute(false);
       GameOver.setMute(false);
       Eat.setMute(false);
     }
   }
}
