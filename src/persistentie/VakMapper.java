/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package persistentie;

import domein.Vak;
import domein.VakMuur;
import domein.VakWandel;
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
public class VakMapper
{

    private List<Vak> vakken;
    
    /**
     * Vakken geven van een bepaald spelbord uit de database
     * @param spelBordNaam
     * @return 
     */
    public List<Vak> geefVakken(String spelBordNaam)
    {
       vakken = new ArrayList<>();
        
        try (Connection conn = DriverManager.getConnection(Connectie.jbl))
        {
            PreparedStatement query = conn.prepareStatement("SELECT * FROM Vak WHERE SpelBord_naam = ?");
            query.setString(1, spelBordNaam);

            try (ResultSet rs = query.executeQuery())
            {
                while (rs.next())
                {
                    /**
                     * Ophalen van variabels uit de databank en daarmee vakken aanmaken
                     */
                    int ID = rs.getInt("id");
                    int xyCoordinaat = rs.getInt("coordinaat");

                    /**
                     * We krijgen een XY-coordinaat binnen (deze is steeds uniek) en vormen deze om tot een X-coordinaat en een Y-coordinaat ( deze zijn niet meer altijd uniek).
                     */
                    switch (ID)
                    {
                        case 1:
                            /**wandelbaar pad*/
                            voegVakWandelToe(ID, xyCoordinaat, false);
                            break;
                        case 2:
                            /**muur*/
                            voegVakMuurToe(ID, xyCoordinaat);
                            break;
                        case 3:
                            /**wandelbaar pad+kist*/
                            voegVakWandelToe(ID, xyCoordinaat, false);
                            break;
                        case 4:
                            /**wandelbaar pad + doel*/
                            voegVakWandelToe(ID, xyCoordinaat, true);
                            break;
                        case 5:
                            /**KistMetDoel*/
                            voegVakWandelToe(ID, xyCoordinaat, true);
                            break;
                        case 6:
                            /**hero*/
                            voegVakWandelToe(ID, xyCoordinaat, false);
                            break;
                    }
                }
            }
        } catch (SQLException ex)
        {
            throw new RuntimeException(ex);
        }
        return vakken;
    }

    /**
     * vak toevoegen aan de database
     * @param ID
     * @param xyCoordinaat
     * @param keuzeSpel
     * @param spelbordNaam 
     */
    public void voegVakToe(int ID, int xyCoordinaat, String keuzeSpel, String spelbordNaam)
    {
        /**
         * Toevoegen van een nieuw vak aan de databanken
         */
        try
        {
            Class.forName("com.mysql.jdbc.Driver");
            Connection conn = DriverManager.getConnection(Connectie.jbl);
            PreparedStatement query = conn.prepareStatement("INSERT INTO Vak(id,coordinaat, Spelbord_Spel_naam, Spelbord_naam)"
                    + "VALUES (?, ?, ?, ?)");
            query.setInt(1, ID);
            query.setInt(2, xyCoordinaat);
            query.setString(3, keuzeSpel);
            query.setString(4, spelbordNaam);
            query.executeUpdate();

        } catch (Exception e)
        {
            System.out.println(e.getMessage());
            throw new RuntimeException(e);
            
        }
    }
    
    /**
     * deleten van vakken uit de database
     * @param spelNaam
     * @param spelbordNaam 
     */
    public void deleteVakken(String spelNaam, String spelbordNaam)
    {
        try
        {
            Class.forName("com.mysql.jdbc.Driver");
            Connection conn = DriverManager.getConnection(Connectie.jbl);
            PreparedStatement query = conn.prepareStatement("DELETE from Vak where (Spelbord_Spel_naam = ? AND Spelbord_naam = ?)");
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
     * wijzigen van een spelnaam voor de vreemde seutel van een vak
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
            PreparedStatement query = conn.prepareStatement("UPDATE Vak set Spelbord_Spel_naam = ? where Spelbord_Spel_naam = ?");
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
     * wijzigen van een spelbord naam voor 
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
            PreparedStatement query = conn.prepareStatement("UPDATE Vak set Spelbord_naam = ? where Spelbord_naam = ?");
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
     * Toevoegen van een muurvak
     * @param ID
     * @param xyCoordinaten 
     */
    private void voegVakMuurToe(int ID, int xyCoordinaten)
    {
        vakken.add(new VakMuur(ID, xyCoordinaten % 10, xyCoordinaten / 10));
    }

    /**
     * Toevoegen van een wandel vak
     * @param ID
     * @param xyCoordinaten
     * @param isDoel 
     */
    private void voegVakWandelToe(int ID, int xyCoordinaten, boolean isDoel)
    {
        vakken.add(new VakWandel(ID, xyCoordinaten % 10, xyCoordinaten / 10, isDoel));
    }

}
