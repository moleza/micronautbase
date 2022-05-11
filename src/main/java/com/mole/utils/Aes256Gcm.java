package com.mole.utils;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;

public class Aes256Gcm {

    //32 Character Key Needed
    public static String encrypt(String strToEncrypt, String key) {
        try {
            byte[] encryptionKey = key.getBytes();
            String ciphertextBase64 = aesGcmEncryptToBase64(encryptionKey, strToEncrypt);
            return ciphertextBase64;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    //32 Character Key Needed
    public static String decrypt(String strToDecrypt, String key) {
        try {
            byte[] encryptionKey = key.getBytes();
            String decryptedtext = aesGcmDecryptFromBase64(encryptionKey, strToDecrypt);
            return decryptedtext;
       } catch(Exception e) {
           e.printStackTrace();
           return null;
       }
    }

    private static byte[] generateRandomNonce() {
        SecureRandom secureRandom = new SecureRandom();
        byte[] nonce = new byte[12];
        secureRandom.nextBytes(nonce);
        return nonce;
    }

    private static String aesGcmEncryptToBase64(byte[] key, String data) throws NoSuchPaddingException, NoSuchAlgorithmException,
            InvalidAlgorithmParameterException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException {
        byte[] nonce = generateRandomNonce();
        SecretKeySpec secretKeySpec = new SecretKeySpec(key, "AES");
        GCMParameterSpec gcmParameterSpec = new GCMParameterSpec(16 * 8, nonce);
        Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding");
        cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec, gcmParameterSpec);
        byte[] ciphertextWithTag = cipher.doFinal(data.getBytes(StandardCharsets.UTF_8));
        byte[] ciphertext = new byte[(ciphertextWithTag.length-16)];
        byte[] gcmTag = new byte[16];
        System.arraycopy(ciphertextWithTag, 0, ciphertext, 0, (ciphertextWithTag.length - 16));
        System.arraycopy(ciphertextWithTag, (ciphertextWithTag.length-16), gcmTag, 0, 16);
        String nonceBase64 = base64Encoding(nonce);
        String ciphertextBase64 = base64Encoding(ciphertext);
        String gcmTagBase64 = base64Encoding(gcmTag);
        return nonceBase64 + ":" + ciphertextBase64 + ":" + gcmTagBase64;
    }

    private static String aesGcmDecryptFromBase64(byte[] key, String data) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidAlgorithmParameterException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException {
        String[] parts = data.split(":", 0);
        byte[] nonce = base64Decoding(parts[0]);
        byte[] ciphertextWithoutTag = base64Decoding(parts[1]);
        byte[] gcmTag = base64Decoding(parts[2]);
        byte[] encryptedData = concatenateByteArrays(ciphertextWithoutTag, gcmTag);
        Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding");
        SecretKeySpec secretKeySpec = new SecretKeySpec(key, "AES");
        GCMParameterSpec gcmParameterSpec = new GCMParameterSpec(16 * 8, nonce);
        cipher.init(Cipher.DECRYPT_MODE, secretKeySpec, gcmParameterSpec);
        return new String(cipher.doFinal(encryptedData));
    }

    private static String base64Encoding(byte[] input) {
        return Base64.getEncoder().encodeToString(input);
    }
    private static byte[] base64Decoding(String input) {
        return Base64.getDecoder().decode(input);
    }

    public static byte[] concatenateByteArrays(byte[] a, byte[] b) {
        return ByteBuffer
                .allocate(a.length + b.length)
                .put(a).put(b)
                .array();
    }
}
