package com.bookislife.sauce.utils;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.Arrays;

/**
 * Password-based encryption
 * <p/>
 * Created by SidneyXu on 2016/01/06.
 */
public class PBEHelper {

    private static final int DEFAULT_ITERATIONS = 10000;
    private static final int DEFAULT_KEY_LENGTH = 256;
    private static final String DEFAULT_CIPHER_TYPE = "AES/CBC/PKCS5Padding";

    private IvParameterSpec iv;
    private byte[] salt;
    private int iterations;
    private String password;
    private String cipherType;

    public PBEHelper(String password, int iterations) {
        this.password = password;
        this.iterations = iterations;
        this.cipherType = DEFAULT_CIPHER_TYPE;
    }

    public PBEHelper(String password) {
        this(password, DEFAULT_ITERATIONS);
    }

    public void setCipherType(String cipherType) {
        this.cipherType = cipherType;
    }

    private SecretKey generatePBEKey(char[] password, byte[] salt) throws NoSuchAlgorithmException, InvalidKeySpecException {
        SecretKeyFactory secretKeyFactory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
        KeySpec keySpec = new PBEKeySpec(password, salt, iterations, DEFAULT_KEY_LENGTH);
        return secretKeyFactory.generateSecret(keySpec);
    }

    public byte[] encryptWithPBE(String plainText, String userPassword) throws Exception {
        SecretKey secretKey = generatePBEKey(userPassword.toCharArray(), getSalt());
        Cipher cipher = Cipher.getInstance(cipherType);
        cipher.init(Cipher.ENCRYPT_MODE, secretKey, getIV());
        return cipher.doFinal(plainText.getBytes("UTF-8"));
    }

    public String decryptWithPBE(byte[] cipherText, String userPassword) throws Exception {
        SecretKey secretKey = generatePBEKey(userPassword.toCharArray(), getSalt());
        Cipher cipher = Cipher.getInstance(cipherType);
        cipher.init(Cipher.DECRYPT_MODE, secretKey, getIV());
        return Arrays.toString(cipher.doFinal(cipherText));
    }

    public IvParameterSpec getIV() {
        if (iv == null) {
            iv = new IvParameterSpec(generateRandomByteArray(32));
        }
        return iv;
    }

    public byte[] getSalt() {
        if (salt == null) {
            salt = generateRandomByteArray(32);
        }
        return salt;
    }

    private static byte[] generateRandomByteArray(int size) {
        byte[] randomByteArray = new byte[size];
        new SecureRandom().nextBytes(randomByteArray);
        return randomByteArray;
    }
}
