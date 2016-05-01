/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import domein.DomeinController;
import java.util.Optional;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import resources.ResourceHandling;

/**
 * FXML Controller class
 *
 * @author donovandesmedt
 */
public class WelkomSchermController extends GridPane
{

    @FXML
    private ComboBox<String> cbTaal;
    @FXML
    private Button btnLogin;
    @FXML
    private Button btnRegistreer;
    @FXML
    private Button btnQuit;


    Stage primaryStage;
    
    /**
     * Constructor
     */
    public WelkomSchermController()
    {
        LoaderSchermen.getInstance().setLocation("WelkomScherm.fxml", this);
        vulCombobox();
    }

    /**
     * Invullen van de comboBox voor talen
     */
    private void vulCombobox()
    {
        ObservableList<String> options
                = FXCollections.observableArrayList(
                        "Nederlands",
                        "Français",
                        "English"
                );
        cbTaal.setItems(options);

    }

    @FXML
    private void cbTaalOnAction(ActionEvent event)
    {
        String initiaal = cbTaal.getSelectionModel().getSelectedItem().substring(0, 2).toLowerCase();
        
        switch (initiaal)
        {
            case "ne":               
                ResourceHandling.getInstance().stelInLanguage("nl", "resources.ResourceBundle_Sokoban_nl");
                break;
            case "en":
                 ResourceHandling.getInstance().stelInLanguage(initiaal, "resources.ResourceBundle_Sokoban_en");
                break;
            case "fr":
                 ResourceHandling.getInstance().stelInLanguage(initiaal, "resources.ResourceBundle_Sokoban_fr");
                break;
        }
        btnLogin.setText( ResourceHandling.getInstance().getString("Aanmelden.knop"));
        btnRegistreer.setText( ResourceHandling.getInstance().getString("Registreer.knop"));
        btnQuit.setText( ResourceHandling.getInstance().getString("Annuleer.knop"));
    }

    @FXML
    private void btnLoginOnAction(ActionEvent event)
    {
        LoaderSchermen.getInstance().load(ResourceHandling.getInstance().getString("Aanmelden.knop"), new LoginSchermController(new DomeinController()), 369, 321, this);
    }

    @FXML
    private void btnRegistreerOnAction(ActionEvent event)
    { 
        LoaderSchermen.getInstance().load(ResourceHandling.getInstance().getString("Registreer.knop"), new RegistratieSchermController(new DomeinController()), 609, 466, this);
    }

    @FXML
    private void btnQuitOnAction(ActionEvent event)
    {
        Alert boodschap = new Alert(Alert.AlertType.CONFIRMATION);
        boodschap.setTitle( ResourceHandling.getInstance().getString("Annuleer.knop"));
        boodschap.setHeaderText( ResourceHandling.getInstance().getString("Quit"));

        ButtonType Annuleer = new ButtonType( ResourceHandling.getInstance().getString("Annuleer.knop"));
        ButtonType Ok = new ButtonType("Ok");
        boodschap.getButtonTypes().setAll(Annuleer, Ok);
        Optional<ButtonType> result = boodschap.showAndWait();
        
        if (result.get() == Ok)
        {
            System.exit(0);
        }
    }
}
