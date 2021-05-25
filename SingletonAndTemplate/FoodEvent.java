package Application.SingletonAndTemplate;


import Application.Enum.SnakePart;

public class FoodEvent {
    private Food food;
    private MyCallable OnTouch;
    private MyCallable Cast;
    public FoodEvent(Food f, MyCallable ontouch, MyCallable cast){
        food=f;
        OnTouch=ontouch;
        Cast=cast;
        FoodEventCenter.addFoodEvent(this);
    }
    public void listen(SnakeBody b){
        try{
            if(b.whatPart(food.GetFoodPosition()).contains(SnakePart.HEAD)){
                OnTouch.call(b);
                for(SnakeBody s:GameEntityCenter.GetOtherSnakes(b)){
                    Cast.call(s);
                }
                GameEntityCenter.removeFood(food);
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
}
