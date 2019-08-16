package controller;

import Entity.Host;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import server.Property;
import service.GridPaneService;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Stream;

public class MainPanelController implements Initializable {

    @FXML
    public GridPane gridPane1;

    private Property property;

//    Image imageProperty = new Image("/resource/img/gear.png", 40, 40, false, true);

    @FXML
    public Button btnProperty;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //UI
//        btnProperty.setGraphic(new ImageView(imageProperty));
        //
        property = Property.getInstance();
        updateSystems();
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
    protected void openPropertyAction() throws IOException {
//        System.out.println("properties clicked");
//        try {
//            java.awt.Desktop.getDesktop().edit(property.FILE);
//        } catch (IOException e) {
//            // OPEN WITH NOTEPAD
//            e.printStackTrace();
//        }

        showPropertiesPanel();
    }


    private void showPropertiesPanel() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/properties_panel.fxml"));

        Scene scene = new Scene(root, 600, 300);
        Stage stage = new Stage();
        property.setPropertySaved(false);
        stage.getIcons().add(new Image("/resource/img/icon.png"));
        stage.setOnCloseRequest(e -> updateSystems());
        stage.setOnHidden(e -> updateSystems());

        stage.setTitle("Properties");
        stage.setScene(scene);
        stage.show();
//        root.<PropertiesPanelController>getController().setTabCloseHandler(() -> root.getTabs().remove(tab));

    }

    //update textboxes
    private void updateSystems() {
        if (property.isPropertySaved()) {
            int totalRows = GridPaneService.getRowCount(gridPane1);
            while (totalRows > 1) {
                gridPane1.getChildren().remove(totalRows * 3 - 3, totalRows * 3);
                totalRows = GridPaneService.getRowCount(gridPane1);
            }
            for (int i = 0; i < property.SERVERS.size(); i++) {
                gridPane1.addRow(i + 1,
                    new TextField(property.SERVERS.get(i).getHostname()),
                    new TextField(property.SERVERS.get(i).getLogin()),
                    new TextField(property.SERVERS.get(i).getPassword()));
            }
        }
    }


    @FXML
    public void startServerAction(ActionEvent actionEvent) throws IOException {
        setSystems();
        showResultPanel((Stage) gridPane1.getScene().getWindow());
    }

    private void showResultPanel(Stage stage) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/result_panel.fxml"));
        Scene scene = new Scene(root, 600, 400);
        stage.getIcons().add(new Image("/resource/img/icon.png"));
        stage.setTitle("IODF gatherer results");
        stage.setScene(scene);
        stage.show();
    }


    //add systems from view to systems repository
    private void setSystems() {
        System.out.println(gridPane1.getChildren().size());
        List<Host> hosts = new ArrayList<>();
        for (int i = 3; i < gridPane1.getChildren().size(); i += 3) {

            Host host = new Host();
            Node hostname = gridPane1.getChildren().get(i);
            Node login = gridPane1.getChildren().get(i + 1);
            Node password = gridPane1.getChildren().get(i + 2);

            if (Stream.of(hostname, login, password).allMatch(node -> node.isManaged() && node instanceof TextField)) {
                host.setHostname(((TextField) hostname).getText());
                host.setLogin(((TextField) login).getText());
                host.setPassword(((TextField) password).getText());
            } else System.out.println("Error occurred, when parsing text boxes");

            hosts.add(host);
        }
        property.SERVERS = hosts;
    }
}
