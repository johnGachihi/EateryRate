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

import java.io.IOException;

public class UserSignUp implements StageSetter{

    private Stage primaryStage;

    @FXML
    public TextField txtUsername;
    public TextField txtEmail;
    public TextField txtPassword;
    public Label lblErrorMessage;

    public void createUserAccount(ActionEvent actionEvent) {
        String username = txtUsername.getText();
        String email = txtEmail.getText();
        String password = txtPassword.getText();

        String errorMessage = DBHelper.addUser(username, email, password);

        if(!errorMessage.isEmpty()){
            lblErrorMessage.setText(errorMessage);
            clearTextInAllTextFields();
        }
        else{
            try{
                FXMLLoader userHomePageLoader = new FXMLLoader(getClass()
                        .getResource("UserHomePage.fxml"));
                Parent userHomePage = userHomePageLoader.load();
                
                StageSetter stageSetter = userHomePageLoader.getController();
                stageSetter.setStage(primaryStage);

                primaryStage.setScene(new Scene(userHomePage));
                primaryStage.show();
            } catch (IOException e){
                e.printStackTrace();
            }
        }
    }
    
    private void clearTextInAllTextFields(){
        txtUsername.setText("");
        txtEmail.setText("");
        txtPassword.setText("");
    }

    @Override
    public void setStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }
}
