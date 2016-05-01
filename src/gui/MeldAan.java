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
public class MeldAan
{
    /**
     * Aanmelden van de speler
     * @param dc 
     */
     public static void meldAan(DomeinController dc)
    {
        boolean flag = true;
        /**
         * opvragen van gebruikersnaam en wachtwoord om aan te melden
         */
        do
        {
        Scanner input = new Scanner(System.in);
        System.out.printf("%s: ",  ResourceHandling.getInstance().getString("Aanmelden.Gebruikersnaam"));
        String naam = input.nextLine().trim();
        System.out.printf("%s: ",  ResourceHandling.getInstance().getString("Aanmelden.Wachtwoord"));
        String wachtwoord = input.nextLine();

        //** aanmelden */
        
        dc.meldAan(naam, wachtwoord);
        flag = false;
        // System.out.println(dc.meldAan(naam, wachtwoord));
        }
        while(flag);
    }
}
