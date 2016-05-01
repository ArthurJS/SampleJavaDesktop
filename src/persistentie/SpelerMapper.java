/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package persistentie;

import domein.Speler;
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
public class SpelerMapper
{

    /**
     * spelers uit de database halen
     * @return 
     */
    public List<Speler> geefSpelers()
    {
        //**Aanmaken spelerslijst
        List<Speler> spelers = new ArrayList<>();

        //**Connectie maken met de database*/
        try (Connection conn = DriverManager.getConnection(Connectie.jbl))
        {
            PreparedStatement query = conn.prepareStatement("SELECT * FROM Speler");
            try (ResultSet rs = query.executeQuery())
            {
                while (rs.next())
                {
                    //**Ophalen van variabels uit de databank en daarmee spelers aanmaken */
                    String gebruikersnaam = rs.getString("gebruikersnaam");
                    String naam = rs.getString("naam");
                    String voornaam = rs.getString("voornaam");
                    String wachtwoord = rs.getString("wachtwoord");
                    boolean beheerder = rs.getBoolean("beheerder");

                    //**Toevoegen speler naar lijst*/
                    spelers.add(new Speler(gebruikersnaam, naam, voornaam, wachtwoord, beheerder));
                }
            }
        } catch (SQLException ex)
        {
            throw new RuntimeException(ex);
        }

        //** dit is donovan zijn zever*/
        //** Dummy variabele*/
        spelers.add(new Speler("desmedt", "donvoan", "dds", "ww", true));

        return spelers;
    }

    /**
     * Geef een speler uit de database aan de hand van een naam
     * @param gebruikernaam
     * @return 
     */
    public Speler geefSpeler(String gebruikernaam)
    {
        Speler speler = null;

        try (Connection conn = DriverManager.getConnection(Connectie.jbl))
        {
            PreparedStatement query = conn.prepareStatement("SELECT * FROM Speler WHERE gebruikersnaam = ?");
            query.setString(1, gebruikernaam);
            try (ResultSet rs = query.executeQuery())
            {
                if (rs.next())
                {
                    String naam = rs.getString("naam");
                    String voornaam = rs.getString("voornaam");
                    String wachtwoord = rs.getString("wachtwoord");
                    String gebruikersnaam = rs.getString("gebruikersnaam");
                    boolean beheerder = rs.getBoolean("beheerder");

                    speler = new Speler(naam, voornaam, gebruikersnaam, wachtwoord, beheerder);
                }
            }
        } catch (SQLException ex)
        {
            throw new RuntimeException(ex);
        }

        return speler;
    }

    /**
     * Toe voegen van een speler aan de database
     * @param speler 
     */
    public void voegToe(Speler speler)
    {
        /**
         * Toevoegen van nieuwe speler aan de databanken
         */
        /**
         * Zo kunnen we dit later opvragen
         */

        try
        {
            Class.forName("com.mysql.jdbc.Driver");
            Connection conn = DriverManager.getConnection(Connectie.jbl);
            PreparedStatement query = conn.prepareStatement("INSERT INTO Speler (gebruikersnaam,naam,voornaam,wachtwoord,beheerder)"
                    + "VALUES (?, ?, ?, ?, ?)");
            query.setString(1, speler.getGebruikersnaam());
            query.setString(2, speler.getNaam());
            query.setString(3, speler.getVoornaam());
            query.setString(4, speler.getWachtwoord());
            query.setBoolean(5, speler.isBeheerder());
            query.executeUpdate();
        } catch (Exception e)
        {
            throw new RuntimeException(e);
        }
    }
    
    /**
     * Wachtwoord opvragen van een speler
     * @param gebruikernaam
     * @return 
     */
    public String geefWachtwoord(String gebruikernaam)
    {
        String wachtwoord ="";

        try (Connection conn = DriverManager.getConnection(Connectie.jbl))
        {
            PreparedStatement query = conn.prepareStatement("SELECT wachtwoord FROM Speler WHERE gebruikersnaam = ?");
            query.setString(1, gebruikernaam);
            try (ResultSet rs = query.executeQuery())
            {
                if (rs.next())
                {
                   wachtwoord = rs.getString("wachtwoord");      
                }
            }
        } catch (SQLException ex)
        {
            throw new RuntimeException(ex);
        }

        return wachtwoord;
    }
}
