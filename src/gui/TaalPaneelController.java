/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import static LanguageResources.Resource.labels;
import static LanguageResources.Resource.supportedLocales;
import domein.DomeinController;
import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;

/**
 * FXML Controller class
 *
 * @author Thibault Fouquaert
 */
public class TaalPaneelController extends HBox implements LanguageReload{
    private final DomeinController dc;
    private final HoofdPaneelController hoofdP;

    public TaalPaneelController(DomeinController dc, HoofdPaneelController hoofdP) {
        this.dc=dc;
        this.hoofdP = hoofdP;
        this.hoofdP.voegControllerObjectToeAanLijst(this);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("TaalPaneel.fxml"));//Pad meegeven naar image etc.....
        loader.setRoot(this);
        loader.setController(this);
        try {
            loader.load();
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }
    

    @FXML
    private void imgTaalNLOnMousePressed(MouseEvent event) {
        supportedLocales[2] = new Locale("nl", "BE");
        labels = ResourceBundle.getBundle("LabelsBundle", supportedLocales[2]);
        this.hoofdP.setAanmeldRegistreerPaneel(new AanmeldRegistreerPaneelController(this.dc, this.hoofdP));
        hoofdP.setCenter(this.hoofdP.getAanmeldRegistreerPaneel());
    }

    @FXML
    private void imgTaalENOnMousePressed(MouseEvent event) {
        
        supportedLocales[0] = new Locale("en", "US");
        labels = ResourceBundle.getBundle("LabelsBundle", supportedLocales[0]);
        this.hoofdP.setAanmeldRegistreerPaneel(new AanmeldRegistreerPaneelController(this.dc, this.hoofdP));
        hoofdP.setCenter(this.hoofdP.getAanmeldRegistreerPaneel());
    }

    @FXML
    private void imgTaalFROnMousePressed(MouseEvent event) {
        
        supportedLocales[1] = new Locale("fr", "BE");
        labels = ResourceBundle.getBundle("LabelsBundle", supportedLocales[1]);
        this.hoofdP.setAanmeldRegistreerPaneel(new AanmeldRegistreerPaneelController(this.dc, this.hoofdP));
        hoofdP.setCenter(this.hoofdP.getAanmeldRegistreerPaneel());
        
    }

    @Override
    public void updateLabels() {
        //lege methode omdat deze klasse geen labels bevat maar wel verplicht is de methode te implemeteren.
    }
}
