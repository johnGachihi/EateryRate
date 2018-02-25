package sample;

import Pojos.Eatery;
import eatery_database.DBHelper;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.util.Callback;
import others.PreferenceHelper;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.prefs.Preferences;

public class UserHomepageController implements StageSetter, Initializable, ViewSetter {

    public Button btnOpenSettings;
    public Button btnSearch;
    private Stage primaryStage;
    private ArrayList<Eatery> eateriesSearchResults;

    @FXML
    public TextField txtSearchBox;
    public GridPane gdpRoot;
    public GridPane gdpSearchNodes;
    public ListView lstSearchResults;

    public void search(ActionEvent actionEvent) {
        eateriesSearchResults = DBHelper
                .getEateriesFromSearchQuery(txtSearchBox.getText());
        setUpLayout();
        populateSearchResultList(eateriesSearchResults);
    }

    private void setUpLayout(){
        gdpRoot.getRowConstraints().get(0).setPercentHeight(7);
        lstSearchResults.setVisible(true);
    }

    private void populateSearchResultList(ArrayList<Eatery> eateries){
        lstSearchResults.setCellFactory(new Callback<ListView, ListCell>() {
            @Override
            public ListCell call(ListView param) {
                return new EateryListCell();
            }
        });
        ObservableList<Eatery> eateries1 = FXCollections.observableArrayList();
        eateries1.addAll(eateries);
        lstSearchResults.setItems(eateries1);
        setListViewListener();
    }

    @Override
    public void setStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        lstSearchResults.setVisible(false);
    }

    private void setListViewListener(){
        lstSearchResults.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observable, Object oldValue, Object newValue) {
                //store last searches results
                Preferences preferences = Preferences
                        .userNodeForPackage(UserHomepageController.class);
               /* byte[] searchResultsInByteArray = PreferenceHelper
                        .getByteArrayFromObject(eateriesSearchResults);
                searchResultsPref.putByteArray("SearchResults", searchResultsInByteArray);*/
                //Preferences preferences = Preferences.userNodeForPackage(UserHomepageController.class);
                preferences.put("searchWord", txtSearchBox.getText());

                //open eatery info screen;
                try{
                    FXMLLoader eateryInfoLoader = new FXMLLoader(getClass().getResource("EateryInfo.fxml"));
                    Parent eateryInfoPage = eateryInfoLoader.load();

                    StageSetter stageSetter = (StageSetter)eateryInfoLoader.getController();
                    stageSetter.setStage(primaryStage);

                    ShareInfo shareInfo = eateryInfoLoader.getController();
                    shareInfo.passObject(newValue);

                    ViewSetter controllerSetter = eateryInfoLoader.getController();
                    controllerSetter.setUpView();

                    primaryStage.setScene(new Scene(eateryInfoPage));
                    primaryStage.show();
                } catch (IOException e){
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public void setUpView() {
        Preferences preferences = Preferences.userNodeForPackage(UserHomepageController.class);
        String searchWord = preferences.get("searchWord", null);
        txtSearchBox.setText(searchWord);
        btnSearch.fire();

    }

    public void openSettings(ActionEvent actionEvent) {
        Preferences preferences = Preferences.userNodeForPackage(UserHomepageController.class);
        preferences.put("searchWord", txtSearchBox.getText());
        try{
            FXMLLoader settingsPageLoader = new FXMLLoader(getClass().getResource("Settings.fxml"));
            Parent settingsPage = settingsPageLoader.load();

            StageSetter stageSetter = settingsPageLoader.getController();
            stageSetter.setStage(primaryStage);

            primaryStage.setTitle("Settings");
            primaryStage.setScene(new Scene(settingsPage));
            primaryStage.show();

        } catch (IOException e){
            e.printStackTrace();
        }
    }
}
