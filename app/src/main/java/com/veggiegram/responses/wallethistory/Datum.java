
package com.veggiegram.responses.wallethistory;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Datum {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("user_id")
    @Expose
    private Integer userId;
    @SerializedName("amount")
    @Expose
    private Integer amount;
    @SerializedName("is_add")
    @Expose
    private Integer isAdd;
    @SerializedName("is_spent")
    @Expose
    private Integer isSpent;
    @SerializedName("order_id")
    @Expose
    private Object orderId;
    @SerializedName("balance")
    @Expose
    private Integer balance;
    @SerializedName("datetime")
    @Expose
    private String datetime;
    @SerializedName("isdeleted")
    @Expose
    private Integer isdeleted;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("addby")
    @Expose
    private String addby;
    @SerializedName("transection_id")
    @Expose
    private String transectionId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public Integer getIsAdd() {
        return isAdd;
    }

    public void setIsAdd(Integer isAdd) {
        this.isAdd = isAdd;
    }

    public Integer getIsSpent() {
        return isSpent;
    }

    public void setIsSpent(Integer isSpent) {
        this.isSpent = isSpent;
    }

    public Object getOrderId() {
        return orderId;
    }

    public void setOrderId(Object orderId) {
        this.orderId = orderId;
    }

    public Integer getBalance() {
        return balance;
    }

    public void setBalance(Integer balance) {
        this.balance = balance;
    }

    public String getDatetime() {
        return datetime;
    }

    public void setDatetime(String datetime) {
        this.datetime = datetime;
    }

    public Integer getIsdeleted() {
        return isdeleted;
    }

    public void setIsdeleted(Integer isdeleted) {
        this.isdeleted = isdeleted;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAddby() {
        return addby;
    }

    public void setAddby(String addby) {
        this.addby = addby;
    }

    public String getTransectionId() {
        return transectionId;
    }

    public void setTransectionId(String transectionId) {
        this.transectionId = transectionId;
    }

}
