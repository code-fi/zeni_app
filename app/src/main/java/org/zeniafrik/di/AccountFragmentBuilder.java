package org.zeniafrik.di;

import org.zeniafrik.ui.account.LoginFragment;
import org.zeniafrik.ui.account.RegisterFragment;
import org.zeniafrik.ui.account.RegistrationFormFragment;
import org.zeniafrik.ui.account.ResetPasswordFragment;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
abstract class AccountFragmentBuilder {

    @ContributesAndroidInjector
    abstract LoginFragment loginFragment();

    @ContributesAndroidInjector
    abstract ResetPasswordFragment resetPasswordFragment();

    @ContributesAndroidInjector
    abstract RegisterFragment registerFragment();

    @ContributesAndroidInjector
    abstract RegistrationFormFragment registrationFormFragment();
}
