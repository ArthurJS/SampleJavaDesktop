/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import domein.DomeinController;
import javafx.scene.paint.Color;
import java.util.Optional;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.stage.Stage;
import resources.ResourceHandling;

/**
 * FXML Controller class
 *
 * @author donovandesmedt
 */
public class ConfigureerSchermController extends Pane
{

    @FXML
    private GridPane gridSpeelveld;
    @FXML
    private GridPane gridKeuze;
    @FXML
    private Button btnSave;
    @FXML
    private Label lblNaam;

    private DomeinController dc;
    private Canvas[][] canvasSpeelveld;
    private Canvas[][] canvasKeuzeveld;
    private String[][] spelbord;
    private int keuzeID = 0;
    private boolean droppedMouse;

    @FXML
    private Pane pane;
    @FXML
    private Label lblLoading;
    private String spelNaam;
    private String spelbordNaam;
    private int previousCol;
    private int previousRow;
    @FXML
    private Button btnBack;
    private boolean wijzigSpel;

    /**
     * Constructor
     *
     * @param dc
     * @param wijzig
     */
    public ConfigureerSchermController(DomeinController dc, boolean wijzig)
    {
        previousCol = -1;
        previousRow = -1;
        this.dc = dc;

        LoaderSchermen.getInstance().setLocation("ConfigureerScherm.fxml", this);
        /**
         * Het aanmaken van nieuw leeg Spelbord
         */
        spelbord = dc.geefDefaultSpelbord();
        spelNaam = dc.geefConfigureerSpel();
        spelbordNaam = dc.geefConfigureerSpelbord();

        btnSave.setText(ResourceHandling.getInstance().getString("Opslaan.btn"));
        btnBack.setText(ResourceHandling.getInstance().getString("Annuleer.knop"));

        canvasSpeelveld = vulCanvasLijstin(gridSpeelveld, 10, 10);
        canvasKeuzeveld = vulCanvasLijstin(gridKeuze, 1, 5);

        vulSpeelbordIn(spelbord);
        vulKeuzeBordIn();

    }

    /**
     * Deze methode zal voor de verschillende vakken van de gridpane canvassen maken.
     */
    private Canvas[][] vulCanvasLijstin(GridPane gridpane, int kolommen, int rijen)
    {
        Canvas[][] canvassen = new Canvas[kolommen][rijen];

        for (int i = 0; i < canvassen.length; i++)
        {
            for (int j = 0; j < canvassen[i].length; j++)
            {
                Canvas csSpeel = new Canvas(30, 30);
                gridpane.add(csSpeel, i, j);
                canvassen[i][j] = csSpeel;
                GridPane.setHalignment(csSpeel, HPos.CENTER);
                GridPane.setValignment(csSpeel, VPos.CENTER);

                GridPane.setHgrow(csSpeel, Priority.ALWAYS);
                GridPane.setVgrow(csSpeel, Priority.ALWAYS);
            }
        }

        return canvassen;
    }

    /**
     * invullen van het spelbord
     *
     * @param spelbord
     */
    private void vulSpeelbordIn(String[][] spelbord)
    {
        for (int i = 0; i < spelbord.length; i++)
        {
            for (int j = 0; j < spelbord[i].length; j++)
            {
                GraphicsContext gc = canvasSpeelveld[j][i].getGraphicsContext2D();
                //gc.setFill(Color.BLACK);
                vulIn(spelbord[i][j], gc);
            }
        }
    }

    /**
     * Deze methode zal de gemaakte canvassen opvullen met de juiste symbolen.
     */
    private void vulKeuzeBordIn()
    {
        String[] lijstItems = dc.geefLijstItems();
        for (int i = 0; i < canvasKeuzeveld.length; i++)
        {
            for (int j = 0; j < canvasKeuzeveld[i].length; j++)
            {
                GraphicsContext gc = canvasKeuzeveld[i][j].getGraphicsContext2D();
                //checkImage(j + 1, gc);
                vulIn(lijstItems[j], gc);
            }
        }
        //dc.geefLijstItems();
    }

    /**
     * Tekenen van een Image
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
     * Deze methode zal kijken wanneer er een symbool van de keuzelijst aangeklikt wordt of gedragged wordt.
     *
     * @param event
     */
    @FXML
    private void keuzeMouseMoved(MouseEvent event)
    {
        droppedMouse = true;
        ObservableList<Node> children = gridKeuze.getChildren();

        for (Node node : children)
        {
            /**
             * KeuzeID waarde toekennen wanneer er op een symbool geclickt word.
             */
            node.setOnMouseClicked(new EventHandler<MouseEvent>()
            {
                @Override
                public void handle(MouseEvent event)
                {
                    keuzeID = gridKeuze.getRowIndex(node);
                    geselecteerdSymbool();
                }

            });
        }
    }

