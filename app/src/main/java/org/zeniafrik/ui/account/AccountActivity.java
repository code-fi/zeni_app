package org.zeniafrik.ui.account;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import org.zeniafrik.R.id;
import org.zeniafrik.R.layout;
import org.zeniafrik.helper.BaseActivity;

import javax.inject.Inject;

import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.support.HasSupportFragmentInjector;

import static android.support.v4.app.FragmentTransaction.TRANSIT_FRAGMENT_OPEN;

public class AccountActivity extends BaseActivity implements HasSupportFragmentInjector {

    @Inject
    DispatchingAndroidInjector<Fragment> dispatchingAndroidInjector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(layout.account_activity);


        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(id.account_fragment_holder, new LoginFragment())
                    .setTransition(TRANSIT_FRAGMENT_OPEN)
                    .commit();
        }

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public AndroidInjector<Fragment> supportFragmentInjector() {
        return dispatchingAndroidInjector;
    }
}
