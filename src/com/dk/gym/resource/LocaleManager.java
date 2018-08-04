package com.dk.gym.resource;

import java.util.Locale;
import java.util.ResourceBundle;

public class LocaleManager {
    private static ResourceBundle resourceBundle = ResourceBundle.getBundle("resource/locale");

    private LocaleManager() {
    }

    public static String getProperty(String key) {
        return resourceBundle.getString(key);
    }

    public static void changeResource(Locale locale) {
        resourceBundle = ResourceBundle.getBundle("resource/locale", locale);
    }
}
