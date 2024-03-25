package hu.skornel02.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import hu.skornel02.App;
import hu.skornel02.entities.BunozoEntity;
import hu.skornel02.repositories.BunozoRepository;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

public class BunozoController implements Initializable {

    public TextField nev;
    public TextField banda;
    public Spinner<Integer> korozes;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        korozes.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 6));
    }

    public void clear() {
        nev.setText("");
        banda.setText("");
        korozes.getValueFactory().setValue(0);
    }

    public void save() {
        var nev = this.nev.getText();
        var banda = this.banda.getText();
        var korozes = (int) this.korozes.getValue();

        if (nev == null || nev.isEmpty()) {
            var alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Hiba");
            alert.setContentText("Nem jo nev!");
            alert.showAndWait();
            return;
        }

        if (banda == null || banda.isEmpty()) {
            var alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Hiba");
            alert.setContentText("Nem jo banda!");
            alert.showAndWait();
            return;
        }

        if (korozes <0 || korozes > 6) {
            var alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Hiba");
            alert.setContentText("Nem jo korozes!");
            alert.showAndWait();
            return;
        }

        var bunozo = new BunozoEntity();
        bunozo.setNev(nev);
        bunozo.setBanda(banda);
        bunozo.setKorozes(korozes);

        if (!BunozoRepository.getInstance().add(bunozo)) {
            var alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Hiba");
            alert.setContentText("Nem sikert letrehozni!");
            alert.showAndWait();
            return;
        }

        var alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText("Siker");
        alert.setContentText("Bunozo sikeresen letrehozva!");
        alert.showAndWait();
    }
}