package org.zeniafrik.services;

import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

/**
 * Created by BraDev ${LOCALE} on 4/24/2018.
 */
public class LIDInstanceService extends FirebaseInstanceIdService {
    private final String TAG = "FirebaseIDService";

    @Override
    public void onTokenRefresh() {
        super.onTokenRefresh();
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        Log.d(TAG, "Refreshed token: " + refreshedToken);
    }
}
