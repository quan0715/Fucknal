package Application;

import java.util.concurrent.Callable;
public class EatEvent {
    SnakeBody body;
    private Callable<Void> calledFunction;
    public EatEvent(SnakeBody body, Callable<Void> calledFunction){
        this.body=body;
        this.calledFunction=calledFunction;
    }
    public void Call(Food food) throws Exception{
        if(this.body.GetHeadX()==food.FoodPosition.getX()&&this.body.GetHeadY()==food.FoodPosition.getY())
            calledFunction.call();
    }
}
