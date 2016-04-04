/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import static LanguageResources.Resource.supportedLocales;
import static LanguageResources.Resource.labels;
import domein.DomeinController;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.BorderPane;

/**
 * FXML Controller class
 *
 * @author Thibault Fouquaert
 */
public class HoofdPaneelController extends BorderPane {

    @FXML
    private MenuItem mniLogOut;
    @FXML
    private MenuItem mniSpelMenu;

    private DomeinController dc;
    private TaalPaneelController taalPaneel;
    private AanmeldRegistreerPaneelController aanmeldRegistreerPaneel;
    private SpelMenuController spelmenuPaneel;
    private SpelNamenController spelnamenPaneel;
    private SpelbordController spelbordPaneel;
    private CongratulationsController congratulationsPaneel;
    private MaakSpelbordController maakSpelbordPaneel;
    private List<Object> paneelObjecten;
    private ConfigSpelController configSpelPaneel;
    private ConfigSpelbordLijstController configSpelbordLijstPaneel;
    private ConfigSpelbordController configSpelbordPaneel;

    public HoofdPaneelController(DomeinController dc) {
        this.dc = dc;
        paneelObjecten = new ArrayList<>();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("HoofdPaneel.fxml"));//Pad meegeven naar image etc.....
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
        taalPaneel = new TaalPaneelController(dc, this);
        this.mniLogOut.setDisable(true);
        this.mniSpelMenu.setDisable(true);
        this.setCenter(taalPaneel);

    }

    public MenuItem getMniSpelMenu() {
        return mniSpelMenu;
    }

    public void setMniSpelMenu(MenuItem mniSpelMenu) {
        this.mniSpelMenu = mniSpelMenu;
    }

    
    public MenuItem getMniLogOut() {
        return mniLogOut;
    }

    public void voegControllerObjectToeAanLijst(Object object) {
        this.paneelObjecten.add(object);
    }

    public TaalPaneelController getTaalPaneel() {
        return taalPaneel;
    }

    public AanmeldRegistreerPaneelController getAanmeldRegistreerPaneel() {
        return aanmeldRegistreerPaneel;
    }

    public SpelMenuController getSpelmenuPaneel() {
        return spelmenuPaneel;
    }


    public ConfigSpelbordLijstController getConfigSpelbordLijstPaneel() {
        return configSpelbordLijstPaneel;
    }

    public void setConfigSpelbordLijstPaneel(ConfigSpelbordLijstController configSpelbordLijstPaneel) {
        this.configSpelbordLijstPaneel = configSpelbordLijstPaneel;
    }

    public ConfigSpelbordController getConfigSpelbordPaneel() {
        return configSpelbordPaneel;
    }

    public void setConfigSpelbordPaneel(ConfigSpelbordController configSpelbordPaneel) {
        this.configSpelbordPaneel = configSpelbordPaneel;
    }
    
    
    
    public SpelNamenController getSpelnamenPaneel() {
        return spelnamenPaneel;
    }

    public ConfigSpelController getConfigspelPaneel() {
        return configSpelPaneel;
    }

    public void setConfigspelPaneel(ConfigSpelController configspelPaneel) {
        this.configSpelPaneel = configspelPaneel;
    }
    

    public SpelbordController getSpelbordPaneel() {
        return spelbordPaneel;
    }

    public MaakSpelbordController getMaakSpelbordPaneel() {
        return maakSpelbordPaneel;
    }

    public void setMaakSpelbordPaneel(MaakSpelbordController maakSpelbordPaneel) {
        this.maakSpelbordPaneel = maakSpelbordPaneel;
    }
    
    

    public void setTaalPaneel(TaalPaneelController taalPaneel) {
        this.taalPaneel = taalPaneel;
    }

    public void setAanmeldRegistreerPaneel(AanmeldRegistreerPaneelController aanmeldRegistreerPaneel) {
        this.aanmeldRegistreerPaneel = aanmeldRegistreerPaneel;
    }

    public void setSpelmenuPaneel(SpelMenuController spelmenuPaneel) {
        this.spelmenuPaneel = spelmenuPaneel;
    }

    public void setSpelnamenPaneel(SpelNamenController spelnamenPaneel) {
        this.spelnamenPaneel = spelnamenPaneel;
    }

    public void setSpelbordPaneel(SpelbordController spelbordPaneel) {
        this.spelbordPaneel = spelbordPaneel;
    }

    public CongratulationsController getCongratulationsPaneel() {
        return congratulationsPaneel;
    }

    public void setCongratulationsPaneel(CongratulationsController congratulationsPaneel) {
        this.congratulationsPaneel = congratulationsPaneel;
    }

    @FXML
    private void mniCloseOnAction() {
        Platform.exit();
    }

    @FXML
    private void mniLogOutOnAction(ActionEvent event) {
        this.setCenter(this.aanmeldRegistreerPaneel);
    }

    @FXML
    private void mniNederlandsOnAction() {
        if (supportedLocales[2] == Locale.ROOT) {
            supportedLocales[2] = new Locale("nl", "BE");
        }
        labels = ResourceBundle.getBundle("LabelsBundle", supportedLocales[2]);
        reload();
    }

    @FXML
    private void mniEngelsOnAction(ActionEvent event) {
        if (supportedLocales[0] == Locale.ENGLISH) {
            supportedLocales[0] = new Locale("en", "US");
        }
        labels = ResourceBundle.getBundle("LabelsBundle", supportedLocales[0]);
        reload();
    }

    @FXML
    private void mniFransOnAction(ActionEvent event) {
        if (supportedLocales[1] == Locale.FRENCH) {
            supportedLocales[1] = new Locale("fr", "BE");
        }
        labels = ResourceBundle.getBundle("LabelsBundle", supportedLocales[1]);
        reload();
    }

    @FXML
    private void mniAboutOnAction(ActionEvent event) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle(labels.getString("GUIaboutheader"));
        alert.setHeaderText(null);
        alert.setContentText(labels.getString("GUIabout"));

        alert.showAndWait();
    }
    
    @FXML
    private void mniSpelMenuOnAction(ActionEvent event){
        this.setCenter(this.spelmenuPaneel);
    }

    private void reload() {
        Node centerNode = this.getCenter();
        for (Object obj : paneelObjecten) {
            if (centerNode.getClass().equals(obj.getClass())) {
                ((LanguageReload) obj).updateLabels();
            }
        }

    }

}
