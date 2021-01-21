
package com.veggiegram.responses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AddAddressObject {

    @SerializedName("pin_location")
    @Expose
    private String pinLocation;
    @SerializedName("latitude")
    @Expose
    private String latitude;
    @SerializedName("longitude")
    @Expose
    private String longitude;
    @SerializedName("userid")
    @Expose
    private String userid;
    @SerializedName("house")
    @Expose
    private String house;
    @SerializedName("street")
    @Expose
    private String street;
    @SerializedName("city")
    @Expose
    private String city;
    @SerializedName("district")
    @Expose
    private String district;
    @SerializedName("state")
    @Expose
    private String state;
    @SerializedName("mobile")
    @Expose
    private String mobile;
    @SerializedName("PostCode")
    @Expose
    private String postCode;
    @SerializedName("firstname")
    @Expose
    private String firstname;
    @SerializedName("lastname")
    @Expose
    private String lastname;
    @SerializedName("landmark")
    @Expose
    private String landmark;

    public AddAddressObject(String pinLocation, String latitude, String longitude, String userid, String house, String street, String city, String district, String state, String mobile, String postCode, String firstname, String lastname, String landmark) {
        this.pinLocation = pinLocation;
        this.latitude = latitude;
        this.longitude = longitude;
        this.userid = userid;
        this.house = house;
        this.street = street;
        this.city = city;
        this.district = district;
        this.state = state;
        this.mobile = mobile;
        this.postCode = postCode;
        this.firstname = firstname;
        this.lastname = lastname;
        this.landmark = landmark;
    }

    public String getPinLocation() {
        return pinLocation;
    }

    public void setPinLocation(String pinLocation) {
        this.pinLocation = pinLocation;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getHouse() {
        return house;
    }

    public void setHouse(String house) {
        this.house = house;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getPostCode() {
        return postCode;
    }

    public void setPostCode(String postCode) {
        this.postCode = postCode;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getLandmark() {
        return landmark;
    }

    public void setLandmark(String landmark) {
        this.landmark = landmark;
    }

}
