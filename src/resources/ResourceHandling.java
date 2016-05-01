/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package resources;

import java.util.Locale;
import java.util.ResourceBundle;

/**
 *
 * @author Toon
 */
public class ResourceHandling
{
    private static ResourceHandling instance = null;
    private static Locale local;
    private static ResourceBundle bundle;

    /**
     * singleton
     */
    protected ResourceHandling()
    {
        /** Resource objecten aanmaken met default waarden (taal: nederlands)*/
        stelInLanguage("nl","resources.ResourceBundle_Sokoban_nl");
    }
    
    /**
     * Dit is noodzakelijke klasse. Deze gaat kijken of het static object al aangemaakt is. zo ja het object wordt aangemaakt zo niet wordt het static object gewoon doorgegeven
     * @return 
     */
    public static ResourceHandling getInstance()
    {
        if(instance == null)
        {
            instance = new ResourceHandling();
        }
        
        return instance;
    }
    
    /**
     * Instellen van een taal
     */
    public  void stelInLanguage(String countryInitials, String bundleName)
    {
        /**Nieuwe taal instellen */
        local = new Locale(countryInitials);
        bundle = ResourceBundle.getBundle(bundleName,local);
    }

    /**
     * Geeft localizatie weer
     * @return 
     */
    public Locale getLocal()
    {
        return local;
    }

    /**
     * Het setten van de localisatie
     * @param local 
     */
    public void setLocal(Locale local)
    {
        /** local object invullen*/
        if (local != null)
        {
            ResourceHandling.local = local;
        } else
        {
            throw new IllegalArgumentException("Geen local object geïnitialiseerd.");
        }
    }

    /**
     * De Resourcebundle opvragen
     * @return 
     */
    public ResourceBundle getBundle()
    {
        return bundle;
    }

    /**
     * de Resourcebundle setten
     * @param bundle 
     */
    public void setBundle(ResourceBundle bundle)
    {
        /**resourcebundle object invullen */
        if (bundle != null)
        {
            ResourceHandling.bundle = bundle;
        } else
        {
            throw new IllegalArgumentException("Geen resourcebundle object aangemaakt.");
        }
    }
    
    /**
     * De inhoude opvragen van een bepaalde  key
     * @param key
     * @return 
     */
    public  String getString(String key)
    {
        return bundle.getString(key);
    }

}
