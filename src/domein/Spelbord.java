/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domein;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import persistentie.KistMapper;
import persistentie.MannetjeMapper;
import persistentie.SpelbordMapper;
import persistentie.VakMapper;
import resources.ResourceHandling;

/**
 *
 * @author donovandesmedt
 */
public class Spelbord
{

    private String naam;
    private boolean Voltooid;
    //private int positieMan;

    private VakMapper vakMapper;
    private List<Vak> vakkenSpelbord;
    private List<Kist> kisten;
    private Mannetje man;
    private int aantalVerplaatsingen;
    private SpelbordMapper spelbordmapper;
    private final int MAX_VAKKEN = 100;
    private boolean bevatMan;
    private boolean alleOnderdelen;
    
    /**
     * constructor
     * @param naam
     * @param Voltooid
     * @param vakkenSpelbord
     * @param kisten
     * @param man 
     */
    public Spelbord(String naam, boolean Voltooid, List<Vak> vakkenSpelbord, List<Kist> kisten, Mannetje man)
    {
        setNaam(naam);
        setVoltooid(Voltooid);
        setVakkenSpelbord(vakkenSpelbord);
        setKisten(kisten);
        setMan(man);
        bevatMan = false;
        alleOnderdelen = false;
        if (vakkenSpelbord != null)
        {
            if (!vakkenSpelbord.isEmpty())
            {
                vulVakkenIn();
                setAantalVerplaatsingen(0);
            }
        }
    }

    /**
     * constructor
     * @param naam 
     */
    public Spelbord(String naam)
    {
        /**
         * Deze constructor maakt aan de hand van een naam een default map aan
         */
        this(naam, false, null, null, null);
        vulDefaultVakkenI();
        kisten = new ArrayList<>();
    }

    /**
     * constructor
     * @param spelbord 
     */
    public Spelbord(Spelbord spelbord)
    {
        this(spelbord.getNaam(), spelbord.isVoltooid(), spelbord.geefVakken(), spelbord.getKisten(), spelbord.getMan());
    }

    /**
     * vul een default spelbord in
     */
    private void vulDefaultVakkenI()
    {
        vakkenSpelbord = new ArrayList<>();
        int j = 0;
        for (int i = 0; i < MAX_VAKKEN; i++)
        {      
            if(i%10==0 && i!=0)
            {
                j++;
            }       
            vulVakMuurIn(i%10, j);       
        }
    }
    
    /**
     * plaatsen van item in het spelbord
     * @param col
     * @param rij
     * @param ID 
     */
    public void plaatsVakInLijst(int col, int rij , int ID)
    {
        int positie = (rij*10) + col;
        switch(ID)
        {
            case 0:
                /**Mannetje plaatsen*/
                if(!bevatMan && !isRand(positie))
                {
                VakWandel vak = plaatsVakWandel(col, rij,false);         
                man = new Mannetje();
                vak.setHero(man);
                man.setVak(vak);
                bevatMan = true;
                bevatKist(positie);
                vakkenSpelbord.set(positie, vak);
                break;
                }
            case 1: 
                /**wandelbaarPad maken*/
                if(!isRand(positie))
                {
                bevatKist(positie);
                bevatMannetje(positie);
                vakkenSpelbord.set(positie, plaatsVakWandel(col, rij,false));
                break;
                }
            case 2:
                /**Doel maken*/
                if(!isRand(positie))
                {
                bevatKist(positie);
                bevatMannetje(positie);
                vakkenSpelbord.set(positie,plaatsVakWandel(col, rij,true));
                break;
                }
            case 3:
                /**Kist*/
                bevatKist(positie);
                bevatMannetje(positie);
                vakkenSpelbord.set(positie, plaatsVakMuur(col, rij));
                break;
            case 4:
                /**Kist*/
                if(!isRand(positie))
                {
                bevatKist(positie);
                bevatMannetje(positie);
                VakWandel vakje = plaatsVakWandel(col, rij, false);
                Kist kist = plaatsKist();
                vakje.setKist(kist);
                vakkenSpelbord.set(positie, vakje);
                kisten.add(kist);
                break;
                }
        }
    }
    
