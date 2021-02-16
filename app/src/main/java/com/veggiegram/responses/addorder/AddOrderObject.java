
package com.veggiegram.responses.addorder;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class AddOrderObject {

    @SerializedName("payment_order_id")
    @Expose
    private String paymentOrderId;
    @SerializedName("transaction_id")
    @Expose
    private String transactionId;
    @SerializedName("total")
    @Expose
    private String total;
    @SerializedName("final_total")
    @Expose
    private String finalTotal;
    @SerializedName("shipping_cost")
    @Expose
    private String shippingCost;
    @SerializedName("discount")
    @Expose
    private String discount;
    @SerializedName("payment_method")
    @Expose
    private String paymentMethod;
    @SerializedName("deliver_address_Id")
    @Expose
    private String deliverAddressId;
    @SerializedName("slot")
    @Expose
    private String slot;
    @SerializedName("wallet")
    @Expose
    private String wallet;

    public AddOrderObject(String paymentOrderId, String transactionId, String total, String finalTotal, String shippingCost, String discount, String paymentMethod, String deliverAddressId, String slot, String wallet) {
        this.paymentOrderId = paymentOrderId;
        this.transactionId = transactionId;
        this.total = total;
        this.finalTotal = finalTotal;
        this.shippingCost = shippingCost;
        this.discount = discount;
        this.paymentMethod = paymentMethod;
        this.deliverAddressId = deliverAddressId;
        this.slot = slot;
        this.wallet = wallet;
    }

    public String getPaymentOrderId() {
        return paymentOrderId;
    }

    public void setPaymentOrderId(String paymentOrderId) {
        this.paymentOrderId = paymentOrderId;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getFinalTotal() {
        return finalTotal;
    }

    public void setFinalTotal(String finalTotal) {
        this.finalTotal = finalTotal;
    }

    public String getShippingCost() {
        return shippingCost;
    }

    public void setShippingCost(String shippingCost) {
        this.shippingCost = shippingCost;
    }

    public String getDiscount() {
        return discount;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public String getDeliverAddressId() {
        return deliverAddressId;
    }

    public void setDeliverAddressId(String deliverAddressId) {
        this.deliverAddressId = deliverAddressId;
    }

    public String getSlot() {
        return slot;
    }

    public void setSlot(String slot) {
        this.slot = slot;
    }

    public String getWallet() {
        return wallet;
    }

    public void setWallet(String wallet) {
        this.wallet = wallet;
    }

}
