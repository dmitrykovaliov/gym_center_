package com.dk.gym.util;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

public class CryptPass {

    private static final Logger LOGGER = LogManager.getLogger();
    private static final String EMPTY_RESULT = "";

    public CryptPass() {
        throw new IllegalStateException("Utility class");
    }

    public static String cryptSha(String pass){


        MessageDigest digest;

        try {
            digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(pass.getBytes(StandardCharsets.UTF_8));

            return Base64.getEncoder().encodeToString(hash);

        } catch (NoSuchAlgorithmException e) {
            LOGGER.log(Level.ERROR, "Crypt SHA-256: " + e);
        }
        return EMPTY_RESULT;
    }
}