    /**
     * kijken of het vak een kist heeft
     * @param positie 
     */
    private void bevatKist(int positie)
    {
        if(vakkenSpelbord.get(positie).getKist() != null)
        {
            int index = kisten.indexOf(vakkenSpelbord.get(positie).getKist());
            kisten.remove(index);
        }
    }
    
    /**
     * kijken of het vak een mannetje heeft
     * @param positie 
     */
    private void bevatMannetje(int positie)
    {
        if(vakkenSpelbord.get(positie).getHero() != null)
            bevatMan = false;        
    }
    
    /**
     * kijken of alles in het spelbpord zit dat er in moet zitten
     * @return 
     */
    public boolean bevatAlleOnderdelen()
    {
        return alleOnderdelen;
    }
    
    /**
     * gaat kijken dat het spelbord goed aangemaakt is en dat er niks vergeten is
     * @return 
     */
    public String gebreken()
    {
        boolean bevatMan = false, bevatKist = false, correctAantalKisten = true;
        int aantalKisten=0, aantalDoelen=0;
        
        for(Vak vak: vakkenSpelbord)
        {
            if(vak.isBewandelbaar())
            {
                VakWandel wandelvak = (VakWandel)vak;
                if(wandelvak.isIsDoel())
                    aantalDoelen++;
                if(vak.bevatMannetje())
                    bevatMan = true;
                if(vak.bevatKist())
                {
                    bevatKist = true;
                    aantalKisten++;
                }       
            }        
        }
        if(aantalKisten !=0 && aantalDoelen != 0 && aantalKisten != aantalDoelen )
            correctAantalKisten = false;
        
        if(bevatMan && bevatKist && correctAantalKisten)
        {
            alleOnderdelen = true;
            return ResourceHandling.getInstance().getString("Custommap.goed");
        }
        else if(bevatMan && bevatKist)
                return ResourceHandling.getInstance().getString("Custommap.kisten");
        else if(bevatMan)
                return ResourceHandling.getInstance().getString("Custommap.man");
        else if(bevatKist)
                return ResourceHandling.getInstance().getString("Custommap.kist");
        else if(aantalDoelen>0)
                return ResourceHandling.getInstance().getString("Custommap.niks");
        else
           return ResourceHandling.getInstance().getString("Custommap.niks");
        
    }
    
    /**
     * bevind je je op de rand
     * @param pos
     * @return 
     */
    private boolean isRand(int pos)
    {
        return(pos < 10 || pos%10 == 0 || pos%10 == 9 || pos > 89);
    }

    /**
     * muur in lijst steken
     * @param positieX
     * @param positieY 
     */
    private void vulVakMuurIn(int positieX, int positieY)
    {
        vakkenSpelbord.add(new VakMuur(2, positieX, positieY));
    }
    
    /**
     * plaatsen van vakwandel
     * @param positieX
     * @param positieY
     * @param isDoel
     * @return 
     */
    private VakWandel plaatsVakWandel(int positieX, int positieY , boolean isDoel)
    {
        return new VakWandel(1, positieX,positieY,isDoel);
    }
    
    /**
     * plaatsen van een vakmuur
     * @param positieX
     * @param positieY
     * @return 
     */
    private VakMuur plaatsVakMuur(int positieX, int positieY)
    {
        return new VakMuur(2, positieX, positieY);
    }
    
    /**
     * plaatsen van de kist
     * @return 
     */
    private Kist plaatsKist()
    {
        return new Kist(false);
    }
    
