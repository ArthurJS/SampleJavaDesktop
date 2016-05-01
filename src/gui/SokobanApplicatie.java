/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import domein.DomeinController;
import java.util.InputMismatchException;
import java.util.Scanner;
import resources.ResourceHandling;

/**
 *
 * @author donovandesmedt
 */
public class SokobanApplicatie
{

    /**
     * Hier wordt er aangemeld en geregistreerd
     */
    public SokobanApplicatie()
    {
        /**
         * Aanmaken domeinmodel
         */
        DomeinController domein = new DomeinController();
        boolean opnieuw = true;
        domein.setIsConsole(true);
        Scanner input = new Scanner(System.in);
        int keuzemogelijkheid = 0;

        /**
         * Taal kiezen
         */
        kiesTaal();

        /**
         * Keuze tussen aanmelden en registreren
         */
        do
        {
            try
            {
                if (keuze(domein))
                {
                    //** Weergeven van gebruikersnaam van de aangemelde speler*/
                    System.out.printf("%n%s %s%n", ResourceHandling.getInstance().getString("Aanmelden.speler"), domein.geefSpeler());

                    if (domein.isSpelerBeheerder())
                    {
                        boolean flag = false;
                        boolean invoer = true;
                        do
                        {
                            /**
                             * Kiezen of je wilt spelen of configureren
                             */
                            do
                            {
                                try
                                {
                                    keuzeMenu();
                                    keuzemogelijkheid = Integer.parseInt(input.next());
                                    invoer = false;
                                } catch (Exception e)
                                {
                                    System.out.println(ResourceHandling.getInstance().getString("keuze.fout"));
                                }
                            } 
                            while (invoer);
                            if (keuzemogelijkheid != 1 && keuzemogelijkheid != 2)
                            {
                                System.out.println(ResourceHandling.getInstance().getString("keuze.fout"));
                            } else
                            {
                                flag = true;
                            }
                        } while (!flag);
                    }

                    /**
                     * Spel Kiezen
                     */
                    if (keuzemogelijkheid == 1 || !domein.isSpelerBeheerder())
                    {
                        KiesSpel.keuzeLevel(domein);
                    } else if (keuzemogelijkheid == 2)
                    {
                        ConfigurerenConsole.configureer(domein);
                    }
                    opnieuw = false;

                } else
                {
                    //throw new Exception();
                }

                //KiesSpel.keuzeLevel(domein);
            } catch (InputMismatchException ime)
            {
                System.out.println(ime.getMessage());
            } catch (Exception e)
            {
                System.out.println(ResourceHandling.getInstance().getString("Exception.keuze.invalid"));
            }
        } while (opnieuw);
    }

    public boolean keuze(DomeinController domein)
    {
        /**
         * keuzemogelijkheid: aanmelden of registreren
         */
        Scanner input = new Scanner(System.in);
        String keuze = "";
        boolean flag = true;
        do
        {

            System.out.print(ResourceHandling.getInstance().getString("Keuze.Prompt"));
            keuze = input.nextLine().trim().toUpperCase();
            if (!keuze.equals(ResourceHandling.getInstance().getString("Keuze.Aanmelden")) && !keuze.equals(ResourceHandling.getInstance().getString("Keuze.Registreren")))
            {
                throw new InputMismatchException(ResourceHandling.getInstance().getString("Exception.keuze"));
            }
        } while (keuze.isEmpty() && !keuze.equals("A") && !keuze.equals("B"));

        /**
         * Aanmelden
         */
        do
        {
            if (keuze.equals(ResourceHandling.getInstance().getString("Keuze.Aanmelden")))
            {
                try
                {
                    MeldAan.meldAan(domein);
                    flag = false;
                } catch (Exception e)
                {
                    System.out.println(ResourceHandling.getInstance().getString("Exception.aanmelden"));
                }
            }

            /**
             * Registreren
             */
            if (keuze.equals(ResourceHandling.getInstance().getString("Keuze.Registreren")))
            {
                try
                {
                    Registreer.registreer(domein);
                    flag = true;
                } catch (Exception e)
                {
                    System.out.println(ResourceHandling.getInstance().getString("Exception.keuze.invalid"));
                }
            }
        } while (flag);

        return true;
    }

    /**
     * Taal Kiezen
     */
    private void kiesTaal()
    {
        Scanner input = new Scanner(System.in);
        String keuze = "";

        do
        {
            /**
             * Taal wordt hier gevraagt
             */
            System.out.print("Kies Taal (NL,EN,FR)!");
            keuze = input.nextLine().toLowerCase();

            switch (keuze)
            {
                /**
                 * Taal wordt hier geset
                 */
                case "nl":
                    ResourceHandling.getInstance().stelInLanguage(keuze, "resources.ResourceBundle_Sokoban_nl");
                    break;
                case "en":
                    ResourceHandling.getInstance().stelInLanguage(keuze, "resources.ResourceBundle_Sokoban_en");
                    break;
                case "fr":
                    ResourceHandling.getInstance().stelInLanguage(keuze, "resources.ResourceBundle_Sokoban_fr");
                    break;
                default:
                    System.out.println("Geen juiste Taal gekozen!");
                    keuze = "";
            }
        } while (keuze.isEmpty());
    }

    /**
     * KeuzeMenuutje oproepen
     */
    private void keuzeMenu()
    {
        System.out.println("\n" + ResourceHandling.getInstance().getString("Keuze.keuzemenu"));
        System.out.printf("1.%s.%n2.%s%n%n", ResourceHandling.getInstance().getString("Speel.knop"), ResourceHandling.getInstance().getString("Keuzescherm.configureer"));
    }
}
