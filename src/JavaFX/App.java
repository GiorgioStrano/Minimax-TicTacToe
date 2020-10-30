package JavaFX;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class App extends Application
{

    private static App instance;
    private Stage stage;

    public App()
    {
        instance = this;
    }

    public static App getInstance()
    {
        return instance;
    }

    public static void main(String[] args)
    {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception
    {
        stage = primaryStage;
        stage.setTitle("DrCulocane");
//        stage.initStyle(StageStyle.TRANSPARENT);

        goToTrisPage();

        stage.show();
    }



    public void goToTrisPage() throws Exception
    {
        Parent root = FXMLLoader.load(getClass().getResource("tris/main.fxml"));
        Scene newScene = new Scene(root, 400, 400);
        newScene.getStylesheets().add(getClass().getResource("tris/style.css").toExternalForm());
        stage.setScene(newScene);
        newScene.getRoot().requestFocus();
    }

    public void quit()
    {
        Platform.exit();
    }


}