    /**
     * in vullen van het spelbord met het mannetje en de kisten
     */
    private void vulVakkenIn()
    {     
        /**Zorgen dat de juiste vakken met de juiste objecten worden ingevuld*/
        int kistIndex = 0;
        
        for(int i = 0; i < vakkenSpelbord.size(); i++)
        {
            if(vakkenSpelbord.get(i).getiD() == 6)
            {
                vakkenSpelbord.get(i).setHero(man);
                man.setVak(vakkenSpelbord.get(i));
            }else if(vakkenSpelbord.get(i).getiD() == 3)
            {
                vakkenSpelbord.get(i).setKist(kisten.get(kistIndex));
                kisten.get(kistIndex).setVak(vakkenSpelbord.get(i));
            }
        }
    }

    /**
     * opvragen van de naam
     * @return 
     */
    public String getNaam()
    {
        return naam;
    }

    /**
     * setten van het spelbord naam
     * @param naam 
     */
    public void setNaam(String naam)
    {
        this.naam = naam;
    }

    /**
     * is het spelbord voltooid?
     * @return 
     */
    public boolean isVoltooid()
    {
        return Voltooid;
    }

    /**
     * setten als het spelbord voltooid is
     * @param Voltooid 
     */
    public void setVoltooid(boolean Voltooid)
    {
        this.Voltooid = Voltooid;
    }

    /**
     * geven van vakken
     * @return 
     */
    public List<Vak> geefVakken()
    {
        return vakkenSpelbord;
    }

    /**
     * setten van het vakken van het spelbord
     * @param vakkenSpelbord 
     */
    public void setVakkenSpelbord(List<Vak> vakkenSpelbord)
    {
        this.vakkenSpelbord = vakkenSpelbord;
    }

    /**
     * opvragen van de lijst met kisten
     * @return 
     */
    public List<Kist> getKisten()
    {
        return kisten;
    }

    /**
     * setten van het kisten
     * @param kisten 
     */
    public void setKisten(List<Kist> kisten)
    {
        this.kisten = kisten;
    }

    /**
     * opvragen van het mannetje
     * @return 
     */
    public Mannetje getMan()
    {
        return man;
    }

    /**
     * set van het manntje
     * @param man 
     */
    public void setMan(Mannetje man)
    {
        this.man = man;
    }

    /**
     * geven van verplaatsingen
     * @return 
     */
    public int getAantalVerplaatsingen()
    {
        return aantalVerplaatsingen;
    }

    /**
     * setten van verplaatsingen
     * @param aantalVerplaatsingen 
     */
    public void setAantalVerplaatsingen(int aantalVerplaatsingen)
    {
        this.aantalVerplaatsingen = aantalVerplaatsingen;
    }

    /**
     * commando wordt ingegeven en zegt hoe en waar het mannetje moet naar verplaatsen
     * @param richting 
     */
    public void verplaatsHero(char richting)
    {
        /**
         * Kijken welk command we binnen gekregen hebben om ons mannetje te laten bewegen
         */

        switch (richting)
        {
            case 'l':
                verplaatsing(-1, -2);
                break;
            case 'r':
                verplaatsing(1, 2);
                break;
            case 'b':
                verplaatsing(-10, -20);
                break;
            case 'o':
                verplaatsing(10, 20);
                break;
        }
    }

    /**
     * verplaatsing doen van de kist
     * @param offset
     * @param volgendOffset 
     */
    private void verplaatsing(int offset, int volgendOffset)
    {
        /**
         * Zien of we kunnen verplaatsen
         */

        if (kanMannetjeVerplaatsen(geefVolgendVak(offset)))
        {
            aantalVerplaatsingen++;
            if (kanKistVerplaatsen(geefVolgendVak(offset), geefVolgendVak(volgendOffset)))
            {
                /**
                 * Kist en mannetje verplaatsen
                 */
                verplaatsKist(volgendOffset, offset);
                verplaats(offset);
            } else if (!(zitKistVast(geefVolgendVak(offset), geefVolgendVak(volgendOffset))))
            {
                /*juist mannetje verplaatsen*/
                verplaats(offset);
            }

        }
    }

    /**
     * geef volgend Vak in de lijst
     * @param offset
     * @return 
     */
    private Vak geefVolgendVak(int offset)
    {
        int positie = (man.getVak().getyCoordinaat() * 10 ) + man.getVak().getxCoordinaat();
        return vakkenSpelbord.get(positie + offset);
    }

