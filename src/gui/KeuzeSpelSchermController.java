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
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import resources.ResourceHandling;

/**
 * FXML Controller class
 *
 * @author donovandesmedt
 */
public class KeuzeSpelSchermController extends Pane
{

    @FXML
    private ListView<String> lijstSpelen;
    @FXML
    private ImageView imgView;
    @FXML
    private Button btnSpeel;
    @FXML
    private Button btnQuit;
    private DomeinController dc;
    private boolean isCustom;
    private boolean change;
    ObservableList<String> options;
    private String spelNaam;

    
    /**
     * constructor
     * @param dc 
     */
    public KeuzeSpelSchermController(DomeinController dc)
    {
        this.dc = dc;
        isCustom = false;
        change = false;

        LoaderSchermen.getInstance().setLocation("KeuzeSpelScherm.fxml", this);
       
        btnSpeel.setText(ResourceHandling.getInstance().getString("Speel.knop"));
        btnQuit.setText(ResourceHandling.getInstance().getString("Knop.terug"));
        vulLijst();

       updateTimer();
    }
    
    /**
     * Deze methode gaat in een bepaalde tijdsinterval kijken wat er veranderd is en de aanpassingen maken
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
                        if(change)
                            lijstSpelen.setItems(options);
                    }
                });
            }
        }, 0, 100);
    }

    
    /**
     * Invullen van de lijsten
     */
    private void vulLijst()
    {
        options = FXCollections.observableArrayList(dc.geefLijstSpelen());
        lijstSpelen.setItems(options);
        //thumbnail("basic");
        selectSpel();
    }

    /**
     * Selecteren van een spel
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
                                    if (lijstSpelen.getSelectionModel().getSelectedItem().equals("custommap"))
                                    {
                                        options = FXCollections.observableArrayList(dc.geefCustomSpelen());

                                        change = true;
                                        isCustom = true;

                                    } else
                                    {
                                        spelNaam = lijstSpelen.getSelectionModel().getSelectedItem();
                                        dc.kiesSpel(spelNaam, isCustom);
                                        try {
                                            thumbnail(spelNaam);
                                        } catch (Exception e) {
                                            thumbnail("basic");
                                        }
                                    }
                                }
                            }
                });

    }

    /**
     * Tekenen van thumbnail van een spelbord
     * @param spelnaam 
     */
    private void thumbnail(String spelnaam)
    {
        imgView.setImage(new Image("/images/" + spelnaam + ".png"));
    }

    @FXML
    private void btnSpeelOnAction(ActionEvent event)
    {
        try
        {
            LoaderSchermen.getInstance().load("Sokoban", new SpelbordController(dc), 450, 300, this);
        } catch (Exception e)
        {
            if(spelNaam != null)
            {
                if(dc.geefLijstSpelborden(spelNaam).length == 0)
                {
                    Alert boodschap = new Alert(Alert.AlertType.WARNING);
                    boodschap.setTitle("WARNING");
                    boodschap.setHeaderText(ResourceHandling.getInstance().getString("Exception.geenSpelborden"));
                    boodschap.showAndWait();
                }
            }
            else
            {
                Alert boodschap = new Alert(Alert.AlertType.WARNING);
                boodschap.setTitle("WARNING");
                boodschap.setHeaderText(ResourceHandling.getInstance().getString("Exception.kiesbord"));
                boodschap.showAndWait();
            }
        }
    }

    @FXML
    private void btnQuitOnAction(ActionEvent event)
    {
        LoaderSchermen.getInstance().load("Sokoban", new KiesConfigureerSchermController(dc), 300, 300, this);
    }

}
