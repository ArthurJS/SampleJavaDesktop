package domein;

import java.util.*;
import persistentie.SpelbordMapper;

/**
 *
 * @author Mario
 */
public class Spel {

    private List<Spelbord> lijstSpelborden;
    private Spelbord spelbordInGebruik;
    private final SpelbordMapper spelbordMapper;
    private String spelNaam;
    private Spelbord nieuwSpelbord;

    /**
     * Constructor die de spelnaam set in het overeenkomstige klasse attribuut
     * en een spelbordMapper object aanmaakt
     * {@link SpelbordMapper#SpelbordMapper()}. Aan de hand van het
     * spelbordMapper object wordt het klasse attribuut lijstSpelborden opgevult
     * door de methode {@link SpelbordMapper#geefSpelbord(java.lang.String)} Als
     * de lijstSpelborden Spelborden bevat worden deze gebruikt om de velden in
     * de Spelbord klasse op te vullen {@link Spelbord#voegVeldToe(java.util.List)
     * }
     * Het eerste spelbord uit deze lijst wordt ook aangewezen aan het klasse
     * attribuut spelbordInGebruik.
     *
     * @param spelNaam
     */
    public Spel(String spelNaam) {
        setSpelNaam(spelNaam);
        spelbordMapper = new SpelbordMapper();
        this.lijstSpelborden = spelbordMapper.geefSpelbord(this.spelNaam);
        if (!this.lijstSpelborden.isEmpty()) { //Nieuw spel bevat nog geen spelborden
            for (int i = 0; i < lijstSpelborden.size(); i++) {
                Spelbord spelbord = lijstSpelborden.get(i);
                spelbord.voegVeldToe(spelbordMapper.geefVelden(this.spelNaam, spelbord.getSpelbordNr()));
                //spelbord.voegInitieelSpelbordToeAanStack();
            }
            spelbordInGebruik = lijstSpelborden.get(0);
        }
    }

    /**
     * Deze methode geeft het klasse attribuut spelNaam terug.
     *
     * @return spelNaam
     */
    public String getSpelNaam() {
        return spelNaam;
    }

    /**
     * Deze methode stelt het klasse attribuut spelNaam in.
     *
     * @param spelNaam
     */
    public void setSpelNaam(String spelNaam) {
        this.spelNaam = spelNaam;
    }

    /**
     * Deze methode verwijderd eerst en vooral alle doelen (attributen) van het
     * spelbordInGebruik object {@link Spelbord#getVeldenMetDoel() }, voegt
     * velden toe aan het spelbordInGebruik object, cleart de undo-Stack {@link Spelbord#clearundoStack()
     * } en reset het attribuut aantalVerplaatsingen {@link Spelbord#setAantalVerplaatsingen(int)
     * }
     *
     * @return Spelbord in de vorm van char[][] {@link Spel#geefSpelbordInGebruik()
     * }
     */
    public char[][] geefSpelbordInGebruikInit() {
        spelbordInGebruik.getVeldenMetDoel().clear();
        spelbordInGebruik.voegVeldToe(spelbordMapper.geefVelden(this.spelNaam, spelbordInGebruik.getSpelbordNr()));
        spelbordInGebruik.setAantalVerplaatsingen(0);
        spelbordInGebruik.clearundoStack();
        return geefSpelbordInGebruik();
    }

    /**
     * Deze methode wordt gebruikt bij het weergeven van een reeds bestaand
     * spelbord
     *
     * @return spelbordInGebruik, deze worden opgevult adhv de relaties door de
     * methode {@link Veld#toChar()}.
     */
    public char[][] geefSpelbordInGebruik() {
        Veld sb[][] = spelbordInGebruik.getSpelbordMap();
        char spelbordReturn[][] = new char[10][10];
        int i = 0;
        for (Veld[] rijen : sb) {
            int j = 0;
            for (Veld kolommen : rijen) {
                if (kolommen == null) {
                    spelbordReturn[i][j] = '+';
                } else {
                    spelbordReturn[i][j] = kolommen.toChar();
                }
                j++;
            }
            i++;
        }
        return spelbordReturn;
    }

    /**
     * Deze methode wordt gebruikt bij het weergeven van een reeds bestaand
     * spelbord
     * @param spelbord
     * @return return char[][] van het mee gegeven spelbord, deze worden
     * opgevult adhv de relaties door de methode {@link Veld#toChar()}.
     */
    public char[][] geefSpelbord(Spelbord spelbord) {
        Veld sb[][] = spelbord.getSpelbordMap();
        char spelbordReturn[][] = new char[10][10];
        int i = 0;
        for (Veld[] rijen : sb) {
            int j = 0;
            for (Veld kolommen : rijen) {
                if (kolommen == null) {
                    spelbordReturn[i][j] = '+';
                } else {
                    spelbordReturn[i][j] = kolommen.toChar();
                }
                j++;
            }
            i++;
        }
        return spelbordReturn;
    }

