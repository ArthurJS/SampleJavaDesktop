/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domein;

import java.util.List;
import persistentie.SpelMapper;

/**
 *
 * @author donovandesmedt
 */
public class SpelRepository
{

    private List<Spel> spelen;
    private SpelMapper spelMapper;

    /**
     * constructor
     */
    public SpelRepository()
    {
        spelMapper = new SpelMapper();
        spelen = spelMapper.geefSpelen();
    }

    /**
     * geef de lijst met spelen
     * @return 
     */
    public List<Spel> geefLijstSpelen()
    {
        return spelen;
    }
    
    /**
     * geef de custom spel
     * @return 
     */
    public List<Spelbord> geefLijstCustomSpelen()
    {
        return spelMapper.geefLijstCustomSpelen();
    }

    /**
     * geef spel
     * @param naam
     * @return 
     */
    public Spel geefSpel(String naam)
    {
        Spel spel = null;
        for(Spel s: spelen)
        {
            if(naam.equals(s.getNaam()))
            {
              spel=s;
              break;
            }
        }

        if (spel != null)
        {
            return spel;
        } else
        {
            throw new IllegalArgumentException("Spel in spelRepository is nog niet geinitialiseerd");
        }
    }
    
}
