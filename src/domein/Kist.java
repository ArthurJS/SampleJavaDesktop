package domein;

/**
 *
 * @author Mario
 */
public class Kist {
    private Veld veld;
    
    /**
     * Constructor van kist die het klasse attribuut veld instelt met het meegegeven veld
     * @param veld waar de kist opstaat
     */
    public Kist(Veld veld){
        this.veld=veld;
    }

    /**
     *Retourneert een veld Object waar de kist opstaat
     * @return
     * veld (Veld)
     */
    public Veld getVeld() {
        return veld;
    }

    /**
     * Stelt een Veld object in waar de kist opstaat
     * @param veld
     */
    public void setVeld(Veld veld) {
        this.veld = veld;
    }
    
    
}

    
