package sample;

import eatery_database.DBHelper;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import javax.swing.*;
import java.io.IOException;
import java.util.prefs.Preferences;

public class Settings implements StageSetter {

    private Stage primaryStage;

    @Override
    public void setStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    public void changeUserPassword(ActionEvent actionEvent) {
        Preferences preferences = Preferences.userNodeForPackage(UserSignUp.class);
        String username = preferences.get("username", null);

        String newPassword = JOptionPane.showInputDialog("Insert new Password");
        if(newPassword.isEmpty()) return;

        DBHelper.updateUserPassword(username, newPassword);
    }

    public void deleteAccount(ActionEvent actionEvent) {
        int choice = JOptionPane.
                showConfirmDialog(null, "Are sure you want to delete your account?");

        if(choice == JOptionPane.NO_OPTION
                || choice == JOptionPane.CANCEL_OPTION)
            return;

        Preferences preferences = Preferences.userNodeForPackage(UserSignUp.class);
        String username = preferences.get("username", null);

        DBHelper.deleteUserAccout(username);

        try{
            FXMLLoader loginPageLoader = new FXMLLoader(getClass().getResource("Login.fxml"));
            Parent loginPage = loginPageLoader.load();

            StageSetter stageSetter = loginPageLoader.getController();
            stageSetter.setStage(primaryStage);

            primaryStage.setTitle("Login");
            primaryStage.setScene(new Scene(loginPage));
            primaryStage.show();
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    public void goBackToPreviousPage(ActionEvent actionEvent) {
        try{
            FXMLLoader previousPageLoader = new FXMLLoader(getClass().getResource("UserHomePage.fxml"));
            Parent previousPage = previousPageLoader.load();

            StageSetter stageSetter = previousPageLoader.getController();
            stageSetter.setStage(primaryStage);

            ViewSetter viewSetter = previousPageLoader.getController();
            viewSetter.setUpView();

            primaryStage.setScene(new Scene(previousPage));
            primaryStage.show();
        } catch (IOException e){
            e.printStackTrace();
        }
    }
}
