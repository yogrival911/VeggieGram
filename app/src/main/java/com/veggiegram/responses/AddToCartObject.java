package com.veggiegram.responses;

public class AddToCartObject {
    private String productid;
    private String quantity;

    public AddToCartObject(String productid, String quantity) {
        this.productid = productid;
        this.quantity = quantity;
    }

    public String getProductid() {
        return productid;
    }

    public void setProductid(String productid) {
        this.productid = productid;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }
}
