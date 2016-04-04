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
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.control.Alert;
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
public class ConfigSpelbordController extends FlowPane implements LanguageReload {

    @FXML
    private GridPane gridpaneSpelbord;
    @FXML
    private Label lblUitlegMaakSpelbord;
    @FXML
    private Label lblAantalKisten;
    @FXML
    private Button btnControleer;
    @FXML
    private Button btnBack;
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
    private ImageView imgViewSpelbord[][] = new ImageView[10][10];
    // moet aangepast worden bij event
    private double imgSeize;
    private int Kistteller = 0;
    private int spelbordNrTeller;
    private ClipboardContent tempContent;
    private Font fontReg;
    private Font fontPoke;
    private String spelnaam;

    public ConfigSpelbordController(DomeinController dc, HoofdPaneelController hpc) {
        this.dc = dc;
        this.hoofdP = hpc;
        this.hoofdP.voegControllerObjectToeAanLijst(this);
        //this.spelbordNrTeller = spelBordNummer;
        fontReg = Font.loadFont(ConfigSpelbordController.class.getResource("font/brandon_light.OTF").toExternalForm(), 14);
        fontPoke = Font.loadFont(ConfigSpelbordController.class.getResource("font/Pokemon Solid.ttf").toExternalForm(), 14);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("ConfigSpelbord.fxml"));
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
        txfAantalDoelen.setText(String.valueOf(dc.geefAantalDoelenConfig()));
        txfAantalKisten.setText(String.valueOf(dc.geefAantalKistenConfig()));
        this.btnControleer.setDisable(true);
        
        updateLabels();
        btnControleer.setFont(fontPoke);
        btnBack.setFont(fontPoke);
        lblAantalDoelen.setFont(fontReg);
        lblAantalKisten.setFont(fontReg);
        lblUitlegMaakSpelbord.setFont(fontReg);
        lblSpelNaam.setFont(fontPoke);

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
                dc.plaatsItemWijziging(item, j, i);
                if (tempContent.getImage() == imgVergMannetje) {
                    updateSpelbord();
                }
                btnControleer.setDisable(false);
                
                txfAantalDoelen.setText(String.valueOf(dc.geefAantalDoelenConfig()));
                txfAantalKisten.setText(String.valueOf(dc.geefAantalKistenConfig()));

            }
        });

    }

    private void updateSpelbord() {
        this.gridpaneSpelbord.getChildren().clear();
        char[][] configSb = dc.geefSpelbordInGebruik();

        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                ImageView imgView = new ImageView();
                Image img = null;
                img = setGepasteImage(configSb[i][j]);
                imgView.setImage(img);
                imgView.setFitWidth(imgSeize);
                imgView.setFitHeight(imgSeize);

                setupDoel(imgView, i, j);
                this.imgViewSpelbord[i][j] = imgView;
                this.gridpaneSpelbord.add(imgView, j, i);

            }

        }
    }

    private Image setGepasteImage(char symboolOpSpelbord) {
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
            dc.IsCorrectControleGewijzigdSpelbord();
            dc.bevestigWijzigingenWegschrijven();

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Sokémon");
                alert.setGraphic(new ImageView(new Image(this.getClass().getResourceAsStream("images/complete-icon.png"))));
                alert.setHeaderText(labels.getString("GUIHeaderTextWijzigVoltooid"));
                alert.setContentText(labels.getString("GoedGedaan"));
                alert.showAndWait();

            alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Sokémon");
            alert.setHeaderText(labels.getString("GUIHeaderTextExtraWijzigSpelbord"));
            alert.setContentText(labels.getString("GUIContentTextExtraWijzigSpelbord"));

            ButtonType buttonTypeOne = new ButtonType(labels.getString("JA"));
            ButtonType buttonTypeTwo = new ButtonType(labels.getString("NEE"));

            alert.getButtonTypes().setAll(buttonTypeOne, buttonTypeTwo);

            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == buttonTypeOne) {
                this.hoofdP.setCenter(this.hoofdP.getConfigSpelbordLijstPaneel());
            } else if (result.get() == buttonTypeTwo) {
                hoofdP.setCenter(this.hoofdP.getSpelmenuPaneel());
            }
            
        } catch (NieuwSpelbordException e) {

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Sokémon");
            alert.setHeaderText(labels.getString("GUIFoutmeldingNieuwSpel"));
            alert.setContentText(e.getMessage());
            alert.showAndWait();
            
        }
    }

    @FXML
    private void btnBackOnMouseClicked(MouseEvent event) {

        hoofdP.setCenter(this.hoofdP.getConfigSpelbordLijstPaneel());
    }

    @Override
    public void updateLabels() {
        this.btnControleer.setText(labels.getString("wijzig"));
        this.btnBack.setText("Back");
        this.lblAantalDoelen.setText(labels.getString("GUIAantalDoelen"));
        this.lblAantalKisten.setText(labels.getString("GUIAantalKisten"));
        this.lblSpelNaam.setText(String.format("%s", dc.geefSpelNaam()));
        this.lblUitlegMaakSpelbord.setText(labels.getString("GUIConfigSpelbordUitleg"));
        
    }

}