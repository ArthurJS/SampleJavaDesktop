/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import gui.SokobanApplicatie;
import gui.SokobanApplicatieFX;
import javafx.application.Application;
import javafx.stage.Stage;

/**
 *
 * @author donovandesmedt
 */
public class StartUp extends Application
{

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args)
    {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception
    {
       new SokobanApplicatieFX(primaryStage);
       //new SokobanApplicatie();
    }
    
}