    /**
     * verplaatsingen
     * @param offset 
     */
    private void verplaats(int offset)
    {
        /**
         * Als er een verplaatsing gebeurt. geven we het hero object door aan het volgend vak en zet we de hero van het vorig vak op null.
         */
        geefVolgendVak(offset).setHero(man);
        geefVolgendVak(0).setHero(null);
        man.setVak(geefVolgendVak(offset));       
    }

    /**
     * verplaatsen van kisten
     * @param vorigePositie
     * @param positie 
     */
    private void verplaatsKist(int vorigePositie, int positie)
    {
        /**
         * Als een kist kan verplaatsen, geven we het kist object door
         */
        //geefVolgendVak(vorigePositie).setKist(geefVolgendVak(positie).getKist());
        Kist kist = geefVolgendVak(positie).getKist();
        kist.setVak(geefVolgendVak(vorigePositie));
        geefVolgendVak(vorigePositie).setKist(kist);
        geefVolgendVak(positie).setKist(null);
    }

    /**
     * kijken of het mannetje kan verplaatsen
     * @param vak
     * @return 
     */
    private boolean kanMannetjeVerplaatsen(Vak vak)
    {
        /**
         * kijken of we niet botsen op een muur
         */
        return (vak.isBewandelbaar());
    }

    /**
     * kijken of de kist kan verplaatsen
     * @param vak
     * @param volgendVak
     * @return 
     */
    private boolean kanKistVerplaatsen(Vak vak, Vak volgendVak)
    {
        /**
         * Eerst kijken of het vak waar het mannetje naartoe gaat wel ene kist bevat, zoniet return we false
         */
        if (vak.bevatKist())
        {
            /**
             * als het vak een kist bevat gaan we kijken of het vak waar de kist naartoe gaat, dat er wel plaats is om te verplaatsen
             */
            return (volgendVak.isBewandelbaar()) && !volgendVak.bevatKist();
        }

        return false;
    }

    /**
     * Kijken of de kist vast zit
     * @param vak
     * @param volgendVak
     * @return 
     */
    private boolean zitKistVast(Vak vak, Vak volgendVak)
    {
        return vak.bevatKist() && (!volgendVak.isBewandelbaar() || volgendVak.bevatKist());
    }

    /**
     * kijken of het spelbord voltooid is
     * @return 
     */
    public boolean isSpelbordVoltooid()
    {
        int aantal = 0;
        int aantalDoelen = 0;

        for (Vak vak : vakkenSpelbord)
        {
            if (vak.isBewandelbaar())
            {
                /**
                 * Hier gaan we op zoek hoeveel doelen er zijn en hoveel doelen ook kisten bevatten
                 */
                VakWandel vakW = (VakWandel) vak;
                if (vakW.isIsDoel())
                {
                    aantalDoelen++;

                    if (vakW.bevatKist())
                    {
                        aantal++;
                    }
                }

            }
        }

        /**
         * Als de aantal doelen gelijk is aan de aantal doelen met een kist dan zetten we het spelbord op voltooid en returnen we true zodat ons spel kan afgesloten worden
         */
        if (aantal == aantalDoelen)
        {
            setVoltooid(true);
        }

        return isVoltooid();
    }

