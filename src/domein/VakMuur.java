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
public class VakMuur extends Vak
{
    /**
     * constructor
     * @param iD
     * @param xCoordinaat
     * @param yCoordinaat 
     */
    public VakMuur(int iD, int xCoordinaat, int yCoordinaat)
    {
        super(iD, xCoordinaat, yCoordinaat);
    }
    
    /**
     * constructor
     */
    public VakMuur()
    {
        super(0,0,0);
    }
    
    @Override 
    /**
     * Is het bewandelbaar?
     */
    public boolean isBewandelbaar()
    {
        return false;
    }
}
