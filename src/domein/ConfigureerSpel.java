/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domein;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author donovandesmedt
 */
public class ConfigureerSpel
{

    private Character[][] bord;
    private Spelbord leegSpelbord;
    private Spel leegSpel;
    private List<Spelbord> spelborden;

    private String spelNaam;
    private String spelbordNaam;
    
    /**
     * constructor
     */
    public ConfigureerSpel()
    {
        //spelbord = new Spelbord("", false, null, null, null);
        bord = new Character[10][10];
        spelborden = new ArrayList<>();
    }

    /**
     * configureren van spelbord
     * @param naam 
     */
    public void configureerSpelbord(String naam)
    {
        /**
         * De naam dat in gegeven is bij het configureren wordt doorgegeven en
         * wordt daarmee een default spelbord meegemaakt
         */
        leegSpelbord = new Spelbord(naam);

    }

    /**
     * configureren van spel
     * @param naam 
     */
    public void configureerSpel(String naam)
    {
        leegSpel = new Spel(naam);
    }

    /**
     * spelbord in database steken
     * @param spelbordNaam 
     */
    public void zetSpelbordInDatabase(String spelbordNaam)
    {
        //leegSpelbord.zetSpelbordInDatabase("custommap", naam);
        leegSpelbord.zetSpelbordInDatabase(leegSpel.getNaam(), spelbordNaam);
    }

    /**
     * spel in database steken
     * @param naam 
     */
    public void zetSpelInDatabase(String naam)
    {
        leegSpel.zetSpelInDataBase(naam);
    }
    
    /**
     * het gewzijgide spelbord in database steken
     * @param spelNaam
     * @param spelbordNaam 
     */
    public void zetGewijzigdSpelbordInDatabase(String spelNaam, String spelbordNaam)
    {
        leegSpel.zetGewijzigdSpelbordInDatabase(spelNaam, spelbordNaam);
    }
    
    /**
     * deleten van het spelbord
     * @param spelbordNaam 
     */
    public void deleteSpelbord(String spelbordNaam)
    {
        leegSpel.deleteSpelbord(spelbordNaam);
    }
    
    /**
     * wijzigen van een spelnaam
     */
    public void wijzigSpelNaam()
    {
        if(spelNaam != null)
            leegSpel.wijzigSpelNaam(spelNaam);
    }
    
    /**
     * spelbordnaam wordt gewijzigd
     */
    public void wijzigSpelbordNaam()
    {
        if(spelbordNaam != null)
            leegSpel.wijzigSpelbordNaam(spelbordNaam);
    }
    
    /**
     * setten van een spelnaam
     * @param spelNaam 
     */
    public void setSpelNaam(String spelNaam)
    {
        this.spelNaam = spelNaam;
    }
    
    /**
     * set van het spelbord naam
     * @param spelbordNaam 
     */
    public void setSpelbordNaam(String spelbordNaam)
    {
        this.spelbordNaam = spelbordNaam;
    }
    
    /**
     * geef een leeg spelbord
     * @return 
     */
    public Spelbord getLeegSpelbord()
    {
        return leegSpelbord;
    }
    
    /**
     * geven van een leegspel
     * @return 
     */
    public Spel getLeegSpel()
    {
        return leegSpel;
    }
    
    /**
     * setten van een leeg spel
     * @param spel 
     */
    public void setLeegSpel(Spel spel)
    {
        this.leegSpel = spel;
    }
    
    /**
     * setten van het spelbord
     * @param spelbord 
     */
    public void setLeegSpelbord(Spelbord spelbord)
    {
        if (spelbord == null)
        {
            throw new NullPointerException("DefaultSpelbord wordt geset met een null pointer object");
        }
        this.leegSpelbord = spelbord;
    }

    /**
     * plaatsen van een vak
     * @param positieX
     * @param positieY
     * @param ID 
     */
    public void plaatsVak(int positieX, int positieY, int ID)
    {
        leegSpelbord.plaatsVakInLijst(positieX, positieY, ID);
    }

    /**
     * bestaat spelbord al ?
     * @param naam
     * @return 
     */
    public boolean bestaatSpelbord(String naam)
    {
        spelborden = leegSpel.getSpelborden();
        
        if(spelborden == null)
            return false;
        
        for(Spelbord spelbord: spelborden)
        {
            if(spelbord.getNaam().equals(naam))
            {
                return true;
            }
        }
        return false;
    }
    
    /**
     * update van het spelbord lijst
     */
    public void updateSpelbordLijst()
    {
            spelborden.add(leegSpelbord);
            leegSpel.setSpelborden(spelborden);
            /**
             * Spelbord setten.
             */
            leegSpel.setSpelbord(leegSpelbord);
    }
    
    /**
     * geef een lijst met items
     * @return 
     */
    public List<Vak> geefLijstItems()
    {
        List<Vak> items = new ArrayList<>();
        
        VakWandel vakMan = new VakWandel(false);
        vakMan.setHero(new Mannetje());
        
        VakWandel vakKist = new VakWandel(false);
        vakKist.setKist(new Kist());
        
        items.add(vakMan);
        items.add(new VakWandel(false));
        items.add(new VakWandel(true));
        items.add(new VakMuur());
        items.add(vakKist);
        return items;
    }

    /**
     * alle onderdelen op het spelbord
     * @return 
     */
    public boolean bevatAlleOnderdelen()
    {
        return leegSpelbord.bevatAlleOnderdelen();
    }

    /**
     * kijken of spelbord alles heeft
     * @return 
     */
    public String gebreken()
    {
        return leegSpelbord.gebreken();
    }
}
