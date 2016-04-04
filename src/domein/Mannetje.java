    package domein;

/**
 *
 * @author Mario
 */
public class Mannetje{
    private Veld veld;
    
    /**
     * Constructor van kist die het klasse attribuut veld instelt met het meegegeven veld
     * @param veld waar de kist opstaat
     */
    public Mannetje(Veld veld){
        this.veld=veld;
    }

    /**
     *Retourneert een veld Object waar het mannetje opstaat
     * @return
     * veld (Veld)
     */
    public Veld getVeld() {
        return veld;
    }

    /**
     * Stelt een Veld object in waar het mannetje opstaat
     * @param veld
     */
    public void setVeld(Veld veld) {
        this.veld = veld;
    }
   
}
