package zachwhaley.atwork;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.location.Location;
import android.location.LocationManager;
import android.support.v7.app.NotificationCompat;
import android.telephony.SmsManager;
import android.util.Log;

import java.util.Calendar;

import static android.content.Context.NOTIFICATION_SERVICE;

public class AtWork {
    public static final String LOG = "AtWork";

    public static void log(String msg) {
        Log.d(LOG, msg);
    }

    public static void err(String msg) {
        Log.e(LOG, msg);
    }

    public static void sendText(Context context, Location location) {
        final Resources res = context.getResources();

        Location workLoc = new Location(AtWork.LOG);
        workLoc.setLatitude(Float.parseFloat(res.getString(R.string.latitude)));
        workLoc.setLongitude(Float.parseFloat(res.getString(R.string.longitude)));

        if (location.distanceTo(workLoc) <= 200) {
            try {
                SmsManager smsMgr = SmsManager.getDefault();
                smsMgr.sendTextMessage(
                        res.getString(R.string.phone_number), null,
                        res.getString(R.string.text_message), null, null);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        else {
            sendNotification(context, res.getString(R.string.not_text_message));
        }
    }

    public static void setAlarm(Context context) {
        AtWork.log("AtWork.setAlarm");
        Intent intent = new Intent(context, AtWorkAlarmReceiver.class);
        PendingIntent alarmIntent = PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        // Set the alarm to start at approximately 9:00am
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.HOUR_OF_DAY, 9);
        alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, alarmIntent);
    }

    public static void setLocatonRequest(Context context) {
        AtWork.log("AtWork.setLocationRequest");
        Intent intent = new Intent(context, AtWorkLocationReceiver.class);
        PendingIntent locationIntent = PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        LocationManager locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        try {
            locationManager.requestSingleUpdate(LocationManager.GPS_PROVIDER, locationIntent);
            AtWork.log("Location requested");
        } catch (SecurityException e) {
            err(e.getMessage());
        }
    }

    public static void sendNotification(Context context, String msg) {
        AtWork.log("AtWork.sendNotification");
        Notification notification = new NotificationCompat.Builder(context)
                .setSmallIcon(R.drawable.ic_stat_name)
                .setContentTitle(AtWork.LOG)
                .setContentText(msg)
                .build();
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(NOTIFICATION_SERVICE);
        notificationManager.notify(1, notification);
    }
}
