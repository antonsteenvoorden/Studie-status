package nl.antonsteenvoorden.ikpmd.service;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Icon;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import android.view.View;

import nl.antonsteenvoorden.ikpmd.App;
import nl.antonsteenvoorden.ikpmd.R;
import nl.antonsteenvoorden.ikpmd.activity.MainActivity;
import nl.antonsteenvoorden.ikpmd.interfaces.Callback;

/**
 * Created by Anton on 4-3-2016.
 */
public class NotificationHandler extends Service implements Callback {


  @Nullable
  @Override
  public IBinder onBind(Intent intent) {
    return null;
  }


  @Override
  public int onStartCommand(Intent intent, int flags, int startId) {
    SharedPreferences settings = getSharedPreferences("LaunchPreferences", 0);
    ModuleObtainer moduleObtainer = ((App) getApplication()).getModuleObtainer(
        settings.getString("username", "None"),
        settings.getString("password", "None")
    );
    moduleObtainer.setCallBack(this);
    moduleObtainer.execute();
    return START_STICKY;
  }


  public void showNotification() {
    Context context = getApplicationContext();
    NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(context);
    notificationBuilder.setSmallIcon(R.drawable.ic_thumb_up_white_24dp);
    notificationBuilder.setContentTitle("Studie status");
    notificationBuilder.setContentText("Er is een nieuw cijfer binnen! ");
    Intent intent = new Intent(this, MainActivity.class);
    TaskStackBuilder taskStackBuilder = TaskStackBuilder.create(context);
    taskStackBuilder.addParentStack(MainActivity.class);
    taskStackBuilder.addNextIntent(intent);
    PendingIntent pendingIntent = taskStackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
    notificationBuilder.setContentIntent(pendingIntent);
    NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
    notificationManager.notify(0,notificationBuilder.build());

  }

  @Override
  public void handleCallBack(Boolean result) {

  }
}
