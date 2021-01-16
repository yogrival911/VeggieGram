package com.veggiegram.responses;

public class SigninObject {
    private String mobile_no;

    public SigninObject(String mobile_no) {
        this.mobile_no = mobile_no;
    }

    public String getMobile_no() {
        return mobile_no;
    }

    public void setMobile_no(String mobile_no) {
        this.mobile_no = mobile_no;
    }
}
