package controller;

import Entity.Server;
import Entity.ServerRepository;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import service.GridPaneService;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.stream.Stream;

public class MainPanelController implements Initializable {

    @FXML
    public GridPane gridPane1;

    private ServerRepository serverRepository;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //TODO init statements

        serverRepository = ServerRepository.getInstance();
    }

    @FXML
    protected void addServerAction(ActionEvent event) {
        int totalRows = GridPaneService.getRowCount(gridPane1);
        gridPane1.addRow(totalRows, new TextField("hostname"), new TextField("login"), new TextField("password"));
    }

    @FXML
    protected void removeServerAction(ActionEvent event) {
        int totalRows = GridPaneService.getRowCount(gridPane1) - 1;
        if (totalRows > 1) gridPane1.getChildren().remove(totalRows * 3, totalRows * 3 + 3);
    }

    @FXML
    public void startServerAction(ActionEvent actionEvent) throws IOException {
        setSystems();

        //TODO pass system repo to result controller

        //        Main.main();
        showResultPanel((Stage) gridPane1.getScene().getWindow());
    }

    private void showResultPanel(Stage stage) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/result_panel.fxml"));
        Scene scene = new Scene(root, 600, 400);

        stage.setTitle("IODF gatherer results");
        stage.setScene(scene);
        stage.show();
    }


    //add systems from view to systems repository
    private void setSystems() {
        System.out.println(gridPane1.getChildren().size());
        for (int i = 3; i < gridPane1.getChildren().size(); i += 3) {

            Server server = new Server();
            Node hostname = gridPane1.getChildren().get(i);
            Node login = gridPane1.getChildren().get(i + 1);
            Node password = gridPane1.getChildren().get(i + 2);

            if (Stream.of(hostname, login, password).allMatch(node -> node.isManaged() && node instanceof TextField)) {
                server.setHostname(((TextField) hostname).getText());
                server.setLogin(((TextField) login).getText());
                server.setPassword(((TextField) password).getText());
            } else System.out.println("Error occurred, when parsing text boxes");

            serverRepository.add(server);
        }
    }
}
