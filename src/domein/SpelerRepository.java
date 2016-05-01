/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domein;

import java.util.ArrayList;
import java.util.List;
import persistentie.SpelerMapper;

/**
 *
 * @author donovandesmedt
 */
public class SpelerRepository
{

    private List<Speler> spelers = new ArrayList<>();
    private SpelerMapper spelerMapper;

    /**
     * constructor
     */
    public SpelerRepository()
    {
        //**aanmaken spelermapper*/
        spelerMapper = new SpelerMapper();
        spelers = spelerMapper.geefSpelers();
    }

    /**
     * geef een lijst met spelers
     * @return 
     */
    public List<Speler> geefSpelers()
    {
        return spelers;
    }

    /**
     * geeft speler terug
     * @param gebruikersnaam
     * @param wachtwoord
     * @return 
     */
    public Speler geefSpeler(String gebruikersnaam, String wachtwoord)
    {
        //** speler opzoeken die we later gaan returnen*/
        Speler speler = spelerMapper.geefSpeler(gebruikersnaam);
        if (speler.getWachtwoord().equals(wachtwoord))
        {
            return speler;
        }
        return null;
    }

    /**
     * voeg speler toe
     * @param speler 
     */
    public void voegToe(Speler speler)
    {
        /**
         * voeg de speler toe aan de database door de methode uit de mapperklasse aan te spreken
         */
        /**
         * eerst kijken of de speler al bestaat
         */
        if (bestaatSpeler(speler.getGebruikersnaam()))
        {
            throw new IllegalArgumentException("Deze gebruikersnaam bestaat al");
        }
        spelerMapper.voegToe(speler);
    }

    /**
     * kijken of de speler bestaat
     * @param gebruikersnaam
     * @return 
     */
    public boolean bestaatSpeler(String gebruikersnaam)
    {
        /**
         * Kijken of de speler al bestaat
         */
        return spelerMapper.geefSpeler(gebruikersnaam) != null;
    }
    
    /**
     * geven van wachtwoord als de speler het vergeten is
     * @param gebruikersnaam
     * @return 
     */
    public String wachtwoordVergeten(String gebruikersnaam)
    {
        return spelerMapper.geefWachtwoord(gebruikersnaam);
    }
}
