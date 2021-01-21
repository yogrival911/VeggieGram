package com.veggiegram.responses;

public class RemoveCartObject {
    private String productid;

    public RemoveCartObject(String productid) {
        this.productid = productid;
    }

    public String getProductid() {
        return productid;
    }

    public void setProductid(String productid) {
        this.productid = productid;
    }
}