    /**
     * Aangeven welk symbool geselecteerd is.
     */
    private void geselecteerdSymbool()
    {
        /**
         * selecteren van vak
         */
        vulKeuzeBordIn();
        GraphicsContext gc = canvasKeuzeveld[0][keuzeID].getGraphicsContext2D();
        tekenRechtHoek(gc, 0, 0, 30, 30, Color.RED);
    }

    /**
     * gekleurde rechthoeken tekenen voor de speler feedback te geven.
     *
     * @param gc
     * @param linksX
     * @param linksY
     * @param rechtsOnderX
     * @param rechtsOnderY
     * @param kleur
     */
    private void tekenRechtHoek(GraphicsContext gc, int linksX, int linksY, int rechtsOnderX, int rechtsOnderY, Color kleur)
    {
        gc.beginPath();
        gc.setStroke(kleur);
        gc.setLineWidth(3);
        gc.rect(linksX, linksY, rechtsOnderX, rechtsOnderY);
        gc.stroke();
        gc.closePath();
    }

    /**
     * Deze methode zal zorgen voor de visuele feedback bij het hoveren.
     *
     * @param node
     * @param droppedMouse
     * @param click
     */
    private void hoverOverSpeelVak(Node node)
    {
        int row = 0;
        int col = 0;

        try
        {
            row = GridPane.getRowIndex(node);
            col = GridPane.getColumnIndex(node);
        } catch (Exception e)
        {
            System.out.println(e.getMessage());
            System.out.println(row + ", " + col);
        }

        if (previousCol != -1 && previousRow != -1)
        {
            GraphicsContext gc = canvasSpeelveld[previousCol][previousRow].getGraphicsContext2D();
            gc.clearRect(0, 0, 30, 30);
            String teken = dc.geefVakOpPositie(previousRow, previousCol);
            vulIn(teken, gc);
        }
        //vulSpeelbordIn(spelbord);
        GraphicsContext gc = canvasSpeelveld[col][row].getGraphicsContext2D();
        tekenRechtHoek(gc, 0, 0, 30, 30, Color.RED);

        previousCol = col;
        previousRow = row;
    }

