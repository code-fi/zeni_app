package org.zeniafrik;


import android.app.Activity;
import android.app.Application;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.squareup.leakcanary.LeakCanary;

import org.zeniafrik.di.AppInjector;

import javax.inject.Inject;

import dagger.android.DispatchingAndroidInjector;
import dagger.android.HasActivityInjector;
import timber.log.Timber;


public class ZeniAfrik extends Application implements HasActivityInjector {

    @Inject
    DispatchingAndroidInjector<Activity> dispatchingAndroidInjector;

    @Override
    public void onCreate() {
        super.onCreate();

        Fresco.initialize(this);
        if (BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());
        }
        if (LeakCanary.isInAnalyzerProcess(this)) {
            // This process is dedicated to LeakCanary for heap analysis.
            // You should not init your app in this process.
            return;
        }
//        PushNotification.initialize((NotificationManager) getSystemService(NOTIFICATION_SERVICE));
        LeakCanary.install(this);
        AppInjector.init(this);
    }



    @Override
    public DispatchingAndroidInjector<Activity> activityInjector() {
        return dispatchingAndroidInjector;
    }

}
