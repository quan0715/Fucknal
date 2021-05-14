package Application;
import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
public class App extends Application {
    public static void main(String[] args) {
        launch(args);
    }  
    @Override
    public void start(Stage primaryStage) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Home.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        System.out.println(root);
        System.out.println(scene);
        System.out.println(primaryStage);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Jack");
        ((HomeController)(loader.getController())).GamePin.requestFocus();
        primaryStage.show();
    }
}
