/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package persistentie;

import domein.Spel;
import domein.Spelbord;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author donovandesmedt
 */
public class SpelMapper
{

    List<Spel> spelen ;
    private SpelbordMapper spelbordMapper;
    
    /**
     * Constructor
     */
    public SpelMapper()
    {
        spelbordMapper = new SpelbordMapper();
    }

    /**
     * Geven spelen uit de database
     * @return 
     */
    public List<Spel> geefSpelen()
    {
        spelen = new ArrayList<>();
        spelbordMapper = new SpelbordMapper();
        try (Connection conn = DriverManager.getConnection(Connectie.jbl))
        {
            PreparedStatement query = conn.prepareStatement("SELECT * FROM Spel");
            try (ResultSet rs = query.executeQuery())
            {
                while (rs.next())
                {
                    //**Ophalen van variabels uit de databank en daarmee spel aanmaken */
                    String naam = rs.getString("naam");
                    

                    //**Toevoegen speler naar lijst*/
                    
                    spelen.add(new Spel(naam, spelbordMapper.geefSpelborden(naam)));
                }
            }
        } catch (SQLException ex)
        {
            throw new RuntimeException(ex);
        }

        return spelen;
    }
    
    /**
     * Geef de custommap spel
     * @return 
     */
    public List<Spelbord> geefLijstCustomSpelen()
    {   
        return spelbordMapper.geefSpelborden("custommap"); 
    }

    /**
     * Voegt een spel toe
     * @param naam 
     */
    public void voegSpelToe(String naam)
    {
        /**
         * Toevoegen van een nieuw vak aan de databanken
         */
        try
        {
            Class.forName("com.mysql.jdbc.Driver");
            Connection conn = DriverManager.getConnection(Connectie.jbl);
            PreparedStatement query = conn.prepareStatement("INSERT INTO Spel(naam)"
                    + "VALUES (?)");
            query.setString(1, naam);
            query.executeUpdate();

        } catch (Exception e)
        {
            System.out.println(e.getMessage());
            throw new RuntimeException(e);         
        }
    }
    
    /**
     * Wijzigen van een spelnaam
     * @param orgNaam
     * @param spelNaam 
     */
    public void wijzigNaam(String orgNaam, String spelNaam)
    {
        try
        {
            Class.forName("com.mysql.jdbc.Driver");
            Connection conn = DriverManager.getConnection(Connectie.jbl);
            PreparedStatement keyCheckOff = conn.prepareStatement("SET foreign_key_checks = 0;");
            PreparedStatement keyCheckOn = conn.prepareStatement("SET foreign_key_checks = 1;");
            PreparedStatement query = conn.prepareStatement("UPDATE Spel set naam = ? where naam = ?");
            query.setString(1, spelNaam);
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
}
