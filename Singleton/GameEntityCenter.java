package Application.Singleton;

import java.util.ArrayList;
import java.util.List;

import Application.Food.Food;
import Application.Snake.SnakeBody;

public class GameEntityCenter {
    static GameEntityCenter instance=new GameEntityCenter();
    private GameEntityCenter(){};
    public List<SnakeBody> snakes=new ArrayList<>();
    public List<Food> foods=new ArrayList<>();
    public static boolean contain(SnakeBody s){return instance.snakes.contains(s);}
    public static boolean contain(Food f){return instance.foods.contains(f);}
    public static boolean addSnakeBody(SnakeBody s){
        if(instance.snakes.contains(s))return false;
        else try {
            instance.snakes.add(s);
            s.showOnScreen();
            return true;
        }
        catch(Exception e){e.printStackTrace();return false;}
    }
    public static boolean removeSnakeBody(SnakeBody s){
        if(!instance.snakes.contains(s))return false;
        else try {
            s.clearOnScreen();
            instance.snakes.remove(s);
            return true;
        }
        catch(Exception e){e.printStackTrace();return false;}
    }
    public static boolean addFood(Food f){
        if(instance.foods.contains(f))return false;
        else try {
            instance.foods.add(f);
            f.showOnScreen();
            return true;
        }
        catch(Exception e){e.printStackTrace();return false;}
    }
    public static boolean removeFood(Food f){
        if(!instance.foods.contains(f))return false;
        else try {
           instance.foods.remove(f);
           f.clearOnScreen();
           FoodEventCenter.removeFoodEvent(f.getEvent());
           return true;
        }
        catch(Exception e){e.printStackTrace();return false;}
    }
    public static void clearAll(){
        for(SnakeBody b:instance.snakes)removeSnakeBody(b);
        for(Food f:instance.foods)removeFood(f);
    }
}
