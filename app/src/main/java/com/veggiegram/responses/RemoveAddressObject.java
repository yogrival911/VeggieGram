package com.veggiegram.responses;

public class RemoveAddressObject {
    private String addressid;

    public RemoveAddressObject(String addressid) {
        this.addressid = addressid;
    }

    public String getAddressid() {
        return addressid;
    }

    public void setAddressid(String addressid) {
        this.addressid = addressid;
    }
}
