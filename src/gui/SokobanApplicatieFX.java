/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import domein.DomeinController;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import resources.ResourceHandling;

/**
 *
 * @author donovandesmedt
 */
public class SokobanApplicatieFX 
{
    
    public SokobanApplicatieFX(Stage primaryStage) throws Exception
    {
//        LoginScherm login = new LoginScherm(new DomeinController());
//        RegistratieScherm registreer = new RegistratieScherm(new DomeinController());
//        WelkomScherm welkom = new WelkomScherm(new DomeinController());
       
      WelkomSchermController root = new WelkomSchermController();
        Scene scene = new Scene(root, 382, 407);
        
//     ConfigureerSchermController root = new ConfigureerSchermController(new DomeinController());
//        Scene scene = new Scene(root, 600, 400);
        
        primaryStage.setTitle("Welkom bij Sokoban");
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    
}
