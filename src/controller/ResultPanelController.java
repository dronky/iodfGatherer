package controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import server.Property;
import service.GridPaneService;

import java.net.URL;
import java.util.ResourceBundle;

public class ResultPanelController implements Initializable {

    @FXML
    public GridPane gridPane2;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Property prop = Property.getInstance();
        int totalRows = GridPaneService.getRowCount(gridPane2) - 1;

        // TODO get iodf dettails and print it:
        //  From property class after call main server class...

        for (int i = totalRows; i < prop.SERVERS.size(); i++) {
            gridPane2.addRow(i + 1,
                new TextField(prop.SERVERS.get(i).getHostname()),
                new TextField("IODF"),
                new TextField("HW IODF"),
                new TextField("Date Time"));
        }
    }
}
