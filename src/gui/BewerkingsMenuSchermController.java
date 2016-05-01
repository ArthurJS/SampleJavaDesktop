/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import domein.DomeinController;
import java.util.Timer;
import java.util.TimerTask;
import javafx.application.Platform;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import resources.ResourceHandling;

/**
 * FXML Controller class
 *
 * @author donovandesmedt
 */
public class BewerkingsMenuSchermController extends GridPane
{

    @FXML
    private Button btnNew;
    @FXML
    private Button btnBack;
    @FXML
    private Button btnBewerk;
    private DomeinController dc;
    @FXML
    private Label lblMessage;
    @FXML
    private ListView<String> lijstSpelen;
    ObservableList<String> options;
    boolean spelGekozen;
    private String spelNaam;
    private String spelbordNaam;
    @FXML
    private Label lblInvoerSpelNaam;
    @FXML
    private Label lblInvoerSpelbordNaam;
    @FXML
    private Button btnDelete;
    private String[] spelen;
    @FXML
    private TextField txfSpelnaam;
    @FXML
    private TextField txfSpelbordnaam;

    /**
     * Constructor
     * @param dc 
     */
    public BewerkingsMenuSchermController(DomeinController dc)
    {

        this.dc = dc;
        
        LoaderSchermen.getInstance().setLocation("BewerkingsMenuScherm.fxml", this);      
        
        txfSpelnaam.setPromptText(ResourceHandling.getInstance().getString("Invoer.creatie.spelNaam"));
        txfSpelbordnaam.setPromptText(ResourceHandling.getInstance().getString("Invoer.creatie.spelbordNaam"));
        btnBack.setText(ResourceHandling.getInstance().getString("Knop.terug"));
        btnBewerk.setText(ResourceHandling.getInstance().getString("Spelbord.bewerk"));
        btnNew.setText(ResourceHandling.getInstance().getString("Spelbord.nieuw"));
        btnDelete.setText(ResourceHandling.getInstance().getString("Knop.delete"));
        
        spelGekozen = false;
        
        lblInvoerSpelNaam.setText(ResourceHandling.getInstance().getString("Invoer.creatie.spelNaam"));
        lblInvoerSpelbordNaam.setText(ResourceHandling.getInstance().getString("Invoer.creatie.spelbordNaam"));
        
        vulLijst();

        updateTimer();
    }

