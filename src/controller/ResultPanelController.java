package controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.GridPane;
import server.Main;
import server.Property;
import service.GridPaneService;

import java.net.URL;
import java.util.ResourceBundle;

public class ResultPanelController implements Initializable {

    @FXML
    public GridPane gridPane2;


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        //initialise service with current grid pane
        GridPaneService gridPaneService = new GridPaneService(gridPane2);
        Main.main(gridPaneService);

        // TODO get iodf dettails and print it:
        //  From property class after call main server class...
    }
}
