package BellSystem;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("addRecords.fxml"));
        primaryStage.setTitle("School Bell System");
        Scene scene = new Scene(root, 1132, 790);
        primaryStage.setScene(scene);
        scene.getStylesheets().add("css/style.css");
        primaryStage.show();


    }


    public static void main(String[] args) {
        launch(args);
    }
}
