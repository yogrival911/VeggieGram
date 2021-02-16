
package com.veggiegram.responses.addorder;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class OrderDatum {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("qty")
    @Expose
    private Integer qty;
    @SerializedName("price")
    @Expose
    private Integer price;

    public OrderDatum(Integer id, Integer qty, Integer price) {
        this.id = id;
        this.qty = qty;
        this.price = price;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getQty() {
        return qty;
    }

    public void setQty(Integer qty) {
        this.qty = qty;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

}
