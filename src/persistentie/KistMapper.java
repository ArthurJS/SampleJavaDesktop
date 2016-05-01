/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package persistentie;

import domein.Kist;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Toon
 */
public class KistMapper
{
    private List<Kist> kisten;
    
    /**
     * Dit gaat kisten toevegen aan de database
     * @param xyCoordinaat
     * @param spelbordnaam 
     */
     public void voegKistToe(int xyCoordinaat, String spelbordnaam)
    {
        kisten = new ArrayList<>();
        /**
         * Toevoegen van een nieuw kistje aan de databanken
         */
        try
        {
            Class.forName("com.mysql.jdbc.Driver");
            Connection conn = DriverManager.getConnection(Connectie.jbl);
            PreparedStatement query = conn.prepareStatement("INSERT INTO Kist(Vak_coordinaat, Vak_Spelbord_naam)"
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
      * Stel dat de naam van spelbord wordt aangepast moet dat ook gebeuren voor de kisten want die worden gelinkt aan een spelbord
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
            PreparedStatement query = conn.prepareStatement("UPDATE Kist set Vak_Spelbord_naam = ? where Vak_Spelbord_naam = ?");
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
     * Delete van kisten uit de database
     * @param spelbordNaam 
     */
    public void deleteKisten(String spelbordNaam)
    {
        try
        {
            Class.forName("com.mysql.jdbc.Driver");
            Connection conn = DriverManager.getConnection(Connectie.jbl);
            PreparedStatement query = conn.prepareStatement("DELETE from Kist where Vak_Spelbord_naam = ?");
            query.setString(1, spelbordNaam);
            query.executeUpdate();

        } catch (Exception e)
        {
            System.out.println(e.getMessage());
            throw new RuntimeException(e);
            
        }
    }
    
    /**
     * Gaat de kisten ophalen uit de database
     * @param spelbordnaam
     * @return 
     */
    public List<Kist> geefKisten(String spelbordnaam)
    {
        kisten = new ArrayList<>();
        
                
        try (Connection conn = DriverManager.getConnection(Connectie.jbl))
        {
            PreparedStatement query = conn.prepareStatement("SELECT * FROM Kist WHERE Vak_Spelbord_naam = ?");
            query.setString(1, spelbordnaam);
            try (ResultSet rs = query.executeQuery())
            {
                while (rs.next())
                {
//                    //**Ophalen van variabels uit de databank en daarmee spel aanmaken */
//                    int  coordinaat = rs.getInt("Vak_coordinaat");
//                    String spelbordNaam = rs.getString("Vak_Spelbord_naam");

                    //**Toevoegen speler naar lijst*/                  
                    kisten.add(new Kist(false));
                }
            }
        } catch (SQLException ex)
        {
            throw new RuntimeException(ex);
        }

        return kisten;
    }
}
