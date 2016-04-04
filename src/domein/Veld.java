package domein;

/**
 *
 * @author Mario
 */
public class Veld {

    private Kist kist;
    private Mannetje hetMannetje;
    private boolean heeftDoel = false;
    private boolean isMuur = false;
    private final int XCOORD;
    private final int YCOORD;
   
    /**
     * Constructor van Veld die alle meegegeven parameters isteld met de repectievelijke klasse attributen
     * @param heeftDoel, heeft het veld een doel
     * @param isMuur, heeft het veld een muur
     * @param XCOORD van het veld
     * @param YCOORD van het veld
     * @param hetMannetje, heeft het veld een mannetje
     * @param kist, heeft het veld een kist
     */
    public Veld(boolean heeftDoel, boolean isMuur,  int XCOORD, int YCOORD, Mannetje hetMannetje, Kist kist ) {
        this.heeftDoel= heeftDoel;
        this.isMuur = isMuur;
        this.XCOORD = XCOORD;
        this.YCOORD = YCOORD;
        this.hetMannetje = hetMannetje;
        this.kist = kist;     
    }

    /**
     * 
     * @return het klasse attribuut XCOORD van Veld
     */
    public int getX() {
        return this.XCOORD;
    }

    /**
     *
     * @return het klasse attribuut YCOORD van Veld
     */
    public int getY() {
        return this.YCOORD;
    }

    /**
     * @return het klasse attribuut kist van Veld
     */
    public Kist getKist() {
        return kist;
    }

    /**
     * Stelt het klasse attribuut kist in met de meegegeven parameter
     * @param kist
     */
    public void setKist(Kist kist) {
        this.kist = kist;
    }

    /**
     *het klasse attribuut hetMannetje van Veld
     * @return
     */
    public Mannetje getHetMannetje() {
        return hetMannetje;
    }

    /**
     *Stelt het klasse attribuut mannetje in met de meegegeven parameter
     * @param hetMannetje
     */
    public void setHetMannetje(Mannetje hetMannetje) {
        this.hetMannetje = hetMannetje;
    }
    
    /**
     *het klasse attribuut heeftDoel van Veld
     * @return
     */
    public boolean getHeeftDoel() {
        return this.heeftDoel;
    }

    /**
     *Stelt het klasse attribuut heeftdoel in met de meegegeven parameter
     * @param heeftDoel
     */
    public void setHeeftDoel(boolean heeftDoel) {
        this.heeftDoel = heeftDoel;
    }

    /**
     * 
     * @return het klasse attribuut isMuur van Veld
     */
    public boolean getIsMuur() {
        return this.isMuur;
    }

    /**
     * Stelt het klasse attribuut isMuur in met de meegegeven parameter
     * @param isMuur
     */
    public void setIsMuur(boolean isMuur) {
        this.isMuur = isMuur;
    }
    
    /**
     * Gaat op basis van de klasse attributen kijken wat het veld meer specifiek is: een kist, een muur, een mannetje, etc. en gaat dit koppelen aan een char
     * @return char die het veld op een bepaalde manier voorsteld:
     * '@': heeft mannetje
     * '*': heeft een kist en een doel
     * 'K': heeft een kist
     * '#': heeft een muur
     * 'o': heeft een doel
     * '.': is een leeg veld
     */
    public char toChar(){
        if(getHetMannetje()!=null)
            return '@';
        else if(getKist() != null && getHeeftDoel()==true)
            return '*';
        else if(getKist()!=null)
            return 'K';
        else if(getIsMuur()==true)
            return '#';
        else if(getHeeftDoel()==true)
            return 'o';
        else return '.';
    }
    
}
