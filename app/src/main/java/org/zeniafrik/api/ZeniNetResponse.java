package org.zeniafrik.api;

import android.support.annotation.Nullable;

public class ZeniNetResponse<T> {
    private T data;
    private meta meta;

    ZeniNetResponse(T data) {
        this.data = data;
    }

    ZeniNetResponse(T data, @Nullable meta meta) {
        this.data = data;
        this.meta = meta;

    }

    public meta getMeta() {
        return meta;
    }

    public T getData() {
        return data;
    }

}
