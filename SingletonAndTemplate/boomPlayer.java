package Application.SingletonAndTemplate;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;

public class boomPlayer {
    private MediaPlayer player;
    private Timeline resetTimeLine;
    public boomPlayer(Media m){
        player=new MediaPlayer(m);
        player.play();
        resetTimeLine=new Timeline(new KeyFrame(Duration.millis(3500),e->{player.seek(Duration.millis(100));}));
        resetTimeLine.setCycleCount(-1);
        resetTimeLine.play();
    }
    public void boom() {
        player.seek(Duration.millis(4100));
        resetTimeLine.stop();
    }
}
