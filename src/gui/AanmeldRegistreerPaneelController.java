/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import static LanguageResources.Resource.labels;
import domein.DomeinController;
import exceptions.GebruikersNaamException;
import exceptions.WachtwoordException;
import exceptions.WachtwoordHerhaalException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

/**
 * FXML Controller class
 *
 * @author Thibault Fouquaert
 */
public class AanmeldRegistreerPaneelController extends TabPane implements LanguageReload{

    @FXML
    private Tab tabAanmelden;
    @FXML
    private TextField txfAanmeldGebruikersnaam;
    @FXML
    private Tooltip totGebruikersnaamAanmeld;
    @FXML
    private Label lblAanmeldGebruikersnaam;
    @FXML
    private Label lblAanmeldWachtwoord;
    @FXML
    private PasswordField pwfAanmeldPaswoord;
    @FXML
    private Tooltip totWachtwoordAanmeld;
    @FXML
    private Button btnAanmeld;
    @FXML
    private ImageView imgAanmeldenPokemon;
    @FXML
    private Text txtPAanmeldopupGebruikersnaam;
    @FXML
    private Text txtPAanmeldopupPasswoord;
    @FXML
    private Tab tabRegistreer;
    @FXML
    private TextField txfRegistreerGebruikersnaam;
    @FXML
    private Label lblRegistreerGebruikersnaam;
    @FXML
    private Label lblVoornaam;
    @FXML
    private PasswordField pwfRegistreerPaswoord;
    @FXML
    private Button btnRegistreer;
    @FXML
    private PasswordField pwfRegistreerHerhaalPaswoord;
    @FXML
    private TextField txfAchternaam;
    @FXML
    private TextField txfVoornaam;
    @FXML
    private Label lblAchternaam;
    @FXML
    private Label lblRegistreerPaswoord;
    @FXML
    private Label lblRegistreerHerhaalPaswoord;
    @FXML
    private ImageView imgRegistratiePokemon;
    @FXML
    private Text txtRegistreerPopupGebruikersnaam;
    @FXML
    private Text txtRegistreerPopupPasswoord;
    @FXML
    private Text txtRegistreerPopupHerhaalPasswoord;

    private DomeinController dc;
    private HoofdPaneelController hoofdP;
    private List<String> lijstPoke;
    final Font fontReg;
    final Font fontPoke;

    public AanmeldRegistreerPaneelController(DomeinController dc, HoofdPaneelController hoofdP) {

        this.dc = dc;
        this.hoofdP = hoofdP;
        this.hoofdP.voegControllerObjectToeAanLijst(this);
        fontReg = Font.loadFont(AanmeldRegistreerPaneelController.class.getResource("font/brandon_light.OTF").toExternalForm(), 14);
        fontPoke = Font.loadFont(AanmeldRegistreerPaneelController.class.getResource("font/Pokemon Solid.ttf").toExternalForm(), 14);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("AanmeldRegistreerPaneel.fxml"));//Pad meegeven naar image etc.....
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
        updateLabels();
        Platform.runLater(new Runnable() { //java kan het nog niet requesten voor volledige initialisatie.
            @Override
            public void run() {
                txfAanmeldGebruikersnaam.requestFocus();
            }
        });;
        this.hoofdP.getMniLogOut().setDisable(false);
                
        lijstPoke = new ArrayList();
        lijstPoke.add("images/igglybuff.png");
        lijstPoke.add("images/snorlax.png");
        lijstPoke.add("images/gengar.png");
        lijstPoke.add("images/glalie.png");
        lijstPoke.add("images/houndoom.png");
        lijstPoke.add("images/meowth.png");
        lijstPoke.add("images/electabuzz.png");
        lijstPoke.add("images/arcanine.png");

