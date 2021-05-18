package Application;
import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
public class App extends Application {
    static Stage stage; 
    public static void main(String[] args) {
        launch(args);
    }  
    @Override
    public void start(Stage primaryStage) throws IOException {
        stage=primaryStage;
        FXMLLoader loader = new FXMLLoader(getClass().getResource("./Scene/Home.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        scene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());
        primaryStage.setScene(scene);
        primaryStage.setTitle("Jack");
        ((HomeController)(loader.getController())).GamePin.requestFocus();
        primaryStage.show();
    }
}
