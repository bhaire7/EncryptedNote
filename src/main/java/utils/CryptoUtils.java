package utils;

import java.nio.charset.StandardCharsets;
import java.security.spec.KeySpec;
import java.util.Base64;

import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;

public class CryptoUtils {

   
    private static final String SECRET_KEY_FACTORY_ALGORITHM = "PBKDF2WithHmacSHA256";
    
    private static final String SALT = "YourSaltHere"; 

    private static SecretKey getKeyFromPassword(String password) throws Exception {
        SecretKeyFactory factory = SecretKeyFactory.getInstance(SECRET_KEY_FACTORY_ALGORITHM);
        KeySpec spec = new PBEKeySpec(password.toCharArray(), SALT.getBytes(), 65536, 256);
        SecretKey secret = new SecretKeySpec(factory.generateSecret(spec).getEncoded(), "AES");
        return secret;
    }

    public static String encrypt(String input, String password) {
        try {
            
            byte[] inputBytes = input.getBytes(StandardCharsets.UTF_8);
            byte[] keyBytes = password.getBytes(StandardCharsets.UTF_8);
            byte[] outputBytes = new byte[inputBytes.length];

            for (int i = 0; i < inputBytes.length; i++) {
                outputBytes[i] = (byte) (inputBytes[i] ^ keyBytes[i % keyBytes.length]);
            }

            
            return Base64.getEncoder().encodeToString(outputBytes);
        } catch (Exception e) {
            
            throw new RuntimeException("Error encrypting data", e);
        }
    }

    public static String decrypt(String cipherText, String password) {
        try {
            
            byte[] cipherBytes = Base64.getDecoder().decode(cipherText);
            byte[] keyBytes = password.getBytes(StandardCharsets.UTF_8);
            byte[] outputBytes = new byte[cipherBytes.length];

            for (int i = 0; i < cipherBytes.length; i++) {
                outputBytes[i] = (byte) (cipherBytes[i] ^ keyBytes[i % keyBytes.length]);
            }

            return new String(outputBytes, StandardCharsets.UTF_8);
        } catch (Exception e) {
            
            return null; 
        }
    }
}