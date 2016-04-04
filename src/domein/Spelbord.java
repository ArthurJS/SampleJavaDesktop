package domein;

import static LanguageResources.Resource.labels;
import exceptions.NieuwSpelbordException;
import java.util.*;

/**
 *
 * @author Thibault Fouquaert
 */
public class Spelbord {

    //private Collection<Veld> velden;
    private ArrayList<Veld> VeldenMetDoel;
    private Mannetje mannetje;//eigenlijk final. gaat niet in code.
    private int aantalVerplaatsingen;
    private Veld[][] spelbordMap;
    private int spelbordNr;
    private Stack<Veld[][]> undoStack;

    // # = muur
    // o = doel (kleine o)
    // K = Kist
    // . = leeg
    // @ = Speler
    // + = achtergrond
    /**
     * Constructor van het spelbord waarbij het aantal verplaatsingen
     * automatisch op 0 wordt gezet.
     *
     * @param spelbordnr nr van het spelbord in de databse, 1-based.
     */
    public Spelbord(int spelbordnr) {
        this(spelbordnr, 0);
    }

    /**
     * Constructor van spelbord, maakt de spelbordmap, lijst van velden met doel
     * en undostack aan.
     *
     * @param spelbordnr nr van het spelbord in de database, 1 based.
     * @param aantalVerplaatsingen aantal verplaatsingen. standaard 0.
     */
    public Spelbord(int spelbordnr, int aantalVerplaatsingen) {
        setSpelbordNr(spelbordnr);
        setAantalVerplaatsingen(aantalVerplaatsingen);
        this.spelbordMap = new Veld[10][10];
        this.VeldenMetDoel = new ArrayList<>();
        this.undoStack = new Stack<>();
    }

    /**
     *
     * @return {@link Spelbord#spelbordMap}
     */
    public Veld[][] getSpelbordMap() {
        return spelbordMap;
    }

    /**
     *
     * @return {@link Spelbord#aantalVerplaatsingen}
     */
    public int geefAantalVerplaatsingen() {
        return this.aantalVerplaatsingen;
    }

    /**
     *
     * @return {@link Spelbord#spelbordNr}
     */
    public int getSpelbordNr() {
        return spelbordNr;
    }

    /**
     * set het spelbordnr, 1-based.
     *
     * @param spelbordNr
     */
    public void setSpelbordNr(int spelbordNr) {
        this.spelbordNr = spelbordNr;
    }

    /**
     * set het aantal verplaatsingen.
     *
     * @param aantalVerplaatsingen
     */
    public void setAantalVerplaatsingen(int aantalVerplaatsingen) {
        this.aantalVerplaatsingen = aantalVerplaatsingen;
    }

    /**
     *
     * @return {@link Spelbord#mannetje}, mag normaal gezien niet null zijn.
     */
    public Mannetje getMannetje() {
        return mannetje;
    }

    /**
     * Leegt de stack waarop de undo's staan.
     */
    public void clearundoStack() {
        this.undoStack.clear();
    }

    /**
     * Checkt of de {@link Spelbord#undoStack} leeg is of niet.
     *
     * @return true als de stack leeg is.
     */
    public boolean IsStackEmpty() {
        return this.undoStack.isEmpty();
    }

    /**
     *
     * @return een lijst met velden waarbij het attr. {@link Veld#heeftDoel}
     * true is.
     */
    public ArrayList<Veld> getVeldenMetDoel() {
        return VeldenMetDoel;
    }

    /**
     * set de lijst van velden met een doel.
     *
     * @param VeldenMetDoel lijst van velden waarbij het attr.
     * {@link Veld#heeftDoel} true is.
     */
    public void setVeldenMetDoel(ArrayList<Veld> VeldenMetDoel) {
        this.VeldenMetDoel = VeldenMetDoel;
    }

