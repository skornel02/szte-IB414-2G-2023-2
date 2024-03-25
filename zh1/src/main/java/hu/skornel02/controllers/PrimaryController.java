package hu.skornel02.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import hu.skornel02.App;
import hu.skornel02.entities.BunozoEntity;
import hu.skornel02.repositories.BunozoRepository;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import static hu.skornel02.App.loadFXML;

public class PrimaryController implements Initializable {
    public VBox container;

    public TableView<BunozoEntity> tableView;
    public TableColumn nevCol;
    public TableColumn bandaCol;
    public TableColumn korozesCol;
    public TableColumn muveletekCol;

    public TextField nevfilter;
    public TextField bandaFilter;
    public Spinner<Integer> korozesFilter;
    private BunozoEntity filter = new BunozoEntity();

    public void createBunozo() throws IOException {
        var stage = new Stage();
        var scene = new Scene(loadFXML("bunozo"), 640, 480);
        stage.setScene(scene);
        stage.show();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        container.setOnKeyPressed(key -> {
            if (key.getCode() == KeyCode.ENTER) {
                refreshGrid();
            }
        });

        tableView.setRowFactory(val -> {
            var row = new TableRow<BunozoEntity>();
            return row;
        });

        nevCol.setCellValueFactory(new PropertyValueFactory<>("nev"));
        bandaCol.setCellValueFactory(new PropertyValueFactory<>("banda"));
        korozesCol.setCellValueFactory(new PropertyValueFactory<>("korozes"));

        korozesFilter.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(-1, 6));

        refreshGrid();
    }

    private void refreshGrid() {
        var buzonok = BunozoRepository.getInstance().find(filter);
        tableView.setItems(FXCollections.observableList(buzonok));

        if (buzonok.isEmpty() && (filter.getNev()  != null || filter.getBanda() != null || filter.getKorozes() != -1)) {
            var alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText("Figyelem");
            alert.setContentText("Nem talalhato adat, probalja mas feltellel a keresest!");
            alert.showAndWait();
        }
    }

    public void saveFilter() {
        var bandaFilter = this.bandaFilter.getText();
        filter.setBanda(bandaFilter.isEmpty() ? null : bandaFilter);

        var nevFilter = this.nevfilter.getText();
        filter.setNev(nevFilter.isEmpty() ? null : nevFilter);

        var korozesFilter = this.korozesFilter.getValue();
        filter.setKorozes(korozesFilter);

        refreshGrid();
    }
}
