/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domein;


/**
 *
 * @author donovandesmedt
 */
public class Vak
{
    private int iD;
    private int xCoordinaat;
    private int yCoordinaat;
    
    private Kist kist;
    private Mannetje hero;
    
    /**
     * constructor
     * @param iD
     * @param xCoordinaat
     * @param yCoordinaat 
     */
    public Vak(int iD, int xCoordinaat, int yCoordinaat)
    {
        steliDIn(iD);
        stelxCoordinaatIn(xCoordinaat);
        stelyCoordinaatIn(yCoordinaat);
    }

    /**
     * id opvragen
     * @return 
     */
    public int getiD()
    {
        return iD;
    }

    /**
     * id setten
     * @param iD 
     */
    public void setiD(int iD)
    {
        steliDIn(iD);
    }
    
    /**
     * id setten
     * @param iD 
     */
    private void steliDIn(int iD)
    {
         this.iD = iD;
    }
    
    /**
     * x coordinaat opvragen
     * @return 
     */
    public int getxCoordinaat()
    {
        return xCoordinaat;
    }

    /**
     * x coordinaat setten
     * @param xCoordinaat 
     */
    public void setxCoordinaat(int xCoordinaat)
    {
        stelxCoordinaatIn(xCoordinaat);
    }
    
    /**
     * x coordinaat setten
     * @param xCoordinaat 
     */
    private void stelxCoordinaatIn(int xCoordinaat)
    {
        this.xCoordinaat = xCoordinaat;
    }
    
    /**
     * y coordinaat opvragen
     * @return 
     */
    public int getyCoordinaat()
    {
        return yCoordinaat;
    }

    /**
     * y coordinaat setten
     * @param yCoordinaat 
     */
    public void setyCoordinaat(int yCoordinaat)
    {
        stelyCoordinaatIn(yCoordinaat);
    }
    
    /**
     * y coordinaat setten
     * @param yCoordinaat 
     */
    private void stelyCoordinaatIn(int yCoordinaat)
    {
        this.yCoordinaat = yCoordinaat;
    }

    /**
     * kist geven
     * @return 
     */
    public Kist getKist()
    {
        return kist;
    }

    /**
     * kist setten
     * @param kist 
     */
    public void setKist(Kist kist)
    {
        this.kist = kist;
    }
    
    /**
     * kijken of het vak een kist heeft
     * @return 
     */
    public boolean bevatKist(){
        return kist != null;
    }

    /**
     * mannetje geven
     * @return 
     */
    public Mannetje getHero()
    {
        return hero;
    }

    /**
     * mannetje setten
     * @param hero 
     */
    public void setHero(Mannetje hero)
    {
        this.hero = hero;
    }
    
   /**
    * kijken of vak bewandelbaar is
    * @return 
    */
    public boolean isBewandelbaar()
    {
        return true;
    }
    
    /**
     * kijken of vak een mannetje heeft
     * @return 
     */
    public boolean bevatMannetje()
    {
        return hero != null;
    }
    
}
