package Application.Template;


import Application.Enum.SnakePart;
import Application.Food.Food;
import Application.Singleton.FoodEventCenter;
import Application.Singleton.GameEntityCenter;
import Application.Snake.SnakeBody;

public class FoodEvent {
    private Food food;
    private MyCallable onTouch;
    public FoodEvent(Food f, MyCallable m){
        food=f;
        onTouch=m;
        FoodEventCenter.addFoodEvent(this);
    }
    public void listen(SnakeBody b){
        try{
            if(b.whatPart(food.GetFoodPosition()).contains(SnakePart.HEAD)){
                onTouch.call(b);
                GameEntityCenter.removeFood(food);
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
}
