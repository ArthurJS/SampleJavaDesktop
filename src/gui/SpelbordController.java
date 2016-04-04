package gui;

import static LanguageResources.Resource.labels;
import domein.DomeinController;
import java.io.IOException;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;

public class SpelbordController extends FlowPane implements LanguageReload {

    @FXML
    private GridPane gridpaneSpelbord;
    @FXML
    private Label lblTotaalAantalSpelborden;
    @FXML
    private Label lblHuidigSpelBord;
    @FXML
    private TextField txfAantalSpelbordenTotaal;
    @FXML
    private TextField txfAantalVoltooid;
    @FXML
    private TextField txfAantalStappen;
    @FXML
    private ImageView movePic;
    @FXML
    private Button btnUndo;
    @FXML
    private Button btnReset;
    @FXML
    private Label lblSpelNaam;
    @FXML
    private Button btnLijstSpelNamen;
//    @FXML
//    private Button btnSpelMenu;

    private DomeinController dc;
    private HoofdPaneelController hoofdP;
    private ImageView imgViewSpelbord[][] = new ImageView[10][10]; //Deze 2d array gebruiken om nieuwe imgviews te zetten in de rijen/kols vd gridpane
    // moet aangepast worden bij event
    private double imgSeize;// =  ((this.hoofdP.getWidth()/10.0)-2);
    private int Kistteller = 0;
    private Font fontReg;
    private Font fontPoke;
    private Object me = this;

