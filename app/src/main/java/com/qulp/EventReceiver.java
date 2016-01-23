package com.qulp;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.location.LocationManager;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import android.util.Log;
import android.widget.Toast;

public class EventReceiver extends BroadcastReceiver {

    private static final int NOTIFICATION_ID = 1000;
    Notification myNotication;

    @Override
    public void onReceive(Context context, Intent intent) {


        String key = LocationManager.KEY_PROXIMITY_ENTERING;
        Toast.makeText(context,"onRcv",Toast.LENGTH_LONG).show();
        Boolean entering = intent.getBooleanExtra(key, false);

        if (entering) {
            Log.d(getClass().getSimpleName(), "entering");
            Toast.makeText(context,"Entering",Toast.LENGTH_LONG).show();
        } else {
            Log.d(getClass().getSimpleName(), "exiting");
            Toast.makeText(context,"Exit",Toast.LENGTH_LONG).show();
        }

        sendNotification(context,"Welcome to my My Zone");
    }


    private void sendNotification(Context context, String notificationDetails) {
        // Create an explicit content Intent that starts the main Activity.
        Intent notificationIntent = new Intent(context, QulpActivity.class);

        // Construct a task stack.
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);

        // Add the main Activity to the task stack as the parent.
        stackBuilder.addParentStack(QulpActivity.class);

        // Push the content Intent onto the stack.
        stackBuilder.addNextIntent(notificationIntent);

        // Get a PendingIntent containing the entire back stack.
        PendingIntent notificationPendingIntent =
                stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);

        // Get a notification builder that's compatible with platform versions >= 4
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context);

        // Define the notification settings.
        builder.setSmallIcon(R.drawable.ic_cast_dark)
                // In a real app, you may want to use a library like Volley
                // to decode the Bitmap.
                .setColor(Color.RED)
                .setContentTitle(notificationDetails)
                .setContentText("Welcome to my zone")
                .setContentIntent(notificationPendingIntent);

        // Dismiss notification once the user touches it.
        builder.setAutoCancel(true);

        // Get an instance of the Notification manager
        NotificationManager mNotificationManager =
                (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        // Issue the notification
        mNotificationManager.notify(0, builder.build());
    }

    private Notification createNotification() {
        Notification notification = new Notification();

        notification.icon = R.drawable.start;
        notification.when = System.currentTimeMillis();

        notification.flags |= Notification.FLAG_AUTO_CANCEL;
        notification.flags |= Notification.FLAG_SHOW_LIGHTS;

        notification.defaults |= Notification.DEFAULT_VIBRATE;
        notification.defaults |= Notification.DEFAULT_LIGHTS;

        notification.ledARGB = Color.WHITE;
        notification.ledOnMS = 1500;
        notification.ledOffMS = 1500;

        return notification;
    }

}