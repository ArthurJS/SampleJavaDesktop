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
public class NieuwSpelbordException extends RuntimeException
{

    public NieuwSpelbordException() {
        super(labels.getString("spelbordFoutAlgemeen"));
    }

    public NieuwSpelbordException(String message) {
        super(message);
    }
    
}