package service;

import Entity.Host;
import javafx.scene.Node;
import javafx.scene.layout.GridPane;

public class GridPaneService {
    public static int getRowCount(GridPane pane) {
        int numRows = pane.getRowConstraints().size();
        for (int i = 0; i < pane.getChildren().size(); i++) {
            Node child = pane.getChildren().get(i);
            if (child.isManaged()) {
                Integer rowIndex = GridPane.getRowIndex(child);
                if (rowIndex != null) {
                    numRows = Math.max(numRows, rowIndex + 1);
                }
            }
        }
        return numRows;
    }


    public static void fillHostData(GridPane pane, Host host){

    }
}