        imgAanmeldenPokemon.setImage(new Image(getClass().getResourceAsStream(lijstPoke.get((int) Math.floor(Math.random() * 8)))));
        imgRegistratiePokemon.setImage(new Image(getClass().getResourceAsStream(lijstPoke.get((int) Math.floor(Math.random() * 8)))));
    }

    @Override
    public void updateLabels() {
        tabAanmelden.setText(labels.getString("GUIaanmelden"));
        tabRegistreer.setText(labels.getString("GUIregistratie"));
        lblAanmeldGebruikersnaam.setText(labels.getString("GUIgebruikersnaam"));
        lblAanmeldGebruikersnaam.setFont(fontReg);
        lblAanmeldWachtwoord.setText(labels.getString("GUIwachtwoord"));
        lblAanmeldWachtwoord.setFont(fontReg);
        lblRegistreerGebruikersnaam.setText(labels.getString("GUIgebruikersnaam"));
        lblRegistreerGebruikersnaam.setFont(fontReg);
        lblVoornaam.setText(labels.getString("GUIvoornaam"));
        lblVoornaam.setFont(fontReg);
        lblAchternaam.setText(labels.getString("GUInaam"));
        lblAchternaam.setFont(fontReg);
        lblRegistreerPaswoord.setText(labels.getString("GUIwachtwoord"));
        lblRegistreerPaswoord.setFont(fontReg);
        lblRegistreerHerhaalPaswoord.setText(labels.getString("GUIwachtwoordB"));
        lblRegistreerHerhaalPaswoord.setFont(fontReg);
        btnAanmeld.setText(labels.getString("GUIlogin"));
        btnRegistreer.setText(labels.getString("GUIregister"));
        totGebruikersnaamAanmeld.setText(labels.getString("foutieveGebruikersnaam1"));
        totWachtwoordAanmeld.setText(labels.getString("GUItotWachtwoord"));
        btnAanmeld.setFont(fontPoke);
        btnRegistreer.setFont(fontPoke);
    }

    @FXML
    private void generalEnterOnKeyPressed(KeyEvent ke) {

        if (ke.getCode().equals(KeyCode.ENTER)) {

            if (txfAanmeldGebruikersnaam.isFocused() && pwfAanmeldPaswoord.getText().isEmpty()) {
                pwfAanmeldPaswoord.requestFocus();
            }

            if (pwfAanmeldPaswoord.isFocused() && txfAanmeldGebruikersnaam.getText().isEmpty()) {
                txfAanmeldGebruikersnaam.requestFocus();
            }

            if ((!pwfAanmeldPaswoord.getText().isEmpty() && !txfAanmeldGebruikersnaam.getText().isEmpty())) {
                aanmelden();
            }

        }

    }

    @FXML
    private void btnAanmeldOnMousePressed(MouseEvent event) {
        aanmelden();
    }

    private void aanmelden() {

        try {
            pwfAanmeldPaswoord.setStyle("");
            txfAanmeldGebruikersnaam.setStyle("");
            txtPAanmeldopupGebruikersnaam.setText("");
            txtPAanmeldopupPasswoord.setText("");

            dc.meldtAan(txfAanmeldGebruikersnaam.getText(), pwfAanmeldPaswoord.getText());
            this.hoofdP.setSpelmenuPaneel(new SpelMenuController(this.dc, this.hoofdP));
            hoofdP.setCenter(this.hoofdP.getSpelmenuPaneel());

        } catch (GebruikersNaamException e) {
            txfAanmeldGebruikersnaam.setStyle("-fx-border-color:red;-fx-border-width:2px;-fx-border-radius:3;");
            txtPAanmeldopupGebruikersnaam.setWrappingWidth(this.getMaxWidth());
            txtPAanmeldopupGebruikersnaam.setText(e.getMessage());
        } catch (WachtwoordException e) {
            pwfAanmeldPaswoord.setStyle("-fx-border-color:red;-fx-border-width:2px;-fx-border-radius:3;");
            txtPAanmeldopupPasswoord.setWrappingWidth(this.getMaxWidth());
            txtPAanmeldopupPasswoord.setText(e.getMessage());
        }
    }

    @FXML
    private void btnRegistreerOnMouseClicked() {
        try {
            pwfRegistreerPaswoord.setStyle("");
            pwfRegistreerHerhaalPaswoord.setStyle("");
            txfRegistreerGebruikersnaam.setStyle("");
            txtRegistreerPopupGebruikersnaam.setText("");
            txtRegistreerPopupPasswoord.setText("");
            txtRegistreerPopupHerhaalPasswoord.setText("");

            dc.registreer(txfAchternaam.getText(), txfVoornaam.getText(), txfRegistreerGebruikersnaam.getText(), pwfRegistreerPaswoord.getText(), pwfRegistreerHerhaalPaswoord.getText());
            this.hoofdP.setSpelmenuPaneel(new SpelMenuController(this.dc, this.hoofdP));
            hoofdP.setCenter(this.hoofdP.getSpelmenuPaneel());

        } catch (GebruikersNaamException e) {
            txfRegistreerGebruikersnaam.setStyle("-fx-border-color:red;-fx-border-width:2px;-fx-border-radius:3;");
            txtRegistreerPopupGebruikersnaam.setText(e.getMessage());
            txtRegistreerPopupGebruikersnaam.setWrappingWidth(this.getMaxWidth());
        } catch (WachtwoordException e) {
            pwfRegistreerPaswoord.setStyle("-fx-border-color:red;-fx-border-width:2px;-fx-border-radius:3;");
            txtRegistreerPopupPasswoord.setText(e.getMessage());
            txtRegistreerPopupPasswoord.setWrappingWidth(this.getMaxWidth());
        } catch (WachtwoordHerhaalException e) {
            pwfRegistreerHerhaalPaswoord.setStyle("-fx-border-color:red;-fx-border-width:2px;-fx-border-radius:3;");
            txtRegistreerPopupHerhaalPasswoord.setText(e.getMessage());
            txtRegistreerPopupHerhaalPasswoord.setWrappingWidth(this.getMaxWidth());
        }

    }

}
