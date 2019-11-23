package org.zeniafrik.models;

import android.support.annotation.Nullable;

public class Feed {
    private final String url, title, image_url, published_on, category;
    private final int post_type;

    public Feed(String url, String title, @Nullable String image_url, String published_on, String category, int post_type) {
        this.url = url;
        this.title = title;
        this.image_url = image_url;
        this.published_on = published_on;
        this.category = category;
        this.post_type = post_type;
    }

    public String getUrl() {
        return url;
    }

    public int getPost_type() {
        return post_type;
    }

    public String getTitle() {
        return title;
    }

    public String getImage_url() {
        return image_url;
    }

    public String getPublished_on() {
        return published_on;
    }

    public String getCategory() {
        return category;
    }
}
