/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package persistentie;

import domein.Mannetje;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author Toon
 */
public class MannetjeMapper
{
    /**
     * Voeg MannetjeToe
     * @param xyCoordinaat
     * @param spelbordnaam 
     */
     public void voegMannetjeToe(int xyCoordinaat, String spelbordnaam)
    {
        /**
         * Toevoegen van een nieuw mannetje aan de databanken
         */
        try
        {
            Class.forName("com.mysql.jdbc.Driver");
            Connection conn = DriverManager.getConnection(Connectie.jbl);
            PreparedStatement query = conn.prepareStatement("INSERT INTO Mannetje(Vak_coordinaat,  Vak_Spelbord_naam)"
                    + "VALUES (?, ?)");         
            query.setInt(1, xyCoordinaat);
            query.setString(2, spelbordnaam);
            query.executeUpdate();

        } catch (Exception e)
        {
            throw new RuntimeException(e);
        }
    }
     
      /**
      * Stel dat de naam van spelbord wordt aangepast moet dat ook gebeuren voor het mannetje want die worden gelinkt aan een spelbord
      * @param orgNaam
      * @param spelbordNaam 
      */
    public void wijzigSpelbordNaam(String orgNaam, String spelbordNaam)
    {
        try
        {
            Class.forName("com.mysql.jdbc.Driver");
            Connection conn = DriverManager.getConnection(Connectie.jbl);
            PreparedStatement keyCheckOff = conn.prepareStatement("SET foreign_key_checks = 0;");
            PreparedStatement keyCheckOn = conn.prepareStatement("SET foreign_key_checks = 1;");
            PreparedStatement query = conn.prepareStatement("UPDATE Mannetje set Vak_Spelbord_naam = ? where Vak_Spelbord_naam = ?");
            query.setString(1, spelbordNaam);
            query.setString(2, orgNaam);
            keyCheckOff.executeQuery();
            query.executeUpdate();
            keyCheckOn.executeQuery();

        } catch (Exception e)
        {
            System.out.println(e.getMessage());
            throw new RuntimeException(e);         
        }
    }
    
    /**
     * Delete van mannetje uit het spelbord
     * @param spelbordNaam 
     */
    public void deleteMannetje(String spelbordNaam)
    {
        try
        {
            Class.forName("com.mysql.jdbc.Driver");
            Connection conn = DriverManager.getConnection(Connectie.jbl);
            PreparedStatement query = conn.prepareStatement("DELETE from Mannetje where (Vak_Spelbord_naam = ?)");
            query.setString(1, spelbordNaam);
            query.executeUpdate();

        } catch (Exception e)
        {
            System.out.println(e.getMessage());
            throw new RuntimeException(e);
            
        }
    }
    
    /**
     * Opvragen van het mannetje
     * @param spelbordnaam
     * @return 
     */
    public Mannetje geefMannetje(String spelbordnaam)
    {
        Mannetje mannetje = null;

        try (Connection conn = DriverManager.getConnection(Connectie.jbl))
        {
            PreparedStatement query = conn.prepareStatement("SELECT * FROM Mannetje WHERE Vak_Spelbord_naam = ?");
            query.setString(1, spelbordnaam);
            try (ResultSet rs = query.executeQuery())
            {
                if (rs.next())
                {
                    //int coordinaat = rs.getInt("Vak_coordinaat");
                    
                    mannetje = new Mannetje();
                }
            }
        } catch (SQLException ex)
        {
            throw new RuntimeException(ex);
        }
        
        return mannetje;
    }
}
