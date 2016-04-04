
package LanguageResources;

import java.text.MessageFormat;
import java.util.Locale;
import java.util.ResourceBundle;




public class Resource {
    
    public static  ResourceBundle labels;
    public static final Locale[] supportedLocales = {
            Locale.ENGLISH,
            Locale.FRENCH,
            Locale.ROOT
        };
    
//    public enum ResourceBundleType {
//    LABELS(ResourceBundle.getBundle("Labels", Locale.ENGLISH)),
//    ERRORS(ResourceBundle.getBundle("Errors", Locale.ENGLISH));
//
//    private final ResourceBundle bundle;
//
//    private ResourceBundleType(ResourceBundle bundle) {
//        this.bundle = bundle;
//    }
//
//    public ResourceBundle getBundle() {
//        return bundle;
//    }
//}
//
//    private static Resource instance;
//    private ResourceBundle bundle;
//
//    private Resource(ResourceBundle bundle) {
//        this.bundle = bundle;
//    }
//
//    public static synchronized Resource getInstance(ResourceBundle bundle) {
//
//        if (instance == null) {
//            instance = new Resource(bundle);
//        } else if (!instance.bundle.getBaseBundleName().equals(bundle.getBaseBundleName())) {
//            instance = new Resource(bundle);
//        }
//
//        return instance;
//    }
//
//    public String getString(String key) {
//        if (bundle.containsKey(key))
//            return this.bundle.getString(key);
//
//        return null;
//    }
//
//    public String getString(String key, Object... params) {
//        if (bundle.containsKey(key))
//            return MessageFormat.format(this.bundle.getString(key), params);
//
//        return null;
//    }
}
