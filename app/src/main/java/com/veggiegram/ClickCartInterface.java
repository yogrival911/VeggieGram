package com.veggiegram;

public interface ClickCartInterface {
    public void increment(int index, int cartQuantity, String productid);
    public void decrement(int index, int cartQuanity, String productid);
    public void clickAdd(int index, int cartQuantity);
}
