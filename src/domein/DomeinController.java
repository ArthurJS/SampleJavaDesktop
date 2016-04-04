package domein;

import static LanguageResources.Resource.labels;
import exceptions.GebruikersNaamException;
import exceptions.WachtwoordException;
import exceptions.WachtwoordHerhaalException;
import java.util.*;

/**
 *
 * @author Thibault Fouquaert
 */
public class DomeinController {

    private Speler deSpeler;
    private final SpelerRepository spelerRep;
    private Spel spel;
    private Spel nieuwSpel;
    private final SpelRepository spelRep;

    /**
     * Constructor die een nieuwe SpelerRepository en SpelRepository aanmaakt en
     * toekent aan de klasse attributen.<p/>
     * {@link SpelerRepository#SpelerRepository()}
     * {@link SpelRepository#SpelRepository()}
     */
    public DomeinController() {
        spelerRep = new SpelerRepository();
        spelRep = new SpelRepository();
    }

    /**
     * Maakt een nieuw {@link Speler} object voor registratie. Indien Wachtwoord
     * en wachtwoordBevestiging gelijk zijn: Set de speler met {@link #setSpeler(domein.Speler)
     * } in het klasse attribuut van de DC en laat het toevoegen aan de Database
     * met {@link SpelerRepository#voegToe(domein.Speler) }. Anders throwed deze
     * methode een {@link exceptions.WachtwoordException}.
     *
     * @param voornaam Voornaam van de speler, mag null zijn.
     * @param achternaam Achternaam van de speler, mag null zijn.
     * @param gebruikersnaam Gebruikersnaam van de speler. Verplicht en uniek.
     * @param wachtwoord Wachtwoord van de speler, wordt gecontroleerd met {@link Speler#setWachtwoord(java.lang.String)
     * } en {@link Speler#validate(java.lang.String) }.
     * @param wachtwoordBevestiging Moet gelijk zijn aan het wachtwoord voor
     * verder te gaan.
     */
    public void registreer(String voornaam, String achternaam, String gebruikersnaam, String wachtwoord, String wachtwoordBevestiging) {
        Speler nieuweSpeler = new Speler(voornaam, achternaam, gebruikersnaam, wachtwoord);
        if (!wachtwoord.equals(wachtwoordBevestiging)) {//exceptie van wachtwoord moeten getrowd worden voor de de herhalingsfout.
            throw new WachtwoordHerhaalException(labels.getString("foutieveBevestiging"));
        }
        spelerRep.voegToe(nieuweSpeler);
        setSpeler(nieuweSpeler);
    }

    /**
     * Meld de speler aan in de applicatie door de speler te setten in de DC met {@link #setSpeler(domein.Speler)
     * }
     * throwed een {@link exceptions.WachtwoordException} als het wachtwoord
     * niet overeenkomt met de gebruikersnaam.
     *
     * @param gebruikersnaam opgegeven gebruikersnaam
     * @param wachtwoord opgegeven wachtwoord
     * @see SpelerRepository#geefSpeler(java.lang.String, java.lang.String)
     */
    public void meldtAan(String gebruikersnaam, String wachtwoord) {
        Speler gevondenSpeler = spelerRep.geefSpeler(gebruikersnaam, wachtwoord);
        if (gevondenSpeler != null) {
            setSpeler(gevondenSpeler);
        } else {
            throw new WachtwoordException(labels.getString("incorrectWachtwoord"));
        }
    }

    /**
     * Throwed een nieuwe {@link GebruikersNaamException} als er geen speler is
     * aangemeld/geset.
     *
     * @return De gebruikersnaam van de aangemelde speler.
     */
    public String geefGebruikersnaam() {
        if (deSpeler != null) {
            return deSpeler.getGebruikersnaam();
        } else {
            throw new GebruikersNaamException(labels.getString("gegevensNietCorrect"));
        }
    }

    /**
     * Throwed een nieuwe {@link GebruikersNaamException} als er geen speler is
     * aangemeld/geset.
     *
     * @return true als de speler adminrechten heeft. False indien dit niet zo
     * is.
     */
    public boolean geefAdminrechten() {
        if (deSpeler != null) {
            return deSpeler.getIsAdmin();
        } else {
            throw new GebruikersNaamException(labels.getString("gegevensNietCorrect"));
        }
    }

    /**
     *
     * @param deSpeler
     */
    public void setSpeler(Speler deSpeler) {
        this.deSpeler = deSpeler;
    }

    /**
     * Throwed een new NullpointerException als er geen namen kunnen worden
     * opgehaald uit de database.
     *
     * @return een List met alle namen v.d. spellen die in de Database zitten.
     * @see SpelRepository#geefSpelnamen()
     */
    public List<String> geefNaamSpellen() {
        List<String> spelNamen = spelRep.geefSpelnamen();
        if (spelNamen != null) {
            return spelNamen;
        } else {
            throw new NullPointerException(labels.getString("lijstNietGevonden"));
        }
    }

