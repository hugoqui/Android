package com.example.hugo.firstapp;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.ContextWrapper;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.RequiresApi;

public class NotificationHelper extends ContextWrapper {

    private static final String channelId = "com.example.hugo.firstapp.EZDRIVE";
    private static final String channelName = "EZDRIVE Channel";
    private NotificationManager manager;

    @RequiresApi(api = Build.VERSION_CODES.O)
    public NotificationHelper(Context base) {
        super(base);
        createChannels();
    }


    private void createChannels(){
        NotificationChannel ezdriveChannel = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            ezdriveChannel = new NotificationChannel(channelId, channelName, NotificationManager.IMPORTANCE_DEFAULT);
            ezdriveChannel.enableLights(true);
            ezdriveChannel.enableVibration(true);
            ezdriveChannel.setLightColor(Color.GREEN);
            ezdriveChannel.setLockscreenVisibility(Notification.VISIBILITY_PRIVATE);

            getManager().createNotificationChannel(ezdriveChannel);
        }
    }

    public NotificationManager getManager() {
        if (manager == null)
            manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        return manager;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public Notification.Builder getEzdriveNotification (String title, String body){
        return new Notification.Builder(getApplicationContext(), channelId)
                .setContentText(body)
                .setContentTitle(title)
                .setSmallIcon(R.mipmap.ic_launcher_round)
                .setAutoCancel(true);
    }
}
