/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import static LanguageResources.Resource.labels;
import domein.DomeinController;
import exceptions.NieuwSpelbordException;
import java.io.IOException;
import java.util.Optional;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Cursor;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;

/**
 * FXML Controller class
 *
 * @author Mario
 */
public class MaakSpelbordController extends FlowPane implements LanguageReload {

    @FXML
    private GridPane gridpaneSpelbord;
    @FXML
    private Label lblUitlegMaakSpelbord;
    @FXML
    private Label lblAantalKisten;
    @FXML
    private Button btnControleer;
    @FXML
    private Button btnReset;
    @FXML
    private TextField txfAantalKisten;
    @FXML
    private TextField txfAantalDoelen;
    @FXML
    private Label lblAantalDoelen;
    @FXML
    private ImageView imgSleepMuur;
    @FXML
    private ImageView imgSleepDoel;
    @FXML
    private ImageView imgSleepMannetje;
    @FXML
    private ImageView imgSleepKist;
    @FXML
    private ImageView imgSleepLeeg;
    @FXML
    private ImageView imgSleepOpvulling;
    @FXML
    private TextField txfSpelNaam;
    @FXML
    private Label lblSpelNaam;

    private Image imgVergMannetje;
    private Image imgVergLeeg;
    private Image imgVergOpvulling;
    private Image imgVergKist;
    private Image imgVergDoel;
    private Image imgVergMuur;

    private DomeinController dc;
    private HoofdPaneelController hoofdP;
    private ImageView imgViewSpelbord[][] = new ImageView[10][10]; //Deze 2d array gebruiken om nieuwe imgviews te zetten in de rijen/kols vd gridpane
    // moet aangepast worden bij event
    private double imgSeize;// =  ((this.hoofdP.getWidth()/10.0)-2);
    private int Kistteller = 0;
    private int spelbordNrTeller = 1;
    private ClipboardContent tempContent;
    private Font fontReg;
    private Font fontPoke;

    public MaakSpelbordController(DomeinController dc, HoofdPaneelController hpc, String temp) {
        this.dc = dc;
        this.hoofdP = hpc;
        this.hoofdP.voegControllerObjectToeAanLijst(this);
        this.dc.maakSpel(temp);
        fontReg = Font.loadFont(MaakSpelbordController.class.getResource("font/brandon_light.OTF").toExternalForm(), 14);
        fontPoke = Font.loadFont(MaakSpelbordController.class.getResource("font/Pokemon Solid.ttf").toExternalForm(), 14);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("MaakSpelbord.fxml"));
        loader.setRoot(this);
        loader.setController(this);
        try {
            loader.load();
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
        imgSeize = ((this.hoofdP.getCenter().computeAreaInScreen() / this.hoofdP.getWidth() / 10.0) - 5.5);
        buildGUI();
    }

    private void buildGUI() {
        txfAantalDoelen.setText(String.valueOf(0));
        txfAantalKisten.setText(String.valueOf(0));
        this.btnControleer.setDisable(true);
        this.btnReset.setDisable(true);
        updateLabels();
        btnControleer.setFont(fontPoke);
        btnReset.setFont(fontPoke);
        lblAantalDoelen.setFont(fontReg);
        lblAantalKisten.setFont(fontReg);
        lblUitlegMaakSpelbord.setFont(fontReg);
        lblSpelNaam.setFont(fontPoke);

        dc.maakSpelbord(spelbordNrTeller);

        imgVergMannetje = new Image(this.getClass().getResourceAsStream("images/MannetjePickachuSand.png"));
        imgVergLeeg = new Image(this.getClass().getResourceAsStream("images/Sand-empty-veld.png"));
        imgVergOpvulling = new Image(this.getClass().getResourceAsStream("images/Grass-Background.png"));
        imgVergKist = new Image(this.getClass().getResourceAsStream("images/Kist3.png"));
        imgVergDoel = new Image(this.getClass().getResourceAsStream("images/doel.png"));
        imgVergMuur = new Image(this.getClass().getResourceAsStream("images/Wall-red-tree.png"));

        imgSleepMannetje.setImage(imgVergMannetje);
        setupBron(imgSleepMannetje);
        imgSleepKist.setImage(imgVergKist);
        setupBron(imgSleepKist);
        imgSleepMuur.setImage(imgVergMuur);
        setupBron(imgSleepMuur);
        imgSleepDoel.setImage(imgVergDoel);
        setupBron(imgSleepDoel);
        imgSleepOpvulling.setImage(imgVergOpvulling);
        setupBron(imgSleepOpvulling);
        imgSleepLeeg.setImage(imgVergLeeg);
        setupBron(imgSleepLeeg);

        updateSpelbord();
    }

