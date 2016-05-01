/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domein;

import java.io.FileNotFoundException;
import java.util.List;
import persistentie.SpelMapper;
import resources.ResourceHandling;

/**
 *
 * @author donovandesmedt
 */
public class Spel
{

    private String naam;
    private boolean Voltooid;
    private boolean isEindeSpel;

    private List<Spelbord> spelborden;
    private Spelbord spelbord;
    private Spelbord initeelSpelbord;
    private Spelbord geresetSpelbord;
    private SpelMapper spelmapper;

    /**
     * constructor
     * @param naam
     * @param spelborden 
     */
    public Spel(String naam, List<Spelbord> spelborden)
    {
        setNaam(naam);
        setIsVoltooid(false);
        setEindeSpel(false);
        setSpelborden(spelborden);
    }
    
    /**
     * constructor
     * @param naam 
     */
    public Spel(String naam)
    {
        this(naam, null);
    }
    
    /**
     * naam van het spel opvragen
     * @return 
     */
    public String getNaam()
    {
        return naam;
    }

    /**
     * setten van naam
     * @param naam 
     */
    public void setNaam(String naam)
    {
        if (!naam.isEmpty() && !naam.equals(""))
        {
            this.naam = naam;
        } else
        {
            throw new IllegalArgumentException("naam spel is leeg");
        }
    }

    /**
     * kijken of het spel voltooid is
     * @return 
     */
    public boolean isVoltooid()
    {
        return Voltooid;
    }

    /**
     * setten of het spel voltooid is
     * @param Voltooid 
     */
    public void setIsVoltooid(boolean Voltooid)
    {
        this.Voltooid = Voltooid;
    }

    /**
     * lijst met spelborden op vragen
     * @return 
     */
    public List<Spelbord> getSpelborden()
    {
        return spelborden;
    }

    /**
     * setten van spelborden van het spel
     * @param spelborden 
     */
    public void setSpelborden(List<Spelbord> spelborden)
    {
        this.spelborden = spelborden;
    }

    /**
     * lijst van spelborden tonen
     * @param gekozenspel
     * @return 
     */
    public String[] geefLijstSpelborden(String gekozenspel)
    {
        /**
         * Array maken van de gekozen spelbord namen
         */
        String[] spelbordenArray = new String[spelborden.size()];

        for (int index = 0; index < spelbordenArray.length; index++)
        {
            spelbordenArray[index] = spelborden.get(index).getNaam();
        }

        return spelbordenArray;
    }

    /**
     * spelbord geven
     * @return 
     */
    public Spelbord getSpelbord()
    {
        return spelbord;
    }
    
    /**
     * geven van het spelbord
     * @param naam
     * @return 
     */
    public Spelbord geefSpelbord(String naam)
    {
        List<Spelbord> spelborden = getSpelborden();
        for(Spelbord s: spelborden)
        {
            if(s.getNaam().equals(naam))
                return s;
        }
        return null;
    }
    
    /**
     * extra methode die de naam van het eerste spelbord teruggeeft dat nog niet
     * voltooid is
     *
     * @param spelbord
     */
    public void zoekNietVoltooidSpelbord()
    {
        for (Spelbord s : spelborden)
        {
            if (!s.isVoltooid())
            {
                /**
                 * Het gevonden spelbord wordt geset
                 */
                setSpelbord(s);
                geresetSpelbord = s;
                //initeelSpelbord = new Spelbord(s);
                return;
            }
        }
        
        setEindeSpel(true);
    }
    
    /**
     * setten van het spelbord
     * @param spelbord 
     */
    public void setSpelbord(Spelbord spelbord)
    {
        if (spelbord != null)
        {
            this.spelbord = spelbord;
        } else
        {
            throw new IllegalArgumentException("Spelbord object is niet geinitialiseerd");
        }
    }

    /**
     * aantal voltooide spellen tonen
     * @return 
     */
    public String toonAantal()
    {
        /**
         * Enkele variabelen aanmaken voor het opslaan van data
         */
        int teller = 0;
        String uitvoer = "";

        for (Spelbord spelbord : spelborden)
        {
            if (spelbord.isVoltooid())
            {
                /**
                 * Op zoek naar voltooide spelborden
                 */
                uitvoer += String.format("Spelbord %s is voltooid%n", spelbord.getNaam());
                teller++;
            }
        }
        uitvoer += String.format("Er zijn in totaal %d spelborden voltooid%n", teller);
        return uitvoer;
    }

