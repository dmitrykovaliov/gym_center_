package com.dk.gym.util;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class CryptPass {

    private static final Logger LOGGER = LogManager.getLogger();

    private static MessageDigest md;

    public static String cryptSha(String pass){
        try {
            md = MessageDigest.getInstance("SHA-256");
            byte[] passBytes = pass.getBytes();
            md.reset();
            byte[] digested = md.digest(passBytes);
            StringBuffer sb = new StringBuffer();
            for(int i=0;i<digested.length;i++){
                sb.append(Integer.toHexString(0xff & digested[i]));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException ex) {
            LOGGER.log(Level.ERROR, CryptPass.class.getName());
        }

        return null;


    }

    //todo
    public static void main(String[] args) {
        LOGGER.log(Level.INFO, CryptPass.cryptSha("1234567A"));
    }
}
