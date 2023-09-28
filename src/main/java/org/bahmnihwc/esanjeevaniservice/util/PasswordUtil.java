package org.bahmnihwc.esanjeevaniservice.util;


import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class PasswordUtil {
    public static String getEncryptedPassword(String password,String salt) {

        String encryptedValue = encryptSHA512(encryptSHA512(password).toLowerCase() +
                encryptSHA512(salt.toString()).toLowerCase());

        return encryptedValue;
    }

    private static String encryptSHA512(String input) {
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("SHA-512");
            byte[] hashedBytes = messageDigest.digest(input.getBytes(StandardCharsets.UTF_8));

            // Convert the hashed bytes to hexadecimal representation
            StringBuilder hexString = new StringBuilder();
            for (byte b : hashedBytes) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) {
                    hexString.append('0');
                }
                hexString.append(hex);
            }

            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            // Handle exception if the SHA-512 algorithm is not available
            e.printStackTrace();
        }

        return null;
    }
}

