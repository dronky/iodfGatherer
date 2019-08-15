package controller;

import Entity.Host;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
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

        Property prop = Property.getInstance();
        int totalRows = GridPaneService.getRowCount(gridPane2) - 1;

        // TODO get iodf dettails and print it:
        //  From property class after call main server class...
    }
}
