/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import domein.DomeinController;
import java.util.Optional;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.canvas.Canvas;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.canvas.*;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import resources.ResourceHandling;

/**
 * FXML Controller class
 *
 * @author Toon
 */
public class SpelbordController extends GridPane
{

    private DomeinController domeinController;
    private String[][] spelbord;
    @FXML
    private Button btnReset;
    @FXML
    private Label lblSpelNaam;
    @FXML
    private Label lblSpelbordNaam;
    @FXML
    private Label lblVerplaatsing;

    private Canvas[][] canvasLijst;
    private KeyCode keyCode;
    @FXML
    private Button btnHome;

    public SpelbordController(DomeinController dc)
    {
        domeinController = dc;
        keyCode = KeyCode.DOWN;

        LoaderSchermen.getInstance().setLocation("Spelbord.fxml", this);

        /**
         * Canvas lijst aanvragen
         */
        canvasLijst = new Canvas[10][10];
        vulCanvasLijstin();

        spelbord = domeinController.geefSpelbord();
        vulSpeelbordIn(spelbord);
        lblVerplaatsing.setText("0");
        lblSpelNaam.setText(domeinController.geefSpel());
        lblSpelbordNaam.setText(domeinController.geefGekozenSpelbord());
        btnHome.setText(ResourceHandling.getInstance().getString("Knop.home"));
    }

    /**
     * Aanmajken van canvassen
     */
    private void vulCanvasLijstin()
    {
        for (int i = 0; i < canvasLijst.length; i++)
        {
            for (int j = 0; j < canvasLijst[i].length; j++)
            {
                Canvas canvas = new Canvas(30, 30);
                this.add(canvas, i, j);
                canvasLijst[i][j] = canvas;
                GridPane.setHalignment(canvas, HPos.CENTER);
                GridPane.setValignment(canvas, VPos.CENTER);

                GridPane.setHgrow(canvas, Priority.ALWAYS);
                GridPane.setVgrow(canvas, Priority.ALWAYS);
            }
        }
    }

    /**
     * Invullen van de images voor het spelbord
     *
     * @param spelbord
     */
    private void vulSpeelbordIn(String[][] spelbord)
    {
        for (int i = 0; i < spelbord.length; i++)
        {
            for (int j = 0; j < spelbord[i].length; j++)
            {

                GraphicsContext gc = canvasLijst[j][i].getGraphicsContext2D();

                switch (spelbord[i][j])
                {
                    case "#":
                        drawImage(gc, "/images/muur.png", 0, 0);
                        break;
                    case "#r":
                        drawImage(gc, "/images/muur_rand.png", 0, 0);
                        break;
                    case "@":
                        drawImage(gc, "/images/wandel.png", 0, 0);
                        tekenMannetje(gc);
                        break;
                    case ".":
                        drawImage(gc, "/images/doel.png", 0, 0);
                        break;
                    case "$.":
                        drawImage(gc, "/images/doel.png", 0, 0);
                        drawImage(gc, "/images/kist.png", 5, 5);
                        break;
                    case "@.":
                        drawImage(gc, "/images/doel.png", 0, 0);
                        tekenMannetje(gc);
                        break;
                    case "$":
                        drawImage(gc, "/images/wandel.png", 0, 0);
                        drawImage(gc, "/images/kist.png", 5, 5);
                        break;
                    case "_":
                        drawImage(gc, "/images/wandel.png", 0, 0);
                        break;

                }
            }
        }
    }

    /**
     * Dit gaat tekenen hoe mannetje getoont moet worden
     *
     * @param gc
     */
    public void tekenMannetje(GraphicsContext gc)
    {
        switch (keyCode)
        {
            case LEFT:
                drawImage(gc, "/images/hero_links.png", 5, 5);
                break;
            case RIGHT:
                drawImage(gc, "/images/hero_rechts.png", 5, 5);
                break;
            case DOWN:
                drawImage(gc, "/images/hero_onder.png", 5, 5);
                break;
            case UP:
                drawImage(gc, "/images/hero_boven.png", 5, 5);
                break;
        }
    }

    /**
     * Tekenen van de Image
     *
     * @param gc
     * @param path
     * @param xPos
     * @param yPos
     */
    public void drawImage(GraphicsContext gc, String path, int xPos, int yPos)
    {
        gc.drawImage(new Image(getClass().getResourceAsStream(path)), xPos, yPos);
    }

    /**
     * Omzetten van een KeyEvent naar een String
     *
     * @param event
     * @return
     */
    private String keyEventToString(KeyEvent event)
    {
        KeyCode key = event.getCode();

        switch (key)
        {
            case LEFT:
                return "links";
            case RIGHT:
                return "rechts";
            case DOWN:
                return "onder";
            case UP:
                return "boven";
            default:
                throw new IllegalArgumentException("Verkeerde command ingegeven!");
        }

    }

    @FXML
    private void gridPaneOnKeyPressed(KeyEvent event)
    {
        domeinController.verplaatsHero(keyEventToString(event));
        keyCode = event.getCode();

        lblVerplaatsing.setText(String.format("%d", domeinController.geefAantalVerplaatsingen()));
        spelbord = domeinController.geefSpelbord();

        if (domeinController.isSpelbordVoltooid())
        {
            vulSpeelbordIn(spelbord);

            domeinController.zoekNietVoltooidSpelbord();
            lblSpelbordNaam.setText(domeinController.geefGekozenSpelbord());
            spelbord = domeinController.geefSpelbord();

            if (domeinController.isEindeSpel())
            {
                LoaderSchermen.getInstance().load("Sokoban", new KeuzeSpelSchermController(domeinController), 600, 389, this);
            } else
            {
                verderSpelen();
            }

            keyCode = KeyCode.DOWN;
        }

        vulSpeelbordIn(spelbord);

    }

    @FXML
    private void resetOnAction(ActionEvent event)
    {
        domeinController.Reset();
        spelbord = null;
        spelbord = domeinController.geefSpelbord();
        lblVerplaatsing.setText("0");
        vulSpeelbordIn(spelbord);
    }

    /**
     * Dit gaat vragen of er nog verder gespeelt wilt worden
     */
    private void verderSpelen()
    {
        Alert boodschap = new Alert(Alert.AlertType.CONFIRMATION);
        boodschap.setTitle(ResourceHandling.getInstance().getString("Speelverder"));
        boodschap.setHeaderText(ResourceHandling.getInstance().getString("Speelverder"));

        ButtonType Annuleer = new ButtonType(ResourceHandling.getInstance().getString("Nee"));
        ButtonType Ok = new ButtonType(ResourceHandling.getInstance().getString("Ja"));
        boodschap.getButtonTypes().setAll(Annuleer, Ok);
        Optional<ButtonType> result = boodschap.showAndWait();

        if (result.get() == Annuleer)
        {
            LoaderSchermen.getInstance().load("sokoban", new KiesConfigureerSchermController(domeinController), 300, 300, this);
        }
    }

    @FXML
    private void btnHomeOnAction(ActionEvent event)
    {
        LoaderSchermen.getInstance().load("Sokoban", new KiesConfigureerSchermController(domeinController), 300, 300, this);
    }

}
