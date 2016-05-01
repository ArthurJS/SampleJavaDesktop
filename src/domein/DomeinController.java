/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domein;

import java.io.FileNotFoundException;
import java.util.List;
import resources.ResourceHandling;

/**
 *
 * @author donovandesmedt
 */
public class DomeinController
{

    private SpelerRepository spelerRepo;
    private SpelRepository spelRepo;
    private Speler speler;
    private Spel spel;
    private ConfigureerSpel configureerSpel;
    private String keuzeSpel;
    private boolean isConsole;
    private String[] lijstSpelen;

    /** contructor */
    public DomeinController()
    {
        /**
         * aanmaken repositories
         */
        spelerRepo = new SpelerRepository();
        //spelRepo = new SpelRepository();
        configureerSpel = new ConfigureerSpel();

        isConsole = false;
    }

    /**
     * setten als we in console zitten
     * @param isConsole 
     */
    public void setIsConsole(boolean isConsole)
    {
        this.isConsole = isConsole;
    }

    /**
     * geef de speler
     * @return 
     */
    public String geefSpeler()
    {
        return speler.getVoornaam();
    }

    /**
     * is speler beheerder
     * @return 
     */
    public boolean isSpelerBeheerder()
    {
        return speler.isBeheerder();
    }

    /**
     * aanmelden van speler
     * @param gebruikersnaam
     * @param wachtwoord 
     */
    public void meldAan(String gebruikersnaam, String wachtwoord)
    {
        /**
         * meld speler aan en haal hem op uit de repository
         */
        Speler gevondenSpeler = spelerRepo.geefSpeler(gebruikersnaam, wachtwoord);
        setSpeler(gevondenSpeler);
    }

    /**
     * registreren van nieuwe speler
     * @param gebruikersnaam
     * @param voornaam
     * @param achternaam
     * @param wachtwoord 
     */
    public void registreer(String gebruikersnaam, String voornaam, String achternaam, String wachtwoord)
    {
        /**
         * maak een nieuwe speler aan en meld hem aan
         */
        Speler nieuweSpeler = new Speler(gebruikersnaam, voornaam, achternaam, wachtwoord);
        spelerRepo.voegToe(nieuweSpeler);
        setSpeler(nieuweSpeler);
    }

    /**
     * sette, van de speler
     * @param speler 
     */
    private void setSpeler(Speler speler)
    {
        //** instellen van aangemelde speler */
        if (speler != null)
        {
            this.speler = speler;
        } else
        {
            throw new IllegalArgumentException(ResourceHandling.getInstance().getString("Exception.speler"));
        }
    }

    /**
     * geef de lijst van spelen door
     * @return 
     */
    public String[] geefLijstSpelen()
    {
        /**
         * String array aanmaken voor het opvangen van spelnamen
         */
        spelRepo = new SpelRepository();
        List<Spel> lijstSpelObjecten = spelRepo.geefLijstSpelen();
        lijstSpelen = new String[lijstSpelObjecten.size()];

        /**
         * String array invullen
         */
        for (int index = 0; index < lijstSpelen.length; index++)
        {
            lijstSpelen[index] = lijstSpelObjecten.get(index).getNaam();
        }

        /**
         * Return van String array
         */
        return lijstSpelen;
    }

    /**
     * geven van de custom spelen
     * @return 
     */
    public String[] geefCustomSpelen()
    {

        List<Spelbord> lijstCustomspelObjecten = spelRepo.geefLijstCustomSpelen();
        String[] lijstCustomSpelen = new String[lijstCustomspelObjecten.size()];

        for (int index = 0; index < lijstCustomSpelen.length; index++)
        {
            lijstCustomSpelen[index] = lijstCustomspelObjecten.get(index).getNaam();
        }
        return lijstCustomSpelen;
    }

    /**
     * spel kiezen
     * @param naam
     * @param custom 
     */
    public void kiesSpel(String naam, boolean custom)
    {
        keuzeSpel = naam;
        /**
         * Gekozen spel wordt bijgehouden
         */

        if (!custom)
        {
            stelSpelIn(spelRepo.geefSpel(naam));
            zoekNietVoltooidSpelbord();
        } else
        {
            stelSpelIn(spelRepo.geefSpel("custommap"));
            setSpelbord(naam);
        }

    }

