package com.shop.service.util.hash;

public interface SHA {
    public String hashPassword256(String password);
    public String hashPassword512(String password);
}
