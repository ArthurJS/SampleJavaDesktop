/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package CUI;

import static LanguageResources.Resource.*;
import exceptions.GebruikersNaamException;
import exceptions.WachtwoordException;
import exceptions.WachtwoordHerhaalException;
import java.util.Scanner;

/**
 *
 * @author Thibault Fouquaert
 */
public class RegistratieMenu 
{
    private static Scanner s = new Scanner(System.in);
    private SokobanApplicatie app;

    public RegistratieMenu(SokobanApplicatie app) {
        this.app = app;
    }
    
    
        public void toonRegistratieMenu() {
        boolean flag = true;
        String gebruikersnaam = "";
        String wachtwoord = "";
        do {
            try {
                System.out.println(labels.getString("geefGebruikersnaamIn"));
                gebruikersnaam = s.next();

                System.out.println(labels.getString("geefVoornaamIn"));
                String voornaam = s.next();

                System.out.println(labels.getString("geefFamilienaamIn"));
                String achternaam = s.next();

                System.out.println(labels.getString("geefWachtwoordIn"));
                wachtwoord = s.next();

                System.out.println(labels.getString("geefWachtwoordBevestiging"));
                String wachtwoordBevestiging = s.next();
                if (wachtwoordBevestiging==null || wachtwoordBevestiging.trim().isEmpty()) {//kan een input null zijn??? 
                    throw new WachtwoordHerhaalException(labels.getString("verplichtVeldException") + "\n");
                }
                else if (!wachtwoord.equals(wachtwoordBevestiging)) {
                    throw new WachtwoordHerhaalException(labels.getString("foutieveBevestiging") + "\n");
                }
                app.dc.registreer(voornaam, achternaam, gebruikersnaam, wachtwoord, wachtwoordBevestiging);

                flag = false;
            } catch (GebruikersNaamException e) {
                System.out.println(e.getMessage());
                s.nextLine();
            } catch (WachtwoordHerhaalException | WachtwoordException e) {
                System.out.println(e.getMessage());
            } catch (Exception e) {
                System.out.printf(labels.getString("aanmeldenMislukt")+", " + e.getMessage() + "%n");
               //e.printStackTrace();
            }
        } while (flag);
        app.dc.meldtAan(gebruikersnaam, wachtwoord);
        app.Sm.toonSpelMenu();
    }
}