    /**
     * Deze methode word gebruikt bij het bouwen van een nieuw spelbord
     *
     * @return niewSpelbord, deze worden opgevult adhv de relaties door de
     * methode {@link Veld#toChar()}.
     */
    public char[][] geefNieuwSpelbord() {
        Veld sb[][] = nieuwSpelbord.getSpelbordMap();
        char spelbordReturn[][] = new char[10][10];
        int i = 0;
        for (Veld[] rijen : sb) {
            int j = 0;
            for (Veld kolommen : rijen) {
                if (kolommen == null) {
                    spelbordReturn[i][j] = '+';
                } else {
                    spelbordReturn[i][j] = kolommen.toChar();
                }
                j++;
            }
            i++;
        }
        return spelbordReturn;
    }

    /**
     * Geeft het aantal voltooide spelborden terug adhv {@link Spelbord#checkIsVoltooid()
     * }
     *
     * @return aantalVoltooid
     */
    public int geefAantalVoltooideSpelborden() {
        int aantalVoltooid = 0;
        for (Spelbord sb : this.lijstSpelborden) {
            if (sb.checkIsVoltooid()) {
                aantalVoltooid += 1;
            }
        }
        return aantalVoltooid;
    }

    /**
     * Geeft het totaal aantal spelborden terug
     *
     * @return aantal spelborden (int) van {@link Spel#lijstSpelborden}
     */
    public int geefTotaalAantalSpelborden() {
        return this.lijstSpelborden.size();
    }

    /**
     * Geeft het aantal verplaatsingen terug adhv
     * {@link Spelbord#geefAantalVerplaatsingen())}
     *
     * @return aantal verplaatsingen van {@link Spel#spelbordInGebruik}
     */
    public int geefAantalVerplaatsingen() {
        return spelbordInGebruik.geefAantalVerplaatsingen();
    }

    /**
     * Verplaatst het mannetje adhv {@link Spelbord#verplaatsMannetje(java.lang.String)
     * }
     *
     * @param beweging de door de gebruiker ingevoerde beweging
     */
    public void verplaatsMannetje(String beweging) {
        spelbordInGebruik.verplaatsMannetje(beweging);
    }

    /**
     * Controlleert of de undo-Stack leeg is adhv
     * {@link Spelbord#IsStackEmpty() ()}
     *
     * @return true/false of deze leeg is.
     */
    public boolean IsStackEmpty() {
        return this.spelbordInGebruik.IsStackEmpty();
    }

    /**
     * Kijkt of het spelbord in gebruik voltooid is
     * {@link Spelbord#checkIsVoltooid()} en veranderd het huidig spel als het
     * voltooid is (behalve het laatste spel, anders Error)
     *
     * @return true/false of deze voltooid is
     */
    public boolean checkSpelbordInGebruikIsVoltooid() {
        boolean isVoltooid = spelbordInGebruik.checkIsVoltooid();
        if (isVoltooid == true) {
            int indexHuidigSpel = this.lijstSpelborden.indexOf(spelbordInGebruik);
            if (!(indexHuidigSpel == lijstSpelborden.size() - 1)) {
                this.spelbordInGebruik = this.lijstSpelborden.get(indexHuidigSpel + 1);
            }
        }
        return isVoltooid;
    }

    /**
     * Checkt of het {@link Spel#spelbordInGebruik} het laatste spelbord van de
     * {@link Spel#lijstSpelborden} is.
     *
     * @return true/false of deze het laatste spelbord is van een spel
     */
    public boolean isLaatsteSpelbordVanHuidigSpel() {
        return this.lijstSpelborden.indexOf(spelbordInGebruik) == lijstSpelborden.size() - 1;
    }

    /**
     * Construeert een nieuw Spelbord adhv een spelbord nummer, dit spelbord
     * wordt toegevoegd aan het klasse attribuut lijstSpelborden en stelt het
     * klaase attribuut nieuwSpelbord in
     *
     * @param spelbordNr
     */
    public void maakSpelbord(int spelbordNr) {
        Spelbord nieuwSpelbordLocal = new Spelbord(spelbordNr);
        this.lijstSpelborden.add(nieuwSpelbordLocal);
        this.nieuwSpelbord = nieuwSpelbordLocal;
    }

    /**
     * plaatst een item adhv {@link Spelbord#plaatsItem(char, int, int) op een nieuw spelbord
     * }
     *
     * @param item symbool voor het gewenste item
     * @param XCOORD de X-coord, 0-based
     * @param YCOORD de Y-coord, 0-based
     */
    public void plaatsItem(char item, int XCOORD, int YCOORD) {
        this.nieuwSpelbord.plaatsItem(item, XCOORD, YCOORD);
    }

    /**
     * plaatst een item adhv {@link Spelbord#plaatsItem(char, int, int)} op een
     * reeds bestaand spelbord
     *
     * @param item symbool voor het gewenste item
     * @param XCOORD de X-coord, 0-based
     * @param YCOORD de Y-coord, 0-based
     */
    public void plaatsItemWijziging(char item, int XCOORD, int YCOORD) {
        this.spelbordInGebruik.plaatsItem(item, XCOORD, YCOORD);
    }

