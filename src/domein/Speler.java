package domein;

import static LanguageResources.Resource.labels;
import exceptions.GebruikersNaamException;
import exceptions.WachtwoordException;

/**
 *
 * @author Thibault Fouquaert
 */
public class Speler {

    private String gebruikersnaam;
    private String wachtwoord;
    private String achternaam;
    private String voornaam;
    private boolean isAdmin;

    /**
     *
     * @return de achternaam van de speler. deze kan null zijn.
     */
    public String getAchternaam() {
        return this.achternaam;
    }

    /**
     * Set de achternaam van de speler. Mag null zijn.
     *
     * @param achternaam achternaam, niet verplicht.
     */
    public void setAchternaam(String achternaam) {
        this.achternaam = achternaam;
    }

    /**
     *
     * @returnde voornaam van de speler.deze kan null zijn.
     */
    public String getVoornaam() {
        return this.voornaam;
    }

    /**
     * Set de voornaam van de speler. Mag null zijn.
     *
     * @param voornaam voornaam, niet verplicht.
     */
    public void setVoornaam(String voornaam) {
        this.voornaam = voornaam;
    }

    /**
     *
     * @return de gebruikersnaam van de gebruiker. Kan niet null zijn en is
     * uniek.
     */
    public String getGebruikersnaam() {
        return this.gebruikersnaam;
    }

    /**
     * Sets de gebruikernaam van de speler. Deze is uniek en mistens 8 tekens
     * lang. throwed een {@link GebruikersNaamException} indien iets niet
     * voldoet.
     *
     * @param gebrnaam unieke naam van minstens 8 tekens.
     */
    public void setGebruikersnaam(String gebrnaam) {
        if (gebrnaam == null || gebrnaam.equals("")) {
            throw new GebruikersNaamException(labels.getString("verplichtVeldException"));
        } else if (gebrnaam.length() < 8) {
            throw new GebruikersNaamException(labels.getString("foutieveGebruikersnaam1"));
        }
        this.gebruikersnaam = gebrnaam;
    }

    /**
     *
     * @return true indien admin.
     */
    public boolean getIsAdmin() {
        return this.isAdmin;
    }

    /**
     * Set de admin rechten
     *
     * @param isAdmin
     */
    public void setIsAdmin(boolean isAdmin) {
        this.isAdmin = isAdmin;
    }

    /**
     *
     * @return het wachtwoord van de speler.
     */
    public String getWachtwoord() {
        return this.wachtwoord;
    }

    /**
     * Set het wachtwoord van de speler. Dit is kan neit null/empty zijn, moet
     * minstens 8 karakters bevatten en moet minstens 1 kleine letter, 1 grote
     * letter en 1 cijfer bevatten. throwed een {@link WachtwoordException}
     * indien iets niet voldoet.
     *
     * @param ww in te stellen wachtwoord.
     * @see Speler#validate(java.lang.String)
     */
    public void setWachtwoord(String ww) {
        if (ww == null || ww.equals("")) {
            throw new WachtwoordException(labels.getString("verplichtVeldException"));
        } else if (ww.length() < 8) {
            throw new WachtwoordException(labels.getString("foutiefWachtwoord1"));
        } else if (validate(ww) == false) {
            throw new WachtwoordException(labels.getString("foutiefWachtwoord2"));
        } else {
            this.wachtwoord = ww;
        }
    }

    /**
     * Set alle attributen in, waardoor ze gecontroleerd worden.
     *
     * @param voornaam
     * @param achternaam
     * @param gebruikersnaam
     * @param wachtwoord
     * @param isAdmin
     */
    public Speler(String voornaam, String achternaam, String gebruikersnaam, String wachtwoord, boolean isAdmin) {
        setVoornaam(voornaam);
        setAchternaam(achternaam);
        setGebruikersnaam(gebruikersnaam);
        setWachtwoord(wachtwoord);
        setIsAdmin(isAdmin);
    }

    /**
     * Maakt gebruik van {@link Speler#Speler(java.lang.String, java.lang.String, java.lang.String, java.lang.String, boolean)
     * }. Set alle waarden in en voert er een controle op door. Set de
     * adminrechten automatisch op false.
     *
     * @param voornaam
     * @param achternaam
     * @param gebruikersnaam
     * @param wachtwoord
     */
    public Speler(String voornaam, String achternaam, String gebruikersnaam, String wachtwoord) {
        this(voornaam, achternaam, gebruikersnaam, wachtwoord, false);
    }

    /**
     * controleert of het wachtwoord minstens 1 kleine letter, 1 grote letter en
     * 1 cijfer bevatten.
     *
     * @param wachtwoord te controleren wachtwoord
     * @return true indien voldoet aan validatie.
     */
    public boolean validate(String wachtwoord) {
        boolean wwInOrde = wachtwoord.matches("(?-i)(?=^.{8,}$)((?!.*\\s)(?=.*[A-Z])(?=.*[a-z]))((?=(.*\\d){1,})|(?=(.*\\W){1,}))^.*$");
        return wwInOrde;
    }

}