    /**
     *
     * @return dubbele array van chars, dat het spelbord voorstelt. Dit spelbord
     * is in de staat hoe het in de database zit. wordt gebruikt voor het
     * RESETTEN van het spelbord.
     * @see Spel#geefSpelbordInGebruikInit()
     */
    public char[][] geefSpelbordInGebruikInit() {
        return spel.geefSpelbordInGebruikInit();
    }

    /**
     * laat een spel ophalen uit de batabase door {@link SpelRepository#geefGeslecteerdSpel(java.lang.String)
     * }, en set het in de domeincontroller indien er een spel gevonden is met
     * {@link #setSpel(domein.Spel)}. Kan een NullPointyerException throwen als
     * er geen spel gevonden is met de opgegeven spelnaam.
     *
     * @param spelNaam de naam van het spel in de Database, hoofdletter
     * ongevoelig.
     */
    public void selecteerSpel(String spelNaam) {
        Spel geselecteerdSpel = spelRep.geefGeslecteerdSpel(spelNaam); //geselecteerdSpel = huidig spel
        if (geselecteerdSpel != null) {
            setSpel(geselecteerdSpel);
        } else {
            throw new NullPointerException(labels.getString("foutieveSpelnaam"));
        }
    }

    /**
     * Set het spel in de DC.
     *
     * @param geselecteerdSpel het geseleceteerde spel opgehaald uit de
     * domeincController
     * @see #selecteerSpel(java.lang.String)
     */
    public void setSpel(Spel geselecteerdSpel) {
        this.spel = geselecteerdSpel;
    }

    /**
     *
     * @return Het spelbord, in een 2d array van chars, waarmee momenteel
     * gespeeld/gewijzig word van het huidig geselecteerde spel. Elke char stelt
     * een symbool voor op de respectievelijke x-y plaats.
     */
    public char[][] geefSpelbordInGebruik() {
        return spel.geefSpelbordInGebruik();
    }

    /**
     *
     * @return Het aantal voltooide spelborden waarmee momenteel
     * gespeeld/gewijzig word van het huidig geselecteerde spel.
     */
    public int geefAantalVoltooideSpelborden() {
        return spel.geefAantalVoltooideSpelborden();
    }

    /**
     *
     * @return Het aantal spelborden van het geselecteerde spel.
     */
    public int geefTotaalAantalSpelborden() {
        return spel.geefTotaalAantalSpelborden();
    }

    /**
     *
     * @param spelnaam Naam die in de database voorkomt.
     * @return Het aantal spelborden van het spel met als spelnaam de parameter.
     * Dit is om een overzicht te kunnen geven hoeveel spelborden elk spel heeft
     * indien er nog geen spel geselecteerd is.
     */
    public int geefTotaalAantalSpelbordenVanInGuiGekozenSpel(String spelnaam) {
        return spelRep.geefTotaalAantalSpelbordenVanInGuiGekozenSpel(spelnaam);
    }

    /**
     *
     * @return het aantal verplaatsingne van het geseleceteerde spel.
     */
    public int geefAantalVerplaatsingen() {
        return spel.geefAantalVerplaatsingen();

    }

    /**
     * Laat het mannetje een beweging uitvoeren op het spelbordInGebruik van Het
     * Geselecteerde spel. Dit veranderd attribuut waarden van veld, kist,
     * mannetje indien er een geldige zet wordt gedaan.
     *
     * @param beweging Letter "z" (boven), "q"(links), "s"(onder), "d"(rechts),
     * "u" (undo). Case insensitive.
     * @see Spel#verplaatsMannetje(java.lang.String)
     */
    public void verplaatsMannetje(String beweging) {
        spel.verplaatsMannetje(beweging);
    }

    /**
     *
     * @return true indien het spelbordInGebruik voltooid is. d.w.z. dat alle
     * doelen op alle kisten staan. false indien dit niet zo is.
     * @see Spel#checkSpelbordInGebruikIsVoltooid()
     */
    public boolean checkSpelbordInGebruikIsVoltooid() {
        return spel.checkSpelbordInGebruikIsVoltooid();
    }

    /**
     *
     * @return true indien het spelbordInGebruik het laatste spelbord in de
     * lijst van spelborden is. false indien dit niet zo is.
     * @see Spel#isLaatsteSpelbordVanHuidigSpel()
     * @see Spel#lijstSpelborden
     */
    public boolean isLaatsteSpelbordVanHuidigSpel() {
        return spel.isLaatsteSpelbordVanHuidigSpel();
    }

