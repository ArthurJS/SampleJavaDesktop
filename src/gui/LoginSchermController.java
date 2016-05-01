/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import domein.DomeinController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import resources.ResourceHandling;

/**
 * FXML Controller class
 *
 * @author donovandesmedt
 */
public class LoginSchermController extends Pane
{

    @FXML
    private Label lblGebruikersnaam;
    @FXML
    private Label lblWachtwoord;
    @FXML
    private TextField txfGebruikersnaam;
    @FXML
    private PasswordField psfWachtwoord;
    @FXML
    private Hyperlink hplVergeten;
    @FXML
    private Button btnLogin;
    @FXML
    private Button btnCancel;
    @FXML
    private Label lblFout;
    private DomeinController dc;
    private WelkomSchermController welkomscherm;

    /**
     * Initializes the controller class.
     */
    public LoginSchermController(DomeinController dc)
    {
        this.dc = dc;
        
        LoaderSchermen.getInstance().setLocation("LoginScherm.fxml", this);
       
        lblGebruikersnaam.setText( ResourceHandling.getInstance().getString("Aanmelden.Gebruikersnaam"));
        lblWachtwoord.setText( ResourceHandling.getInstance().getString("Aanmelden.Wachtwoord"));
        btnLogin.setText( ResourceHandling.getInstance().getString("Aanmelden.knop"));
        btnCancel.setText( ResourceHandling.getInstance().getString("Annuleer.knop"));
        hplVergeten.setText( ResourceHandling.getInstance().getString("WachtwoordVergeten.boodschap"));
        txfGebruikersnaam.requestFocus();
    }

    @FXML
    private void hplVergetenOnAction(ActionEvent event)
    {
        if (txfGebruikersnaam.getText().isEmpty())
        {
            lblFout.setText( ResourceHandling.getInstance().getString("Exception.aanmelden.leegVeld"));         
        } else
        {
            try
            {
                String ww = dc.wachtwoordVergeten(txfGebruikersnaam.getText());
                if (ww.isEmpty())
                {
                    lblFout.setText( ResourceHandling.getInstance().getString("WachtwoordVergeten.SpelerOnbestaand"));
                } else
                {
                    alert(Alert.AlertType.ERROR, "ERROR",  ResourceHandling.getInstance().getString("WachtwoordVergeten.tip") + " " + ww.charAt(0) + " " +  ResourceHandling.getInstance().getString("WachtwoordVergeten.tip2") + " " + ww.charAt(ww.length() - 1));
                }
                
            } catch (Exception e)
            {
                lblFout.setText( ResourceHandling.getInstance().getString("Exception.registreer.wachtwoord"));
            }
        }
    }

    @FXML
    private void btnLoginOnAction(ActionEvent event)
    {
        try
        {
            dc.meldAan(txfGebruikersnaam.getText(), psfWachtwoord.getText());
            
            alert(Alert.AlertType.INFORMATION,  ResourceHandling.getInstance().getString("Aanmelden.succes"), dc.geefSpeler() + " " +  ResourceHandling.getInstance().getString("Registratie.succes2"));
             
            LoaderSchermen.getInstance().load(ResourceHandling.getInstance().getString("Keuzescherm.titel"), new KiesConfigureerSchermController(dc), 300, 300,this);
        } catch (Exception e)
        {
            leegVeld(txfGebruikersnaam);
            leegVeld(psfWachtwoord);
            lblFout.setText( ResourceHandling.getInstance().getString("Exception.aanmelden"));
        }
    }
    @FXML
    private void btnCancelOnAction(ActionEvent event)
    {
        LoaderSchermen.getInstance().load("Sokoban", new WelkomSchermController(), 382, 407, this);
    }
    
    private void leegVeld(TextField txf)
    {
        if(txf.getText().isEmpty())
        {
            txf.setId("Fout");
        }
        else
            txf.setId("");
    }
    
    /**
     * Alert scherm aanmaken
     * @param soort
     * @param titel
     * @param header 
     */
    private void alert(Alert.AlertType soort, String titel, String header)
    {
        Alert.AlertType al = Alert.AlertType.WARNING;
        switch (soort)
        {
            case WARNING: al = Alert.AlertType.WARNING;
            case ERROR: al = Alert.AlertType.ERROR;
            case INFORMATION: al = Alert.AlertType.INFORMATION;
        }
        Alert boodschap = new Alert(al);
        boodschap.setTitle(titel);
        boodschap.setHeaderText(header);
        boodschap.showAndWait();
    }   
}
