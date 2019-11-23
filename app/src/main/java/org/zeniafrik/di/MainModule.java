package org.zeniafrik.di;

import android.app.Application;
import android.arch.persistence.room.Room;
import android.content.SharedPreferences;

import org.zeniafrik.api.ApplicationService;
import org.zeniafrik.db.UserDao;
import org.zeniafrik.factory.LiveDataCallAdapterFactory;

import java.io.File;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.content.Context.MODE_PRIVATE;
import static java.util.concurrent.TimeUnit.SECONDS;
import static timber.log.Timber.i;

@Module(includes = ViewModelBuilder.class)
public class MainModule {

    @Provides
    @Singleton
    ApplicationService applicationService(OkHttpClient okHttpClient) {
        return new Retrofit.Builder().baseUrl("http://192.168.42.2/zeniafrikapi/public/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(new LiveDataCallAdapterFactory())
                .client(okHttpClient)
                .build()
                .create(ApplicationService.class);
    }

    @Provides
    @Singleton
    HttpLoggingInterceptor httpLoggingInterceptor() {
        return new HttpLoggingInterceptor(message -> i(message)).setLevel(HttpLoggingInterceptor.Level.BODY);
    }

    @Provides
    @Singleton
    CacheControl cacheControl() {
        return new CacheControl.Builder()
                .maxAge(60, SECONDS)
                .build();
    }

    @Provides
    @Singleton
    Cache cache(File file) {
        return new Cache(file, 10 * 1000 * 1000);
    }

    @Provides
    @Singleton
    OkHttpClient okHttpClient(Cache cache, HttpLoggingInterceptor httpLoggingInterceptor, SharedPreferences preferences, CacheControl cacheControl) {
        return new OkHttpClient.Builder()
                .addInterceptor(chain -> {
                    String token = String.format("Bearer %s", preferences.getString("token", "ZeniAccessToken"));
                    return chain.proceed(chain.request().newBuilder()
                            .addHeader("Cache-control", cacheControl.toString())
                            .addHeader("Authorization", token)
                            .header("Content-Type", "application/x-www-form-urlencoded; charset=utf-8")
                            .header("Accept", "application/json")
                            .header("User-Agent", "Zeniafrik Android App")
                            .build());
                })
                .addInterceptor(httpLoggingInterceptor)
                .cache(cache)
                .build();
    }

    @Provides
    @Singleton
    File file(Application application) {
        return new File(application.getCacheDir(), "okhttp-cache");

    }


    @Provides
    @Singleton
    AppDatabase database(Application application) {
        return Room.databaseBuilder(application, AppDatabase.class, "org.zeniafrik.local-db").build();
    }

    @Provides
    @Singleton
    UserDao userDao(AppDatabase database) {
        return database.userDao();
    }
//
//    @Provides
//    @Singleton
//    SchoolDao schoolDao(AppDatabase database){
//        return database.schoolDao();
//    }
//
//    @Provides
//    @Singleton
//    MentorDao mentorDao(AppDatabase database){
//        return database.mentorDao();
//    }

    @Provides
    @Singleton
    SharedPreferences sharedPreferences(Application application) {
        return application.getSharedPreferences("com.zeniafrik.shared", MODE_PRIVATE);
    }
}
