package com.shop.app.hash;

public interface Salt {

    public String createSalt16();

    public String createSalt256();

    public String creteSalt512();
}
