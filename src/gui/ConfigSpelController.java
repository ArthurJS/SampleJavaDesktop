
package gui;

import static LanguageResources.Resource.labels;
import domein.DomeinController;
import java.io.IOException;
import java.net.URL;
import java.util.Collections;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.text.Font;


public class ConfigSpelController extends FlowPane implements LanguageReload {
    @FXML
    private AnchorPane AnchorPane;
    @FXML
    private ListView<String> lvwLijstSpelNamen;
    @FXML
    private Label lblOverzicht;
    @FXML
    private ImageView imgOverzicht;
    @FXML
    private Label lblDesc;
    @FXML
    private Label lblAantalSpelborden;
    @FXML
    private Button btnConfigSpel;
    @FXML
    private Button btnBackSpelMenu;

   private DomeinController dc;
    private HoofdPaneelController hoofdP;
    private Font fontReg;
    private Font fontPoke;


    public ConfigSpelController(DomeinController dc, HoofdPaneelController hoofdP) {

        this.dc = dc;
        this.hoofdP = hoofdP;
        this.hoofdP.voegControllerObjectToeAanLijst(this);
        fontReg = Font.loadFont(SpelNamenController.class.getResource("font/brandon_light.OTF").toExternalForm(), 14);
        fontPoke = Font.loadFont(SpelNamenController.class.getResource("font/Pokemon Solid.ttf").toExternalForm(), 10);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("ConfigSpel.fxml"));
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
         btnConfigSpel.setText(labels.getString("GUIconfig"));
         btnBackSpelMenu.setFont(fontPoke);
         btnBackSpelMenu.setText(labels.getString("GUIback"));
         btnConfigSpel.setFont(fontPoke);
    }
    
    private void update(int geselecteerdSpelbord){
        String spelnaam = lvwLijstSpelNamen.getItems().get(geselecteerdSpelbord);
        Image img = null;
        try{
            img = new Image(getClass().getResourceAsStream("images/"+spelnaam+".png"));
        }
        catch(NullPointerException e){
            img = new Image(getClass().getResourceAsStream("images/spelnaamFotoCustom.png"));
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
    private void btnConfigSpelOnMouseClicked(MouseEvent event) {
        this.dc.selecteerSpel(lvwLijstSpelNamen.getSelectionModel().getSelectedItem());
        this.hoofdP.setConfigSpelbordLijstPaneel(new ConfigSpelbordLijstController(this.dc, this.hoofdP));
        hoofdP.setCenter(this.hoofdP.getConfigSpelbordLijstPaneel());

    }
}