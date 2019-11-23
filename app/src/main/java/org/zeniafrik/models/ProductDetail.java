package org.zeniafrik.models;

import java.util.ArrayList;

import javax.annotation.Nullable;

public class ProductDetail {
    protected final int id;
    private String description, slug;
    private int views, likes, image_count;
    private ArrayList<String> images;
    private ProductCategory category;
    private User publisher;
    private Extra extra;

    public ProductDetail(int id, String description, String slug, @Nullable Extra extra, int views,
                         int likes, int image_count, ArrayList<String> images, ProductCategory category, User publisher) {
        this.id = id;
        this.description = description;
        this.slug = slug;
        this.views = views;
        this.likes = likes;
        this.image_count = image_count;
        this.images = images;
        this.extra = extra;
        this.category = category;
        this.publisher = publisher;
    }

    public int getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public String getSlug() {
        return slug;
    }

    public Extra getExtra() {
        return extra;
    }

    public int getViews() {
        return views;
    }

    public int getLikes() {
        return likes;
    }

    public int getImage_count() {
        return image_count;
    }

    public ArrayList<String> getImages() {
        return images;
    }

    public ProductCategory getCategory() {
        return category;
    }

    public User getPublisher() {
        return publisher;
    }

    public static class Extra {
        private String size, mma, color;

        public Extra(String size, String mma, String color) {
            this.size = size;
            this.mma = mma;
            this.color = color;
        }

        public String getSize() {
            return size;
        }

        public String getMma() {
            return mma;
        }

        public String getColor() {
            return color;
        }
    }
}

