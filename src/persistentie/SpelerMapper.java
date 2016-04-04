package persistentie;


import domein.Speler;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SpelerMapper {

    /**
     *
     * @param gebruikersnaam
     * @param wachtwoord
     */
    public Speler geefSpeler(String gebruikersnaam, String wachtwoord) {
        Speler speler = null;
        //connection.close();
        try (Connection connectie = DriverManager.getConnection(Connectie.JDBC_URL)) {
            PreparedStatement query = connectie.prepareStatement("SELECT * FROM sokoban.speler WHERE gebruikersnaam = ?");
            query.setString(1, gebruikersnaam.toLowerCase());
            try (ResultSet rs = query.executeQuery()) {
                if (rs.next()) {
                    String voornaam = rs.getString("voornaam");
                    String achternaam = rs.getString("achternaam");
                    int wachtwoordhash = rs.getInt("wachtwoord");
                    boolean admin = rs.getBoolean("admin");
                    
                    if(wachtwoordhash==wachtwoord.hashCode()){
                    speler = new Speler(voornaam, achternaam, gebruikersnaam, wachtwoord, admin);
                    }
                }
            }
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
        catch (Exception e)
        {
            throw new RuntimeException(e);
        }

        return speler;
    }

    public void voegToe(Speler speler) {

        try (Connection conn = DriverManager.getConnection(Connectie.JDBC_URL)) {
            PreparedStatement query = conn.prepareStatement("INSERT INTO sokoban.speler(voornaam, achternaam, gebruikersnaam, wachtwoord, admin)"
                    + " VALUES (?, ?, ?, ?, ?);");
            query.setString(1, speler.getVoornaam());
            query.setString(2, speler.getAchternaam());
            query.setString(3, speler.getGebruikersnaam().toLowerCase());
            query.setInt(4, speler.getWachtwoord().hashCode());
            query.setBoolean(5, speler.getIsAdmin());
            query.executeUpdate();
            

        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }

    }
    
    public boolean bestaatSpeler(String gebruikersnaam) {
        boolean bestaat = false;
        //connection.close();
        try (Connection connectie = DriverManager.getConnection(Connectie.JDBC_URL)) {
            PreparedStatement query = connectie.prepareStatement("SELECT * FROM sokoban.speler WHERE gebruikersnaam = ?");
            query.setString(1, gebruikersnaam.toLowerCase());
            try (ResultSet rs = query.executeQuery()) {
                if (rs.next()) {
                    bestaat = true;
                    }
                }
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
        catch (Exception e)
        {
            throw new RuntimeException(e);
        }

        return bestaat;
    }
}

