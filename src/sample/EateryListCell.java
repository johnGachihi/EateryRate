package sample;

import Pojos.Eatery;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.layout.GridPane;

import java.io.IOException;

public class EateryListCell extends ListCell<Eatery>{

    @Override
        protected void updateItem(Eatery item, boolean empty) {
            super.updateItem(item, empty);
            if(empty){
                setGraphic(null);
            } else{
                try{
                    GridPane eateryItem = FXMLLoader.load(
                            getClass().getResource("SearchResultListItem.fxml"));
                    for (Node n : eateryItem.getChildren()){
                        if(n.getId() != null && n.getId().equals("lblEateryName")){
                            Label lblEateryName = (Label)n;
                            lblEateryName.setText(item.getEateryName());
                        }
                    }
                    setGraphic(eateryItem);
                } catch (IOException e){
                    e.printStackTrace();
                }
            }
        }
}
