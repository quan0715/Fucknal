package Application;

import java.util.ArrayList;

import javafx.scene.Node;

public class GameCurrentChildrenArray {
    static GameCurrentChildrenArray Instance=new GameCurrentChildrenArray();
    private ArrayList<Node> arr;
    public void set(ArrayList<Node> a){arr=a;}
    public ArrayList<Node> get(){return arr;}
    private GameCurrentChildrenArray(){}
}
