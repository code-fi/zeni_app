package org.zeniafrik.models;

/**
 * Created by BraDev ${LOCALE} on 5/8/2018.
 */
public class BaseShop {
    protected String name, price, imageUrl;

    public BaseShop(String name, String price, String imageUrl) {
        this.name = name;
        this.price = price;
        this.imageUrl = imageUrl;
    }

    public String getName() {
        return name;
    }

    public String getPrice() {
        return price;
    }

    public String getImageUrl() {
        return imageUrl;
    }
}