    /**
     * Deze methode gaat over een tijdsinterval een check doen of er iets is veranderd
     */
    private void updateTimer()
    {
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask()
        {
            @Override
            public void run()
            {
                Platform.runLater(new Runnable()
                {
                    public void run()
                    {
                        lijstSpelen.setItems(options);
                    }
                });
            }
        }, 0, 100);
    }

    /**
     * Opvullen van de lijsten
     */
    private void vulLijst()
    {
        spelen = dc.geefLijstSpelen();
        options = FXCollections.observableArrayList(spelen);
        lijstSpelen.setItems(options);
        selectSpel();
    }

    /**
     * Selecteren van een Spel
     */
    private void selectSpel()
    {
        lijstSpelen.getSelectionModel().selectedItemProperty()
                .addListener(new InvalidationListener()
                        {
                            @Override
                            public void invalidated(Observable o)
                            {
                                if (lijstSpelen.getSelectionModel().getSelectedItem() != null)
                                {

                                    if (spelGekozen)
                                    {
                                        /**
                                         * Gekozen spelbord setten.
                                         */
                                        spelbordNaam = lijstSpelen.getSelectionModel().getSelectedItem();
                                        dc.setSpelbord(spelbordNaam);                                      
                                        txfSpelbordnaam.setText(spelbordNaam);
                                        

                                    } else
                                    {
                                        txfSpelnaam.setPromptText(ResourceHandling.getInstance().getString("Invoer.wijzig.spelNaam"));
                                        txfSpelbordnaam.setPromptText(ResourceHandling.getInstance().getString("Invoer.wijzig.spelbordNaam"));
                                        lblInvoerSpelNaam.setText(ResourceHandling.getInstance().getString("Invoer.wijzig.spelNaam"));
                                        lblInvoerSpelbordNaam.setText(ResourceHandling.getInstance().getString("Invoer.wijzig.spelbordNaam"));
                                        
                                        spelNaam = lijstSpelen.getSelectionModel().getSelectedItem();
                                        setGekozenSpel();
                                        options = FXCollections.observableArrayList(dc.geefLijstSpelborden(spelNaam));
                                        spelGekozen = true;
                                        txfSpelnaam.setText(spelNaam);
                                    }
                                }
                            }
                });

    }

    /**
     * Het gekozen Spel wordt geset
     */
    private void setGekozenSpel()
    {
        for (String spel : spelen)
        {
            if (spel.equals(spelNaam))
            {
                dc.setSpel(spel);
            }
        }
    }

    @FXML
    private void btnBackOnAction(ActionEvent event)
    {
        LoaderSchermen.getInstance().load("Sokoban", new KiesConfigureerSchermController(dc), 300, 300, this);
    }

    @FXML
    private void btnBewerkOnAction(ActionEvent event)
    {
        if (!txfSpelnaam.getText().isEmpty())
        {
            dc.saveSpelNaam(txfSpelnaam.getText());
            if(!txfSpelbordnaam.getText().isEmpty())
            {               
                dc.saveSpelbordNaam(txfSpelbordnaam.getText());
            }         
        }
        else
        {
            if(!txfSpelbordnaam.getText().isEmpty())
            {               
                dc.saveSpelbordNaam(txfSpelbordnaam.getText());
            }
        }
        try
        {
            LoaderSchermen.getInstance().load("Sokoban", new ConfigureerSchermController(dc, true), 500, 442, this);
        }
        catch(Exception e)
        {
            System.out.println(e.getMessage());
            lblMessage.setText(ResourceHandling.getInstance().getString("Exception.selecteerSpelbord"));
        }
    }

    @FXML
    private void btnDeleteOnAction(ActionEvent event)
    {
        dc.deleteSpelbord(spelbordNaam);
        dc.setSpel(spelNaam);
        options = FXCollections.observableArrayList(dc.geefLijstSpelborden(spelNaam));
    }
    
    @FXML
    private void btnNewOnAction(ActionEvent event)
    {
        String spel = txfSpelnaam.getText();
        String spelbord = txfSpelbordnaam.getText();

        if (spel == null || spel.isEmpty())
        {
            lblMessage.setText(ResourceHandling.getInstance().getString("Invoer.creatie.spelNaam"));
            txfSpelnaam.requestFocus();
        }
        else if (spelbord == null || spelbord.isEmpty())
        {
            lblMessage.setText(ResourceHandling.getInstance().getString("Invoer.creatie.spelbordNaam"));
            txfSpelbordnaam.requestFocus();
        }
        else
        {
        if (dc.bestaatSpel(spel))
            {
                dc.setSpel(spel);
                if(dc.bestaatSpelbord(spelbord))
                {
                    lblMessage.setText(ResourceHandling.getInstance().getString("Spelbord.bestaatAl"));
                    txfSpelbordnaam.setText("");
                    txfSpelbordnaam.requestFocus();
                }
                else
                {
                    //dc.setSpelbord(spelbord);
                    dc.ConfigureerSpelbord(spelbord);
                    dc.updateSpelbordLijst();
                    /**
                     * in dc moet de methode configureerSpel.setLeegSpelbord(spelbord); geschreven worden met het huidige spelbord
                     */
                    dc.setSpelbord(spelbord);
                    laadVolgendScherm();
                }
            }
        else
            {
                dc.ConfigureerSpel(spel);
                dc.ConfigureerSpelbord(spelbord);
                laadVolgendScherm();
            }
        }
    }
    
    /**
     * Verder naar het volgende scherm gaan om het spelbord aan te passen
     */
    private void laadVolgendScherm()
    {
        LoaderSchermen.getInstance().load("Sokoban", new ConfigureerSchermController(dc, false), 500, 442, this);
    }

    

}
