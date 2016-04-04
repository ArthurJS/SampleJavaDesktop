/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package exceptions;

import static LanguageResources.Resource.labels;

/**
 *
 * @author DBSowner
 */
public class KeuzeMenuOutOfRange extends IllegalArgumentException
{

    public KeuzeMenuOutOfRange() {
        super(labels.getString("keuzemenuOutOfRange"));
    }

    public KeuzeMenuOutOfRange(String s) {
        super(s);
    }
    
    
}
