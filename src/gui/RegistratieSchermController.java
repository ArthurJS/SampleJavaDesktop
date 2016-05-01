/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import domein.DomeinController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import resources.ResourceHandling;

/**
 * FXML Controller class
 *
 * @author donovandesmedt
 */
public class RegistratieSchermController extends Pane
{

    @FXML
    private Label lblNaam;
    @FXML
    private Label lblGebruikersnaam;
    @FXML
    private Label lblAchternaam;
    @FXML
    private Label lblWachtwoord;
    @FXML
    private Label lblWachtwoord2;
    @FXML
    private TextField txfNaam;
    @FXML
    private TextField txfAchternaam;
    @FXML
    private TextField txfGebruikersnaam;
    @FXML
    private Button btnRegistreer;
    @FXML
    private Button btnCancel;
    @FXML
    private PasswordField pwfWachtwoord;
    private DomeinController dc;
    @FXML
    private PasswordField pwfWachtwoord2;
    @FXML
    private Label lblFout;

    /**
     * Initializes the controller class.
     */
    public RegistratieSchermController(DomeinController dc)
    {
        this.dc = dc;
        
        LoaderSchermen.getInstance().setLocation("RegistratieScherm.fxml", this);
     
        lblGebruikersnaam.setText(ResourceHandling.getInstance().getString("Registreer.Gebruikersnaam"));
        lblWachtwoord.setText(ResourceHandling.getInstance().getString("Registreer.Wachtwoord"));
        lblNaam.setText(ResourceHandling.getInstance().getString("Registreer.Voornaam"));
        lblAchternaam.setText(ResourceHandling.getInstance().getString("Registreer.Achternaam"));
        lblWachtwoord2.setText(ResourceHandling.getInstance().getString("Registreer.HerhaalWachtwoord"));
        btnRegistreer.setText(ResourceHandling.getInstance().getString("Registreer.knop"));
        btnCancel.setText(ResourceHandling.getInstance().getString("Annuleer.knop"));
    }

    @FXML
    private void btnRegistreerOnAction(ActionEvent event)
    {
        try
        {
            String voornaam = txfNaam.getText();
            String achternaam = txfAchternaam.getText();
            String gebruikersnaam = vraagInput(txfGebruikersnaam.getText(), 8, true, false);
            String wachtwoord = vraagInput(pwfWachtwoord.getText(), 8, true, true);
            String wachtwoord2 = pwfWachtwoord2.getText();
            if (!wachtwoord.equals(wachtwoord2))
            {
                throw new IllegalArgumentException(ResourceHandling.getInstance().getString("Exception.registreer.herhaalWachtwoord"));
            }
            dc.registreer(gebruikersnaam, voornaam, achternaam, wachtwoord);
            Alert boodschap = new Alert(Alert.AlertType.ERROR);
            boodschap.setTitle(ResourceHandling.getInstance().getString("Registratie.succes"));
            boodschap.setHeaderText(dc.geefSpeler() + " " + ResourceHandling.getInstance().getString("Registratie.succes2"));
            boodschap.showAndWait();

            LoaderSchermen.getInstance().load(ResourceHandling.getInstance().getString("Keuzescherm.titel"), new KiesConfigureerSchermController(dc), 300, 300, this);
        } catch (Exception e)
        {
            leegVeld(txfGebruikersnaam);
            leegVeld(pwfWachtwoord);
            leegVeld(pwfWachtwoord2);
            lblFout.setText(e.getMessage());
        }
    }

    /**
     * Gaat kijken of het textfieldje leeg is of niet
     * @param txf 
     */
    private void leegVeld(TextField txf)
    {
        if (txf.getText().isEmpty())
        {
            txf.setId("Fout");
        } else
        {
            txf.setId("");
        }
    }

    @FXML
    private void btnCancelOnAction(ActionEvent event)
    {
        LoaderSchermen.getInstance().load("Sokoban",  new WelkomSchermController(), 382, 407, this);
    }

    /**
     * Gaat lijken of de input dat ingegeven correct is
     * @param meegegevenElement
     * @param minLength
     * @param cijfers
     * @param isWachtwoord
     * @return 
     */
    private static String vraagInput(String meegegevenElement, int minLength, boolean cijfers, boolean isWachtwoord)
    {

        /**
         * één input die dan adhv booleans op verschillende manieren gecheckt wordt
         */
        String input;

        char kar;
        boolean goedeInput = false;

        /**
         * filter voor wachtwoord check
         */
        if (isWachtwoord == true)
        {
            boolean lower = false, upper = false, cijfer = false;
            do
            {
                input = meegegevenElement;
                for (int i = 0; i < input.length(); i++)
                {
                    kar = input.charAt(i);
                    if (Character.isUpperCase(kar))
                    {
                        upper = true;
                    } else if (Character.isLowerCase(kar))
                    {
                        lower = true;
                    } else if (Character.isDigit(kar))
                    {
                        cijfer = true;
                    }
                }
                goedeInput = (lower == true && upper == true && cijfer == true);
                if (goedeInput == false || input.length() < minLength)
                {
                    throw new IllegalArgumentException(ResourceHandling.getInstance().getString("Exception.registreer.wachtwoord"));
                }
            } while (input.isEmpty() || goedeInput == false);
            return input;
        } 
        
        /**
         * filter op letters en/of cijfers
         */
        else if (isWachtwoord == false && cijfers == true)
        {
            boolean letter = false, cijfer = false;
            do
            {
                input = meegegevenElement;
                for (int i = 0; i < input.length(); i++)
                {
                    kar = input.charAt(i);
                    if (Character.isAlphabetic(kar))
                    {
                        letter = true;
                    } else if (Character.isDigit(kar))
                    {
                        cijfer = true;
                    }
                }
                goedeInput = (letter == true || cijfer == true);
                if (goedeInput == false || input.length() < minLength)
                {
                    throw new IllegalArgumentException(ResourceHandling.getInstance().getString("Exception.registreer.gebruikersnaam"));
                }
            } while (input.isEmpty() || goedeInput == false);
            return input;
        } /**
         * filter op enkel letters
         */
        else
        {
            boolean letter = false;
            do
            {
                input = meegegevenElement;
                for (int i = 0; i < input.length(); i++)
                {
                    kar = input.toLowerCase().charAt(i);
                    if (Character.isWhitespace(kar) || kar == '-')
                    {
                        goedeInput = true;
                    } else if (kar < 'a' || kar > 'z')
                    {

                        goedeInput = false;
                        throw new IllegalArgumentException(ResourceHandling.getInstance().getString("Exception.registreer.naam"));

                    } else
                    {
                        goedeInput = true;
                        letter = true;
                    }
                }
                if (input.length() < minLength)
                {
                    throw new IllegalArgumentException(ResourceHandling.getInstance().getString("Exception.registreer.cijfer"));
                }
                if (!letter)
                {
                    throw new IllegalArgumentException(ResourceHandling.getInstance().getString("Exception.registreer.naam"));
                }
            } while (input.isEmpty() || goedeInput == false);
            return input;
        }
    }

}