    /**
     * setten van het spelbord
     * @param naam 
     */
    public void setSpelbord(String naam)
    {
        List<Spelbord> spelborden = spel.getSpelborden();
        for (Spelbord spelbord : spelborden)
        {
            if (spelbord.getNaam().equals(naam))
            {
                spel.setSpelbord(spelbord);
                configureerSpel.setLeegSpelbord(spelbord);
            }
        }
    }

    /**
     * setten van het spel
     * @param naam 
     */
    public void setSpel(String naam)
    {
        spelRepo = new SpelRepository();
        List<Spel> spelen = spelRepo.geefLijstSpelen();
        for (Spel spel : spelen)
        {
            if (spel.getNaam().equals(naam))
            {
                stelSpelIn(spel);
                configureerSpel = new ConfigureerSpel();
                configureerSpel.setLeegSpel(spel);
            }
        }

    }

    /**
     * de naam van het gekozen spelbord geven
     * @return 
     */
    public String geefGekozenSpelbord()
    {
        return spel.getSpelbord().getNaam();
    }

    /**
     * opzoek gaan naar een niet voltooid spelbord
     */
    public void zoekNietVoltooidSpelbord()
    {
        spel.zoekNietVoltooidSpelbord();
    }

    /**
     * spel instellen
     * @param spel 
     */
    private void stelSpelIn(Spel spel)
    {
        /**
         * instellen van gekozen spel
         */
        if (spel != null)
        {
            this.spel = spel;
        } else
        {
            throw new IllegalArgumentException(ResourceHandling.getInstance().getString("Exception.spel"));
        }
    }

    /**
     * geven van het gesette spel
     * @return 
     */
    public String geefSpel()
    {
        return spel.getNaam();
    }

    /**
     * spel geven dat geconfigureerd moet worden
     * @return 
     */
    public String geefConfigureerSpel()
    {
        return configureerSpel.getLeegSpel().getNaam();
    }

    /**
     * spelbord geven dat geconfigureerd moet worden
     * @return 
     */
    public String geefConfigureerSpelbord()
    {
        return configureerSpel.getLeegSpelbord().getNaam();
    }

    /**
     * deze methode zal de namen van alle beschikbare spelborden van het gekozen spel weergeven, waarna de speler een spelbord moet kiezen
     *
     * @param gekozenspel
     */
    // domeincontroller kent spebordRepo niet maar heb geen idee hoe ik anders die lijst van spelborden kan afdrukken
    public String[] geefLijstSpelborden(String gekozenspel)
    {
        return spel.geefLijstSpelborden(gekozenspel);
    }

    /**
     * deze methode zal het gekozen spelbord teruggeven
     */
    public String[][] geefSpelbord()
    {
        /**
         * Deze methode zal van elk vak het id vragen en op basis van het id een spelbord maken.
         */
        Spelbord gekozenSpelbord = spel.getSpelbord();
        return zetSpelbordOm(gekozenSpelbord);
    }

    /**
     * spelbord wordt omgezet
     * @param spelbord
     * @return 
     */
    private String[][] zetSpelbordOm(Spelbord spelbord)
    {
        List<Vak> vakken = spelbord.geefVakken();
        String[][] spelbordString = new String[vakken.size() / 10][vakken.size() / 10];

        for (int i = 0; i < spelbordString.length; i++)
        {
            for (int j = 0; j < spelbordString[i].length; j++)
            {
                int positie = 10 * i + j;
                spelbordString[i][j] = vulIn(vakken, positie);
            }
        }
        return spelbordString;
    }
    
    /**
     * lijst met items geven
     * @return 
     */
    public String[] geefLijstItems()
    {
        List<Vak> items = configureerSpel.geefLijstItems();
        String [] lijstItems = new String[items.size()];
        isConsole = true;
        for(int i=0; i<items.size(); i++)
        {
            lijstItems[i] = vulIn(items, i);
        }
        isConsole = false;
        return lijstItems;
    }

    /**
     * is de muur op de rand
     * @param vak
     * @return 
     */
    private boolean isMuurRand(Vak vak)
    {
        /**
         * Kijken of de muur wel een rand is
         */
        return vak.isBewandelbaar(); //instanceof VakWandel;
    }

