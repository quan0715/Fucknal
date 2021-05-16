package Application;

import javafx.collections.ObservableList;
import javafx.scene.Node;

public class GameCurrentChildrenArray {
    static GameCurrentChildrenArray Instance=new GameCurrentChildrenArray();
    private ObservableList<Node> arr;
    public void set(ObservableList<Node> observableList){arr=observableList;}
    public ObservableList<Node> get(){return arr;}
    private GameCurrentChildrenArray(){}
}
