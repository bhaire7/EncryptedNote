package utils;

import java.nio.charset.StandardCharsets;
import java.security.spec.KeySpec;
import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;

public class CryptoUtils {

    private static final String ALGORITHM = "AES/CBC/PKCS5Padding";
    private static final String SECRET_KEY_FACTORY_ALGORITHM = "PBKDF2WithHmacSHA256";
    private static final byte[] IV = { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15 }; // A fixed IV for simplicity, but a random one is better for production
    private static final String SALT = "YourSaltHere"; // Should be unique and stored securely

    private static SecretKey getKeyFromPassword(String password) throws Exception {
        SecretKeyFactory factory = SecretKeyFactory.getInstance(SECRET_KEY_FACTORY_ALGORITHM);
        KeySpec spec = new PBEKeySpec(password.toCharArray(), SALT.getBytes(), 65536, 256);
        SecretKey secret = new SecretKeySpec(factory.generateSecret(spec).getEncoded(), "AES");
        return secret;
    }

    public static String encrypt(String input, String password) {
        try {
            SecretKey key = getKeyFromPassword(password);
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            cipher.init(Cipher.ENCRYPT_MODE, key, new IvParameterSpec(IV));
            byte[] cipherText = cipher.doFinal(input.getBytes(StandardCharsets.UTF_8));
            return Base64.getEncoder().encodeToString(cipherText);
        } catch (Exception e) {
            // In a real app, handle this more gracefully
            throw new RuntimeException("Error encrypting data", e);
        }
    }

    public static String decrypt(String cipherText, String password) {
        try {
            SecretKey key = getKeyFromPassword(password);
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            cipher.init(Cipher.DECRYPT_MODE, key, new IvParameterSpec(IV));
            byte[] plainText = cipher.doFinal(Base64.getDecoder().decode(cipherText));
            return new String(plainText, StandardCharsets.UTF_8);
        } catch (Exception e) {
            // This can happen if the password is wrong
            return null; 
        }
    }
}