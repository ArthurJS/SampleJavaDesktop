/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CUI;

import static LanguageResources.Resource.labels;
import exceptions.GebruikersNaamException;
import exceptions.WachtwoordException;
import exceptions.WachtwoordHerhaalException;
import java.util.Scanner;

/**
 *
 * @author Thibault Fouquaert
 */
public class AanmeldMenu {

    private static Scanner s = new Scanner(System.in);
    private final SokobanApplicatie app;

    public AanmeldMenu(SokobanApplicatie app) {
        this.app = app;
    }

    public void toonAanmeldMenu() {
        boolean flag = true;
        do {
            try {
                System.out.println(labels.getString("gebruikersnaam"));
                String gebruikersnaam = s.next();
                System.out.println(labels.getString("passwoord"));
                String wachtwoord = s.next();
                app.dc.meldtAan(gebruikersnaam, wachtwoord);
                flag = false;
            } catch (GebruikersNaamException | WachtwoordException | WachtwoordHerhaalException e) {
                System.out.println(e.getMessage());//labels.getString("geenGebruikersnaam")
            } catch (Exception e) {
                System.out.printf(labels.getString("aanmeldenMislukt") + ", " + e.getMessage() + "%n");
                //e.printStackTrace();
            }
        } while (flag);
        app.Sm.toonSpelMenu();
    }

}
