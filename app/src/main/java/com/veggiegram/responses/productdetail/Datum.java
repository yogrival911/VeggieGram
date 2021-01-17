
package com.veggiegram.responses.productdetail;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Datum {

    @SerializedName("productid")
    @Expose
    private Integer productid;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("price")
    @Expose
    private Integer price;
    @SerializedName("sellprice")
    @Expose
    private Integer sellprice;
    @SerializedName("image")
    @Expose
    private String image;
    @SerializedName("description_image")
    @Expose
    private Object descriptionImage;
    @SerializedName("unit")
    @Expose
    private String unit;
    @SerializedName("unitname")
    @Expose
    private String unitname;
    @SerializedName("avail_quantity")
    @Expose
    private Integer availQuantity;
    @SerializedName("whishlisted")
    @Expose
    private Integer whishlisted;
    @SerializedName("cartquantity")
    @Expose
    private Object cartquantity;

    public Integer getProductid() {
        return productid;
    }

    public void setProductid(Integer productid) {
        this.productid = productid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Integer getSellprice() {
        return sellprice;
    }

    public void setSellprice(Integer sellprice) {
        this.sellprice = sellprice;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Object getDescriptionImage() {
        return descriptionImage;
    }

    public void setDescriptionImage(Object descriptionImage) {
        this.descriptionImage = descriptionImage;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getUnitname() {
        return unitname;
    }

    public void setUnitname(String unitname) {
        this.unitname = unitname;
    }

    public Integer getAvailQuantity() {
        return availQuantity;
    }

    public void setAvailQuantity(Integer availQuantity) {
        this.availQuantity = availQuantity;
    }

    public Integer getWhishlisted() {
        return whishlisted;
    }

    public void setWhishlisted(Integer whishlisted) {
        this.whishlisted = whishlisted;
    }

    public Object getCartquantity() {
        return cartquantity;
    }

    public void setCartquantity(Object cartquantity) {
        this.cartquantity = cartquantity;
    }

}
