package zachwhaley.atwork

import android.app.AlarmManager
import android.app.Notification
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.res.Resources
import android.location.Location
import android.location.LocationManager
import android.support.v7.app.NotificationCompat
import android.telephony.SmsManager
import android.util.Log

import java.util.Calendar

import android.content.Context.NOTIFICATION_SERVICE

object AtWork {
    val LOG = "AtWork"

    fun log(msg: String?) {
        Log.d(LOG, msg ?: "null")
    }

    fun err(msg: String?) {
        Log.e(LOG, msg ?: "null")
    }

    fun sendText(context: Context, location: Location) {
        val res = context.resources

        val workLoc = Location(AtWork.LOG)
        workLoc.latitude = java.lang.Float.parseFloat(res.getString(R.string.latitude)).toDouble()
        workLoc.longitude = java.lang.Float.parseFloat(res.getString(R.string.longitude)).toDouble()

        if (location.distanceTo(workLoc) <= 200) {
            try {
                val smsMgr = SmsManager.getDefault()
                smsMgr.sendTextMessage(
                        res.getString(R.string.phone_number), null,
                        res.getString(R.string.text_message), null, null)
            } catch (e: Exception) {
                e.printStackTrace()
            }

        } else {
            sendNotification(context, res.getString(R.string.not_text_message))
        }
    }

    fun setAlarm(context: Context) {
        AtWork.log("AtWork.setAlarm")
        val intent = Intent(context, AtWorkAlarmReceiver::class.java)
        val alarmIntent = PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)

        // Set the alarm to start at approximately 9:00am
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val calendar = Calendar.getInstance()
        calendar.timeInMillis = System.currentTimeMillis()
        calendar.set(Calendar.HOUR_OF_DAY, 9)
        alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.timeInMillis, AlarmManager.INTERVAL_DAY, alarmIntent)
    }

    fun setLocationRequest(context: Context) {
        AtWork.log("AtWork.setLocationRequest")
        val intent = Intent(context, AtWorkLocationReceiver::class.java)
        val locationIntent = PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)

        val locationManager = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        try {
            locationManager.requestSingleUpdate(LocationManager.GPS_PROVIDER, locationIntent)
            AtWork.log("Location requested")
        } catch (e: SecurityException) {
            err(e.message)
        }

    }

    fun sendNotification(context: Context, msg: String) {
        AtWork.log("AtWork.sendNotification")
        val notification = NotificationCompat.Builder(context)
                .setSmallIcon(R.drawable.ic_stat_name)
                .setContentTitle(AtWork.LOG)
                .setContentText(msg)
                .build()
        val notificationManager = context.getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.notify(1, notification)
    }
}
