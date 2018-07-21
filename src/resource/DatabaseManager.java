package resource;

import java.util.Locale;
import java.util.ResourceBundle;

public class DatabaseManager {
    private static final ResourceBundle resourceBundle = ResourceBundle.getBundle("resource/database");

    private DatabaseManager() {
    }

    public static String getProperty(String key) {
        return resourceBundle.getString(key);
    }

}
