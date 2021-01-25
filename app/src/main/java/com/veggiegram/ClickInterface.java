package com.veggiegram;

public interface ClickInterface {
    public void click(int index);
    public void clickRemoveCart(int index, String productid);
    public void clickRemoveAddress(int index, int addressid);
    public void clickSelectAddress(int index, int addressid);
}
