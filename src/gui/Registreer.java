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
 * @author User
 */
public class Registreer
{

    /**
     * Registreren van speler
     * @param dc 
     */
    public static void registreer(DomeinController dc)
    {

        /**
         * aanmaken gebruikersnaam, voornaam, achternaam, wachtwoord, inlezen door middel van vraagInput methode, doorgeven aan dc.registreer
         */
        String gebruikersnaam = "", voornaam = "", achternaam = "", wachtwoord = "";
        boolean opnieuw;

        do
        {
            try
            {
                voornaam = vraagInput( ResourceHandling.getInstance().getString("Registreer.Voornaam"), 2, false, false);
                opnieuw = false;
            } catch (Exception e)
            {
                System.out.println(e.getMessage());
                opnieuw = true;
            }
        } while (opnieuw);

        do
        {
            try
            {
                achternaam = vraagInput( ResourceHandling.getInstance().getString("Registreer.Achternaam"), 3, false, false);
                opnieuw = false;
            } catch (Exception e)
            {
                System.out.println(e.getMessage());
                opnieuw = true;
            }
        } while (opnieuw);

        do
        {
            try
            {
                gebruikersnaam = vraagInput( ResourceHandling.getInstance().getString("Registreer.Gebruikersnaam"), 8, true, false);
                opnieuw = false;
            } catch (Exception e)
            {
                System.out.println(e.getMessage());
                opnieuw = true;
            }
        } while (opnieuw);

        do
        {
            try
            {
                wachtwoord = vraagInput( ResourceHandling.getInstance().getString("Registreer.Wachtwoord"), 8, true, true);
                opnieuw = false;
            } catch (Exception e)
            {
                System.out.println(e.getMessage());
                opnieuw = true;
            }
        } while (opnieuw);

        dc.registreer(gebruikersnaam, voornaam, achternaam, wachtwoord);
    }

    /**
     * Kijken of de input wel goed is
     * @param prompt
     * @param minLength
     * @param cijfers
     * @param isWachtwoord
     * @return 
     */
    private static String vraagInput(String prompt, int minLength, boolean cijfers, boolean isWachtwoord)
    {

        /**
         * één input die dan adhv booleans op verschillende manieren gecheckt wordt
         */
        Scanner scan = new Scanner(System.in);
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
                System.out.printf("%s: ", prompt);
                input = scan.nextLine();
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
                    throw new IllegalArgumentException( ResourceHandling.getInstance().getString("Exception.registreer.wachtwoord"));
                }
            } while (input.isEmpty() || goedeInput == false);
            return input;
        } /**
         * filter op letters en/of cijfers
         */
        else if (isWachtwoord == false && cijfers == true)
        {
            boolean letter = false, cijfer = false;
            do
            {
                System.out.printf("%s: ", prompt);
                input = scan.nextLine().trim();
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
                    throw new IllegalArgumentException( ResourceHandling.getInstance().getString("Exception.registreer.gebruikersnaam"));
                }
            } while (input.isEmpty() || goedeInput == false);
            return input;
        } /**
         * filter op enkel letters
         */
        else
        {
            boolean letter=false;
            do
            {
                System.out.printf("%s: ", prompt);
                input = scan.nextLine();
                for (int i = 0; i < input.length(); i++)
                {
                    kar = input.toLowerCase().charAt(i);
                    if (Character.isWhitespace(kar) || kar == '-')
                    {
                        goedeInput = true;
                    } else if (kar < 'a' || kar > 'z')
                    {

                        goedeInput = false;
                        throw new IllegalArgumentException( ResourceHandling.getInstance().getString("Exception.registreer.naam"));

                    } else
                    {
                        goedeInput = true;
                        letter = true;
                    }
                }
                if (input.length() < minLength)
                {
                    throw new IllegalArgumentException( ResourceHandling.getInstance().getString("Exception.registreer.cijfer"));
                }
                if (!letter){
                    throw new IllegalArgumentException( ResourceHandling.getInstance().getString("Exception.registreer.naam"));
                }
            } while (input.isEmpty() || goedeInput == false);
            return input;
        }
    }

}