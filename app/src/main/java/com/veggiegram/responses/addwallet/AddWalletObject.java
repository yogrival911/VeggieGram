
package com.veggiegram.responses.addwallet;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AddWalletObject {

    @SerializedName("amount")
    @Expose
    private String amount;
    @SerializedName("transection_id")
    @Expose
    private String transectionId;

    public AddWalletObject(String amount, String transectionId) {
        this.amount = amount;
        this.transectionId = transectionId;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getTransectionId() {
        return transectionId;
    }

    public void setTransectionId(String transectionId) {
        this.transectionId = transectionId;
    }

}
