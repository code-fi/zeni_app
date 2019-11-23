package org.zeniafrik.models;

import android.support.annotation.Nullable;

public class MarketSectionModel<T> {

    public static final String V_LAYOUT = "v", H_LAYOUT = "h", G_LAYOUT = "g";
    public int type;
    public String layout;
    private T SectionList;
    private String SectionTitle;

    public MarketSectionModel(@Nullable String SectionTitle, int type, T SectionList, @Nullable String layout) {
        this.SectionList = SectionList;
        this.type = type;
        this.SectionTitle = SectionTitle;
        this.layout = layout;
    }

    public String getSectionTitle() {
        return SectionTitle;
    }

    public T getSectionList() {
        return SectionList;
    }

    public void setSectionList(T sectionList) {
        SectionList = sectionList;
    }
}