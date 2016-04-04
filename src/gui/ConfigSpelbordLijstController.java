/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import static LanguageResources.Resource.labels;
import domein.DomeinController;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

/**
 * FXML Controller class
 *
 * @author Mario
 */
public class ConfigSpelbordLijstController extends FlowPane implements LanguageReload  {
    @FXML
    private ListView<String> lvwLijstSpelborden;
    @FXML
    private Label lblPreview;
    @FXML
    private GridPane gridpaneSpelbordPreview;
    @FXML
    private Button btnConfigSpelbord;
    @FXML
    private Button btnBackConfigSpel;
    @FXML
    private ImageView ivVerwijderSpelbord;
    
    private DomeinController dc;
    private HoofdPaneelController hoofdP;
    private ImageView imgViewSpelbord[][] = new ImageView[10][10]; //Deze 2d array gebruiken om nieuwe imgviews te zetten in de rijen/kols vd gridpane
    // moet aangepast worden bij event
    private double imgSeize;// =  ((this.hoofdP.getWidth()/10.0)-2);
    private Font fontReg;
    private Font fontPoke;
    private int geselecteerdSpelbordIndex;
    
    public ConfigSpelbordLijstController(DomeinController dc, HoofdPaneelController hpc) {
        this.dc = dc;
        this.hoofdP = hpc;
        
        this.hoofdP.voegControllerObjectToeAanLijst(this);
        fontReg = Font.loadFont(SpelbordController.class.getResource("font/brandon_light.OTF").toExternalForm(), 14);
        fontPoke = Font.loadFont(SpelbordController.class.getResource("font/Pokemon Solid.ttf").toExternalForm(), 10);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("ConfigSpelbordLijst.fxml"));//Pad meegeven naar image etc.....
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
        imgSeize = ((this.hoofdP.getCenter().computeAreaInScreen() / this.hoofdP.getWidth() / 10.0) - 25.5);
       
        
        
        
        updateLabels();
        updateSpelbord(0);
        btnBackConfigSpel.setFont(fontPoke);
        btnConfigSpelbord.setFont(fontPoke);
        lblPreview.setFont(fontReg);
        ivVerwijderSpelbord.setImage(new Image(this.getClass().getResourceAsStream("images/delete-icon.png")));
        
        
    }
    
    @Override
    public void updateLabels(){
        
        List<String> lijstSpelbordNamen = new ArrayList<>();
        
        for(int i = 0;i< dc.geefTotaalAantalSpelbordenVanInGuiGekozenSpel(dc.geefSpelNaam());i++)
               lijstSpelbordNamen.add(String.format("%s %d",labels.getString("GUIspelbord"), i+1));
        lvwLijstSpelborden.setItems(FXCollections.observableArrayList(lijstSpelbordNamen));
        this.lblPreview.setText(labels.getString("GUIPreview"));
        
        btnBackConfigSpel.setText(labels.getString("GUIBackConfigSpel"));
        
        btnConfigSpelbord.setText(labels.getString("GUIWijzigSpelbord"));
       
        
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
    
     private void updateSpelbord(int index)
    {
        
        this.gridpaneSpelbordPreview.getChildren().clear();
        ArrayList<char[][]> spelborden = null;
        char[][] geselecteerdSpelbord;
        spelborden= dc.geefLijstSpelbordenGeselecteerdSpel();
            geselecteerdSpelbord = spelborden.get(index);
            
        
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                ImageView imgView = new ImageView();
                Image img = null;
                img = setGepasteImage(geselecteerdSpelbord[i][j]);
                imgView.setImage(img);
                //imgView.resize((this.hoofdP.getCenter().computeAreaInScreen()/this.hoofdP.getWidth()/10)-0.5,(this.hoofdP.getCenter().computeAreaInScreen()/this.hoofdP.getWidth()/10)-0.5);
                imgView.setFitWidth(imgSeize);
                imgView.setFitHeight(imgSeize);
//              GridPane.setConstraints(imgView, j, i);
                this.imgViewSpelbord[i][j] = imgView;
                this.gridpaneSpelbordPreview.add(imgView, j, i);

            }

        }
    }

     

    @FXML
    private void lvwSpelNamenOnMouseClicked(MouseEvent event) {
        
       this.geselecteerdSpelbordIndex = lvwLijstSpelborden.getSelectionModel().getSelectedIndex();
       this.dc.setSpelSpelbordInGebruik(geselecteerdSpelbordIndex+1);//nodig om te verwijderen.
            updateSpelbord(geselecteerdSpelbordIndex);
            
    }

    @FXML
    private void btnConfigSpelbordOnMouseClicked(MouseEvent event) {
        this.dc.setSpelSpelbordInGebruik(geselecteerdSpelbordIndex+1);
        this.hoofdP.setConfigSpelbordPaneel(new ConfigSpelbordController(this.dc, this.hoofdP));
        hoofdP.setCenter(this.hoofdP.getConfigSpelbordPaneel());
    }

    @FXML
    private void btnBackConfigSpelOnMouseClicked(MouseEvent event) {
        hoofdP.setCenter(this.hoofdP.getConfigspelPaneel());
    }
    
    @FXML
    private void ivVerwijderSpelbordOnMouseClicked(){
        
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Sokémon");
            alert.setHeaderText(labels.getString("GUIHeaderTextVerwijderSpelbord"));
            alert.setContentText(labels.getString("GUIContentTextVerwijderSpelbord"));


            ButtonType buttonTypeOne = new ButtonType(labels.getString("JA"));
            ButtonType buttonTypeTwo = new ButtonType(labels.getString("NEE"));

            alert.getButtonTypes().setAll(buttonTypeOne, buttonTypeTwo);

            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == buttonTypeOne) {
                dc.verwijderSpelbord();
//                dc.setSpelSpelbordInGebruik(0);
                if(dc.geefTotaalAantalSpelbordenVanInGuiGekozenSpel(dc.geefSpelNaam())== 0){
                        this.hoofdP.setConfigspelPaneel(new ConfigSpelController(this.dc, this.hoofdP));
                        hoofdP.setCenter(this.hoofdP.getConfigspelPaneel());
                }
                else
                buildGUI();
                
    }
    
}
}
