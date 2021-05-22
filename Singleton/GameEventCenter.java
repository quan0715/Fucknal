package Application.Singleton;

import java.util.ArrayList;
import java.util.List;

import Application.Template.GameEvent;

public class GameEventCenter {
    private static GameEventCenter instance=new GameEventCenter();
    private GameEventCenter(){};
    private List<GameEvent> gameEvents=new ArrayList<>();
    public static boolean find(GameEvent e){return instance.gameEvents.contains(e);}
    public static boolean addGameEvent(GameEvent e){
        if(!instance.gameEvents.contains(e))return false;
        else {try {return instance.gameEvents.add(e);}catch (Exception err){return false;}}
    }
    public static boolean removeGameEvent(GameEvent e){
        if(!instance.gameEvents.contains(e))return false;
        else {try {return instance.gameEvents.remove(e);}catch (Exception err){return false;}}
    }
}
