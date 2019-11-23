package org.zeniafrik.ui.extras;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import org.zeniafrik.R;


public class ActivityIndicatorDialog extends Dialog {

    public ActivityIndicatorDialog(@NonNull Context context) {
        super(context, R.style.ZeniAfrik_DialogFullScreen);
        setContentView(R.layout.loader_component);
    }


    protected ActivityIndicatorDialog(@NonNull Context context, boolean cancelable, @Nullable OnCancelListener cancelListener) {
        super(context, false, cancelListener);
    }


    @Override
    public void onBackPressed() {

//        super.onBackPressed();
    }
}