    /**
     * Uitmaken welke Image getekent moet worden
     *
     * @param teken
     * @param gc
     */
    private void vulIn(String teken, GraphicsContext gc)
    {
        switch (teken)
        {
            case "#":
                drawImage(gc, "/images/muur.png", 0, 0);
                break;
            case "#r":
                drawImage(gc, "/images/muur_rand.png", 0, 0);
                break;
            case "@":
                drawImage(gc, "/images/wandel.png", 0, 0);
                drawImage(gc, "/images/hero_onder.png", 5, 5);
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

    /**
     * Plaatsen van een vak op het spelbord
     *
     * @param node
     */
    private void plaatsVak(Node node)
    {
        /**
         * Plaatsen van een vak
         */
        int row = gridSpeelveld.getRowIndex(node);
        int col = gridSpeelveld.getColumnIndex(node);

        dc.plaatsVak(col, row, keuzeID);
    }

    /**
     * Deze methode zal kijken wanneer er met de muist over het spelbord gehoverd wordt en wanneer er een vak van het spelbord aangeklikt wordt.
     *
     * @param event
     */
    @FXML
    private void speelMouseMoved(MouseEvent event)
    {
        ObservableList<Node> childrenList = gridSpeelveld.getChildren();

        if (droppedMouse)
        {
            for (Node node : childrenList)
            {
                node.setOnMouseMoved(new EventHandler<MouseEvent>()
                {
                    @Override
                    public void handle(MouseEvent event)
                    {
                        /*Hoveren*/
                        hoverOverSpeelVak(node);
                    }
                });

                node.setOnMouseClicked(new EventHandler<MouseEvent>()
                {
                    @Override
                    public void handle(MouseEvent event)
                    {
                        /*plaatsen van een vak*/
                        plaatsVak(node);
                        spelbord = dc.geefDefaultSpelbord();
                        vulSpeelbordIn(spelbord);
                    }
                });
            }
        }
    }

    /**
     * Deze methode zal kijken welke afbeelding getekend moet worden.
     *
     * @param id
     * @param gc
     */
    private void checkImage(int id, GraphicsContext gc)
    {
        switch (id)
        {
            case 1:
                drawImage(gc, "/images/wandel.png", 0, 0);
                drawImage(gc, "/images/hero_onder.png", 5, 5);
                break;
            case 2:
                drawImage(gc, "/images/wandel.png", 0, 0);
                break;
            case 3:
                drawImage(gc, "/images/doel.png", 0, 0);
                break;
            case 4:
                drawImage(gc, "/images/muur.png", 0, 0);
                break;
            case 5:
                drawImage(gc, "/images/wandel.png", 0, 0);
                drawImage(gc, "/images/kist.png", 5, 5);
                break;
        }
    }

    @FXML
    private void btnSaveOnAction(ActionEvent event)
    {
        alert(Alert.AlertType.WARNING, "ATTENTION", dc.gebreken());

        /**
         * volgende methode gaat de lege vakken opvullen met muren en het spelbord in de db plaatsen.
         */
        lblLoading.setText("Saving...");

        Stage stage = new Stage();
        Scene loadScene = new Scene(new LaadSchermController(), 300, 200);
        stage.setTitle("Saving!");
        stage.setScene(loadScene);
        stage.setResizable(false);

        if (dc.bevatAlleOnderdelen())
        {
            if (wijzigSpel)
            {
                try
                {
                    stage.show();
                    dc.zetGewijzigdSpelbordInDatabase(spelNaam, spelbordNaam);
                    dc.wijzigSpelNaam();
                    dc.wijzigSpelbordNaam();
                    lblLoading.setText("");
                } catch (Exception e)
                {
                    alert(Alert.AlertType.WARNING, "ERROR", ResourceHandling.getInstance().getString("Exception.opslaan.map"));
                    lblLoading.setText("");
                }
            } else
            {
                try
                {
                    stage.show();
                    if (!dc.bestaatSpel(spelNaam))
                    {
                        dc.zetSpelInDatabase(spelNaam);
                    }

                    if (dc.bestaatSpelbord(spelbordNaam))
                    {
                        dc.zetGewijzigdSpelbordInDatabase(spelNaam, spelbordNaam);
                    } else
                    {
                        dc.zetSpelbordInDatabase(spelbordNaam);
                    }

                    lblLoading.setText("");

                } catch (Exception e)
                {
                    alert(Alert.AlertType.WARNING, "ERROR", ResourceHandling.getInstance().getString("Exception.opslaan.map"));
                    lblLoading.setText("");
                }
            }
            stage.close();
            lblLoading.setText("");
            if (!wijzigSpel)
            {
                gaVerder();
            } else
            {
                LoaderSchermen.getInstance().load("Sokoban", new KiesConfigureerSchermController(dc), 300, 300, this);
            }
        }
    }

    /**
     * Methode die vraagt of je een nieuw spelbord wilt maken of terug wilt keren
     */
    private void gaVerder()
    {
        /**
         * Dialog tonen waarbij de gebruiker de keuze krijgt om al dan niet nog een spelbord te maken.
         */
        Alert keuze = new Alert(Alert.AlertType.CONFIRMATION);
        keuze.setTitle(ResourceHandling.getInstance().getString("Configureer.gaverder"));
        keuze.setHeaderText(ResourceHandling.getInstance().getString("Knop.verder"));

        ButtonType btnNieuw = new ButtonType(ResourceHandling.getInstance().getString("Spelbord.nieuw"));
        ButtonType btnCancel = new ButtonType(ResourceHandling.getInstance().getString("Annuleer.knop"));

        keuze.getButtonTypes().setAll(btnCancel, btnNieuw);
        Optional<ButtonType> result = keuze.showAndWait();

        if (result.get() == btnNieuw)
        {
            LoaderSchermen.getInstance().load("Sokoban", new GaVerderSchermController(dc), 488, 213, this);
        } else
        {
            LoaderSchermen.getInstance().load("Sokoban", new KiesConfigureerSchermController(dc), 300, 300, this);
        }
    }

    private void alert(Alert.AlertType soort, String titel, String header)
    {
        Alert.AlertType al = Alert.AlertType.WARNING;
        switch (soort)
        {
            case WARNING:
                al = Alert.AlertType.WARNING;
                break;
            case ERROR:
                al = Alert.AlertType.ERROR;
                break;
        }
        Alert boodschap = new Alert(al);
        boodschap.setTitle(titel);
        boodschap.setHeaderText(header);
        boodschap.showAndWait();
    }

    @FXML
    private void btnBackOnAction(ActionEvent event)
    {
        LoaderSchermen.getInstance().load(ResourceHandling.getInstance().getString("Keuzescherm.titel"), new KiesConfigureerSchermController(dc), 300, 300, this);
    }
}
