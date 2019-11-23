package org.zeniafrik.helper;

import android.app.Notification;
import android.app.NotificationManager;

public class PushNotification {

    private static PushNotification instance;
    private final NotificationManager notificationManager;

    private PushNotification(NotificationManager notificationManager) {
        this.notificationManager = notificationManager;
    }

    public static PushNotification getInstance() {
        return instance;
    }

    public static void initialize(NotificationManager notificationManager){
        if (instance == null) instance = new PushNotification(notificationManager);
    }

    public void addNotification(int id,Notification notification){
        notificationManager.notify(id, notification);
    }
}
