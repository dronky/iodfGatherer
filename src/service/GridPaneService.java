package service;

import Entity.Host;
import javafx.scene.Node;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

public class GridPaneService {

    private GridPane gridPane;

    public GridPaneService(GridPane gridPane) {
        this.gridPane = gridPane;
    }

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


    public void fillHostData(Host host){
        int totalRows = GridPaneService.getRowCount(this.gridPane);
        System.out.println("host update");

//        for (int i = totalRows; i < prop.SERVERS.size(); i++) {
//            gridPane2.addRow(i + 1,
//                new TextField(prop.SERVERS.get(i).getHostname()),
//                new TextField("IODF"),
//                new TextField("HW IODF"),
//                new TextField("Date Time"));
//        }

            gridPane.addRow(totalRows + 1,
                new TextField(host.getHostname()),
                new TextField(host.getIodf()),
                new TextField(host.getHardwareIodf()),
                new TextField("datetime"));



    }
}
