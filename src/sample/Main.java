package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        //To be removed.
        Parent homePageRoot = FXMLLoader.load(getClass().getResource("UserHomepage.fxml"));

        FXMLLoader loginPageLoader = new FXMLLoader(getClass().getResource("Login.fxml"));
        Parent loginRoot = loginPageLoader.load();

        StageSetter stageSet = (StageSetter)loginPageLoader.getController();
        stageSet.setStage(primaryStage);

        primaryStage.setTitle("Hello World");
        primaryStage.setScene(new Scene(loginRoot));
        primaryStage.show();

    }


    public static void main(String[] args) {
        launch(args);
    }
}
