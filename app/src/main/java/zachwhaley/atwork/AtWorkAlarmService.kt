package zachwhaley.atwork

import android.app.IntentService
import android.content.Context
import android.content.Intent
import android.location.Location
import android.location.LocationManager
import android.support.v4.content.WakefulBroadcastReceiver

class AtWorkAlarmService : IntentService("AtWorkAlarmService") {

    override fun onHandleIntent(intent: Intent?) {
        AtWork.log("AtWorkAlarmService.onHandleIntent")
        intent?.let {
            when (it.action) {
                "Morning Alarm" -> handleMorningAlarm()
                "Evening Alarm" -> handleEveningAlarm()
                else -> AtWork.err("Unknown alarm")
            }
        }
        WakefulBroadcastReceiver.completeWakefulIntent(intent)
    }

    fun handleMorningAlarm() {
        val locationManager = this.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        var loc: Location? = null
        try {
            loc = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER)
        } catch (e: SecurityException) {
            AtWork.err(e.message)
        }

        if (loc != null) {
            if (AtWork.closeToWork(this, loc)) {
                AtWork.sendText(this, AtWork.AT_WORK_A)
            } else {
                AtWork.sendNotification(this, AtWork.AT_WORK_Q, "Yes")
            }
        } else {
            AtWork.setLocationRequest(this)
        }
    }

    fun handleEveningAlarm() {
        AtWork.sendNotification(this, AtWork.LEAVING_Q, "Yes")
    }
}
