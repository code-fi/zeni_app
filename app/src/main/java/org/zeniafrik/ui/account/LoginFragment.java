package org.zeniafrik.ui.account;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import org.zeniafrik.R;
import org.zeniafrik.R.id;
import org.zeniafrik.R.layout;
import org.zeniafrik.di.Injectable;
import org.zeniafrik.extras.Validator;
import org.zeniafrik.factory.ViewModelFactory;
import org.zeniafrik.helper.ViewLifecycleFragment;
import org.zeniafrik.models.Credential;
import org.zeniafrik.ui.account.ViewModels.LoginViewModel;
import org.zeniafrik.ui.extras.ActivityIndicatorDialog;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

import static java.util.Objects.requireNonNull;
import static org.zeniafrik.extras.Validator.isLessThan;

/**
 * Created by BraDev ${LOCALE} on 4/30/2018.
 */
public class LoginFragment extends ViewLifecycleFragment implements Injectable {


    @BindView(id.phone_input)
    EditText phone_input;
    @BindView(id.password_input)
    EditText password_input;
    @Inject
    ViewModelFactory factory;
    @Inject
    LoginViewModel viewModel;
    private Unbinder unbinder;
    private ActivityIndicatorDialog indicatorDialog;

    @OnClick(id.open_register)
    public void openRegister() {
        getFragmentManager().beginTransaction()
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                .replace(id.account_fragment_holder, new RegisterFragment())
                .addToBackStack(null)
                .commit();
    }

    @OnClick(id.login_button)
    public void attemptLogin() {
        String phone = String.valueOf(phone_input.getText()),
                password = String.valueOf(password_input.getText());
        View v = null;
        String message = null;
        if (!Validator.isValidGhanaNumber(phone)) {
            v = phone_input;

            message = getString(R.string.valid_gh_number_prompt);
        } else if (isLessThan(password, 8)) {
            v = password_input;
            message = getString(R.string.invalid_password_prompt);

        }

        if (message == null) {
            viewModel.attemptLogin(new Credential(phone, password));
            indicatorDialog.show();
        } else {
            v.requestFocus();
            makeText(message);
        }
    }

    @OnClick(id.forgot_password)
    public void openReset() {
        ResetPasswordFragment resetPasswordFragment = new ResetPasswordFragment();
        resetPasswordFragment.show(getChildFragmentManager(), "RESET_");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(layout.login_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        unbinder = ButterKnife.bind(this, view);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        indicatorDialog = new ActivityIndicatorDialog(getContext());

        viewModel = ViewModelProviders.of(this, factory).get(LoginViewModel.class);
        viewModel.getResponse().observe(getViewLifecycleOwner(), response -> {
            if (response != null) switch (response.status) {
                case SUCCESS:
                    makeText(requireNonNull(response.data).getMessage());
                    requireNonNull(getActivity()).finish();
                    break;
                case ERROR:
                    if (indicatorDialog.isShowing()) indicatorDialog.hide();
                    makeText(response.message);
                    break;
            }
        });
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        indicatorDialog.dismiss();
        indicatorDialog = null;
        unbinder.unbind();
    }


    private void makeText(String msg) {
        Toast.makeText(requireNonNull(getContext()), msg, Snackbar.LENGTH_LONG).show();
    }
}
