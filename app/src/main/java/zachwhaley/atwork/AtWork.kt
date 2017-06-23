package zachwhaley.atwork

import android.app.AlarmManager
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Context.NOTIFICATION_SERVICE
import android.content.Intent
import android.location.Location
import android.location.LocationManager
import android.support.v4.app.NotificationCompat
import android.telephony.SmsManager
import android.util.Log
import java.util.Calendar

object AtWork {
    val LOG = "AtWork"

    val AT_WORK_Q = "Are you at work? 😕"
    val AT_WORK_A = "At work 😘"

    val LEAVING_Q = "Are you leaving? 😕"
    val LEAVING_A = "Leaving 😀"

    fun log(msg: String?) {
        Log.d(LOG, msg ?: "null")
    }

    fun err(msg: String?) {
        Log.e(LOG, msg ?: "null")
    }

    fun closeToWork(context: Context, location: Location): Boolean {
        val res = context.resources
        val workLoc = Location(AtWork.LOG)

        workLoc.latitude = java.lang.Float.parseFloat(res.getString(R.string.latitude)).toDouble()
        workLoc.longitude = java.lang.Float.parseFloat(res.getString(R.string.longitude)).toDouble()
        return location.distanceTo(workLoc) <= 200
    }

    fun sendText(context: Context, message: String) {
        val res = context.resources

        try {
            val smsMgr = SmsManager.getDefault()
            val number = res.getString(R.string.phone_number)
            smsMgr.sendTextMessage(number, null, message, null, null)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun sendNotification(context: Context, question: String, answer: String) {
        val actionIntent = Intent(context, AtWorkNotificationService::class.java)
        actionIntent.action = question
        val pendingAction = PendingIntent.getService(context, 0, actionIntent, PendingIntent.FLAG_UPDATE_CURRENT)
        val action = NotificationCompat.Action.Builder(0, answer, pendingAction).build()

        val notification = NotificationCompat.Builder(context)
                .setSmallIcon(R.drawable.ic_stat_name)
                .setContentTitle(AtWork.LOG)
                .setContentText(question)
                .addAction(action)
                .build()
        val notificationManager = context.getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.notify(1, notification)
    }

    fun setAlarms(context: Context) {
        AtWork.log("AtWork.setAlarms")
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

        // Set alarm at approximately 9:00am
        val morningIntent = Intent(context, AtWorkAlarmReceiver::class.java)
        morningIntent.action = "Morning Alarm"
        val morningPIntent = PendingIntent.getBroadcast(context, 1, morningIntent, PendingIntent.FLAG_UPDATE_CURRENT)
        val morning = Calendar.getInstance()
        morning.timeInMillis = System.currentTimeMillis()
        morning.set(Calendar.HOUR_OF_DAY, 9)
        alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP,
                                         morning.timeInMillis,
                                         AlarmManager.INTERVAL_DAY,
                                         morningPIntent)

        // Set alarm at approximately 4:30pm
        val eveningIntent = Intent(context, AtWorkAlarmReceiver::class.java)
        eveningIntent.action = "Evening Alarm"
        val eveningPIntent = PendingIntent.getBroadcast(context, 2, eveningIntent, PendingIntent.FLAG_UPDATE_CURRENT)
        val evening = Calendar.getInstance()
        evening.timeInMillis = System.currentTimeMillis()
        evening.set(Calendar.HOUR_OF_DAY, 16)
        evening.set(Calendar.MINUTE, 30)
        alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP,
                                         evening.timeInMillis,
                                         AlarmManager.INTERVAL_DAY,
                                         eveningPIntent)
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
}
