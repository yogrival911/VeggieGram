package com.veggiegram.responses;

public class WishListObject {
    private String productid;

    public WishListObject(String productid) {
        this.productid = productid;
    }

    public String getProductid() {
        return productid;
    }

    public void setProductid(String productid) {
        this.productid = productid;
    }
}
