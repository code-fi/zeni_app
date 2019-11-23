package org.zeniafrik.models;

import android.support.annotation.Nullable;

public final class Review {
    private final int id, product_id;
    private final String reviewer_name, reviewer_email, comment, date;
    private final float rating;

    public Review(int id, int product_id, String reviewer_name, String reviewer_email, @Nullable String comment, String date, float rating) {
        this.id = id;
        this.product_id = product_id;
        this.reviewer_name = reviewer_name;
        this.reviewer_email = reviewer_email;
        this.comment = comment;
        this.date = date;
        this.rating = rating;
    }
}
