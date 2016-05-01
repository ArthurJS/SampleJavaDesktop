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
public class VakWandel extends Vak
{
    private boolean isDoel;
    
    /**
     * constructor
     * @param iD
     * @param xCoordinaat
     * @param yCoordinaat
     * @param isDoel 
     */
    public VakWandel(int iD, int xCoordinaat, int yCoordinaat, boolean isDoel)
    {
        super(iD, xCoordinaat, yCoordinaat);
        
        stelDoelIn(isDoel);
    }
    
    /**
     * constructor
     * @param isDoel 
     */
    public VakWandel(boolean isDoel)
    {
        super(0,0,0);
        stelDoelIn(isDoel);
    }

    /**
     * kijken of het vak een doel heeft
     * @return 
     */
    public boolean isIsDoel()
    {
        return isDoel;
    }

    /**
     * instellen dat het een doel heeft
     * @param isDoel 
     */
    public void setIsDoel(boolean isDoel)
    {
        stelDoelIn(isDoel);
    }
    
     /**
     * instellen dat het een doel heeft
     * @param isDoel 
     */
    private void stelDoelIn(boolean isDoel)
    {
       this.isDoel = isDoel; 
    }

    @Override
     /**
     * kijken of het bewandelbaar is
     * @param isDoel 
     */
    public boolean isBewandelbaar()
    {
        return true;
    }
    
    
    
    
}
