package com.hristoforov.elective.utils;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import static com.hristoforov.elective.constants.CommonConstants.SHA_256_ALGORITHM;

public class PasswordHashingUtil {

    public static String encode(String password,String algorithm) {
        try {
            MessageDigest md = MessageDigest.getInstance(algorithm);
            byte[] hash = md.digest(password.getBytes(StandardCharsets.UTF_8));

            BigInteger number = new BigInteger(1, hash);

            StringBuilder hexString = new StringBuilder(number.toString(16));

            while (hexString.length() < 32) {
                hexString.insert(0, '0');
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    public static boolean verify(String hashPassword, String password) {
        return hashPassword.equals(encode(password,SHA_256_ALGORITHM));
    }

}