    /**
     * Voegt alle velden toe van de meegegeven list, aan de
     * {@link Spelbord#spelbordMap} op basis van de X/Y coordinaten van het veld
     * object. Als het veld een {@link Mannetje} bevat in
     * {@link Veld#hetMannetje}, wordt dit object geset in
     * {@link Spelbord#mannetje}. Als het {@link Veld#heeftDoel} true is, wordt
     * het veld toegevoegd aan de {@link Spelbord#VeldenMetDoel}
     *
     * @param velden een List van {@link Veld} objecten.
     */
    public void voegVeldToe(List<Veld> velden) {
        for (Veld veld : velden) {
            int x = veld.getX();
            int y = veld.getY();
            this.spelbordMap[y][x] = veld;
            if (veld.getHetMannetje() != null) {
                this.mannetje = veld.getHetMannetje();
            }
            if (veld.getHeeftDoel()) {
                this.VeldenMetDoel.add(veld);
            }
        }
    }

    /**
     * Verplaats het mannetje en (eventueel) een kist als een geldige zet gedaan
     * word. geldige zet = Als er in de richting dat je het mannetje wilt
     * verplaatsen(boven, onder, links, rechts) geen muur staat, of geen 2
     * kisten achter elkaar in de richting van je verplaatsing. Als een kist
     * verplaatst wordt, zal het {@link Veld#kist} van het originele veld
     * toegekend worden aan het {@link Veld#kist} van het veld in de richting
     * waarin je je verplaatst. Als er een geldige zet word gezet zal het
     * {@link Veld#hetMannetje} worden toegekend aan het nieuwe
     * {@link Veld#hetMannetje} van de richting waarin je je verplaats. Ook het
     * {@link Spelbord#mannetje} verwijst dan nog steeds naar het mannetje op
     * het ander veld. Bij een geldige zet word er ook een copy (gebruikts {@link Spelbord#deepCopyVeld(domein.Veld[][])
     * }) van het spelbordmap op de stack geplaatst. Als de beweging u is, word
     * de {@link Spelbord#spelbordMap} ingesteld naar de copy van de
     * {@link Spelbord#spelbordMap} bij de vorige verplaatsing.
     *
     * @param beweging richting waarin je je wilt verplaatsen. Z, s, q , d. u
     * voor een undo.
     */
    public void verplaatsMannetje(String beweging) {
        Veld plaatsMannetje = mannetje.getVeld();
        int x = plaatsMannetje.getX();
        int y = plaatsMannetje.getY();
        boolean succesMovement = false;

        if (!beweging.equalsIgnoreCase("u")) {
            this.undoStack.push(deepCopyVeld(spelbordMap));
        }
        if (beweging.equalsIgnoreCase("q")) {
            //eerst checken of volgende plaats geen muur is.
            if (spelbordMap[y][x - 1].getIsMuur() == false) {
                //dan checken of het een kist is. zonee, enkel mannetje verplaatsen en niets van kisten.
                if (spelbordMap[y][x - 1].getKist() != null) {
                    if (spelbordMap[y][x - 2].getIsMuur() == false && spelbordMap[y][x - 2].getKist() == null) {
                        //hier de kist van 1 vak naast je naar 2 vakken naast je opschuiven
                        Kist teVerschuivenKist = spelbordMap[y][x - 1].getKist();
                        spelbordMap[y][x - 1].setKist(null);
                        spelbordMap[y][x - 2].setKist(teVerschuivenKist);
                    } else {
                        this.undoStack.pop();
                        return;
                    }
                }  //het mannetje niet verschuiven als je de kist naast je ook niet kan verschuiven
                //hier het mannetje verschuiven naar het veld naast je.
                this.mannetje.setVeld(spelbordMap[y][x - 1]);
                spelbordMap[y][x].setHetMannetje(null);
                spelbordMap[y][x - 1].setHetMannetje(mannetje);
                aantalVerplaatsingen++;
                succesMovement = true;
            }
            if (succesMovement == false) {
                this.undoStack.pop();
            }
            return; //als je een if van een beweging binnen 'kan', mag je na die code direct stoppen want de andere ifs zijn het dan sowieso niet meer.
        }

        if (beweging.equalsIgnoreCase("d")) {
            if (spelbordMap[y][x + 1].getIsMuur() == false) {
                if (spelbordMap[y][x + 1].getKist() != null) {
                    if (spelbordMap[y][x + 2].getIsMuur() == false && spelbordMap[y][x + 2].getKist() == null) {
                        Kist teVerschuivenKist = spelbordMap[y][x + 1].getKist();
                        spelbordMap[y][x + 1].setKist(null);
                        spelbordMap[y][x + 2].setKist(teVerschuivenKist);
                    } else {
                        this.undoStack.pop();
                        return;
                    }
                }
                this.mannetje.setVeld(spelbordMap[y][x + 1]);
                spelbordMap[y][x + 1].setHetMannetje(mannetje);
                spelbordMap[y][x].setHetMannetje(null);
                aantalVerplaatsingen++;
                succesMovement = true;
            }
            if (succesMovement == false) {
                this.undoStack.pop();
            }
            return;
        }

        if (beweging.equalsIgnoreCase("s")) {
            if (spelbordMap[y + 1][x].getIsMuur() == false) {
                if (spelbordMap[y + 1][x].getKist() != null) {
                    if (spelbordMap[y + 2][x].getIsMuur() == false && spelbordMap[y + 2][x].getKist() == null) {
                        Kist teVerschuivenKist = spelbordMap[y + 1][x].getKist();
                        spelbordMap[y + 1][x].setKist(null);
                        spelbordMap[y + 2][x].setKist(teVerschuivenKist);
                    } else {
                        this.undoStack.pop();
                        return;
                    }
                }
                this.mannetje.setVeld(spelbordMap[y + 1][x]);
                spelbordMap[y + 1][x].setHetMannetje(mannetje);
                spelbordMap[y][x].setHetMannetje(null);
                aantalVerplaatsingen++;
                succesMovement = true;
            }
            if (succesMovement == false) {
                this.undoStack.pop();
            }
            return;
        }

        if (beweging.equalsIgnoreCase("z")) {
            if (spelbordMap[y - 1][x].getIsMuur() == false) {
                if (spelbordMap[y - 1][x].getKist() != null) {
                    if (spelbordMap[y - 2][x].getIsMuur() == false && spelbordMap[y - 2][x].getKist() == null) {
                        Kist teVerschuivenKist = spelbordMap[y - 1][x].getKist();
                        spelbordMap[y - 1][x].setKist(null);
                        spelbordMap[y - 2][x].setKist(teVerschuivenKist);
                    } else {
                        this.undoStack.pop();
                        return;
                    }
                }
                this.mannetje.setVeld(spelbordMap[y - 1][x]);
                spelbordMap[y - 1][x].setHetMannetje(mannetje);
                spelbordMap[y][x].setHetMannetje(null);
                aantalVerplaatsingen++;
                succesMovement = true;
            }
            if (succesMovement == false) {
                this.undoStack.pop();
            }
            return;
        }

        if (beweging.equalsIgnoreCase("u")) {
            if (!undoStack.empty()) {
                aantalVerplaatsingen--;
                this.VeldenMetDoel.clear();
                Veld[][] veldtemp = (Veld[][]) this.undoStack.pop();
                for (Veld[] row : veldtemp) {
                    for (Veld col : row) {
                        if (col != null) {
                            if (col.getHetMannetje() != null) {
                                this.mannetje = col.getHetMannetje();
                            }
                            if (col.getHeeftDoel()) {
                                this.VeldenMetDoel.add(col);
                            }
                        }
                    }
                }
                this.spelbordMap = veldtemp;
            }

        }

    }

