/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domein;

/**
 *
 * @author donovandesmedt
 */
public class Speler
{

    private String naam;
    private String voornaam;
    private String gebruikersnaam;
    private String wachtwoord;
    private boolean beheerder;

    /**
     * constructor
     * @param gebruikersnaam
     * @param naam
     * @param voornaam
     * @param wachtwoord
     * @param beheerder 
     */
    public Speler(String gebruikersnaam, String naam, String voornaam, String wachtwoord, boolean beheerder)
    {
        setGebruikersnaam(gebruikersnaam);
        setNaam(naam);
        setVoornaam(voornaam);
        setWachtwoord(wachtwoord);
        setBeheerder(beheerder);
    }

    /**
     * contructor
     * @param naam
     * @param voornaam
     * @param gebruikersnaam
     * @param wachtwoord 
     */
    public Speler(String naam, String voornaam, String gebruikersnaam, String wachtwoord)
    {
        this(naam, voornaam, gebruikersnaam, wachtwoord, false);
    }

    /**
     * geef naam van de speler
     * @return 
     */
    public String getNaam()
    {
        return naam;
    }

    /**
     * set van de naam
     * @param naam 
     */
    private void setNaam(String naam)
    {
        if (naam == null || naam.length() == 0)
        {
            throw new IllegalArgumentException("Elke speler heeft verplicht een naam.");
        }
        this.naam = naam;
    }

    /**
     * geven van voornaam
     * @return 
     */
    public String getVoornaam()
    {
        return voornaam;
    }

    /**
     * setten van de voornaam
     * @param voornaam 
     */
    private void setVoornaam(String voornaam)
    {
        if (voornaam == null || voornaam.length() == 0)
        {
            throw new IllegalArgumentException("Elke speler heeft verplicht een voornaam.");
        }
        this.voornaam = voornaam;
    }

    /**
     * Gebruikersnaam opvragen
     * @return 
     */
    public String getGebruikersnaam()
    {
        return gebruikersnaam;
    }

    /**
     * gebruikersnaam setten
     * @param gebruikersnaam 
     */
    private void setGebruikersnaam(String gebruikersnaam)
    {
        if (gebruikersnaam == null || gebruikersnaam.length() == 0)
        {
            throw new IllegalArgumentException("Elke speler heeft verplicht een gebruikersnaam.");
        }
        this.gebruikersnaam = gebruikersnaam;
    }

    /**
     * wachtwoord opvragen
     * @return 
     */
    public String getWachtwoord()
    {
        return wachtwoord;
    }

    /**
     * setten van het wachtwoord
     * @param wachtwoord 
     */
    private void setWachtwoord(String wachtwoord)
    {
        if (wachtwoord == null || wachtwoord.length() == 0)
        {
            throw new IllegalArgumentException("Elke speler heeft verplicht een wachtwoord.");
        }
        this.wachtwoord = wachtwoord;
    }

    /**
     * kijken of de speler een beheerder is
     * @return 
     */
    public boolean isBeheerder()
    {
        return beheerder;
    }

    /**
     * setten van de beheerder
     * @param beheerder 
     */
    private void setBeheerder(boolean beheerder)
    {
        this.beheerder = beheerder;
    }

}
