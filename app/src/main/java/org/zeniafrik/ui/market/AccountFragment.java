package org.zeniafrik.ui.market;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.zeniafrik.R;
import org.zeniafrik.R.layout;
import org.zeniafrik.di.Injectable;
import org.zeniafrik.factory.ViewModelFactory;
import org.zeniafrik.models.UserLocalObject;
import org.zeniafrik.ui.account.AccountActivity;
import org.zeniafrik.ui.market.ViewModels.AccountFragmentViewModel;
import org.zeniafrik.ui.user.basic.DashboardActivity;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by BraDev ${LOCALE} on 5/2/2018.
 */
public class AccountFragment extends Fragment implements Injectable {

    @Inject
    SharedPreferences sharedPreferences;

    @Inject
    ViewModelFactory factory;

    @Inject
    AccountFragmentViewModel accountFragmentViewModel;

    private Context mContext;
    private Unbinder unbinder;
    private UserLocalObject userLocalObject;

    @OnClick(R.id.mp_btn)
    public void openProfile() {
        Intent intent = new Intent();
        if (userLocalObject != null) {
            if (userLocalObject.account_type.equalsIgnoreCase("basic")) intent.setClass(mContext, DashboardActivity.class);
            else intent.setClass(mContext, org.zeniafrik.ui.user.premium.DashboardActivity.class);
        } else {
            intent.setClass(mContext, AccountActivity.class);
        }
        startActivity(intent);
    }

    @OnClick(R.id.order_btn)
    public void openOrders() {

    }

    @OnClick(R.id.faq_btn)
    public void openFAQ() {
        startWebViewActivity(getString(R.string.faq), "FAQ");
    }

    @OnClick(R.id.pp_btn)
    public void openPrivacyPolicy() {
        startWebViewActivity(getString(R.string.url_privacy_policy), getString(R.string.title_privacy_policy));
    }

    @OnClick(R.id.tou_btn)
    public void openTermsOfUse() {
        startWebViewActivity(getString(R.string.url_tou), getString(R.string.title_tou));
    }

    @OnClick(R.id.bp_btn)
    public void openBuyerProtection() {
        startWebViewActivity(getString(R.string.url_b_pro), getString(R.string.title_b_pro));
    }

    @OnClick(R.id.cu_btn)
    public void openContact() {

    }

    @OnClick(R.id.share_btn)
    public void shareApp() {
        Intent shareIntent = new Intent();
        shareIntent.setAction(Intent.ACTION_SEND)
                .putExtra(Intent.EXTRA_TEXT, getString(R.string.share_app_content))
                .setType("text/plain");
        startActivity(Intent.createChooser(shareIntent, getString(R.string.share_app)));
    }

    @OnClick(R.id.rate_btn)
    public void rateApp() {

    }

    private void startWebViewActivity(@NonNull String url, @Nullable String title) {
        Intent intent = new Intent();
        String[] a = {title, "https://zeniconsult.com/"+url};
        intent.putExtra("a", a);
        intent.setClass(mContext, WebViewActivity.class);
        startActivity(intent);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        unbinder = ButterKnife.bind(this, view);
        mContext = getActivity();

        accountFragmentViewModel = ViewModelProviders.of(AccountFragment.this,factory).get(AccountFragmentViewModel.class);
        accountFragmentViewModel.getLocalUserObject().observe(AccountFragment.this, user_response->this.userLocalObject = user_response.data);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(layout.profile_fragment, container, false);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
        mContext = null;
    }
}