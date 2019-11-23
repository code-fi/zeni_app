package org.zeniafrik.di;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;

import org.zeniafrik.ZeniAfrik;

import dagger.android.AndroidInjection;
import dagger.android.support.AndroidSupportInjection;
import dagger.android.support.HasSupportFragmentInjector;


/**
 * Created by BraDev ${LOCALE} on 1/14/2018.
 */
public class AppInjector {
    private AppInjector() {
    }

    public static void init(ZeniAfrik mainApplication) {
//
        DaggerApplicationComponent.builder().application(mainApplication).build().inject(mainApplication);
//
        mainApplication
                .registerActivityLifecycleCallbacks(new Application.ActivityLifecycleCallbacks() {
                    @Override
                    public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
                        handleActivity(activity);
                    }

                    @Override
                    public void onActivityStarted(Activity activity) {

                    }

                    @Override
                    public void onActivityResumed(Activity activity) {

                    }

                    @Override
                    public void onActivityPaused(Activity activity) {

                    }

                    @Override
                    public void onActivityStopped(Activity activity) {

                    }

                    @Override
                    public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

                    }

                    @Override
                    public void onActivityDestroyed(Activity activity) {

                    }
                });
    }

    private static void handleActivity(Activity activity) {
        if (activity instanceof HasSupportFragmentInjector || activity instanceof Injectable) {
            AndroidInjection.inject(activity);
        }
        if (activity instanceof FragmentActivity) {
            ((FragmentActivity) activity).getSupportFragmentManager().registerFragmentLifecycleCallbacks(new FragmentManager.FragmentLifecycleCallbacks() {
                @Override
                public void onFragmentCreated(FragmentManager fm, Fragment f, Bundle savedInstanceState) {
                    if (f instanceof Injectable) {
                        AndroidSupportInjection.inject(f);
                    }
                }
            }, true);
        }
    }
}
