/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import domein.DomeinController;
import java.util.Scanner;
import resources.ResourceHandling;

/**
 *
 * @author donovandesmedt
 */
public class KiesSpel
{

    /**
     * Hier kies je welk spel je wilt spelen en krijg je een spelbord voorgeschoteld. 
     * Je kan hier ook de spelborden spelen.
     * @param dc 
     */
    public static void keuzeLevel(DomeinController dc)
    {
        /**
         * Scanner object aanmaken, boolean voor error handling
         */
        Scanner input = new Scanner(System.in);
        boolean opnieuw = true;

        do
        {
            try
            {

                /**
                 * de gebruiker een overzicht van spelen tonen en hem laten kiezen welke hij wil spelen en worden de spellen in geladen
                 */
                /**
                 * Lijst met spellen worden ingeladen en omgezet naar String
                 */
                String[] lijstSpellen = dc.geefLijstSpelen();

                String keuzeSpel;
                boolean flag = false;

                do
                {
                    System.out.println("\n" + ResourceHandling.getInstance().getString("SpelKeuze.overzicht") + "\n");

                    int index = 1;

                    for (String spel : lijstSpellen)
                    {
                        System.out.printf("%d. %s%n", index, spel);
                        index++;
                    }

                    /**
                     * Keuze maken van welk spel
                     */
                    System.out.printf("%n%s%n", ResourceHandling.getInstance().getString("SpelKeuze.keuze"));
                    keuzeSpel = input.nextLine().trim();

                    /**
                     * Is de keuze die we maken wel goed ?
                     */
                    for (String spel : lijstSpellen)
                    {
                        if (spel.equals(keuzeSpel))
                        {
                            /**
                             * Ja de keuze die we maken is goed.
                             */
                            flag = true;
                            break;
                        }
                    }

                    if (!flag)
                    {
                        /**
                         * Nee, de keuze die we maken is niet goed
                         */
                        System.out.println("\n" + ResourceHandling.getInstance().getString("Keuze.KiesSpel"));
                    }

                } while (!flag);

                /**
                 * Spel Kiezen
                 */
                dc.kiesSpel(keuzeSpel, false);
                opnieuw = false;

                /**
                 * de gebruiker een overzicht van spelborden tonen en hem laten kiezen welke hij wil spelen
                 */
                System.out.println("\n" + ResourceHandling.getInstance().getString("Spelbord.overzicht") + "\n");

                int index = 1;
                for (String spelbord : dc.geefLijstSpelborden(keuzeSpel))
                {
                    /**
                     * Tonen van spelborden
                     */
                    System.out.printf("%d. %s%n", index, spelbord);
                    index++;
                }

                /**
                 * Deze methode zal op basis van een txt file een spelbord maken en de verschillende vakken in de db plaatsen.
                 */
                // dc.maakSpelbord();
                boolean nieuwSpelbord = false;
                do
                {

                    /**
                     * Tekenen van het bord.
                     */
                    System.out.println("");
                    printSpelbord(dc.geefSpelbord());
                    System.out.println("");
                    do
                    {
                        /**
                         * String om de richting die in gegeven is op te vangen
                         */
                        String richting;

                        do
                        {

                            /**
                             * Vragen welke richting je het ventje wilt laten bewegen
                             */
                            System.out.print(ResourceHandling.getInstance().getString("Keuze.Richting"));
                            richting = input.nextLine().trim().toLowerCase();
                            richting = omzettenNaarRichting(richting);

                            /**
                             * Als de richting leeg is (niet klopt) herbegint het
                             */
                            if (richting.isEmpty())
                            {
                                System.out.println(ResourceHandling.getInstance().getString("Richting.foutmelding"));
                            }
                        } while (richting.isEmpty());

                        /**
                         * mannetje beweegt!
                         */
                        dc.verplaatsHero(richting);

                        /**
                         * verplaatsingen
                         */
                        System.out.printf("%n%s%d%n", ResourceHandling.getInstance().getString("Richting.verplaatsing"), dc.geefAantalVerplaatsingen());

                        /**
                         * Hertekenen van spelbord
                         */
                        System.out.println("");
                        printSpelbord(dc.geefSpelbord());
                        System.out.println("");

                    } while (!dc.isSpelbordVoltooid());

                    /**
                     * Voltooid
                     */
                    System.out.printf("Je hebt het spelbord voltooid!%n%n");

                    /**
                     * toon de voltooide Spelborden
                     */
                    System.out.printf("%s%n", dc.toonAantal());

                    /**
                     * opzoek naar volgend spelbord
                     */
                    dc.zoekNietVoltooidSpelbord();

                    if (!dc.isEindeSpel())
                    {
                        System.out.printf("%s%n", ResourceHandling.getInstance().getString("speelverder.spelbord"));                       
                        nieuwSpelbord = dc.speelVerder(input.nextLine().trim().toLowerCase());
                    }
                    else
                    {
                        System.out.printf("%s%n", ResourceHandling.getInstance().getString("speelverder.eindespel"));
                    }

                } while (!dc.isEindeSpel() && nieuwSpelbord);

                /**
                 * de gebruiker de keuze laten verder te spelen of niet.
                 */
                System.out.printf("%s%n", ResourceHandling.getInstance().getString("Speelverder"));
                if (dc.speelVerder(input.nextLine().trim().toLowerCase()))
                {
                    KiesSpel.keuzeLevel(dc);
                }

                /**
                 * nagaan of het spel al dan niet beëindigd is (later automatisch)
                 */
                System.out.printf("%s%n", ResourceHandling.getInstance().getString("Speelverder.isEindeSpel"));

            } catch (Exception e)
            {
                System.out.println(e.getMessage());
            }

        } while (opnieuw);
    }

    /**
     * Tekenen van spelborden
     * @param spelbord 
     */
    private static void printSpelbord(String[][] spelbord)
    {
        for (String[] spelbord1 : spelbord)
        {
            for (String spelbord11 : spelbord1)
            {
                System.out.printf("%s", spelbord11);
            }
            System.out.println("");
        }
    }

    /**
     * Omzetten van de richtingen
     * @param richting
     * @return 
     */
    private static String omzettenNaarRichting(String richting)
    {
        /**
         * Omzetten naar gecode Strings die belangrijk zijn;
         */
        String vertaal = "";

        switch (richting)
        {
            case "rechts":
            case "droite":
            case "right":
                vertaal = "rechts";
                break;
            case "links":
            case "gauche":
            case "left":
                vertaal = "links";
                break;
            case "onder":
            case "dessous":
            case "down":
                vertaal = "onder";
                break;
            case "boven":
            case "dessus":
            case "up":
                vertaal = "boven";
                break;
            default:
                vertaal = "";
        }
        return vertaal;
    }
}
