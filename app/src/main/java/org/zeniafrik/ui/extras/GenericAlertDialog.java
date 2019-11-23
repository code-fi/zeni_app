package org.zeniafrik.ui.extras;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.widget.TextView;

import org.zeniafrik.R;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class GenericAlertDialog extends Dialog {
    private String message;
    public int type;

    public void setType(int type) {
        this.type = type;
    }

    void setMessage(String message) {
        this.message = message;
    }

    @BindView(R.id.error_view)
    TextView error_view;

    @BindView(R.id.error_type)
    TextView error_type;

    @BindString(R.string.oops)
    public String oops;

    @BindString(R.string.alert_success_type)
    public String success;

    @OnClick(R.id.action_btn)
    public void onAction() {
        cancel();
    }

    public GenericAlertDialog(@NonNull Context context, String message, int type) {
        super(context, R.style.Theme_AppCompat_DayNight_Dialog_Alert);
        setContentView(R.layout.generic_alert_component);
        setMessage(message);
        setType(type);
    }

    public GenericAlertDialog(@NonNull Context context, boolean cancelable, @Nullable OnCancelListener cancelListener, String message, int type) {
//        super(context, cancelable, cancelListener);
        super(context, R.style.Theme_AppCompat_DayNight_Dialog_Alert);
        setCancelable(cancelable);
        if(cancelListener!=null)setOnCancelListener(cancelListener);
        setContentView(R.layout.generic_alert_component);
        setMessage(message);
        setType(type);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
         ButterKnife.bind(this);
        error_view.setText(message);
        if (type == 2) error_type.setText(oops);
    }
}
