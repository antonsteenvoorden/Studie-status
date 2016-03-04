package nl.antonsteenvoorden.ikpmd.service;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;

import nl.antonsteenvoorden.ikpmd.R;

/**
 * Created by Anton on 4-3-2016.
 */
public class NotificationHandler extends Service{
  NotificationManager notificationManager;
  int id;
  public NotificationHandler() {
    notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
  }

  public void sendNotification() {
    NotificationCompat.Builder mBuilder =
        new NotificationCompat.Builder(this)
            .setSmallIcon(R.drawable.ic_thumb_up_white_24dp)
            .setContentTitle("My notification")
            .setContentText("Hello World!");

    notificationManager.notify(id,mBuilder.build());
    id++;
  }

  @Nullable
  @Override
  public IBinder onBind(Intent intent) {
    return null;
  }
}
