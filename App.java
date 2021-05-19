package Application;
import java.io.File;
import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
public class App extends Application {
    static Stage stage; 
    
    static MusicController player = new MusicController();
    public static void main(String[] args) {
        launch(args);
    }  
    @Override
    public void start(Stage primaryStage) throws IOException {
        stage=primaryStage;
        FXMLLoader loader = new FXMLLoader(getClass().getResource("./Scene/Home.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        loader = new FXMLLoader(getClass().getResource("./Scene/Home.fxml"));
        root = loader.load();
        scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Jack");
        primaryStage.setResizable(false);
        ((HomeController)(loader.getController())).GamePin.requestFocus();
        primaryStage.show();
    }
}
