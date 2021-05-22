package Application.Singleton;

import java.util.ArrayList;
import java.util.List;

import Application.Food.Food;
import Application.Snake.SnakeBody;

public class GameEntityCenter {
    private static GameEntityCenter instance=new GameEntityCenter();
    private GameEntityCenter(){};
    private List<SnakeBody> snakes=new ArrayList<>();
    private List<Food> foods=new ArrayList<>();
    public static boolean contain(SnakeBody s){return instance.snakes.contains(s);}
    public static boolean contain(Food f){return instance.foods.contains(f);}
    public static boolean addSnakeBody(SnakeBody s){
        if(!instance.snakes.contains(s))return false;
        else try {return instance.snakes.add(s);}catch(Exception e){return false;}
    }
    public static boolean removeSnakeBody(SnakeBody s){
        if(!instance.snakes.contains(s))return false;
        else try {return instance.snakes.remove(s);}catch(Exception e){return false;}
    }
    public static boolean addFood(Food f){
        if(!instance.foods.contains(f))return false;
        else try {return instance.foods.add(f);}catch(Exception e){return false;}
    }
    public static boolean addSnakeBody(Food f){
        if(!instance.foods.contains(f))return false;
        else try {return instance.foods.add(f);}catch(Exception e){return false;}
    }
}
