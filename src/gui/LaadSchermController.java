/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import javafx.scene.layout.Pane;

/**
 * FXML Controller class
 *Saving schermpje
 * @author Toon
 */
public class LaadSchermController extends Pane
{
    public LaadSchermController()
    {
        LoaderSchermen.getInstance().setLocation("LaadScherm.fxml", this);
    }
}
