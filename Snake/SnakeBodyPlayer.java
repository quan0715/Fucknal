package Application.Snake;

import java.util.concurrent.Callable;

import Application.Controller.HomeController;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;

public class SnakeBodyPlayer {
    private Timeline snakeTimeline;
    private SnakeBody snake;
    private DirectionController directionController;
    private Callable<Boolean> shouldStop;
    private int counter=0;
    private boolean stop=false;
    public SnakeBodyPlayer(DirectionController d, int sp, Callable<Boolean> f){
        snake=new SnakeBody(HomeController.Player1, 300, 300);
        snake.clearOnScreen();
        directionController=d;snake.speed=sp;shouldStop=f;
        snakeTimeline=new Timeline(new KeyFrame(Duration.millis(1),e->{controll();}));
        snakeTimeline.setCycleCount(Timeline.INDEFINITE);
    }
    public void SetSnakeBody(SnakeBody b){
        snake.clearOnScreen();
        snake=b;
        play();
    }
    public void setStopCondition(Callable<Boolean> f){
        shouldStop=f;
    }
    public SnakeBody getSnakeBody(){return snake;}
    private void controll() {
        if(!stop){
            counter++;
            if(counter >= snake.speed){
                counter=0;
                snake.Move(directionController.NextDirection());
            }
            try {if(shouldStop.call()){
                stop();
            }} 
            catch (Exception e) {
                System.out.println("at SnakeBodyPlayer.controll shouldStop==null");
            }
        }
    }
    public void setSpeed(int time) {
        snake.speed=time;
    }
    public void stop() {
        stop=true;
        snakeTimeline.stop();
    }
    public void pause() {
        snakeTimeline.pause();
    }
    public void play() {
        snakeTimeline.play();
        stop=false;
    }
}
