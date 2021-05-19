package Application;


import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

public class MusicController {
  private MediaPlayer BackGroundMediaPlayer;
  public void PlayBackgroudMusic(){

    Media background = new Media(getClass().getResource("tok.mp3").toExternalForm());
    this.BackGroundMediaPlayer = new MediaPlayer(background);
    this.BackGroundMediaPlayer.play();
    System.out.println("Playing...");
  }
  
  public void StopBackgroudMusic() {
    this.BackGroundMediaPlayer.stop();
  }
}
