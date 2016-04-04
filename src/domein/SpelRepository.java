package domein;

import java.util.*;
import persistentie.*;

/**
 *
 * @author Mario
 */
public class SpelRepository {

    //private Collection<Spel> alleSpellen;
    private final SpelMapper spelMapper;
    //private List<String> alleSpelNamen;

    /**
     *Constructor van SpelRepository die een nieuw SpelRepository object aanmaakt en
     * toekent aan het klasse attribuut {@link SpelMapper#SpelMapper() }
     */
    public SpelRepository() {
        spelMapper = new SpelMapper();
    }

    /**
     * Geeft de lijst terug van alle spelnamen adhv het spelMapper object
     * @return
     */
    public List<String> geefSpelnamen() {
        return spelMapper.geefSpelNamen();
    }

    /**
     * Geeft het geselecteerde spel terug adhv {@link SpelMapper#geefGeselecteerdSpel(java.lang.String)}
     * @param spelNaam
     * @return 
     * Spel met overeenkomstige spelnaam
     */
    public Spel geefGeslecteerdSpel(String spelNaam) {
        return spelMapper.geefGeselecteerdSpel(spelNaam);
    }
    
    /**
     * Geeft het totaal aantal spelborden terug van de in GUI gekozen spel
     * @param spelnaam de naam van het gewenste te retouren spel
     * @return
     * aantal spelborden (int) adhv {@link SpelMapper#geefTotaalAantalSpelbordenVanInGuiGekozenSpel(java.lang.String)  }
     */
    public int geefTotaalAantalSpelbordenVanInGuiGekozenSpel(String spelnaam){
        return spelMapper.geefTotaalAantalSpelbordenVanInGuiGekozenSpel(spelnaam);
    }

    /**
     * Registreert het nieuw aangemaakte spel adhv {@link SpelMapper#registreerNieuwSpel(domein.Spel)  }
     * @param nieuwSpel te registreren spel
     */
    public void registreerNieuwSpel(Spel nieuwSpel){
        spelMapper.registreerNieuwSpel(nieuwSpel);
    }
}