    /**
     * Controleeert of het nieuw aangemaakte spelbord voldoet aan de voorwaarden{@link Spelbord#IsCorrectControleSpelbord()
     * }
     *
     * @return true/false of het voldoet aan de voorwaarden
     */
    public boolean IsCorrectControleNieuwSpelbord() {
        return this.nieuwSpelbord.IsCorrectControleSpelbord();
    }

    /**
     * Stelt alle spelborden, in de lijstSpelborden, in adhv het spelbordMapper
     * object {@link SpelbordMapper#registreerNieuwSpelbord(java.lang.String, domein.Spelbord)
     * }
     */
    public void registreerNieuwSpelbord() {
        for (int aantalspelborden = 0; aantalspelborden < this.lijstSpelborden.size(); aantalspelborden++) {
            this.spelbordMapper.registreerNieuwSpelbord(this.spelNaam, this.lijstSpelborden.get(aantalspelborden));
        }
    }

    /**
     * Verwijderd het aangemaakte spelbord, en maakt een nieuw Spelbord aan. {@link Spel#maakSpelbord(int)
     * }
     *
     * @param spelbordNr het te verwijderen spelbord, voorgesteld door zijn
     * nummer
     */
    public void resetBord(int spelbordNr) {
        this.lijstSpelborden.remove(lijstSpelborden.size() - 1);
        maakSpelbord(spelbordNr);
    }

    /**
     * Geeft het aantal doelen terug {@link Spelbord#geefAantalDoelen() } van
     * een nieuw spel
     *
     * @return aantal doelen (int)
     */
    public int geefAantalDoelen() {
        return this.nieuwSpelbord.geefAantalDoelen();
    }

    /**
     * Geeft het aantal doelen terug {@link Spelbord#geefAantalDoelen() } van
     * een reeds bestaand spel
     *
     * @return aantal doelen (int)
     */
    public int geefAantalDoelenConfig() {
        return this.spelbordInGebruik.geefAantalDoelen();
    }

    /**
     * Geeft het aantal kisten terug {@link Spelbord#geefAantalKisten() } van
     * een nieuw spel
     *
     * @return aantal kisten (int)
     */
    public int geefAantalKisten() {
        return this.nieuwSpelbord.geefAantalKisten();
    }

    /**
     * Geeft het aantal kisten terug {@link Spelbord#geefAantalKisten() } van
     * een reeds bestaand spel
     *
     * @return aantal kisten (int)
     */
    public int geefAantalKistenConfig() {
        return this.spelbordInGebruik.geefAantalKisten();
    }

    /**
     * Geeft een lijst terug van char[][] (alle spelborden) van een Spel. Deze
     * lijst wordt gevult door {@link Spel#geefSpelbord(domein.Spelbord) }
     *
     * @return lijst met spelborden van char[][]
     */
    public ArrayList<char[][]> geefLijstSpelbordenGeselecteerdSpel() {
        ArrayList<char[][]> spelborden = new ArrayList<>();
        for (Spelbord sb : this.lijstSpelborden) {
            spelborden.add(geefSpelbord(sb));
        }

        return spelborden;
    }

    /**
     * Geeft het huidig spelbord in gebruik terug in char[][]
     *
     * @param nr nummer van het spelbord
     * @return spelbordInGebruik in char[][[] adhv {@link Spel#geefSpelbordInGebruik()
     * }
     */
    public char[][] geefGekozenSpelbord(int nr) {

        Spelbord spelbord = this.lijstSpelborden.get(nr - 1);
        this.spelbordInGebruik = spelbord;

        return geefSpelbordInGebruik();
    }

    /**
     * verwijderd een spelbord adhv het spelbordMapper object {@link SpelbordMapper#verwijderSpelbord(domein.Spel, domein.Spelbord)
     * }
     */
    public void verwijderSpelbord() {
        this.spelbordMapper.verwijderSpelbord(this, this.spelbordInGebruik);
    }

    /**
     * Schrijft de spelnaam en spelbordInGebruik weg naar de databank adhv
     * spelbordMapper {@link SpelbordMapper#bevestigWijzigingenWegschrijven(java.lang.String, domein.Spelbord)
     * }
     */
    public void bevestigWijzigingenWegschrijven() {
        this.spelbordMapper.bevestigWijzigingenWegschrijven(this.spelNaam, this.spelbordInGebruik);;
    }

    /**
     * Stelt het spelbordInGebruikIn adhv het spelbord nr, deze is 1 groter dan
     * de positie de positie binnen de lijstSpelborden {@link SpelbordMapper#bevestigWijzigingenWegschrijven(java.lang.String, domein.Spelbord)
     * }
     *
     * @param nr nummer van het spelbord
     */
    public void setSpelbordInGebruik(int nr) {
        this.spelbordInGebruik = this.lijstSpelborden.get(nr - 1);
    }

    /**
     * Kijkt of de veranderingen van het reeds bestaande spelbord voldoen aan de
     * voorwaarden
     *
     * @return true/false of spelbord voldoet aan de voorwaarden
     */
    public boolean IsCorrectControleGewijzigdSpelbord() {
        return this.spelbordInGebruik.IsCorrectControleSpelbord();
    }
}
