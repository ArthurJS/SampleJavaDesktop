
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import static LanguageResources.Resource.labels;
import domein.DomeinController;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import javafx.animation.FadeTransition;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.util.Duration;


public class SpelNamenController extends FlowPane implements LanguageReload{
    
   @FXML
    private ListView<String> lvwLijstSpelNamen;
    @FXML
    private Label lblOverzicht;
    @FXML
    private ImageView imgOverzicht;
    @FXML
    private GridPane gridPrev;
    @FXML
    private Button btnPlaySpelMenu;
    @FXML
    private Button btnBackSpelMenu;
    @FXML
    private  Label lblAantalSpelborden;
    
    private DomeinController dc;
    private HoofdPaneelController hoofdP;
    private Font fontReg;
    private Font fontPoke;


    public SpelNamenController(DomeinController dc, HoofdPaneelController hoofdP) {

        this.dc = dc;
        this.hoofdP = hoofdP;
        this.hoofdP.voegControllerObjectToeAanLijst(this);
        fontReg = Font.loadFont(SpelNamenController.class.getResource("font/brandon_light.OTF").toExternalForm(), 14);
        fontPoke = Font.loadFont(SpelNamenController.class.getResource("font/Pokemon Solid.ttf").toExternalForm(), 10);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("SpelNamen.fxml"));
        loader.setRoot(this);
        loader.setController(this);
        try {
            loader.load();
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
        buildGUI();
        this.lvwLijstSpelNamen.getSelectionModel().select(0);
        this.lvwLijstSpelNamen.getFocusModel().focus(0);
        update(0);
    }

    private void buildGUI()
    {
        List<String> lijstSpelNamen = dc.geefNaamSpellen();
        Collections.sort(lijstSpelNamen);
        lvwLijstSpelNamen.setItems(FXCollections.observableArrayList(lijstSpelNamen));
        updateLabels();
        
    }
    
    @Override
    public void updateLabels(){
        lblOverzicht.setText("Details");
        lblOverzicht.setFont(fontReg);
        lblAantalSpelborden.setFont(fontReg);
         btnPlaySpelMenu.setText(labels.getString("GUIspeel"));
         btnBackSpelMenu.setFont(fontPoke);
         btnBackSpelMenu.setText(labels.getString("GUIback"));
         btnPlaySpelMenu.setFont(fontPoke);
    }
    
    private void update(int geselecteerdSpelbord){
        String spelnaam = lvwLijstSpelNamen.getItems().get(geselecteerdSpelbord);
        Image img = null;
        try{
            img = new Image(getClass().getResourceAsStream("images/"+spelnaam+".png"));
        }
        catch(NullPointerException e){
            img = new Image(getClass().getResourceAsStream("images/spelnaamFotoCustom.png"));//"+((int) Math.floor(Math.random() * 5))+"
        }
        imgOverzicht.setImage(img);
        
        lblAantalSpelborden.setText(labels.getString("aantalSpelborden")+dc.geefTotaalAantalSpelbordenVanInGuiGekozenSpel(spelnaam));
    }    
    
    @FXML
    private void lvwSpelNamenOnMouseClicked(MouseEvent event) {
        int geselecteerdSpelbord = lvwLijstSpelNamen.getSelectionModel().getSelectedIndex();
        if(geselecteerdSpelbord != -1)
            update(geselecteerdSpelbord);
    }
    
     @FXML
    private void btnBackSpelMenuOnMouseClicked(MouseEvent event) {
        hoofdP.setCenter(this.hoofdP.getSpelmenuPaneel());

    }
    
     @FXML
    private void btnPlaySpelMenuOnMouseClicked(MouseEvent event) {
        this.dc.selecteerSpel(lvwLijstSpelNamen.getSelectionModel().getSelectedItem());
        this.hoofdP.setSpelbordPaneel(new SpelbordController(this.dc, this.hoofdP));
        hoofdP.setCenter(this.hoofdP.getSpelbordPaneel());

    }
}
    