    /**
     * DomeinController vraagt aan spel de naam van het laatste voltooide spelbord
     *
     * @return
     */
    public String toonAantal()
    {
        return spel.toonAantal();
    }

    /**
     * kijken of er verder gespeelt moet worden
     * @param keuze
     * @return 
     */
    public boolean speelVerder(String keuze)
    {
        /**
         * spelbord tonen en vragen wilt u verder spelen of niet, bij wel wordt het keuzemenu opnieuw getoont
         */
        return spel.speelVerder(keuze);
    }

    /**
     * is het spel op zijn einde?
     * @return 
     */
    public boolean isEindeSpel()
    {
        /**
         * bij speelverder false dan wordt spel beëindigt
         */
        return spel.isEindeSpel();
    }

    /**
     * kijken of het spelbord voltooid is
     * @return 
     */
    public boolean isSpelbordVoltooid()
    {
        /**
         * Return of het spel voltooid is of niet
         */
        return spel.isSpelbordVoltooid();
    }

    /**
     * verplaatsen van de hero
     * @param richting 
     */
    public void verplaatsHero(String richting)
    {
        /**
         * verplaatsen van de hero
         */
        spel.verplaatsHero(richting.charAt(0));
    }

    /**
     * vergeten wachtwoord geven
     * @param gebruikersnaam
     * @return 
     */
    public String wachtwoordVergeten(String gebruikersnaam)
    {
        return spelerRepo.wachtwoordVergeten(gebruikersnaam);
    }

    /**
     * reseten van spelbord
     */
    public void Reset()
    {
        /**
         * Resetten van het spelbord
         */
        spel.Reset();
    }

    /**
     * aantal verplaatsingen geven
     * @return 
     */
    public int geefAantalVerplaatsingen()
    {
        return spel.geefAantalVerplaatsingen();
    }

    /**
     * configureren van spelbord
     * @param naam 
     */
    public void ConfigureerSpelbord(String naam)
    {
        configureerSpel.configureerSpelbord(naam);
    }

    /**
     * configureren van een spel
     * @param naam 
     */
    public void ConfigureerSpel(String naam)
    {
        configureerSpel.configureerSpel(naam);
    }

    /**
     * default spelbord geven
     * @return 
     */
    public String[][] geefDefaultSpelbord()
    {
        return zetSpelbordOm(configureerSpel.getLeegSpelbord());
    }

    /**
     * spelbord geven
     * @param naam
     * @return 
     */
    public String[][] geefSpelbord(String naam)
    {
        return zetSpelbordOm(spel.geefSpelbord(naam));
    }

    /**
     * plaatsen van een vak
     * @param positieX
     * @param positieY
     * @param ID 
     */
    public void plaatsVak(int positieX, int positieY, int ID)
    {
        configureerSpel.plaatsVak(positieX, positieY, ID);
    }

    /**
     * plaatsen van txtfile in database
     */
    public void maakSpelbord() throws FileNotFoundException
    {
        /**
         * SpelBord maken
         */
        spel.maakSpelbord(keuzeSpel);
    }

    /**
     * spelbord in database plaatsen
     * @param naam 
     */
    public void zetSpelbordInDatabase(String naam)
    {
        configureerSpel.zetSpelbordInDatabase(naam);
    }

    /**
     * spel in database steken
     * @param naam 
     */
    public void zetSpelInDatabase(String naam)
    {
        configureerSpel.zetSpelInDatabase(naam);
    }

    /**
     * gewijzigd spelbord in database steken
     * @param spelNaam
     * @param spelbordNaam 
     */
    public void zetGewijzigdSpelbordInDatabase(String spelNaam, String spelbordNaam)
    {
        configureerSpel.zetGewijzigdSpelbordInDatabase(spelNaam, spelbordNaam);
    }
    
    /**
     * delete van het spelbord
     * @param spelbordNaam 
     */
    public void deleteSpelbord(String spelbordNaam)
    {
        configureerSpel.deleteSpelbord(spelbordNaam);
    }
    
    /**
     * wijzigen van de spelnaam
     */
    public void wijzigSpelNaam()
    {
        configureerSpel.wijzigSpelNaam();
    }
    
    /**
     * wijzigen van het spelbordnaam
     */
    public void wijzigSpelbordNaam()
    {
        configureerSpel.wijzigSpelbordNaam();
    }
    
