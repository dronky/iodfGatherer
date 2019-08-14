package controller;

import Entity.Server;
import Entity.ServerRepository;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.stream.Stream;

public class MainPanelController implements Initializable {

    @FXML
    public GridPane grdPane1;

    @FXML
    public Button myButton;

    @FXML
    public TextField myTextField;

    @FXML
    public Text actiontarget;

    private ServerRepository serverRepository;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //TODO init statements

        serverRepository = new ServerRepository();
    }

    @FXML
    protected void addServerAction(ActionEvent event) {
        int totalRows = getRowCount(grdPane1);
        grdPane1.addRow(totalRows, new TextField("hostname"), new TextField("login"), new TextField("password"));
    }

    @FXML
    protected void removeServerAction(ActionEvent event) {
        int totalRows = getRowCount(grdPane1) - 1;
        if (totalRows > 1) grdPane1.getChildren().remove(totalRows * 3, totalRows * 3 + 3);
    }

    @FXML
    public void startServerAction(ActionEvent actionEvent) {
        setSystems();
        System.out.println(serverRepository);
        //        Main.main();
    }

    private void setSystems() {
        System.out.println(grdPane1.getChildren().size());
        for (int i = 3; i < grdPane1.getChildren().size(); i += 3) {

            Server server = new Server();
            Node hostname = grdPane1.getChildren().get(i);
            Node login = grdPane1.getChildren().get(i + 1);
            Node password = grdPane1.getChildren().get(i + 2);

            if (Stream.of(hostname, login, password).allMatch(node -> node.isManaged() && node instanceof TextField)) {
                server.setHostname(((TextField) hostname).getText());
                server.setLogin(((TextField) login).getText());
                server.setPassword(((TextField) password).getText());
            } else System.out.println("Error occurred, when parsing text boxes");

            serverRepository.add(server);
        }
    }

    private int getRowCount(GridPane pane) {
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
}
