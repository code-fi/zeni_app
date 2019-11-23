package org.zeniafrik.models;

public final class Banner {
    private final int type, id;
    private final String imageurl;

    public Banner(int type, int id, String imageurl) {
        this.type = type;
        this.id = id;
        this.imageurl = imageurl;
    }

    public int getType() {
        return type;
    }

    public int getId() {
        return id;
    }

    public String getImageUrl() {
        return imageurl;
    }
}
