package com.mole.utils;

import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;

public class Argon2Encode {
    
    private static String decode = "$argon2i$v=19$m=65536,t=22,p=1";

    public static String encrypt(String strToEncrypt)  {

        // default argon2i, salt 16 bytes, hash length 32 bytes.
        Argon2 argon2 = Argon2Factory.create();

        char[] password = strToEncrypt.toCharArray();

        try {
            // iterations = 10
            // memory = 64m
            // parallelism = 1
            String hash = argon2.hash(22, 65536, 1, password);
            String[] hashed = hash.split("p=1");
            return hashed[1];
        } catch (Exception e) {
            System.out.println("Error while encrypting: " + e.toString());
            return null;
        } finally {
            // Wipe confidential data
            argon2.wipeArray(password);
        }
    }

    public static Boolean verify(String encryptedString, String password)  {
        // default argon2i, salt 16 bytes, hash length 32 bytes.
        Argon2 argon2 = Argon2Factory.create();
        char[] passwordArray = (password).toCharArray();
        try {
            // argon2 verify hash
            if (argon2.verify(decode+encryptedString, passwordArray)) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            System.out.println("Error while verifying: " + e.toString());
            return false;
        } finally {
            // Wipe confidential data
            argon2.wipeArray(passwordArray);
        }
    }

}