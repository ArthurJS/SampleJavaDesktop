
package exceptions;

import static LanguageResources.Resource.labels;


public class WachtwoordException extends RuntimeException 
{
      
    public WachtwoordException() {
        super(labels.getString("WachtwoordException"));
    }

    public WachtwoordException(String s) {
        super(s);
    }  
    
    public WachtwoordException(String s, Throwable cause){
        super(s, cause);
    }
    public WachtwoordException(Throwable cause){
        super(cause);
    }
}
