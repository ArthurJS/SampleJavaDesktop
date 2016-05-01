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
public class Mannetje
{

    private Vak vak;

    /**
     * mannetje
     */
    public Mannetje()
    {
       
    }

    /**
     * geef vak
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
            throw new IllegalArgumentException("Vak in hero krijgt een null object");
        }
    }

}
