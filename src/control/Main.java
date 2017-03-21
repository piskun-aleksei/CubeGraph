package control;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("chart.fxml"));
        Parent root = (Parent)loader.load();
        primaryStage.setTitle("Hello World");
        primaryStage.setScene(new Scene(root, 850, 475));
        primaryStage.show();

        Controller controller = (Controller)loader.getController();
        controller.setActions(primaryStage);
    }
    public static void main(String[] args) {
        launch(args);
    }
}
