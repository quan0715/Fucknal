package Application.SingletonAndTemplate;


import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;

public class boomPlayer {
    private MediaPlayer player;
    private Timeline resetTimeLine;
    private boolean preBoomPlayed;
    private boolean boomed;
    public boomPlayer(Media m){
        preBoomPlayed=false;
        boomed=false;
        player=new MediaPlayer(m);
        resetTimeLine=new Timeline(new KeyFrame(Duration.millis(2000),e->{
            player.seek(Duration.millis(1500));
        }));
        resetTimeLine.setCycleCount(-1);
    }
    public void preboom(){
        if(!preBoomPlayed){
            player.seek(Duration.millis(100));
            player.play();
            resetTimeLine.play();
            preBoomPlayed=true;
        }
    }
    public void boom() {
        if(!preBoomPlayed)player.play();
        boomed=true;
        player.seek(Duration.millis(4100));
        resetTimeLine.stop();
    }
    public void stop() {
        resetTimeLine.stop();
        if(!boomed)player.stop();
    }
}
