package sample;

import Pojos.Eatery;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class EateryInfo implements ShareInfo, StageSetter, Initializable, ViewSetter {

    //AIzaSyBUPRQq1jPAyB8JP_opgR0EzpNTIOalqsI

    private Stage primaryStage;
    Eatery eatery;

    @FXML
    public WebView mapsWebView;
    public Label lblEateryName;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }

    @Override
    public void passObject(Object object) {
        this.eatery = (Eatery)object;
    }

    @Override
    public void setStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    @Override
    public void setUpView() {
        lblEateryName.setText(eatery.getEateryName());
    }

    public void showLocationOnMaps(ActionEvent actionEvent) {
        WebEngine webEngine = mapsWebView.getEngine();
        webEngine.loadContent(
                "<!DOCTYPE html>\n" +
                        "<html>\n" +
                        "  <head>\n" +
                        "    <style>\n" +
                        "       #map {\n" +
                        "        height: 400px;\n" +
                        "        width: 100%;\n" +
                        "       }\n" +
                        "    </style>\n" +
                        "  </head>\n" +
                        "  <body>\n" +
                        "    <div id=\"map\"></div>\n" +
                        "    <script>\n" +
                        "      function initMap() {\n" +
                        "        var location = {lat: " + eatery.getLatitude() + ", lng:" + eatery.getLongitude() + "}\n" +
                        "        var map = new google.maps.Map(document.getElementById('map'), {\n" +
                        "          zoom: 4,\n" +
                        "          center: location\n" +
                        "        });\n" +
                        "        var marker = new google.maps.Marker({\n" +
                        "          position: location,\n" +
                        "          map: map\n" +
                        "        });\n" +
                        "      }\n" +
                        "    </script>\n" +
                        "    <script async defer\n" +
                        "    src=\"https://maps.googleapis.com/maps/api/js?key=AIzaSyBUPRQq1jPAyB8JP_opgR0EzpNTIOalqsI&callback=initMap\">\n" +
                        "    </script>\n" +
                        "  </body>\n" +
                        "</html>"
        );
    }

    @FXML
    public void goBackToPreviousScene(ActionEvent actionEvent) {
        try{
            FXMLLoader previousPageLoader = new FXMLLoader(getClass().getResource("UserHomepage.fxml"));
            Parent previousPage = previousPageLoader.load();

            StageSetter stageSetter = previousPageLoader.getController();
            stageSetter.setStage(primaryStage);

            ViewSetter viewSetter = previousPageLoader.getController();
            viewSetter.setUpView();

            primaryStage.setScene(new Scene(previousPage));
            primaryStage.show();
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}
