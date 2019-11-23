package org.zeniafrik.models;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.Nullable;

public final class ProductCategory implements Parcelable {
    public static final Creator<ProductCategory> CREATOR = new Creator<ProductCategory>() {
        @Override
        public ProductCategory createFromParcel(Parcel in) {
            return new ProductCategory(in);
        }

        @Override
        public ProductCategory[] newArray(int size) {
            return new ProductCategory[size];
        }
    };
    private int id, parent_id;
    private String name, slug, image_url;

    public ProductCategory(int id, int parent_id, String name, @Nullable String slug, @Nullable String image_url) {
        this.id = id;
        this.parent_id = parent_id;
        this.name = name;
        this.slug = slug;
        this.image_url = image_url;
    }

    public ProductCategory(int id, int parent_id, String name, String image_url) {
        this.id = id;
        this.parent_id = parent_id;
        this.name = name;
        this.image_url = image_url;
    }

    public ProductCategory(int id, String name, String slug) {
        this.id = id;
        this.name = name;
        this.slug = slug;
    }

    protected ProductCategory(Parcel in) {
        id = in.readInt();
        parent_id = in.readInt();
        name = in.readString();
        slug = in.readString();
        image_url = in.readString();
    }

    public int getParent_id() {
        return parent_id;
    }

    public String getName() {
        return name;
    }

    public String getSlug() {
        return slug;
    }

    public String getImage_url() {
        return image_url;
    }

    public int getId() {
        return id;
    }

    public int getParentId() {
        return parent_id;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeInt(parent_id);
        dest.writeString(name);
        dest.writeString(slug);
    }
}


