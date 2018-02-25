package sample;

import eatery_database.DBHelper;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import javax.swing.*;
import java.io.IOException;
import java.util.prefs.Preferences;

public class LoginController implements StageSetter{

    private Stage primaryStage;

    @FXML
    public Label lblErrorMessage;
    public TextField txtUsername;
    public TextField txtPassword;

    public boolean userIsAuthentic(String username, String password){
        String pass = DBHelper.getUserPassword(username);
        if (pass.isEmpty()){
            lblErrorMessage.setText("User not found");
            return false;
        }
       if(pass.equals(password))
            return true;
       else{
           lblErrorMessage.setText("Password and username do not match");
           return false;
       }

    }


    public void login(ActionEvent actionEvent) {
        String username = txtUsername.getText().toString();
        String password = txtPassword.getText().toString();
        if(username.isEmpty() || password.isEmpty())
            lblErrorMessage.setText("Username and Password required");

        if(userIsAuthentic(username, password)){
            Preferences preferences = Preferences.userNodeForPackage(LoginController.class);
            preferences.put("username", username);
            openHomePage();
        }
    }

    private void openHomePage(){
        try{
            FXMLLoader homePageLoader =
                    new FXMLLoader(getClass().getResource("UserHomepage.fxml"));
            Parent homePage = homePageLoader.load();

            StageSetter stageSetter = (StageSetter)homePageLoader.getController();
            stageSetter.setStage(primaryStage);

            primaryStage.setScene(new Scene(homePage));

        } catch (IOException e){
            e.printStackTrace();
        }
    }

    @Override
    public void setStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    public void openSignUpPage(ActionEvent actionEvent) {
        try{
            FXMLLoader signUpPageLoader = new FXMLLoader(getClass().getResource("UserSignUp.fxml"));
            Parent signUpPage = signUpPageLoader.load();

            StageSetter stageSetter = signUpPageLoader.getController();
            stageSetter.setStage(primaryStage);

            primaryStage.setTitle("User Signup");
            primaryStage.setScene(new Scene(signUpPage));
            primaryStage.show();
        } catch (IOException e){
            e.printStackTrace();
        }
    }
}
