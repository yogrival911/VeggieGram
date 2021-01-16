package com.veggiegram.responses.otp;

public class SendOTPObject {
    private String phone;
    private String signature;

    public SendOTPObject(String phone, String signature) {
        this.phone = phone;
        this.signature = signature;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }
}
