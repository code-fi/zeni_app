package org.zeniafrik.models;

import java.util.ArrayList;

public class BaseMarketComponents {
    private ArrayList<Banner> banners;
    private ArrayList<BaseProduct.ProductListing> product_listings;

    public BaseMarketComponents(ArrayList<Banner> banners, ArrayList<BaseProduct.ProductListing>
            product_listings) {
        this.banners = banners;
        this.product_listings = product_listings;
    }

    public ArrayList<Banner> getBanners() {
        return banners;
    }

    public ArrayList<BaseProduct.ProductListing> getProductListings() {
        return product_listings;
    }
}
