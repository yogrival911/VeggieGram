package com.veggiegram;

public interface ClickCartInterface {
    public void increment(int index, int cartQuantity, String productid, int sellPrice);
    public void decrement(int index, int cartQuanity, String productid, int sellPrice);
    public void clickAdd(int index, int cartQuantity, String productid);
    public void clickWishList(int index, String productid);
    public void clickRemoveWishList(int index, String productid);
}
