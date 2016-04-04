package domein;

import static LanguageResources.Resource.labels;
import exceptions.GebruikersNaamException;
import exceptions.WachtwoordException;
import persistentie.*;

public class SpelerRepository {

	private final SpelerMapper mapper;
//	private Collection<Speler> alleSpelers;

         /**
          * Constructor die een SpelerMapper object aanmaakt.
	  */
    public SpelerRepository() {
        mapper = new SpelerMapper();
    }
    
	/**
	 * Voegt de speler toe aan de database met behlup van het SpelerMapper object adhv {@link SpelerMapper#voegToe(domein.Speler) }
         * en controleert of de naam reeds voorkomt in de database
         * Deze methode kan een {@link GebruikersNaamException} gooien.
	 * @param speler De (Speler)speler die je wilt toevoegen
	 */
        public void voegToe(Speler speler) {
       if (bestaatSpeler(speler.getGebruikersnaam()))
            throw new GebruikersNaamException(labels.getString("bestaandeSpeler"));
       
       mapper.voegToe(speler);
    }
        
	/**
	 * Geeft een speler terug adhv de parameters, deze methode controleert met behulp van het Spelermapper object of de speler bestaat {@link SpelerRepository#bestaatSpeler(java.lang.String) }
         * en throwt een {@link GebruikersNaamException} wanneer dit niet het geval is.
	 * @param gebruikersnaam van de speler
	 * @param wachtwoord van de speler
	 */
	public Speler geefSpeler(String gebruikersnaam, String wachtwoord) {
                if(bestaatSpeler(gebruikersnaam)==false) {
                    throw new GebruikersNaamException(labels.getString("geenGebruikersnaam"));
                }else{
                    Speler spelerUitDB = mapper.geefSpeler(gebruikersnaam, wachtwoord);
                    return spelerUitDB;
                }
	}
	/**
	 * Gaat na aan de hand van het SpelerMapper object of een speler bestaat met een bepaald gebruikersnaam {@link SpelerMapper#bestaatSpeler(java.lang.String) }
	 * @param gebruikersnaam
	 */
	private boolean bestaatSpeler(String gebruikersnaam) {
		return mapper.bestaatSpeler(gebruikersnaam);
	}

}