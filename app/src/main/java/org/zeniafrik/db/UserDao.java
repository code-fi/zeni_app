package org.zeniafrik.db;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import org.zeniafrik.models.UserLocalObject;

import static android.arch.persistence.room.OnConflictStrategy.REPLACE;

@Dao
public interface UserDao {
    @Update(onConflict = REPLACE)
    void updateUserLocal(UserLocalObject userLocalObject);

    @Insert(onConflict = REPLACE)
    void save(UserLocalObject userLocalObject);

    @Query("SELECT * FROM UserDetail LIMIT 1")
    LiveData<UserLocalObject> getUser();

    @Query("DELETE FROM UserDetail")
    void deleteAll();
}
