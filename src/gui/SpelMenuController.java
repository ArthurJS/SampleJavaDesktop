/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import static LanguageResources.Resource.labels;
import domein.DomeinController;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.control.TextInputDialog;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;

/**
 * FXML Controller class
 *
 * @author Mario
 */
public class SpelMenuController extends GridPane implements LanguageReload {

    @FXML
    private Label LblPokeBuild;
    @FXML
    private Label LblPokeConfig;
    @FXML
    private ImageView ImgPokeBuild;
    @FXML
    private ImageView ImgBallRight;
    @FXML
    private ImageView ImgPokeConfig;
    @FXML
    private ImageView ImgPokePlay;
    @FXML
    private ImageView ImgBallLeft;
    @FXML
    private Label lblWelkom;
    @FXML
    private Label LblPokePlay;

    private DomeinController dc;
    private HoofdPaneelController hoofdP;
    private Font fontReg;
    private Font fontPoke;
    private List spelNamen;

    public SpelMenuController(DomeinController dc, HoofdPaneelController hoofdP) {

        this.dc = dc;
        this.hoofdP = hoofdP;
        this.hoofdP.voegControllerObjectToeAanLijst(this);
        fontReg = Font.loadFont(SpelMenuController.class.getResource("font/brandon_light.OTF").toExternalForm(), 14);
        fontPoke = Font.loadFont(SpelMenuController.class.getResource("font/Pokemon Solid.ttf").toExternalForm(), 14);
        spelNamen = dc.geefNaamSpellen();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("SpelMenu.fxml"));//Pad meegeven naar image etc.....
        loader.setRoot(this);
        loader.setController(this);
        try {
            loader.load();
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
        this.hoofdP.getMniSpelMenu().setDisable(false);
        updateLabels();
    }

    @Override
    public void updateLabels() {
        lblWelkom.setText(String.format("%s%s", labels.getString("GUIwelkom"), dc.geefGebruikersnaam()));
        lblWelkom.setFont(Font.loadFont(AanmeldRegistreerPaneelController.class.getResource("font/brandon_light.OTF").toExternalForm(), 25));
        LblPokePlay.setText(labels.getString("GUIspeel"));
        LblPokePlay.setFont(fontReg);
        LblPokeBuild.setText(labels.getString("GUIbouw"));
        LblPokeBuild.setFont(fontReg);
        LblPokeConfig.setText(labels.getString("GUIconfig"));
        LblPokeConfig.setFont(fontReg);
    }

    @FXML
    private void imgPokePlayOnMouseClicked(MouseEvent event) {
        play();
    }

    @FXML
    private void lblPokePlayOnMouseClicked(MouseEvent event) {
        play();
    }

    private void play() {
        this.hoofdP.setSpelnamenPaneel(new SpelNamenController(this.dc, this.hoofdP));
        hoofdP.setCenter(this.hoofdP.getSpelnamenPaneel());
    }

    private void build() {
        TextInputDialog dialog = new TextInputDialog(labels.getString("GUISpelnaamNieuwSpel"));
        dialog.setTitle("Sokémon");
        dialog.setHeaderText(labels.getString("GUIHeaderTextNieuwSpel"));
        dialog.setContentText(labels.getString("GUIContentTextNieuwSpel"));
        errorDialog(dialog);//moet met rucursie!!!!!!
    }

    private void errorDialog(TextInputDialog dialog) {
        Optional<String> result = dialog.showAndWait();
        if (result.isPresent()) {
            String temp = result.get();

            if (containsCaseInsensitive(temp, spelNamen)) {
                dialog.setGraphic(new ImageView(new Image(this.getClass().getResourceAsStream("images/icon-error.png"))));
                dialog.setHeaderText(labels.getString("GUIFoutmeldingNieuwSpel"));
                dialog.setContentText(labels.getString("GUIFNaamBestaatNiewSpel"));
                errorDialog(dialog);
            } else {
                this.hoofdP.setMaakSpelbordPaneel(new MaakSpelbordController(this.dc, this.hoofdP, temp));
                hoofdP.setCenter(this.hoofdP.getMaakSpelbordPaneel());
            }
        }
    }

    private void config() {

        if (dc.geefAdminrechten()) {
            this.hoofdP.setConfigspelPaneel(new ConfigSpelController(this.dc, this.hoofdP));
            hoofdP.setCenter(this.hoofdP.getConfigspelPaneel());
        } else {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Sokémon");
            alert.setHeaderText(labels.getString("GUIFoutmeldingNieuwSpel"));
            alert.setContentText(labels.getString("GUIadmin"));
            alert.showAndWait();
        }
    }

    public boolean containsCaseInsensitive(String strToCompare, List<String> list) {
        for (String str : list) {
            if (str.equalsIgnoreCase(strToCompare)) {
                return (true);
            }
        }
        return (false);
    }

    @FXML
    private void LblPokeBuildOnMouseClicked(MouseEvent event) {
        build();
    }

    @FXML
    private void LblPokeConfigOnMouseClicked(MouseEvent event) {
        config();
    }

    @FXML
    private void ImgPokeBuildOnMouseClicked(MouseEvent event) {
        build();
    }

    @FXML
    private void ImgPokeConfigOnMouseClicked(MouseEvent event) {
        config();
    }

}