    /**
     * Maakt een nieuw spel aan met een unieke spelnaam en set deze in het
     * {@link #nieuwSpel} van de DC.
     *
     * @param spelnaam Naam dat nog niet voorkomt in de database.
     */
    public void maakSpel(String spelnaam) {
        Spel nieuwSpelLocal = new Spel(spelnaam);
        this.nieuwSpel = nieuwSpelLocal;
    }

    /**
     * Maakt een nieuw spelbord aan van het {@link #nieuwSpel}. Spelbordnummer
     * volgen elkaar op.
     *
     * @param spelbordNr Nummer van 1-
     *
     * @see Spel#maakSpelbord(int)
     */
    public void maakSpelbord(int spelbordNr) {
        this.nieuwSpel.maakSpelbord(spelbordNr);
    }

    /**
     * Wordt gebruikt bij CREATIE van een spel. Plaats een item op de
     * corresponderende x-y coord van het {@link Spel#nieuwSpelbord}, dit
     * spelbord zit in {@link #nieuwSpel}.
     *
     * @param item # = muur o = doel (kleine o) K = Kist . = leeg
     * @ = Speler + = achtergrond. A.d.h.v. deze symbolen wroden de attrubiten u
     * {@link Veld}, {@link Mannetje} en {@link Kist} correct ingesteld.
     *
     * @param XCOORD 0-based. ligt tussen 0-9
     * @param YCOORD 0-based. ligt tussen 0-9
     * @see Spel#plaatsItem(char, int, int)
     */
    public void plaatsItem(char item, int XCOORD, int YCOORD) {
        this.nieuwSpel.plaatsItem(item, XCOORD, YCOORD);
    }

    /**
     * Wordt gebruikt bij WIJZIGING van een bestaand spel. Plaats een item op de
     * corresponderende x-y coord van het {@link Spel#spelbordInGebruik}, dit
     * spelbord zit in {@link #spel}.
     *
     * @param item # = muur o = doel (kleine o) K = Kist . = leeg
     * @ = Speler + = achtergrond. A.d.h.v. deze symbolen wroden de attrubiten u
     * {@link Veld}, {@link Mannetje} en {@link Kist} correct ingesteld.
     *
     * @param XCOORD 0-based. ligt tussen 0-9
     * @param YCOORD 0-based. ligt tussen 0-9
     * @see Spel#plaatsItemWijziging(char, int, int)
     */
    public void plaatsItemWijziging(char item, int XCOORD, int YCOORD) {
        this.spel.plaatsItemWijziging(item, XCOORD, YCOORD);
    }

    /**
     * Reset het bord bij creatie van een nieuw spelbord.
     *
     * @param spelbordNr de nummer van het spelbord dat momenteel gecreeerd
     * word.
     * @see Spel#resetBord(int)
     */
    public void resetBord(int spelbordNr) {
        this.nieuwSpel.resetBord(spelbordNr);
    }

    /**
     *
     * @return true indien de {@link Spelbord#undoStack} in SpelbordInGebruik
     * van het geselecteerde spel leeg is. false indien het niet leeg is.
     * @see Spel#IsStackEmpty()
     */
    public boolean IsStackEmpty() {
        return this.spel.IsStackEmpty();
    }

    /**
     * Roept {@link Spel#IsCorrectControleNieuwSpelbord() } aan. Controleert of
     * het {@link Spel#nieuwSpelbord} van het voldoet aan de voorwaarden. De
     * voorwaarden zijn: Er moet een volledig gesloten muur zijn. Er mogen geen
     * velden(.,@,K,o,*) grenzen aan achtergrond opvullig, er moet een muur
     * tussen zitten. Het aantal mannetjes = 1. Het aantal doelen is minsten 1
     * en het aantal kisten = het aantal doelen.
     *
     * @return true indien aan de voorwaarden voldaan word, false indien niet.
     */
    public boolean IsCorrectControleNieuwSpelbord() {
        return this.nieuwSpel.IsCorrectControleNieuwSpelbord();
    }
    
    /**
     * Roept {@link Spel#IsCorrectControleGewijzigdSpelbord()} aan. Controleert of
     * het {@link Spel#spelbordInGebruik} van het voldoet aan de voorwaarden. De
     * voorwaarden zijn: Er moet een volledig gesloten muur zijn. Er mogen geen
     * velden(.,@,K,o,*) grenzen aan achtergrond opvullig, er moet een muur
     * tussen zitten. Het aantal mannetjes = 1. Het aantal doelen is minsten 1
     * en het aantal kisten = het aantal doelen.
     *
     * @return true indien aan de voorwaarden voldaan word, false indien niet.
     */
    public boolean IsCorrectControleGewijzigdSpelbord(){
        return this.spel.IsCorrectControleGewijzigdSpelbord();
    }

