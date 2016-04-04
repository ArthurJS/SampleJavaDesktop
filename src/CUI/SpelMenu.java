/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CUI;

import static LanguageResources.Resource.labels;
import exceptions.KeuzeMenuOutOfRange;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 *
 * @author Thibault Fouquaert
 */
public class SpelMenu {

    private static Scanner s = new Scanner(System.in);
    private final SokobanApplicatie app;

    public SpelMenu(SokobanApplicatie app) {
        this.app = app;
    }
    
    
    public void toonSpelMenu() {
        boolean blijvenHerhalenFlag = true;
        do {
            try {

                System.out.printf("%n" + labels.getString("welkom") + " %s%n%n", app.dc.geefGebruikersnaam());
                System.out.printf(labels.getString("keuzeMenu") + "%n", 1, 2, 3);
                System.out.print(labels.getString("keuze"));
                int keuze = s.nextInt();

                if (!(keuze <= 3 && keuze > 0)) {
                    throw new KeuzeMenuOutOfRange(labels.getString("getalNietInLijst") + " \n");
                }

                if (keuze == 1) {
                    toonSpelNamen();
                } else if (keuze == 2) {
                    app.Ms.ConfigureerSpel();
                } else if (keuze == 3) {
                    app.Ws.spelWijzigen();
                }

                blijvenHerhalenFlag = false;
            } catch (InputMismatchException e) {
                System.out.println(labels.getString("foutieveInputKeuzemenu"));
                s.nextLine();

            } catch (KeuzeMenuOutOfRange e) {
                System.out.println(e.getMessage());
                s.nextLine();

            }
        } while (blijvenHerhalenFlag);
    }

    public void toonSpelNamen() {
        boolean flag=true;

        do{
            int i = 1;
        try {
            System.out.printf(labels.getString("beschikbareSpellen") + "%n%n");
            for (String naam : app.dc.geefNaamSpellen()) {
                System.out.printf("%-10d%s%n", i++, naam);
            }
            toonKeuzeSpellen();
            flag=false;
        } catch (NullPointerException e) {
            System.out.printf(labels.getString("foutieveKeuzeSpel")+ "%n");
            
        } catch (Exception e) {
            
            System.out.printf(labels.getString("mislukt") + "%n");   
        }
        }while(flag);
        
    }

    public void toonKeuzeSpellen() {
        Scanner invoer = new Scanner(System.in);
        System.out.println(labels.getString("spelbordSelecteren"));//Spel selecteren.
        String keuze = invoer.nextLine();
        app.dc.selecteerSpel(keuze);
        app.Is.toonSpel();

    }

    public void toonSpelbord() {
        String uit = "";
        for (char[] rijen : app.dc.geefSpelbordInGebruik()) {
            for (char kolommen : rijen) {
                uit += kolommen+" ";
            }
            uit += "\n";
        }
        System.out.println(uit);
        
    }
}
