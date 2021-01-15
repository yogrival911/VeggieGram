
package com.veggiegram.responses.banner;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Datum {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("image")
    @Expose
    private String image;
    @SerializedName("tittle")
    @Expose
    private String tittle;
    @SerializedName("bannercount")
    @Expose
    private Integer bannercount;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getTittle() {
        return tittle;
    }

    public void setTittle(String tittle) {
        this.tittle = tittle;
    }

    public Integer getBannercount() {
        return bannercount;
    }

    public void setBannercount(Integer bannercount) {
        this.bannercount = bannercount;
    }

}