    /**
     * Cloned de {@link Spelbord#spelbordMap} en zorgt ervoor dat er nieuwe veld
     * objecten ontstaan met dezelfde waarde, maar een andere referentie.
     *
     * @return 2d array 10*10 van {@link Veld}
     */
    private Veld[][] deepCopyVeld(Veld[][] origineelMap) {
        Veld mapcopy[][] = new Veld[10][10];
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                Veld origineelVeld = origineelMap[i][j];
                if (origineelVeld == null) {
                    mapcopy[i][j] = null;
                } else {
                    mapcopy[i][j] = new Veld(origineelVeld.getHeeftDoel(), origineelVeld.getIsMuur(), origineelVeld.getX(), origineelVeld.getY(), null, null);
                    if (origineelVeld.getHetMannetje() != null) {
                        Mannetje nieuwMannetje = new Mannetje(mapcopy[i][j]);
                        mapcopy[i][j].setHetMannetje(nieuwMannetje);
                    }
                    if (origineelVeld.getKist() != null) {
                        mapcopy[i][j].setKist(new Kist(null));
                        mapcopy[i][j].getKist().setVeld(mapcopy[i][j]);
                    }
                }
            }
        }
        return mapcopy;

    }

    /**
     * Checkt of het spelbord voltooid door te kijken of alle kisten op een veld
     * staan met {@link Veld#heeftDoel} die true zijn staan.
     *
     * @return true als het spelbord completed is.
     * @see Spelbord#VeldenMetDoel
     */
    public boolean checkIsVoltooid() {
        boolean voorAlleKistenOpEenDoel = true;
        for (Veld v : this.VeldenMetDoel) {
            if (v.getKist() == null) {
                voorAlleKistenOpEenDoel = false;
            }
        }
        return voorAlleKistenOpEenDoel;
    }

    /**
     * Voegt één velden toe aan de {@link Spelbord#spelbordMap} op basis van de
     * X/Y coordinaten van het veld object. Als het veld een {@link Mannetje}
     * bevat in {@link Veld#hetMannetje}, wordt dit object geset in
     * {@link Spelbord#mannetje}. Als het {@link Veld#heeftDoel} true is, wordt
     * het veld toegevoegd aan de {@link Spelbord#VeldenMetDoel}
     *
     * @param veld
     */
    public void voegVeldToeAanNieuwSpelbord(Veld veld) {
        int x = veld.getX();
        int y = veld.getY();
        this.spelbordMap[y][x] = veld;
        if (veld.getHetMannetje() != null) {
            this.mannetje = veld.getHetMannetje();
        }
        if (veld.getHeeftDoel()) {
            this.VeldenMetDoel.add(veld);
        }
    }

    /**
     * set het element op Y(rij), X(kolom) op null. Dit wordt dan afgedrukt al
     * een achtergrond element.
     *
     * @param x KOLOM van de 2D array, 0-based.
     * @param y RIJ van de 2D array, 0-based.
     */
    public void voegAchtergrondToe(int x, int y) {
        this.spelbordMap[y][x] = null;
    }

    /**
     * Maakt een nieuw veld aan met correcte bijhorde attributen adv de
     * Item-parameter. Dit nieuw veld word toegevoegd aan de
     * {@link Spelbord#spelbordMap} via {@link Spelbord#voegVeldToeAanNieuwSpelbord(domein.Veld)
     * }
     *
     * @param item item waarop de attribuutwaarden van veld gebaseerd zijn.
     * @param XCOORD KOLOM van de 2D array, 0-based.
     * @param YCOORD RIJ van de 2D array, 0-based.
     */
    public void plaatsItem(char item, int XCOORD, int YCOORD) {
        if (this.mannetje != null && XCOORD == this.mannetje.getVeld().getX() && YCOORD == this.mannetje.getVeld().getY()) {
            this.mannetje.getVeld().setHetMannetje(null);//plaats van mannetje overschrijven
            this.mannetje.setVeld(null);
            this.mannetje = null;
        }
        if (this.mannetje != null && item == '@') { //tweede mannetje plaatsen
            this.mannetje.getVeld().setHetMannetje(null);//plaats van mannetje overschrijven
            this.mannetje.setVeld(null);
            this.mannetje = null;
        }
        if (this.spelbordMap[YCOORD][XCOORD] != null && this.spelbordMap[YCOORD][XCOORD].getHeeftDoel()) {
            this.VeldenMetDoel.remove(this.spelbordMap[YCOORD][XCOORD]);//plaats van een doel overschrijven
        }
        if (item == 'o') {
            Veld veld = new Veld(true, false, XCOORD, YCOORD, null, null);
            voegVeldToeAanNieuwSpelbord(veld);
        } else if (item == 'k') {
            Kist kist = new Kist(null);
            Veld veld = new Veld(false, false, XCOORD, YCOORD, null, kist);
            kist.setVeld(veld);
            voegVeldToeAanNieuwSpelbord(veld);
        } else if (item == '#') {
            Veld veld = new Veld(false, true, XCOORD, YCOORD, null, null);
            voegVeldToeAanNieuwSpelbord(veld);
        } else if (item == '.') {
            Veld veld = new Veld(false, false, XCOORD, YCOORD, null, null);
            voegVeldToeAanNieuwSpelbord(veld);
        } else if (item == '@') {
            if (this.mannetje == null) {
                Mannetje mannetjeTemp = new Mannetje(null);
                Veld veld = new Veld(false, false, XCOORD, YCOORD, mannetjeTemp, null);
                mannetjeTemp.setVeld(veld);
                voegVeldToeAanNieuwSpelbord(veld);
            } else {
                throw new NieuwSpelbordException(labels.getString("geen2Mannetjes"));
            }
        } else if (item == '+') {
            voegAchtergrondToe(XCOORD, YCOORD);
        } else {
            throw new NieuwSpelbordException(labels.getString("onbekendSymboolVervang"));
        }
    }

    /**
     * Controleert of het spelbord dat moet worden weggeschreven naar de
     * database geen fouten bevat. Controle: Er moet een muur staan tussen een
     * achtergrond en een bespeelbaar veld. Zo kan er ook nooit een
     * indexOutOfBounds exc. zijn bij {@link Spelbord#verplaatsMannetje(java.lang.String)
     * }. Er moet een gesloten contuur zijn van muren, er moet minstens 1
     * mannetje zijn, er moet minsten 1 doel zijn en evenveel kisten als doelen.
     * Throwed een {@link NieuwSpelbordException} als er een voorwaarde niet
     * voldaan wordt.
     *
     * @return true als alle controles slagen. zal nooit false returnen maar een
     * {@link NieuwSpelbordException} werpen in andere gevallen.
     */
    public boolean IsCorrectControleSpelbord() {
        boolean correct = true;
        for (int i = 0; i < this.spelbordMap.length; i++) {
            for (int j = 0; j < this.spelbordMap[i].length; j++) {
                try {
                    if (this.spelbordMap[i][j] != null && this.spelbordMap[i][j].getIsMuur() == false) { //als het een 'zand' is
                        if (this.spelbordMap[i - 1][j] == null) {//boven
                            throw new NieuwSpelbordException(labels.getString("zandNaastGras"));
                        } else if (this.spelbordMap[i + 1][j] == null) {//onder
                            throw new NieuwSpelbordException(labels.getString("zandNaastGras"));
                        } else if (this.spelbordMap[i][j - 1] == null) {//links
                            throw new NieuwSpelbordException(labels.getString("zandNaastGras"));
                        } else if (this.spelbordMap[i][j + 1] == null) {//rechts
                            throw new NieuwSpelbordException(labels.getString("zandNaastGras"));
                        }
                    }
                } catch (IndexOutOfBoundsException e) {
                    throw new NieuwSpelbordException(labels.getString("zandBuitenkant"));
                }
            }
        }

        for (int i = 0; i < this.spelbordMap.length; i++) { //1rij vastpakken
            int x = 0;
            boolean heeftMuurLinks = false;
            if (bevatEenZandVeld(i, -1)) {
                for (int j = 0; j < this.spelbordMap[i].length; j++) { //rij van links naar rechts afgaan
                    if (this.spelbordMap[i][j] != null && this.spelbordMap[i][j].getIsMuur()) {
                        heeftMuurLinks = true;
                        x = j;
                    }
                }
                if (heeftMuurLinks == false) {
                    throw new NieuwSpelbordException(labels.getString("geenGeslotenRand"));
                }
                boolean heeftMuurRechts = false;
                for (int k = this.spelbordMap[i].length - 1; k >= 0; k--) { //rij van rechts naar links afgaan
                    if ((this.spelbordMap[i][k] != null && this.spelbordMap[i][k].getIsMuur() && k != x)) { //heeft 2 verschillende muren
                        heeftMuurRechts = true;
                    }
                }
                if (heeftMuurRechts == false) {
                    throw new NieuwSpelbordException(labels.getString("geenGeslotenRand"));
                }

            }
        }

        for (int i = 0; i < 10; i++) { //1 kolom vastpakken
            int y = 0;
            boolean heeftMuurBoven = false;
            if (bevatEenZandVeld(-1, i)) {
                for (int j = 0; j < 10; j++) { //rij van links naar rechts afgaan

                    if (this.spelbordMap[j][i] != null && this.spelbordMap[j][i].getIsMuur()) {
                        heeftMuurBoven = true;
                        y = j;
                    }

                }
                if (heeftMuurBoven == false) {
                    throw new NieuwSpelbordException(labels.getString("geenGeslotenRand"));
                }

                boolean heeftMuurOnder = false;
                for (int k = 9; k >= 0; k--) { //rij van rechts naar links afgaan

                    if ((this.spelbordMap[k][i] != null && this.spelbordMap[k][i].getIsMuur() && k != y)) { //heeft 2 verschillende muren
                        heeftMuurOnder = true;
                    }

                }
                if (heeftMuurOnder == false) {
                    throw new NieuwSpelbordException(labels.getString("geenGeslotenRand"));
                }

            }
        }
        if (this.mannetje == null) {
            throw new NieuwSpelbordException(labels.getString("geenMannetje"));
        }

        if (this.VeldenMetDoel.isEmpty()) {
            throw new NieuwSpelbordException(labels.getString("minstenEenDoel"));
        }

        if (geefAantalKisten() != this.VeldenMetDoel.size()) {
            throw new NieuwSpelbordException(labels.getString("kistenEnDoelenOngelijk"));
        }

        return correct;
    }

    private boolean bevatEenZandVeld(int rij, int kol) {//over een rij iteren= rij megeven, kol = -1. en omgekeerd.
        boolean bevatZand = false;
        if (rij != -1) {
            for (int i = 0; i < 10; i++) {
                if (this.spelbordMap[rij][i] != null && this.spelbordMap[rij][i].getIsMuur() == false) {
                    bevatZand = true;
                }
            }
        } else if (kol != -1) {
            for (int i = 0; i < 10; i++) {
                if (this.spelbordMap[i][kol] != null && this.spelbordMap[i][kol].getIsMuur() == false) {
                    bevatZand = true;
                }
            }
        } else {
            throw new IllegalArgumentException();
        }
        return bevatZand;
    }

    /**
     *
     * @return het aantal doelen op het spelbord
     */
    public int geefAantalDoelen() {
        return VeldenMetDoel.size();
    }

    /**
     *
     * @return het aantal kisten op het spelbord.
     */
    public int geefAantalKisten() {
        int aantal = 0;
        for (Veld[] rij : spelbordMap) {
            for (Veld col : rij) {
                if (col != null && col.getKist() != null) {
                    aantal++;
                }
            }
        }

        return aantal;
    }
}