    /**
     * saven spelnaam
     * @param spelNaam 
     */
    public void saveSpelNaam(String spelNaam)
    {
        configureerSpel.setSpelNaam(spelNaam);
    }
    
    /**
     * saven van het spelbordnaam
     * @param spelbordNaam 
     */
    public void saveSpelbordNaam(String spelbordNaam)
    {
        configureerSpel.setSpelbordNaam(spelbordNaam);
    }

    /**
     * bestaat het spelbord
     * @param naam
     * @return 
     */
    public boolean bestaatSpelbord(String naam)
    {
        return configureerSpel.bestaatSpelbord(naam);
    }
    
    /**
     * updaten van de spelbordlijst
     */
    public void updateSpelbordLijst()
    {
        configureerSpel.updateSpelbordLijst();
        
    }

    /**
     * bestaat het spel
     * @param naam
     * @return 
     */
    public boolean bestaatSpel(String naam)
    {
        if(lijstSpelen == null)
            lijstSpelen = geefLijstSpelen();
        String[] spelen = lijstSpelen;

        for(String spelNaam: spelen)
        {
            if(spelNaam.equals(naam))
                return true;
        }
        return false;
    }

    /**
     * bevat het alle onderdelen
     * @return 
     */
    public boolean bevatAlleOnderdelen()
    {
        return configureerSpel.bevatAlleOnderdelen();
    }

    /**
     * geeft weer wat de problemen zijn van het nieuwe spelbord
     * @return 
     */
    public String gebreken()
    {
        return configureerSpel.gebreken();
    }
    
    
    /**
     * kijken welk vak wat is en de gepaste string doorgeven
     * @param vakken
     * @param positie
     * @return 
     */
    private String vulIn(List<Vak> vakken, int positie)
    {
        if (!vakken.get(positie).isBewandelbaar())
        {
            if (!isConsole)
            {
                if (positie - 11 < 0)
                {
                    if (isMuurRand(vakken.get(positie + 10)) || isMuurRand(vakken.get(positie + 1))
                            || isMuurRand(vakken.get(positie + 11)) || isMuurRand(vakken.get(positie + 9)))
                    {
                        return "#r";
                    } else
                    {
                        return "#";
                    }
                } else if (positie + 11 >= vakken.size())
                {
                    if (isMuurRand(vakken.get(positie - 10)) || isMuurRand(vakken.get(positie - 1))
                            || isMuurRand(vakken.get(positie - 11)) || isMuurRand(vakken.get(positie - 9)))
                    {
                        return "#r";
                    } else
                    {
                        return "#";
                    }
                } else if (isMuurRand(vakken.get(positie - 10)) || isMuurRand(vakken.get(positie + 10)) || isMuurRand(vakken.get(positie + 1)) || isMuurRand(vakken.get(positie - 1))
                        || isMuurRand(vakken.get(positie - 11)) || isMuurRand(vakken.get(positie + 11)) || isMuurRand(vakken.get(positie - 9)) || isMuurRand(vakken.get(positie + 9)))
                {
                    return "#r";
                }
            } else
            {
                return "#";
            }
        } else if (vakken.get(positie).isBewandelbaar())
        {

            VakWandel vakWandel = (VakWandel) vakken.get(positie);

            if (vakWandel.isIsDoel())
            {
                if (!isConsole)
                {
                    if (vakWandel.bevatKist())
                    {
                        return "$.";
                    } else if (vakWandel.bevatMannetje())
                    {
                        return "@.";
                    }
                    else
                    {
                        return ".";
                    }
                } else
                {
                     if (vakWandel.bevatKist())
                    {
                        return "$";
                    } else if (vakWandel.bevatMannetje())
                    {
                        return "@";
                    }
                    else
                    {
                        return ".";
                    }
                }
            } else
            {
                if (vakWandel.bevatKist())
                {
                    return "$";
                } else if (vakWandel.bevatMannetje())
                {
                    return "@";
                } else
                {
                    return "_";
                }
            }
        }

        return "#";
    }

    /**
     * geef vak op een bepaalde positie
     * @param col
     * @param row
     * @return 
     */
    public String geefVakOpPositie(int col, int row)
    {
        List<Vak> vakken = configureerSpel.getLeegSpelbord().geefVakken();
        int positie = 10 * col + row;

        return vulIn(vakken, positie);
    }
}
