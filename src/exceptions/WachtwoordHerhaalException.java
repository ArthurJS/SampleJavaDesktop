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
public class WachtwoordHerhaalException extends RuntimeException
{
    public WachtwoordHerhaalException() {
        super(labels.getString("WachtwoordHerhaalException"));
    }

    public WachtwoordHerhaalException(String s) {
        super(s);
    }  
    
    public WachtwoordHerhaalException(String s, Throwable cause){
        super(s, cause);
    }
    public WachtwoordHerhaalException(Throwable cause){
        super(cause);
    }
}