    /**
     * Schrijft een nieuw spel weg door aanroep van
     * {@link SpelRepository#registreerNieuwSpel(domein.Spel)} en schrijft
     * alsook de spelborden van dit nieuw spel weg door de aanroepn van {@link Spel#registreerNieuwSpelbord()
     * }
     */
    public void registreerNieuwSpel() {
        spelRep.registreerNieuwSpel(this.nieuwSpel);
        this.nieuwSpel.registreerNieuwSpelbord();
    }

    /**
     * aanroep van {@link Spel#geefNieuwSpelbord() }
     *
     * @return Het spelbord, in een 2d array van chars, van een nieuw gecreerd
     * spel waarvan het spelbord gemaakt wordt. Elke char stelt een symbool voor
     * op de respectievelijke x-y plaats.
     */
    public char[][] geefNieuwSpelbord() {
        return nieuwSpel.geefNieuwSpelbord();
    }

    /**
     * roept {@link Spel#geefAantalDoelen() } aan.
     *
     * @return het aantal doelen van het {@link Spel#nieuwSpelbord} in het
     * {@link #nieuwSpel} van de DC.
     */
    public int geefAantalDoelen() {
        return nieuwSpel.geefAantalDoelen();
    }
    
    /**
     * roept {@link Spel#geefAantalDoelenConfig() } aan.
     *
     * @return het aantal doelen van het {@link Spel#spelbordInGebruik} in het bestaande
     * {@link #spel} van de DC.
     */
    public int geefAantalDoelenConfig() {
        return spel.geefAantalDoelenConfig();
    }

    /**
     * roept {@link Spel#geefAantalKisten() } aan.
     *
     * @return het aantal kisten van het {@link Spel#nieuwSpelbord} in het
     * {@link #nieuwSpel} van de DC.
     */
    public int geefAantalKisten() {
        return nieuwSpel.geefAantalKisten();
    }
    
    /**
     * roept {@link Spel#geefAantalKistenConfig() } aan.
     *
     * @return het aantal kisten van het {@link Spel#spelbordInGebruik} in het
     * {@link #spel} van de DC.
     */
    public int geefAantalKistenConfig() {
        return spel.geefAantalKistenConfig();
    }

    /**
     * Roept {@link Spel#getSpelNaam() } aan.
     *
     * @return De naam van het {@link #nieuwSpel} van de DC.
     */
    public String geefNiewSpelSpelnaam() {
        return nieuwSpel.getSpelNaam();
    }

    /**
     * Roept {@link Spel#geefLijstSpelbordenGeselecteerdSpel() } aan.
     *
     * @return Alle spelborden uit {@link Spel#lijstSpelborden}, in een 2d array
     * van chars. Elke char stelt een symbool voor op de respectievelijke x-y
     * plaats.
     */
    public ArrayList<char[][]> geefLijstSpelbordenGeselecteerdSpel() {
        return spel.geefLijstSpelbordenGeselecteerdSpel();
    }

    /**
     * Roept roept {@link Spel#geefGekozenSpelbord(int)() } aan.
     *
     * @param nr Nummer van het spelbord in het geselecteerde spel.
     * @return Het spelbord met opgegeven nummer, in een 2d array van chars,
     * vanuit {@link Spel#lijstSpelborden} van het geselcteerde spel. Elke char
     * stelt een symbool voor op de respectievelijke x-y plaats.
     */
    public char[][] geefGekozenSpelbord(int nr) {
        return spel.geefGekozenSpelbord(nr);
    }

    /**
     * roept {@link Spel#verwijderSpelbord() } aan. Verwijderd het spelbord
     * opbject uit de {@link Spel#lijstSpelborden} van het geselecteerde spel.
     */
    public void verwijderSpelbord() {
        spel.verwijderSpelbord();
    }

    /**
     * Roept {@link Spel#bevestigWijzigingenWegschrijven() } aan. Schrijft de
     * wijzigingen van alle spelborden in het geselecteerde spel weg naar de
     * Database.
     */
    public void bevestigWijzigingenWegschrijven() {
        this.spel.bevestigWijzigingenWegschrijven();
    }

    /**
     * Roept {@link Spel#setSpelbordInGebruik(int) } aan. Set het spelbord in
     * gebruik naar een spelbord met opgegeven nr. uit
     * {@link Spel#lijstSpelborden}. Standaard (gebeurt in {@link Spel#Spel(java.lang.String)
     * }) staat {@link Spel#spelbordInGebruik} ingesteld op het allereerste
     * spelbord uit {@link Spel#lijstSpelborden}.
     *
     * @param spelbordNummer Het nummer van het spelbord dat je wil setten.
     */
    public void setSpelSpelbordInGebruik(int spelbordNummer) {
        spel.setSpelbordInGebruik(spelbordNummer);
    }
    
    /**
     * Roept {@link Spel#getSpelNaam() } aan.
     * @return De naam van het geselecteerde spel terug.
     */
    public String geefSpelNaam(){
        return spel.getSpelNaam();
    }
    }
