/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import domein.DomeinController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import resources.ResourceHandling;

/**
 * FXML Controller class
 *
 * @author Toon
 */
public class KiesConfigureerSchermController extends VBox
{
    @FXML
    private Button btnKiesSpel;
    @FXML
    private Button btnConfigureerSpel;
    @FXML
    private Button btnTerug;
     
    private DomeinController dc;
   
    /**
     * Constructor
     * @param dc 
     */
    public KiesConfigureerSchermController(DomeinController dc)
    {
        this.dc = dc;
        
        LoaderSchermen.getInstance().setLocation("KiesConfigureerScherm.fxml", this);
        
        btnKiesSpel.setText(ResourceHandling.getInstance().getString("Keuzescherm.titel"));
        btnConfigureerSpel.setText(ResourceHandling.getInstance().getString("Keuzescherm.configureer"));
        btnTerug.setText(ResourceHandling.getInstance().getString("Annuleer.knop"));
        
        if(!dc.isSpelerBeheerder())
        {
            btnConfigureerSpel.setDisable(true);
        }
    }

    @FXML
    private void btnKiesSpelOnAction(ActionEvent event)
    {
        LoaderSchermen.getInstance().load("Sokoban", new KeuzeSpelSchermController(dc), 600, 389, this);
    }

    @FXML
    private void btnConfigureerOnAction(ActionEvent event)
    {
        LoaderSchermen.getInstance().load("Sokoban", new BewerkingsMenuSchermController(dc), 397, 385, this);
    }
    @FXML
    private void btnTerugOnAction(ActionEvent event)
    {
        LoaderSchermen.getInstance().load("Sokoban", new WelkomSchermController(), 382, 407, this);
    }
    
}
