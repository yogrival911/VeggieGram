
package com.veggiegram.responses.order;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Datum {

    @SerializedName("orderid")
    @Expose
    private Integer orderid;

    @SerializedName("order_id")
    @Expose
    private String orderId;

    @SerializedName("created_at")
    @Expose
    private String createdAt;

    @SerializedName("total")
    @Expose
    private Integer total;
    @SerializedName("GST")
    @Expose
    private Integer gST;
    @SerializedName("shipping_cost")
    @Expose
    private Integer shippingCost;
    @SerializedName("discount")
    @Expose
    private Integer discount;
    @SerializedName("payment_method")
    @Expose
    private String paymentMethod;
    @SerializedName("transaction_id")
    @Expose
    private String transactionId;
    @SerializedName("payment_status_text")
    @Expose
    private String paymentStatusText;
    @SerializedName("order_status")
    @Expose
    private String orderStatus;
    @SerializedName("delivery_status")
    @Expose
    private Integer deliveryStatus;
    @SerializedName("delivered_date")
    @Expose
    private Integer deliveredDate;
    @SerializedName("cancel_request")
    @Expose
    private Integer cancelRequest;
    @SerializedName("cancelled_approved")
    @Expose
    private Integer cancelledApproved;
    @SerializedName("return_request")
    @Expose
    private Integer returnRequest;
    @SerializedName("return_approved")
    @Expose
    private Integer returnApproved;
    @SerializedName("refundtype")
    @Expose
    private String refundtype;
    @SerializedName("Contactperson")
    @Expose
    private String contactperson;
    @SerializedName("contactNo")
    @Expose
    private String contactNo;
    @SerializedName("address")
    @Expose
    private String address;

    public Integer getOrderid() {
        return orderid;
    }

    public void setOrderid(Integer orderid) {
        this.orderid = orderid;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public Integer getGST() {
        return gST;
    }

    public void setGST(Integer gST) {
        this.gST = gST;
    }

    public Integer getShippingCost() {
        return shippingCost;
    }

    public void setShippingCost(Integer shippingCost) {
        this.shippingCost = shippingCost;
    }

    public Integer getDiscount() {
        return discount;
    }

    public void setDiscount(Integer discount) {
        this.discount = discount;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public String getPaymentStatusText() {
        return paymentStatusText;
    }

    public void setPaymentStatusText(String paymentStatusText) {
        this.paymentStatusText = paymentStatusText;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public Integer getDeliveryStatus() {
        return deliveryStatus;
    }

    public void setDeliveryStatus(Integer deliveryStatus) {
        this.deliveryStatus = deliveryStatus;
    }

    public Integer getDeliveredDate() {
        return deliveredDate;
    }

    public void setDeliveredDate(Integer deliveredDate) {
        this.deliveredDate = deliveredDate;
    }

    public Integer getCancelRequest() {
        return cancelRequest;
    }

    public void setCancelRequest(Integer cancelRequest) {
        this.cancelRequest = cancelRequest;
    }

    public Integer getCancelledApproved() {
        return cancelledApproved;
    }

    public void setCancelledApproved(Integer cancelledApproved) {
        this.cancelledApproved = cancelledApproved;
    }

    public Integer getReturnRequest() {
        return returnRequest;
    }

    public void setReturnRequest(Integer returnRequest) {
        this.returnRequest = returnRequest;
    }

    public Integer getReturnApproved() {
        return returnApproved;
    }

    public void setReturnApproved(Integer returnApproved) {
        this.returnApproved = returnApproved;
    }

    public String getRefundtype() {
        return refundtype;
    }

    public void setRefundtype(String refundtype) {
        this.refundtype = refundtype;
    }

    public String getContactperson() {
        return contactperson;
    }

    public void setContactperson(String contactperson) {
        this.contactperson = contactperson;
    }

    public String getContactNo() {
        return contactNo;
    }

    public void setContactNo(String contactNo) {
        this.contactNo = contactNo;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

}
