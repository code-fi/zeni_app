package org.zeniafrik.ui.account;

import android.app.Dialog;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import org.zeniafrik.R;
import org.zeniafrik.R.layout;
import org.zeniafrik.di.Injectable;
import org.zeniafrik.factory.ViewModelFactory;
import org.zeniafrik.models.RegisterFormObject;
import org.zeniafrik.ui.account.ViewModels.RegistrationViewModel;
import org.zeniafrik.ui.extras.ActivityIndicatorDialog;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

import static java.util.Objects.requireNonNull;
import static org.zeniafrik.extras.Validator.isLessThan;
import static org.zeniafrik.extras.Validator.isValidEmail;
import static org.zeniafrik.extras.Validator.isValidGhanaNumber;

/**
 * Created by BraDev ${LOCALE} on 5/2/2018.
 */
public class RegistrationFormFragment extends AppCompatDialogFragment implements Injectable {
    @Inject
    ViewModelFactory factory;
    @Inject
    RegistrationViewModel viewModel;
    @BindView(R.id.name_input)
    EditText name_input;
    @BindView(R.id.phone_input)
    EditText phone_input;
    @BindView(R.id.email_input)
    EditText email_input;
    @BindView(R.id.password_input)
    EditText password_input;
    @BindView(R.id.password_confirmation_input)
    EditText password_confirm_input;
    private Unbinder unbinder;
    private ActivityIndicatorDialog indicatorDialog;

    @OnClick(R.id.register_button)
    public void attemptRegister() {
        String name = name_input.getText().toString(),
                phone = phone_input.getText().toString(),
                email = email_input.getText().toString(),
                password = password_input.getText().toString(),
                password_confirmation = password_confirm_input.getText().toString(),
                msg = null;

        View view = null;

        if (isLessThan(name, 3)) {
            view = name_input;
            msg = getString(R.string.fashion_min_error);
        } else if (!isValidGhanaNumber(phone)) {
            view = phone_input;
            msg = getString(R.string.valid_gh_number_prompt);
        } else if (!isValidEmail(email)) {
            view = email_input;
            msg = getString(R.string.valid_email_prompt);
        } else if (isLessThan(password, 8)) {
            view = password_input;
            msg = getString(R.string.password_min_prompt);
        } else if (!password.equals(password_confirmation)) {
            view = password_confirm_input;
            msg = getString(R.string.password_not_match_prompt);
        }

        if (msg != null) {
            view.requestFocus();
            showText(msg);
        } else {
            indicatorDialog.show();
            viewModel.setRegisterFormObject(new RegisterFormObject(name, email, password,
                    phone));
        }

    }

    @OnClick(R.id.tns)
    public void tnsOnClick() {

    }


    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        setStyle(AppCompatDialogFragment.STYLE_NO_FRAME, R.style.ZeniAfrik_DialogFullScreen);
        return super.onCreateDialog(savedInstanceState);
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(layout.register_form_component, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        indicatorDialog = new ActivityIndicatorDialog(getContext());
        viewModel = ViewModelProviders.of(RegistrationFormFragment.this, factory).get
                (RegistrationViewModel.class);

        viewModel.getResponse().observe(RegistrationFormFragment.this, response -> {
            switch (requireNonNull(response).status) {
                case SUCCESS:
                    showText(requireNonNull(response.data).getMessage());
                    dismiss();
                    requireNonNull(getActivity()).finish();
                    break;
                case ERROR:
                    if (indicatorDialog.isShowing()) indicatorDialog.hide();
                    showText(response.message);
                    break;
            }
        });
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        unbinder = ButterKnife.bind(this, view);
    }

    @Override
    public void onDestroyView() {
        unbinder.unbind();
        if (indicatorDialog.isShowing()) indicatorDialog.dismiss();
        indicatorDialog = null;
        super.onDestroyView();
    }

    private void showText(String msg) {
        Toast.makeText(requireNonNull(getContext()), msg, Snackbar.LENGTH_LONG).show();
    }
}
