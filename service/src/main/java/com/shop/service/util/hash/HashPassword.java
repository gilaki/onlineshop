package com.shop.service.util.hash;


import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;

public class HashPassword implements SHA,Salt{

    private StringBuilder hexString;

    private static MessageDigest digest512;
    private static MessageDigest digest256;

    static {
        try {
            digest256 = MessageDigest.getInstance("SHA-256");
            digest512 = MessageDigest.getInstance("SHA-512");

        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String hashPassword256(String password) {
        digest512.reset();
        byte[] hashBytes = getHashBytes256(password);
        hexString = new StringBuilder();
        for (byte b : hashBytes) {
            hexString.append(String.format("%02x", b));
        }
        return hexString.toString();
    }

    @Override
    public String hashPassword512(String password) {
        byte[] hashBytes = getHashBytes512(password);
        hexString = new StringBuilder();
        for (byte b : hashBytes) {
            hexString.append(String.format("%02x", b));
        }
        return hexString.toString();
    }

    @Override
    public String createSalt16() {
        String salt =  generateSalt(16);
        digest256.update(salt.getBytes());
        digest512.update(salt.getBytes());
        System.out.println(salt);
        return salt;
    }

    @Override
    public String createSalt256() {
        String salt =  generateSalt(256);
        digest512.update(salt.getBytes());
        digest256.update(salt.getBytes());
        System.out.println(salt);
        return salt;
    }

    @Override
    public String createSalt512() {
        String salt =  generateSalt(512);
        digest512.update(salt.getBytes());
        digest256.update(salt.getBytes());
        System.out.println("\n"+salt+"\n");
        return salt;
    }

    private void digestUpdate256(String salt){
        digest512.update(salt.getBytes());
    }

    private void digestUpdate512(String salt){
        digest512.update(salt.getBytes());
    }

    private byte[] getHashBytes512(String password){
        return digest512.digest(password.getBytes());
    }
    private byte[] getHashBytes256(String password){
        return digest256.digest(password.getBytes());
    }

    private String generateSalt(int length) {
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[length];
        random.nextBytes(salt);
        return Base64.getEncoder().encodeToString(salt);
    }
    @Override
    public String toString() {
        if (hexString == null) {
            hexString = new StringBuilder();
        }
        return hexString.toString();
    }

    public static void main(String[] args) {
        HashPassword hashPassword = new HashPassword();
        hashPassword.createSalt16();
        hashPassword.hashPassword256("123456");
        System.out.println(hashPassword);
    }


}