    /**
     * geeft een vak op een bepaalde positie
     * @param col
     * @param row
     * @return 
     */
    public Vak geefVakOpPositie(int col, int row)
    {
        int positie = (row*10) + col; 
        return vakkenSpelbord.get(positie);
    }
    
    
    /**
     * Volgende methodes enkel gebruiken bij het in laden van textfile in de Database
     */
    public void maakBord(String keuzeSpel) throws FileNotFoundException
    {
        /**
         * Dit deel zal elke lijn uit de txt-file achtereenvolgens in een String plaatsen, deze wordt later omgezet in een dubbele array.
         */
        Scanner input = new Scanner(new File("maps/easy2.txt"));//.useDelimiter(",\\s*");
        String woord = "";
        String spelbordElement = "";
        Character[][] vakken = new Character[10][10];

        String naamSpelbord = input.nextLine();

        while (input.hasNextLine())
        {
            String line = input.nextLine();
            Scanner word = new Scanner(line);
            while (word.hasNext())
            {
                spelbordElement += word.next();
            }
            word.close();
        }

        /**
         * Dit deel zal elk karakter van de string in een dubbele array plaatsen.
         */
        for (int i = 0; i < vakken.length; i++)
        {
            for (int j = 0; j < vakken[i].length; j++)
            {
                vakken[i][j] = spelbordElement.charAt((i * 10) + j);
            }
        }
        zetVakkenInDatabase(keuzeSpel, naamSpelbord, vakken);
    }
    
    /**
     * wijizig spelNaam
     * @param orgNaam
     * @param spelNaam 
     */
    public void wijzigSpelNaam(String orgNaam, String spelNaam)
    {
        vakMapper = new VakMapper();
        spelbordmapper = new SpelbordMapper();
        
        vakMapper.wijzigSpelNaam(orgNaam, spelNaam);
        spelbordmapper.wijzigSpelNaam(orgNaam, spelNaam);
    }
    
    /**
     * wijzig spelbordnaam
     * @param spelbordNaam 
     */
    public void wijzigSpelbordNaam(String spelbordNaam)
    {
        vakMapper = new VakMapper();
        KistMapper kistMap = new KistMapper();
        MannetjeMapper manMap = new MannetjeMapper();
        spelbordmapper = new SpelbordMapper();
        
        vakMapper.wijzigSpelbordNaam(getNaam(), spelbordNaam);
        kistMap.wijzigSpelbordNaam(getNaam(), spelbordNaam);
        manMap.wijzigSpelbordNaam(getNaam(), spelbordNaam);
        spelbordmapper.wijzigSpelbordNaam(getNaam(), spelbordNaam);
    }
    
    /**
     * een gewijzgd spelbord in de database plaatsen
     * @param spelnaam
     * @param spelbordNaam 
     */
    public void zetGewijzigdSpelbordInDatabase(String spelnaam, String spelbordNaam)
    {
        vakMapper = new VakMapper();
        spelbordmapper = new SpelbordMapper();
        KistMapper kistMap = new KistMapper();
        MannetjeMapper manMap = new MannetjeMapper();
        
        
        kistMap.deleteKisten(spelbordNaam);
        manMap.deleteMannetje(spelbordNaam);     
        vakMapper.deleteVakken(spelnaam, spelbordNaam);
        spelbordmapper.deleteSpelbord(spelnaam, spelbordNaam);
        
        
        
        zetSpelbordInDatabase(spelnaam, spelbordNaam);
    }
    
    /**
     * deleten van het spelbord
     * @param spelNaam 
     */
    public void deleteSpelbord(String spelNaam)
    {
        String spelbordNaam = getNaam();
        vakMapper = new VakMapper();
        spelbordmapper = new SpelbordMapper();
        KistMapper kistMap = new KistMapper();
        MannetjeMapper manMap = new MannetjeMapper();
        
        kistMap.deleteKisten(spelbordNaam);
        manMap.deleteMannetje(spelbordNaam);     
        vakMapper.deleteVakken(spelNaam, spelbordNaam);
        spelbordmapper.deleteSpelbord(spelNaam, spelbordNaam);
    }
    
