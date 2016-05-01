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
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import resources.ResourceHandling;

/**
 * FXML Controller class
 *
 * @author donovandesmedt
 */
public class GaVerderSchermController extends GridPane
{
    @FXML
    private Label lblTitel;
    @FXML
    private Label lblInvoer;
    @FXML
    private TextField txfInvoer;
    @FXML
    private Button btnNew;
    @FXML
    private Button btnCancel;
    @FXML
    private Label lblMessage;
    private DomeinController dc;

    /**
     * constructor
     * @param dc 
     */
    public GaVerderSchermController(DomeinController dc)
    {
        this.dc = dc;
        LoaderSchermen.getInstance().setLocation("GaVerderScherm.fxml", this);
        lblTitel.setText(ResourceHandling.getInstance().getString("Knop.verder"));
        lblInvoer.setText(ResourceHandling.getInstance().getString("Invoer.creatie.spelbordNaam"));
    }

    @FXML
    private void btnNewOnAction(ActionEvent event)
    {
        if(!txfInvoer.getText().isEmpty())
        {
            String spelbord = txfInvoer.getText();
            String spel = dc.geefSpel();
            dc.setSpel(spel);
            dc.ConfigureerSpelbord(spelbord);
            dc.updateSpelbordLijst();
            dc.setSpelbord(spelbord);
            LoaderSchermen.getInstance().load("Sokoban", new ConfigureerSchermController(dc, false), 500, 442, this);
        }
        else
        {
            lblMessage.setText(ResourceHandling.getInstance().getString("Invoer.creatie.spelbordNaam"));
        }
    }

    @FXML
    private void btnCancelOnAction(ActionEvent event)
    {
        LoaderSchermen.getInstance().load("Sokoban", new KiesConfigureerSchermController(dc), 300, 300, this);
    }
    
}
