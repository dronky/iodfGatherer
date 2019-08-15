package service;

import Entity.Host;
import javafx.scene.layout.GridPane;

public class HostService {

    private GridPane gridPane;

    public HostService(GridPane gridPane) {
        this.gridPane = gridPane;
    }

    private static void updateHost(GridPane gridPane, Host host){
        System.out.println("host update");
    }
}
