package org.zeniafrik.models;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.Nullable;

import java.util.List;

/**
 * Created by BraDev ${LOCALE} on 5/8/2018.
 */
public class BaseProduct implements Parcelable {

    public static final Creator<BaseProduct> CREATOR = new Creator<BaseProduct>() {
        @Override
        public BaseProduct createFromParcel(Parcel in) {
            return new BaseProduct(in);
        }

        @Override
        public BaseProduct[] newArray(int size) {
            return new BaseProduct[size];
        }
    };
    protected final int id;
    public String name, image_url, publisher,status;
    private double rating, price;
    private String localDes,sizes,colors,target;
    private int category_id;

    public BaseProduct(int id, String name, double price, String image_url, String publisher, double rating,@Nullable String status) {
        this.name = name;
        this.id = id;
        this.price = price;
        this.rating = rating;
        this.image_url = image_url;
        this.publisher = publisher;
        this.status = status;
    }

    public BaseProduct(String name, double price, String description, @Nullable String size, @Nullable String colors, String target, int category_id){
        id = 1;
        this.name = name;
        this.price = price;
        this.localDes = description;
        sizes= size;
        this.target=target;
        this.colors=colors;
        this.category_id = category_id;
    }

    public String getLocalDes() {
        return localDes;
    }

    public String getSizes() {
        return sizes;
    }

    public String getColors() {
        return colors;
    }

    public String getTarget() {
        return target;
    }

    protected BaseProduct(Parcel in) {
        id = in.readInt();
        name = in.readString();
        image_url = in.readString();
        publisher = in.readString();
        rating = in.readDouble();
        price = in.readDouble();
    }

    public int getId() {
        return id;
    }

    public double getPrice() {
        return price;
    }

    public double getRating() {
        return rating;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(name);
        dest.writeString(image_url);
        dest.writeString(publisher);
        dest.writeDouble(rating);
        dest.writeDouble(price);
    }

    public int getCategory_id() {
        return category_id;
    }

    public class ProductListing {
        public String title;
        public List<BaseProduct> data;

        public ProductListing(String title, List<BaseProduct> data) {
            this.title = title;
            this.data = data;
        }
    }
}
