package com.example.practiceCTF;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.security.SecureRandom;
import java.security.spec.KeySpec;
import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;

public class AESEncryption {
    private static final String AES_MODE = "AES/CTR/NoPadding";
    private static final int KEY_SIZE = 128;
    private static final int ITERATIONS = 10000;

    @RequiresApi(api = Build.VERSION_CODES.O)
    public static String encrypt(String input, String key) {
        try {
            SecretKey secretKey = generateKey(key);

            byte[] ivBytes = new byte[16];
            new SecureRandom().nextBytes(ivBytes);
            IvParameterSpec iv = new IvParameterSpec(ivBytes);

            Cipher cipher = Cipher.getInstance(AES_MODE);
            cipher.init(Cipher.ENCRYPT_MODE, secretKey, iv);

            byte[] encryptedBytes = cipher.doFinal(input.getBytes());

            byte[] combinedBytes = new byte[ivBytes.length + encryptedBytes.length];
            System.arraycopy(ivBytes, 0, combinedBytes, 0, ivBytes.length);
            System.arraycopy(encryptedBytes, 0, combinedBytes, ivBytes.length, encryptedBytes.length);

            return Base64.getEncoder().encodeToString(combinedBytes);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public static String decrypt(String encryptedInput, String key) {
        try {
            byte[] combinedBytes = Base64.getDecoder().decode(encryptedInput);

            byte[] ivBytes = new byte[16];
            System.arraycopy(combinedBytes, 0, ivBytes, 0, ivBytes.length);
            IvParameterSpec iv = new IvParameterSpec(ivBytes);

            byte[] encryptedBytes = new byte[combinedBytes.length - ivBytes.length];
            System.arraycopy(combinedBytes, ivBytes.length, encryptedBytes, 0, encryptedBytes.length);

            SecretKey secretKey = generateKey(key);

            Cipher cipher = Cipher.getInstance(AES_MODE);
            cipher.init(Cipher.DECRYPT_MODE, secretKey, iv);

            byte[] decryptedBytes = cipher.doFinal(encryptedBytes);

            return new String(decryptedBytes);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private static SecretKey generateKey(String key) throws Exception {
        SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
        KeySpec spec = new PBEKeySpec(key.toCharArray(), new byte[16], ITERATIONS, KEY_SIZE);
        SecretKey tmp = factory.generateSecret(spec);
        return new SecretKeySpec(tmp.getEncoded(), "AES");
    }
}