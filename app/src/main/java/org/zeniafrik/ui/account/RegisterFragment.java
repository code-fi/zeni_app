package org.zeniafrik.ui.account;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.zeniafrik.R.id;
import org.zeniafrik.R.layout;
import org.zeniafrik.di.Injectable;

import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by BraDev ${LOCALE} on 4/30/2018.
 */
public class RegisterFragment extends Fragment implements Injectable {

    private Unbinder unbinder;

    @OnClick(id.login)
    public void showLogin() {
        getActivity().onBackPressed();
    }

    @OnClick(id.open_register)
    public void showRegisterForm() {
        new RegistrationFormFragment().show(getChildFragmentManager(), "REGISTER_FORM");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(layout.register_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        unbinder = ButterKnife.bind(this, view);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}