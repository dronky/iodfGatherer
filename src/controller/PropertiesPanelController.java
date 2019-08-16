package controller;

import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import server.Property;

import java.io.IOException;
import java.net.URL;
import java.util.Observable;
import java.util.Observer;
import java.util.ResourceBundle;

public class PropertiesPanelController implements Initializable, Observer {

    public TextField tfSystems;
    public TextField tfAddress;
    public TextField tfConsole;
    public TextField tfMsgclass;
    public Button btnSave;
    public Button btnCancel;
    private Property prop;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        prop = Property.getInstance();
        prop.reReadFile();
        tfSystems.setText(String.join(";",prop.SYSTEMS));
        tfAddress.setText(prop.ADDRESS);
        tfConsole.setText(prop.CONSOLE);
        tfMsgclass.setText(prop.MSGCLASS);
    }


    public void saveAction(ActionEvent actionEvent) throws IOException {
        //TODO check fields
        prop.updateSystems(tfSystems.getText());
        prop.updateAddress(tfAddress.getText());
        prop.updateConsole(tfConsole.getText());
        prop.updateMsgclass(tfMsgclass.getText());
        prop.reReadFile();
        ((Stage) btnCancel.getScene().getWindow()).close();
    }

    public void cancelAction(ActionEvent actionEvent) {
        ((Stage) btnCancel.getScene().getWindow()).close();
    }

    @Override
    public void update(Observable o, Object arg) {

    }
}
