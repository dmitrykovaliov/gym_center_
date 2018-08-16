package com.dk.gym.resource;

import java.util.Locale;
import java.util.ResourceBundle;

/**
 * The Class LocaleManager. Getting resource value with key parameter.
 * Initializing and changing resource file due to changing locale parameter.
 */
public class LocaleManager {
    
    /** The resource bundle. */
    private static ResourceBundle resourceBundle = ResourceBundle.getBundle("resource/locale");

    /**
     * Instantiates a new locale manager.
     */
    private LocaleManager() {
    }

    /**
     * Gets the property.
     *
     * @param key the key
     * @return the property
     */
    public static String getProperty(String key) {
        return resourceBundle.getString(key);
    }

    /**
     * Change resource.
     *
     * @param locale the locale
     */
    public static void changeResource(Locale locale) {
        resourceBundle = ResourceBundle.getBundle("resource/locale", locale);
    }
}
