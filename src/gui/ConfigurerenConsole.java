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
 * @author Toon
 */
public class ConfigurerenConsole
{
    private static String spel;
    private static String spelbord;
    /**
     * Hier krijgt de gebruiker de keuze om het spel te configureren of om het te spelen
     *
     * @param dc
     */
    public static void configureer(DomeinController dc)
    {
        /**
         * nodige variabele aanmaken
         */
        Scanner scan = new Scanner(System.in);
        String spel = "";
        String spelbord = "";
        int keuze = 0;
        boolean nieuwBord = false;
        boolean flag = false;
        
        do
        {
            keuze = bewerkMenu();
            if (keuze != 1 && keuze != 2)
            {
                System.out.println(ResourceHandling.getInstance().getString("Exception.foutNummer"));
            }
        
        /**
         * Configureren van het spel
         */
        spel = geefNaamSpel(dc);
        dc.ConfigureerSpel(spel);

        }
        while (keuze != 1 && keuze != 2);
        maakSpelbord(dc);
    }

   
    /**
     * Printen van het spelbord
     * @param spelbord 
     */
    private static void printSpelbord(String[][] spelbord)
    {
        System.out.println();
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
     * Aanmaken van een ItemsMenu
     * @param dc 
     */
    private static void itemsMenu(DomeinController dc)
    {
        System.out.println("\n"+ ResourceHandling.getInstance().getString("Invoer.item"));
        System.out.println("1.#  2.@  3._  4.. 5.$ 6.save");
    }

    /**
     * String dat binngen gekregen omgezet naar id
     * @param item
     * @return 
     */
    private static int itemNaarId(String item)
    {
        int id = 0;

        switch (item)
        {
            case "@":
                id = 0;
                break;
            case "_":
                id = 1;
                break;
            case ".":
                id = 2;
                break;
            case "#":
                id = 3;
                break;
            case "$":
                id = 4;
                break;
            case "save":
                id = 5;
                break;
            default:
                id = 6;
                break;
        }

        return id;
    }

    /**
     * UC6
     * @return 
     */
    private static int bewerkMenu()
    {
        Scanner input = new Scanner(System.in);
        int keuze = 0;
        boolean flag = true;
        do
        {
            System.out.println(ResourceHandling.getInstance().getString("Spel.keuze"));
            try
            {
                keuze = Integer.parseInt(input.next());
                flag = false;
            }
            catch(Exception e)
            {
                System.out.println(ResourceHandling.getInstance().getString("Invoer.fout"));
            }
        }
        while(flag);
        return keuze;
    }

    /**
     * UC6
     * @return 
     */
    private static void wijzigSpel(DomeinController dc)
    {
        /**
         * Uce case 6
         */
        Scanner input = new Scanner(System.in);
        String[] lijstSpellen = dc.geefLijstSpelen();

        int index = 1;

        for (String spel : lijstSpellen)
        {
            System.out.printf("%d. %s%n", index, spel);
            index++;
        }

        System.out.println("\nWelk spel wilt u wijzigen?");
        String spelNaam = input.nextLine();

        WijzigMenu();
        int keuze = input.nextInt();

        if (keuze == 1)
        {
            System.out.println("Geef nieuwe naam op voor " + spelNaam + "!");
            String nieuweNaam = input.next();

            dc.ConfigureerSpel(nieuweNaam);
            dc.wijzigSpelNaam();

            wijzigSpel(dc);
        } else if (keuze == 2)
        {
            String[] lijstSpelborden = dc.geefLijstSpelborden(spelNaam);
            index = 1;

            for (String spelborden : lijstSpelborden)
            {
                System.out.printf("%d. %s%n", index, spelborden);
                index++;
            }

            System.out.println("Welk Spelbord wilt u wijzigen?");
            String spelbord = input.next();

            dc.ConfigureerSpel(spelNaam);
            dc.ConfigureerSpelbord(spelbord);

        } else if (keuze == 3)
        {
            dc.ConfigureerSpel(spelNaam);
            dc.ConfigureerSpelbord(geefSpelbordNaam(dc));

        }

    }

    /**
     * UC6
     * @return 
     */
    private static void WijzigMenu()
    {
        System.out.println("\n1.Naam wijzigen?\n2.Spelbord wijzigen?\n3.Nieuw Spelbord?\nGeef nummer in!\n");
    }

    /**
     * Opvragen van het het naam van het spel
     * @param dc
     * @return 
     */
    private static String geefNaamSpel(DomeinController dc)
    {
        Scanner scan = new Scanner(System.in);
        /**
         * Bestaat het spel al ?
         */
        boolean flag = false;
        do
        {
            System.out.println(ResourceHandling.getInstance().getString("Invoer.creatie.spelNaam"));
            spel = String.valueOf(scan.nextLine());
            try{
                dc.bestaatSpel(spel);
            }
            catch(Exception e)
            {
                System.out.println(e.getMessage());
            }
            if (dc.bestaatSpel(spel))
            {
                /**
                 * ja
                 */
                System.out.println(ResourceHandling.getInstance().getString("Spel.bestaatAl"));

            } else if (spel.isEmpty())
            {
                /**
                 * ingegeven String is leeg
                 */
                System.out.println(ResourceHandling.getInstance().getString("Invoer.leeg"));
            } else
            /**
             * ja
             */
            {
                flag = true;
            }
        } while (!flag);

        return spel;
    }

    /**
     * Op vragen van de naam van het spelbord
     * @param dc
     * @return 
     */
    private static String geefSpelbordNaam(DomeinController dc)
    {
        Scanner scan = new Scanner(System.in);
        boolean flag = false;
        do
        {
            /**
             * Naam opvragen
             */
            System.out.println(ResourceHandling.getInstance().getString("Invoer.creatie.spelbordNaam"));
            spelbord = String.valueOf(scan.nextLine());

            if (spelbord.isEmpty())
            {
                /**
                 * Spelbord naam is leeg
                 */
                System.out.println(ResourceHandling.getInstance().getString("Invoer.leeg"));
            } else if (dc.bestaatSpelbord(spelbord))
            {
                /**
                 * Spelbord naam bestaat al
                 */
                System.out.println(ResourceHandling.getInstance().getString("Spelbord.bestaatAl"));
            } else
            {
                flag = true;
            }
        } while (!flag);

        dc.ConfigureerSpelbord(spelbord);
        return spelbord;
    }
    private static void maakSpelbord(DomeinController dc)
    {
        /**
         * Spelbord maken
         */
        Scanner scan = new Scanner(System.in);
        boolean flag = false;
        boolean magSave = false;
        boolean nieuwBord = false;
        int keuze = 0;
 
        do{
            geefSpelbordNaam(dc);
        /**
         * print Spelbord
         */
        printSpelbord(dc.geefDefaultSpelbord());

        do
        {
            
            int id = 0;
            int kolom = 0;
            int rij = 0;
            String item = "";
            flag = false;
            boolean invoerKolom = true;
            boolean invoerRij = true;
            do
            {
                try
                {
                    /**
                     * Keuze vragen en invullen
                     */
                    do
                    {
                        /**
                         * ItemsMenu tonen
                         */
                        itemsMenu(dc);
                        item = scan.next();
                        id = itemNaarId(item);
                    } 
                    while (id == 6);

                    if (id < 0 || id > 5)
                    {
                        System.out.println(ResourceHandling.getInstance().getString("Exception.foutNummer"));
                    } else
                    {
                        flag = true;
                    }
                } catch (IllegalArgumentException e)
                {
                    System.out.println(ResourceHandling.getInstance().getString("Invoer.juistItem"));
                }
            } while (!flag);

            if (id != 5)
            {
                flag = false;
                
                do
                {
                    /**
                     * Kolom nummer opvragen
                     */
                    do
                    {
                        try{
                            System.out.println(ResourceHandling.getInstance().getString("Invoer.kolom"));
                            kolom = Integer.parseInt(scan.next());
                            invoerKolom = false;
                        }
                        catch(Exception e)
                        {
                            System.out.println(ResourceHandling.getInstance().getString("Invoer.fout"));
                        }
                    }
                    while(invoerKolom);

                    if (kolom >= 0 && kolom < 10)
                    {
                        if (kolom == 0 || kolom == 9)
                        {
                            System.out.println(ResourceHandling.getInstance().getString("OngeldigeLocatie"));
                        } else
                        {
                            flag = true;
                        }
                    } else
                    {
                        System.out.println(ResourceHandling.getInstance().getString("Exception.kolomOnbestaand"));
                    }
                } while (!flag);

                flag = false;
                do
                {
                    /**
                     * Rij opvragen
                     */
                    do
                    {
                        try
                        {
                            System.out.println(ResourceHandling.getInstance().getString("Invoer.rij"));
                            rij = Integer.parseInt(scan.next());
                            invoerRij = false;
                        }
                        catch(Exception e)
                        {
                            System.out.println(ResourceHandling.getInstance().getString("Invoer.fout"));
                        }
                    }
                    while(invoerRij);

                    if (rij >= 0 && rij < 10)
                    {
                        if (rij == 0 || rij == 9)
                        {
                            System.out.println(ResourceHandling.getInstance().getString("OngeldigeLocatie"));
                        } else
                        {
                            flag = true;
                        }
                    } else
                    {
                        System.out.println(ResourceHandling.getInstance().getString("Exception.rijOnbestaand"));
                    }
                } while (!flag);

                /**
                 * Plaatsen van een vak
                 */
                dc.plaatsVak(kolom, rij, id);

                printSpelbord(dc.geefDefaultSpelbord());
            } else
            {  
                System.out.println(dc.gebreken());
                if(dc.bevatAlleOnderdelen())
                    magSave = true;
            }

        } while (!magSave);
        
        try{
            dc.zetSpelInDatabase(spel);
            dc.zetSpelbordInDatabase(spelbord);
        }
        catch(Exception e)
        {
            System.out.println(ResourceHandling.getInstance().getString("Exception.opslaan.map"));
        }
        System.out.println(ResourceHandling.getInstance().getString("Opslaan.success"));
        System.out.println(ResourceHandling.getInstance().getString("Opslaan.next"));
            String antwoord = scan.next().toLowerCase().trim();

            if (antwoord.equals(ResourceHandling.getInstance().getString("Opslaan.prompt")))
            {
                nieuwBord = true;
            } else
            {
                System.exit(-1);
            }
        } 
        while (nieuwBord);
    }
}
