package com.shop.service.util.hash;

public interface Salt {
    public String createSalt16();
    public String createSalt256();
    public String createSalt512();
}