    public SpelbordController(DomeinController dc, HoofdPaneelController hpc) {
        this.dc = dc;
        this.hoofdP = hpc;
        this.hoofdP.voegControllerObjectToeAanLijst(this);
        fontReg = Font.loadFont(SpelbordController.class.getResource("font/brandon_light.OTF").toExternalForm(), 14);
        fontPoke = Font.loadFont(SpelbordController.class.getResource("font/Pokemon Solid.ttf").toExternalForm(), 14);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Spelbord.fxml"));//Pad meegeven naar image etc.....
        loader.setRoot(this);
        loader.setController(this);
        try {
            loader.load();
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
        buildGUI();
    }

    private void buildGUI() {
        imgSeize = ((this.hoofdP.getCenter().computeAreaInScreen() / this.hoofdP.getWidth() / 10.0) - 5.5);
        this.btnUndo.setDisable(true);
        this.btnReset.setDisable(true);
        btnReset.setFont(fontPoke);
        btnUndo.setFont(fontPoke);
        lblSpelNaam.setFont(fontPoke);
        updateLabels();
        updateSpelbord();

        Scene scene = this.hoofdP.getScene();
        scene.setOnKeyPressed(new EventHandler<KeyEvent>() {

            @Override
            public void handle(KeyEvent event) {
                if (hoofdP.getCenter().getClass().equals(me.getClass())) {//referen naar this wijst niet meer naar Spelbordcontroller in innerklasse.

                    String keuze = "";
                    if (event.getCode() == KeyCode.Z) {
                        keuze = "z";
                        movePic.setImage(new Image(getClass().getResourceAsStream("images/MovesUp.png")));
                    } else if (event.getCode() == KeyCode.Q) {
                        keuze = "q";
                        movePic.setImage(new Image(getClass().getResourceAsStream("images/MovesLeft.png")));
                    } else if (event.getCode() == KeyCode.S) {
                        keuze = "s";
                        movePic.setImage(new Image(getClass().getResourceAsStream("images/MovesDown.png")));
                    } else if (event.getCode() == KeyCode.D) {
                        keuze = "d";
                        movePic.setImage(new Image(getClass().getResourceAsStream("images/MovesRight.png")));
                    } else {
                        return;
                    }

                    dc.verplaatsMannetje(keuze);
                    btnUndo.setDisable(false);
                    btnReset.setDisable(false);
                    updateSpelbord();
                    if (!dc.isLaatsteSpelbordVanHuidigSpel() && dc.checkSpelbordInGebruikIsVoltooid()) {//deze methode past het spelbordInGebruik aan als het voltooid is, dus de return waarde moet niet gecontroleerd worden
                        try {
                            Thread.sleep(250);
                        } catch (InterruptedException ex) {
                            Thread.currentThread().interrupt();
                        }
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("Sokémon");
                        alert.setGraphic(new ImageView(new Image(this.getClass().getResourceAsStream("images/complete-icon.png"))));
                        alert.setHeaderText(labels.getString("eenSpelbordVoltooidHeader"));
                        alert.setContentText(labels.getString("eenspelbordVoltooidContent"));
                        alert.showAndWait();
                        updateSpelbord();
                    }

                    if (dc.checkSpelbordInGebruikIsVoltooid() & dc.isLaatsteSpelbordVanHuidigSpel() == true) {
                        hoofdP.setCongratulationsPaneel(new CongratulationsController(dc, hoofdP));
                        hoofdP.setCenter(hoofdP.getCongratulationsPaneel());
                    }
                }
            }

        });

        scene.setOnKeyReleased(new EventHandler<KeyEvent>() {

            @Override
            public void handle(KeyEvent event) {
                movePic.setImage(new Image(getClass().getResourceAsStream("images/Moves.png")));
            }
        });
    }

    @Override
    public void updateLabels() {
        this.lblSpelNaam.setText(dc.geefSpelNaam());
        this.btnLijstSpelNamen.setText(labels.getString("GUIBackConfigSpel"));
        //this.btnSpelMenu.setText(labels.getString("GUIback"));
        this.lblTotaalAantalSpelborden.setText(labels.getString("totaalAantalsb"));
        this.lblHuidigSpelBord.setText(labels.getString("aantalVoltooide"));
        lblHuidigSpelBord.setFont(fontReg);
        lblTotaalAantalSpelborden.setFont(fontReg);

    }

    @FXML
    private void btnUndoOnMouseClicked(MouseEvent event) {

        dc.verplaatsMannetje("u");
        updateSpelbord();
        if (this.dc.IsStackEmpty()) {
            this.btnUndo.setDisable(true);
            this.btnReset.setDisable(true);
        }

    }

    @FXML
    private void btnLijstSpelNamenMouseClicked(MouseEvent event) {
        this.hoofdP.setCenter(this.hoofdP.getSpelnamenPaneel());
    }

//    @FXML
//    private void btnSpelMenu(MouseEvent event) {
//        this.hoofdP.setCenter(this.hoofdP.getSpelmenuPaneel());
//    }

    @FXML
    private void btnResetOnMouseClicked(MouseEvent event) {

        dc.geefSpelbordInGebruikInit();
        updateSpelbord();
        this.btnUndo.setDisable(true);
        this.btnReset.setDisable(true);
    }

    private Image setGepasteImage(char symboolOpSpelbord) {//gebruiken voor updaten en creatie.
        Image img = null;

        switch (symboolOpSpelbord) {
            case '@':
                img = new Image(this.getClass().getResourceAsStream("images/MannetjePickachuSand.png"));
                break;
            case 'K':
                img = new Image(this.getClass().getResourceAsStream("images/Kist3.png"));
                break;
            case '#':
                img = new Image(this.getClass().getResourceAsStream("images/Wall-red-tree.png"));
                break;
            case 'o':
                img = new Image(this.getClass().getResourceAsStream("images/doel.png"));
                break;
            case '*':
                img = new Image(this.getClass().getResourceAsStream("images/KistOpDoel.png"));
                break;
            case '.':
                img = new Image(this.getClass().getResourceAsStream("images/Sand-empty-veld.png"));
                break;
            case '+':
                img = new Image(this.getClass().getResourceAsStream("images/Grass-Background.png"));
                break;
        }

        return img;
    }

    private void updateSpelbord()//gebruik deze methode bij eventafhandling
    {
        this.txfAantalSpelbordenTotaal.setText("" + dc.geefTotaalAantalSpelborden());
        this.txfAantalVoltooid.setText("" + dc.geefAantalVoltooideSpelborden());
        this.txfAantalStappen.setText("" + dc.geefAantalVerplaatsingen());
        this.gridpaneSpelbord.getChildren().clear();
        char[][] sb = dc.geefSpelbordInGebruik();

        for (int i = 0; i < 10; i++) {//Deze methode is eigenlijk niet performant. Beter met enhanced for kijken welke images veranderd zijn en die aanpassen.
            for (int j = 0; j < 10; j++) {
                ImageView imgView = new ImageView();
                Image img = null;
                img = setGepasteImage(sb[i][j]);
                imgView.setImage(img);
                //imgView.resize((this.hoofdP.getCenter().computeAreaInScreen()/this.hoofdP.getWidth()/10)-0.5,(this.hoofdP.getCenter().computeAreaInScreen()/this.hoofdP.getWidth()/10)-0.5);
                imgView.setFitWidth(imgSeize);
                imgView.setFitHeight(imgSeize);
//              GridPane.setConstraints(imgView, j, i);
                this.imgViewSpelbord[i][j] = imgView;
                this.gridpaneSpelbord.add(imgView, j, i);

            }

        }
    }
}
