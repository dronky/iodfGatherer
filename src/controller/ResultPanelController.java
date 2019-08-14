package controller;

import Entity.ServerRepository;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import service.GridPaneService;

import java.net.URL;
import java.util.ResourceBundle;

public class ResultPanelController implements Initializable {

    @FXML
    public GridPane gridPane2;

    ServerRepository serverRepository;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        serverRepository = ServerRepository.getInstance();
        int totalRows = GridPaneService.getRowCount(gridPane2) - 1;

        // TODO get iodf dettails and print it:

        for (int i = totalRows; i < serverRepository.getCount(); i++) {
            gridPane2.addRow(i + 1,
                new Label(serverRepository.get(i).getHostname()),
                new Label("IODF"),
                new Label("HW IODF"),
                new Label("Date Time"));
        }
    }
}
