/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *Singelton klasse
 * @author Toon
 */
public class LoaderSchermen
{
    private static LoaderSchermen instance = null;
    
    /**
     * constructor is protected gemaakt zodat men er niet aan kunt
     */
    protected LoaderSchermen()
    {}
    
    /**
     * Dit is noodzakelijke klasse. Deze gaat kijken of het static object al aangemaakt is. zo ja het object wordt aangemaakt zo niet wordt het static object gewoon doorgegeven
     * @return 
     */
    public static LoaderSchermen getInstance()
    {
        if(instance == null)
            instance = new LoaderSchermen();
        return instance;
    }
    
    /**
     * Inladen van een nieuw scherm
     * @param titel
     * @param scherm
     * @param width
     * @param height
     * @param node 
     */
    public void load(String titel, Parent scherm, int width, int height,Node node)
    {
        Stage stage = (Stage) node.getScene().getWindow();
        stage.setTitle(titel);
        Scene scene = new Scene(scherm, width, height);
        stage.setScene(scene);
    }
    
    /**
     * de locatie zetten van de schermen
     * @param fxmlBestand
     * @param node 
     */
    public void setLocation(String fxmlBestand, Node node)
    {
         FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource(fxmlBestand));
        loader.setRoot(node);
        loader.setController(node);
        try
        {
            loader.load();
        } catch (IOException ex)
        {
            throw new RuntimeException();
        }
    }
}
