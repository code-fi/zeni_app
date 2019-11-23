package org.zeniafrik.models;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "UserDetail")
public class UserLocalObject {
    @PrimaryKey(autoGenerate = true)
    public int id;

    public String last_update,name,email,phone,account_type;

    public UserLocalObject() {
    }
}
