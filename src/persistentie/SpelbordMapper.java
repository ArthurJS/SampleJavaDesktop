/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package persistentie;

import domein.Kist;
import domein.Mannetje;
import domein.Spelbord;
import domein.Vak;
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
public class SpelbordMapper
{

    List<Spelbord> spelborden;
    private VakMapper vakMapper;
    private KistMapper kistMapper;
    private MannetjeMapper mannetjeMapper;

    /**
     * Geven van spelborden uit de database aan de hand van een spel
     * @param bordnaam
     * @return 
     */
    public List<Spelbord> geefSpelborden(String bordnaam)
    {
        spelborden = new ArrayList<>();
        vakMapper = new VakMapper();
        kistMapper = new KistMapper();
        mannetjeMapper = new MannetjeMapper();
        
        try (Connection conn = DriverManager.getConnection(Connectie.jbl))
        {
            PreparedStatement query = conn.prepareStatement("SELECT * FROM Spelbord WHERE Spel_naam = ?");
            query.setString(1, bordnaam);
            try (ResultSet rs = query.executeQuery())
            {
                while (rs.next())
                {
                    //**Ophalen van variabels uit de databank en daarmee spel aanmaken */
                    String naam = rs.getString("naam");
                    
                    List<Vak> vakken = vakMapper.geefVakken(naam);
                    List<Kist> kisten = kistMapper.geefKisten(naam);
                    Mannetje mannetje = mannetjeMapper.geefMannetje(naam);

                    //**Toevoegen speler naar lijst*/ 
                    spelborden.add(new Spelbord(naam,false,vakken, kisten, mannetje));
                }
            }
        } catch (SQLException ex)
        {
            throw new RuntimeException(ex);
        }

        return spelborden;
    }
    
    /**
     * Deze vraagt alle spelborden
     * @return 
     */
    public List<Spelbord> geefAlleSpelborden()
    {
        spelborden = new ArrayList<>();
        vakMapper = new VakMapper();
        kistMapper = new KistMapper();
        mannetjeMapper = new MannetjeMapper();
        
        try (Connection conn = DriverManager.getConnection(Connectie.jbl))
        {
            PreparedStatement query = conn.prepareStatement("SELECT * FROM Spelbord");
            try (ResultSet rs = query.executeQuery())
            {
                while (rs.next())
                {
                    //**Ophalen van variabels uit de databank en daarmee spel aanmaken */
                    String naam = rs.getString("naam");

                    //**Toevoegen speler naar lijst*/ 
                    spelborden.add(new Spelbord(naam,false,vakMapper.geefVakken(naam), kistMapper.geefKisten(naam), mannetjeMapper.geefMannetje(naam)));
                }
            }
        } catch (SQLException ex)
        {
            throw new RuntimeException(ex);
        }

        return spelborden;
    }
    
    /**
     * Voegt spelbord toe aan de database
     * @param naam
     * @param spelNaam 
     */
     public void voegSpelbordToe(String naam, String spelNaam)
    {
        /**
         * Toevoegen van een nieuw vak aan de databanken
         */
        try
        {
            Class.forName("com.mysql.jdbc.Driver");
            Connection conn = DriverManager.getConnection(Connectie.jbl);
            PreparedStatement query = conn.prepareStatement("INSERT INTO Spelbord(naam, Spel_naam)"
                    + "VALUES (?, ?)");
            query.setString(1, naam);
            query.setString(2, spelNaam);
            query.executeUpdate();

        } catch (Exception e)
        {
            System.out.println(e.getMessage());
            throw new RuntimeException(e);
            
        }
    }
     
     /**
      * deleten van het spelbord
      * @param spelNaam
      * @param spelbordNaam 
      */
    public void deleteSpelbord(String spelNaam, String spelbordNaam)
    {
        try
        {
            Class.forName("com.mysql.jdbc.Driver");
            Connection conn = DriverManager.getConnection(Connectie.jbl);
            PreparedStatement query = conn.prepareStatement("DELETE from Spelbord where (Spel_naam = ? AND naam = ?)");
            query.setString(1, spelNaam);
            query.setString(2, spelbordNaam);
            query.executeUpdate();

        } catch (Exception e)
        {
            System.out.println(e.getMessage());
            throw new RuntimeException(e);
            
        }
    }
    
    /**
     * Wijzigen van het spel naam
     * @param orgNaam
     * @param spelNaam 
     */
    public void wijzigSpelNaam(String orgNaam, String spelNaam)
    {
        try
        {
            Class.forName("com.mysql.jdbc.Driver");
            Connection conn = DriverManager.getConnection(Connectie.jbl);
            PreparedStatement keyCheckOff = conn.prepareStatement("SET foreign_key_checks = 0;");
            PreparedStatement keyCheckOn = conn.prepareStatement("SET foreign_key_checks = 1;");
            PreparedStatement query = conn.prepareStatement("UPDATE Spelbord set Spel_naam = ? where Spel_naam = ?");
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
    
    /**
     * Wijzigen van het spelbord Naam
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
            PreparedStatement query = conn.prepareStatement("UPDATE Spelbord set naam = ? where naam = ?");
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
}

