package org.zeniafrik.di;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import org.zeniafrik.db.UserDao;
import org.zeniafrik.models.UserLocalObject;

@Database(entities = {UserLocalObject.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract UserDao userDao();
}
