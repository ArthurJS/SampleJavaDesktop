/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import static LanguageResources.Resource.labels;
import domein.DomeinController;
import java.io.IOException;
import javafx.animation.FadeTransition;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.util.Duration;

public class CongratulationsController extends VBox implements LanguageReload {

    @FXML
    private Label lblCongratz;
    @FXML
    private Button btnOpnieuw;
    @FXML
    private Button btnSluitAf;

    private DomeinController dc;
    private HoofdPaneelController hoofdP;
    final Font fontPoke;

    public CongratulationsController(DomeinController dc, HoofdPaneelController hoofdP) {

        this.dc = dc;
        this.hoofdP = hoofdP;
        this.hoofdP.voegControllerObjectToeAanLijst(this);
        fontPoke = Font.loadFont(CongratulationsController.class.getResource("font/Pokemon Solid.ttf").toExternalForm(), 24);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Congratulations.fxml"));
        loader.setRoot(this);
        loader.setController(this);
        try {
            loader.load();
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }

        buildGUI();

    }

    private void buildGUI() {
        FadeTransition ft = new FadeTransition(Duration.millis(1250), hoofdP);
        ft.setFromValue(0.0);
        ft.setToValue(1.0);
        ft.play();
        this.lblCongratz.setFont(fontPoke);
        updateLabels();

    }

    @Override
    public void updateLabels() {
        lblCongratz.setText(labels.getString("congratz"));
        btnOpnieuw.setText(labels.getString("GUIopnieuwSpelen"));
        btnSluitAf.setText(labels.getString("GUIStop"));
    }

    @FXML
    private void btnOpnieuwOnAction(MouseEvent event) {
        hoofdP.setCenter(this.hoofdP.getSpelnamenPaneel());
    }

    @FXML
    private void btnSluitAfOnAction(MouseEvent event) {
        Platform.exit();
    }

}
