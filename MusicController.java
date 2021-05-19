package Application;

import java.io.File;
import java.net.URL;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;

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
