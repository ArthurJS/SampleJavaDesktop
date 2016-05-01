/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domein;

/**
 *
 * @author Toon
 */
public class Kist
{

    
    private boolean opDoel;
    private Vak vak;

    /**
     * constructor
     * @param opDoel 
     */
    public Kist(boolean opDoel)
    {
        setOpDoel(opDoel);
    }
    
    /**
     * constructor
     */
    public Kist()
    {
        
    }
    
    /**
     * kijken of de kist staat op het doel staat
     * @return 
     */
    public boolean isOpDoel()
    {
        return opDoel;
    }

    /**
     * kist staat op het doel
     * @param opDoel 
     */
    public void setOpDoel(boolean opDoel)
    {
        this.opDoel = opDoel;
    }

    /**
     * vak op vragen waar de kist op staat
     * @return 
     */
    public Vak getVak()
    {
        return vak;
    }

    /**
     * setten van het vak
     * @param vak 
     */
    public void setVak(Vak vak)
    {
        if (vak != null)
        {
            this.vak = vak;
        } else

        {
            throw new IllegalArgumentException("Vak van kist krijgt een null object!");
        }
    }

}