    /**
     * verder spelen
     * @param keuze
     * @return 
     */
    public boolean speelVerder(String keuze)
    {
        return  ResourceHandling.getInstance().getString("Speelverder.keuze").contains(keuze);
    }

    /**
     * als het einde is behaald
     * @param isEindeSpel 
     */
    public void setEindeSpel(boolean isEindeSpel)
    {
        this.isEindeSpel = isEindeSpel;
    }

    /**
     * einde van het spel
     * @return 
     */
    public boolean isEindeSpel()
    {
        return isEindeSpel;
    }

    /**
     * hero verplaatsen
     * @param richting 
     */
    public void verplaatsHero(char richting)
    {
        spelbord.verplaatsHero(richting);
    }

    /**
     * kijken of het spelbord is voltooid
     * @return 
     */
    public boolean isSpelbordVoltooid()
    {
        return spelbord.isSpelbordVoltooid();
    }

    /**
     * resetten van een spelbord
     */
    public void Reset()
    {
        List<Spelbord> spelborden = spelbord.geefOrigineleSpelborden(getNaam());
        for (Spelbord e : spelborden)
        {
            if(e.getNaam().equals(geresetSpelbord.getNaam()))
            {
                initeelSpelbord = e;
                break;
            }
        }
        spelbord.setAantalVerplaatsingen(0);
        geresetSpelbord.setVoltooid(true);
        spelbord = null;
        spelbord = new Spelbord(initeelSpelbord);
    }
    
    /**
     * vak geven op een bepaalde positie
     * @param col
     * @param row
     * @return 
     */
    public Vak geefVakOpPositie(int col, int row)
    {
        return spelbord.geefVakOpPositie(col, row);
    }
    
    /**
     * Spelbord maken van een textfile
     */
    public void maakSpelbord(String keuzeSpel) throws FileNotFoundException
    {
        spelbord.maakBord(keuzeSpel);
    }
    
    /**
     * aantal verplaatsingen
     * @return 
     */
    public int geefAantalVerplaatsingen()
    {
        return spelbord.getAantalVerplaatsingen();
    }
    
    /**
     * spel in database plaatsen
     * @param naam 
     */
    public void zetSpelInDataBase(String naam)
    {
        /**
         * Spel in de database zetten.
         */
        spelmapper = new SpelMapper();
        spelmapper.voegSpelToe(naam);
        
    }
    
    /**
     * gewijzig spelbord plaatsen in het spelbord
     * @param spelnaam
     * @param spelbordnaam 
     */
    public void zetGewijzigdSpelbordInDatabase(String spelnaam, String spelbordnaam)
    {
        getSpelbord().zetGewijzigdSpelbordInDatabase(spelnaam, spelbordnaam);
    }
    
    /**
     * deleten van een spelbord
     * @param spelbordNaam 
     */
    public void deleteSpelbord(String spelbordNaam)
    {
        for(Spelbord spelbord: spelborden)
        {
            if(spelbord.getNaam().equals(spelbordNaam))
                spelbord.deleteSpelbord(getNaam());
        }
    }
    
    /**
     * wijzigen van de spelnaam
     * @param spelNaam 
     */
    public void wijzigSpelNaam(String spelNaam)
    {
        getSpelbord().wijzigSpelNaam(getNaam(), spelNaam);
        spelmapper = new SpelMapper();
        spelmapper.wijzigNaam(getNaam(), spelNaam);
    }
    
    /**
     * wijzig van de spelbord naam
     * @param spelbordNaam 
     */
    public void wijzigSpelbordNaam(String spelbordNaam)
    {
        getSpelbord().wijzigSpelbordNaam(spelbordNaam);
    }
    
    /**
     * spelbord in database plaatsen
     * @param Spelnaam 
     */
    public void zetSpelbordInDatabase(String Spelnaam)
    {
        /**
         * Spelborden in de database zetten.
         */
        getSpelbord().zetSpelbordInDatabase(Spelnaam, getSpelbord().getNaam());
    }

}
