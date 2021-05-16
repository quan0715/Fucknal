package Application;

import javafx.scene.layout.AnchorPane;

public class FoodGenerator{
  private AnchorPane GameTable;
  private Food food;
  //private Class currentClass;
  public FoodGenerator(AnchorPane GameTable, NormalFood food){
    this.GameTable = GameTable;
    this.food = food;
  }
  
  public void RefreshFood(){
    GameTable.getChildren().remove(food.GetFoodBody());
    food.FoodInit();
    GameTable.getChildren().add(food.GetFoodBody());
  }
}
