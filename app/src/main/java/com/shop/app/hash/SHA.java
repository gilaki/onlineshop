package com.shop.app.hash;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public interface SHA {

    public String hashPassword256(String password);

    public String hashPassword512(String password);
}
