package CUI;

import static LanguageResources.Resource.labels;
import exceptions.KeuzeMenuOutOfRange;
import java.util.InputMismatchException;
import java.util.Scanner;

public class InSpel {

    private static Scanner s = new Scanner(System.in);
    private final SokobanApplicatie app;

    public InSpel(SokobanApplicatie app) {
        this.app = app;
    }

    public void toonSpel() {
        boolean spelbordIsVoltooid;//de methode checkSpelbordIngebruik past het spelbord in gebruik aan als hij true is, dus een 2e maal aanroepen in if zou false weergeven omdat het huidgespelbord ondertussen al is aangepast.
        System.out.printf("%n" + labels.getString("totaalAantalsb") + " %d%n", app.dc.geefTotaalAantalSpelborden());
        System.out.printf(labels.getString("aantalVoltooide") + " %d%n%n", app.dc.geefAantalVoltooideSpelborden());

        while (app.dc.isLaatsteSpelbordVanHuidigSpel() == false | (spelbordIsVoltooid = app.dc.checkSpelbordInGebruikIsVoltooid()) == false) {
            if (spelbordIsVoltooid == true) {
                System.out.printf("%n" + labels.getString("totaalAantalsb") + " %d%n", app.dc.geefTotaalAantalSpelborden());
                System.out.printf(labels.getString("aantalVoltooide") + " %d%n%n", app.dc.geefAantalVoltooideSpelborden());
            }
            app.Sm.toonSpelbord();
            boolean blijvenHerhalenFlag = true;
            String keuze = "";
            do {
                try {
                    System.out.printf(labels.getString("aantalBewegingen") + ": %d%n", app.dc.geefAantalVerplaatsingen());
                    System.out.printf("%n" + labels.getString("omhoog") + "%n");
                    System.out.printf(labels.getString("omlaag") + "%n");
                    System.out.printf(labels.getString("rechts") + "%n");
                    System.out.printf(labels.getString("links") + "%n");

                    keuze = s.next();
                    keuze = keuze.toLowerCase();

                    if (!(keuze.equals("z") || keuze.equals("q") || keuze.equals("s") || keuze.equals("d"))) {
                        throw new KeuzeMenuOutOfRange(labels.getString("letterNietInLijst"));
                    }
                    blijvenHerhalenFlag = false;

                } catch (InputMismatchException e) {
                    System.out.println(labels.getString("foutieveInput"));
                    s.nextLine();

                } catch (KeuzeMenuOutOfRange e) {
                    System.out.println(e.getMessage());
                    s.nextLine();

                }

            } while (blijvenHerhalenFlag);

            app.dc.verplaatsMannetje(keuze);
        }
        app.Sm.toonSpelbord();//allerlaatse opgeloste spelbord nog printen
        System.out.println(labels.getString("congratz") + "\n");
        app.Sm.toonSpelMenu();
    }

}