    private void setupBron(ImageView iv) {

        iv.setOnDragDetected(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent event) {

                Dragboard db = iv.startDragAndDrop(TransferMode.MOVE);
//                ImageView ghost = iv;
//                ghost.setFitHeight(45);
//                ghost.setFitWidth(45);
//                Image ghostImg = ghost.getImage();
//                Image ghostImg = new Image(this.getClass().getResourceAsStream("images/Sand-empty-veld.png"));
//                db.setDragView(ghostImg);
                ClipboardContent content = new ClipboardContent();
                content.putImage(iv.getImage());
                db.setContent(content);
                tempContent = content;

                event.consume();
            }
        });

        iv.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
                iv.setCursor(Cursor.HAND);
            }
        });
    }

    private void setupDoel(ImageView ivT, int i, int j) {

        ivT.setOnDragOver(new EventHandler<DragEvent>() {
            @Override
            public void handle(DragEvent event) {

                Dragboard db = event.getDragboard();

                if (db.hasImage()) {
                    event.acceptTransferModes(TransferMode.MOVE);
                }

                event.consume();

            }
        });

        ivT.setOnDragDropped(new EventHandler<DragEvent>() {
            @Override
            public void handle(DragEvent event) {
                char item = 0;
                Dragboard db = event.getDragboard();
                if (db.hasImage()) {
                    if (tempContent.getImage() == imgVergMannetje) {
                        ivT.setImage(db.getImage());
                        item = '@';
                    } else if (tempContent.getImage() == imgVergDoel) {
                        ivT.setImage(db.getImage());
                        item = 'o';
                    } else if (tempContent.getImage() == imgVergKist) {
                        ivT.setImage(db.getImage());
                        item = 'k';
                    } else if (tempContent.getImage() == imgVergLeeg) {
                        ivT.setImage(db.getImage());
                        item = '.';
                    } else if (tempContent.getImage() == imgVergOpvulling) {
                        ivT.setImage(db.getImage());
                        item = '+';
                    } else if (tempContent.getImage() == imgVergMuur) {
                        ivT.setImage(db.getImage());
                        item = '#';
                    }
                    event.setDropCompleted(true);
                } else {
                    event.setDropCompleted(false);
                }

                event.consume();
                dc.plaatsItem(item, j, i);
                //omdat er maar 1 mannetje kan zijn zal het vorige mannetje verdwijnen indien er een nieuw word gezet, dit kan enkel getoond worden door updaten vd map.
                if (tempContent.getImage() == imgVergMannetje) {
                    updateSpelbord();
                }
                btnControleer.setDisable(false);
                btnReset.setDisable(false);
                txfAantalDoelen.setText(String.valueOf(dc.geefAantalDoelen()));
                txfAantalKisten.setText(String.valueOf(dc.geefAantalKisten()));

                
//                String spelbord = "";
//                for (char[] rijen : dc.geefNieuwSpelbord()) {
//                    for (char kolommen : rijen) {
//                        spelbord += kolommen + " ";
//                    }
//                    spelbord += "\n";
//                }
//                System.out.println(spelbord);

            }
        });

    }

    private void updateSpelbord() {
        this.gridpaneSpelbord.getChildren().clear();
        char[][] nieuwSb = dc.geefNieuwSpelbord();

        for (int i = 0; i < 10; i++) {//Deze methode is eigenlijk niet performant. Beter met enhanced for kijken welke images veranderd zijn en die aanpassen.
            for (int j = 0; j < 10; j++) {
                ImageView imgView = new ImageView();
                Image img = null;
                img = setGepasteImage(nieuwSb[i][j]);
                imgView.setImage(img);
                imgView.setFitWidth(imgSeize);
                imgView.setFitHeight(imgSeize);

                setupDoel(imgView, i, j);
                this.imgViewSpelbord[i][j] = imgView;
                this.gridpaneSpelbord.add(imgView, j, i);

            }

        }
    }

    private Image setGepasteImage(char symboolOpSpelbord) {//gebruiken voor updaten en creatie.
        Image img = null;

        switch (symboolOpSpelbord) {
            case '@':
                img = imgVergMannetje;
                break;
            case 'K':
                img = imgVergKist;
                break;
            case '#':
                img = imgVergMuur;
                break;
            case 'o':
                img = imgVergDoel;
                break;
            case '*':
                img = new Image(this.getClass().getResourceAsStream("images/KistOpDoel.png"));
                break;
            case '.':
                img = imgVergLeeg;
                break;
            case '+':
                img = imgVergOpvulling;
                break;
        }

        return img;
    }

    @FXML
    private void btnControleerOnMouseClicked(MouseEvent event) {
        try {
            dc.IsCorrectControleNieuwSpelbord();

            Alert alert = new Alert(AlertType.CONFIRMATION);
            alert.setTitle("Sokémon");
            alert.setHeaderText(labels.getString("GUIHeaderTextExtraSpelbord"));
            alert.setContentText(labels.getString("GUIContentTextExtraSpelbord"));


            ButtonType buttonTypeOne = new ButtonType(labels.getString("JA"));
            ButtonType buttonTypeTwo = new ButtonType(labels.getString("NEE"));

            alert.getButtonTypes().setAll(buttonTypeOne, buttonTypeTwo);

            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == buttonTypeOne) {
                spelbordNrTeller++;
                buildGUI();
            } else if (result.get() == buttonTypeTwo) {
                dc.registreerNieuwSpel();

                alert = new Alert(AlertType.INFORMATION);
                alert.setTitle("Sokémon");
                alert.setGraphic(new ImageView(new Image(this.getClass().getResourceAsStream("images/complete-icon.png"))));
                alert.setHeaderText(labels.getString("GUIHeaderTextCreatieVoltooid"));
                alert.setContentText(labels.getString("AantalSpelborden") + dc.geefTotaalAantalSpelbordenVanInGuiGekozenSpel(dc.geefNiewSpelSpelnaam()) + labels.getString("GoedGedaan"));
                alert.showAndWait();

                hoofdP.setCenter(this.hoofdP.getSpelmenuPaneel());
            }
            //pop up menu nieuw spelbord of stoppen :p
        } catch (NieuwSpelbordException e) {

            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Sokémon");
            alert.setHeaderText(labels.getString("GUIFoutmeldingNieuwSpel"));
            alert.setContentText(e.getMessage());
            alert.showAndWait();
            // System.out.println(e.getMessage());

        }
    }

    @FXML
    private void btnResetOnMouseClicked(MouseEvent event) {

        spelbordNrTeller--;
        dc.resetBord(spelbordNrTeller);
        updateSpelbord();
        this.btnReset.setDisable(true);
        this.btnControleer.setDisable(true);
        txfAantalDoelen.setText(String.valueOf(0));
        txfAantalKisten.setText(String.valueOf(0));
    }

    @Override
    public void updateLabels() {
        this.btnControleer.setText(labels.getString("maak"));
        this.btnReset.setText(labels.getString("reset"));
        this.lblAantalDoelen.setText(labels.getString("GUIAantalDoelen"));
        this.lblAantalKisten.setText(labels.getString("GUIAantalKisten"));
        this.lblSpelNaam.setText(dc.geefNiewSpelSpelnaam());
        this.lblUitlegMaakSpelbord.setText(labels.getString("GUIMaakSpelbordUitleg"));
    }

}
