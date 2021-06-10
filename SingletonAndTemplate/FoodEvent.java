package SnakeGame.SingletonAndTemplate;


import SnakeGame.Enum.SnakePart;

public class FoodEvent {
    private Food food;
    private MyCallable OnHeadTouch;
    private MyCallable OnBodyTouch;
    private MyCallable Cast;
    public FoodEvent(Food f, MyCallable onHeadTouch, MyCallable onBodyTouch, MyCallable cast){
        food=f;
        OnHeadTouch=onHeadTouch;
        OnBodyTouch = onBodyTouch;
        Cast=cast;
        FoodEventCenter.addFoodEvent(this);
    }
    public void listen(SnakeBody b){
        try{
            if(b.whatPart(food.GetFoodPosition()).contains(SnakePart.HEAD)){
                OnHeadTouch.call(b);
                for(SnakeBody s:GameEntityCenter.GetOtherSnakes(b)){
                    Cast.call(s);
                }
                GameEntityCenter.removeFood(food);
            }
            if(b.whatPart(food.GetFoodPosition()).contains(SnakePart.BODY)){
                OnBodyTouch.call(b);
                GameEntityCenter.removeFood(food);
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
}
