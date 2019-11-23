package org.zeniafrik.ui.account;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.zeniafrik.R;
import org.zeniafrik.R.layout;

/**
 * Created by BraDev ${LOCALE} on 5/2/2018.
 */
public class ResetPasswordFragment extends AppCompatDialogFragment {
//    private Unbinder unbinder;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        setStyle(AppCompatDialogFragment.STYLE_NO_FRAME, R.style.ZeniAfrik_DialogFullScreen);
        return super.onCreateDialog(savedInstanceState);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
//        unbinder = ButterKnife.bind(this, view);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(layout.reset_password_fragment, container, false);
    }

    @Override
    public void onDestroyView() {
//        unbinder.unbind();
        super.onDestroyView();
    }
}