    /**
     * Het spelbord wordt geplaatst in de database
     * @param spelNaam
     * @param naamSpelbord 
     */
    public void zetSpelbordInDatabase(String spelNaam,String naamSpelbord)
    {
        vakMapper = new VakMapper();
        spelbordmapper = new SpelbordMapper();
        KistMapper kistMap = new KistMapper();
        MannetjeMapper manMap = new MannetjeMapper();
        int x=0;
        int y=0;
        
        spelbordmapper.voegSpelbordToe(naamSpelbord, spelNaam);
        for(Vak vak: vakkenSpelbord)
        {
            x = vak.getxCoordinaat();
            y = vak.getyCoordinaat();
                      
            if(vak.isBewandelbaar()) 
            {            
                VakWandel vakwandel = (VakWandel)vak;
                    if(vakwandel.bevatKist())
                    {
                        vakMapper.voegVakToe(3, x + (10*y), spelNaam, naamSpelbord);
                        kistMap.voegKistToe(x + (10*y), naamSpelbord);
                    }
                    else if(vakwandel.bevatMannetje())
                    {
                        vakMapper.voegVakToe(6, x + (10*y), spelNaam, naamSpelbord);
                        manMap.voegMannetjeToe(x + (10*y), naamSpelbord);
                    }
                    else if(vakwandel.isIsDoel())
                    {
                        vakMapper.voegVakToe(4, x + (10*y), spelNaam, naamSpelbord);
                    }
                    else
                        vakMapper.voegVakToe(1, x + (10*y), spelNaam, naamSpelbord);                     
                                 
            }
            else if(!vak.isBewandelbaar())
            {
                vakMapper.voegVakToe(2, x + (10*y), spelNaam, naamSpelbord);
            }
        }
    }
    
    /**
     * Deze methode zal de spelbordarray overlopen en elk vakje in de db plaatsen. Hiervoor hebben we een id, x-en y-coordinaat, naam van het spel als de naam van het spelbord nodig.
     *
     * @param keuzeSpel
     */
    public void zetVakkenInDatabase(String keuzeSpel, String naamSpelbord, Character[][] vakken)
    {
        vakMapper = new VakMapper();
        KistMapper kistMap = new KistMapper();
        MannetjeMapper manMap = new MannetjeMapper();

        for (int i = 0; i < vakken.length; i++)
        {
            for (int j = 0; j < vakken[i].length; j++)
            {
                switch (vakken[i][j])
                {
                    case '_':
                        // de methode oproepen die een vakje in de db maakt met als parameters de ID, x-en y-coordinaat
                        vakMapper.voegVakToe(1, j + (10 * i), keuzeSpel, naamSpelbord);
                        break;
                    case '#':
                        vakMapper.voegVakToe(2, j + (10 * i), keuzeSpel, naamSpelbord);
                        break;
                    case '$':
                        vakMapper.voegVakToe(3, j + (10 * i), keuzeSpel, naamSpelbord);
                        kistMap.voegKistToe(j + (10 * i), naamSpelbord);
                        break;
                    case '.':
                        vakMapper.voegVakToe(4, j + (10 * i), keuzeSpel, naamSpelbord);
                        break;
                    case '*':
                        vakMapper.voegVakToe(5, j + (10 * i), keuzeSpel, naamSpelbord);
                        break;
                    case '@':
                        vakMapper.voegVakToe(6, j + (10 * i), keuzeSpel, naamSpelbord);
                        manMap.voegMannetjeToe(j + (10 * i), naamSpelbord);
                        break;
                }
            }
        }
    }

    /**
     * geeft de originele spelborden
     * @param spelnaam
     * @return 
     */
    public List<Spelbord> geefOrigineleSpelborden(String spelnaam)
    {
        spelbordmapper = new SpelbordMapper();
        return spelbordmapper.geefSpelborden(spelnaam);
    }
    
    /**
     * gaat kijken of het spelbord bestaat
     * @param naam
     * @return 
     */
    public boolean bestaatSpelbord(String naam)
    {
        List<Spelbord> spelborden;
        spelbordmapper = new SpelbordMapper();
        if(!naam.isEmpty())
            spelborden = spelbordmapper.geefAlleSpelborden();
        else    
            spelborden = spelbordmapper.geefSpelborden("custommap");
        for(Spelbord spelbord: spelborden)
        {
            if(spelbord.getNaam().equals(naam))
                return true;
        }
        return false;
    }

}
