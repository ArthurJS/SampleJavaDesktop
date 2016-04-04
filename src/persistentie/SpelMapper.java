package persistentie;

import domein.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SpelMapper {

    public List<String> geefSpelNamen() {
        List<String> spelNamen = new ArrayList<>();

        try (Connection connectie = DriverManager.getConnection(Connectie.JDBC_URL)) {
            PreparedStatement query = connectie.prepareStatement("SELECT Spelnaam FROM sokoban.spel");

            try (ResultSet rs = query.executeQuery()) {
                while (rs.next()) {
                    spelNamen.add(rs.getString(1));
                }
            }
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return spelNamen;
    }

    public Spel geefGeselecteerdSpel(String spelnaam) {
        Spel spel = null;
        //connection.close();
        try (Connection connectie = DriverManager.getConnection(Connectie.JDBC_URL)) {
            PreparedStatement query = connectie.prepareStatement("SELECT * FROM sokoban.spel WHERE Spelnaam = ?");
            query.setString(1, spelnaam);
            try (ResultSet rs = query.executeQuery()) {
                if (rs.next()) {
                    
                    spel = new Spel(rs.getString(1));
                }
            }
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return spel;
    }
    
    public int geefTotaalAantalSpelbordenVanInGuiGekozenSpel(String spelnaam){
        int aantalSpelborden = 0;

        try (Connection connectie = DriverManager.getConnection(Connectie.JDBC_URL)) {
            PreparedStatement query = connectie.prepareStatement("SELECT COUNT(*) FROM sokoban.spelbord WHERE spel_Spelnaam = ?");
            query.setString(1, spelnaam);
            try (ResultSet rs = query.executeQuery()) {
                if (rs.next()) {
                    aantalSpelborden = Integer.parseInt(rs.getString(1));
                }
            }
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        } catch (NumberFormatException e) {
            throw new RuntimeException(e);
        }

        return aantalSpelborden;
    }
    public void registreerNieuwSpel(Spel nieuwespel){
        try (Connection connectie = DriverManager.getConnection(Connectie.JDBC_URL)){
            PreparedStatement sql = connectie.prepareStatement("INSERT INTO sokoban.spel (Spelnaam) values(?);");
            sql.setString(1, nieuwespel.getSpelNaam());
            sql.executeUpdate();
        }catch (SQLException ex){
            throw new RuntimeException(ex);
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }
}
