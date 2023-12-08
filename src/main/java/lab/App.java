package lab;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;


public class App extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    private Canvas canvas;
    private GameController controller;
    @Override
    public void start(Stage primaryStage) {
        try {
            //Construct a main window with a canvas.

            FXMLLoader loader = new FXMLLoader(getClass().getResource("gameview.fxml"));
            BorderPane pane = loader.load();

            Scene scene = new Scene(pane);


            primaryStage.setScene(scene);
            primaryStage.resizableProperty().set(false);
            primaryStage.setTitle("Java I - Space Invaders");
            primaryStage.show();
            controller = loader.getController();

            //Exit program when main window is closed
            primaryStage.setOnCloseRequest(this::exitProgram);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    private void exitProgram(WindowEvent evt) {
        controller.stopGame();
        System.exit(0);
    }
}