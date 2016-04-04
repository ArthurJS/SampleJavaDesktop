/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package exceptions;

import static LanguageResources.Resource.labels;

/**
 *
 * @author Thibault Fouquaert
 */
public class GebruikersNaamException extends RuntimeException
{

    public GebruikersNaamException() {
        super(labels.getString("controleer"));
    }

    public GebruikersNaamException(String message) {
        super(message);
    }
    
}
