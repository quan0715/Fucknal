package Application.SingletonAndTemplate;

import java.util.ArrayList;
import java.util.List;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;

public class FoodEventCenter {
    private static FoodEventCenter instance=new FoodEventCenter();
    private Timeline listeners;
    private List<FoodEvent> foodEvents=new ArrayList<>();
    private List<FoodEvent> removeList=new ArrayList<>();
    private FoodEventCenter(){
        listeners=new Timeline(new KeyFrame(Duration.millis(1),ev->{
            for(FoodEvent e:removeList)foodEvents.remove(e);
            removeList.clear();
            for(SnakeBody b:GameEntityCenter.instance.snakes){
                try{for(FoodEvent e:foodEvents){
                    e.listen(b);
                }}catch(Exception exc){break;}
            }
        }));
        listeners.setCycleCount(-1);
        listeners.play();
    };
    public static boolean find(FoodEvent e){return instance.foodEvents.contains(e);}
    public static boolean addFoodEvent(FoodEvent e){
        if(instance.foodEvents.contains(e))return false;
        else {try {return instance.foodEvents.add(e);}catch (Exception err){return false;}}
    }
    public static boolean removeFoodEvent(FoodEvent e){
        if(!instance.foodEvents.contains(e))return false;
        else {try {return instance.removeList.add(e);}catch (Exception err){return false;}}
    }
}
