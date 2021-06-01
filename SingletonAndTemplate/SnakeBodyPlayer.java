package Application.SingletonAndTemplate;

import java.util.concurrent.Callable;

import Application.Controller.HomeController;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;

public class SnakeBodyPlayer {
    private Timeline snakeTimeline;
    private SnakeBody snake;
    private int startSpeed;
    private DirectionController directionController;
    private int counter=0;
    private boolean stop=false;
    private Callable<Boolean> shouldStop;
    private Callable<Void> skill=null;
    private int skillAmount=0;
    private int skillCount=0;
    public SnakeBodyPlayer(DirectionController d, int sp, Callable<Boolean> f){
        startSpeed=sp;
        snake=new SnakeBody(HomeController.Player1, startSpeed, 300, 300);
        snake.m_player=this;
        snake.clearOnScreen();
        directionController=d;
        shouldStop=f;
        snakeTimeline=new Timeline(new KeyFrame(Duration.millis(1),e->{controll();}));
        snakeTimeline.setCycleCount(Timeline.INDEFINITE);
    }
    public void SetSnakeBody(SnakeBody b){
        GameEntityCenter.removeSnakeBody(snake);
        skill=null;
        snake=b;
        snake.m_player=this;
        play();
    }
    public void setStopCondition(Callable<Boolean> f){
        shouldStop=f;
    }
    public SnakeBody getSnakeBody(){return snake;}
    private void controll() {
        if(!stop){
            counter++;
            if(counter >= snake.GetSpeed()*snake.GetFoodBuff()){
                counter=0;
                snake.Move(directionController.NextDirection());
                if(directionController.GetCanFire()){
                    // interface to fire skill
                    if(skill!=null){
                        skillCount--;
                        try {
                            skill.call();
                        } catch (Exception e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                        if(skillCount==0)skill = null;
                    }
                    directionController.setCanFire(false);
                }
            }
            try {
                if(shouldStop.call()){stop();}
            } 
            catch (Exception e) {
                System.out.println("at SnakeBodyPlayer.controll shouldStop==null");
            }
        }
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
    void setSkill(int count, Callable<Void> m_skill){
        if(m_skill==null){
            skillAmount--;
            if(skillAmount==0)
                skill=null;
            return;
        }
        skill=m_skill;
        skillCount=count;
        skillAmount++;
    }
}
