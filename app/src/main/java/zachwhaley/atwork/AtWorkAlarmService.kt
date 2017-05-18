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
        val locationManager = this.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        var loc: Location? = null
        try {
            loc = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER)
        } catch (e: SecurityException) {
            AtWork.err(e.message)
        }

        if (loc != null) {
            AtWork.sendText(this, loc)
        } else {
            AtWork.setLocationRequest(this)
        }
        WakefulBroadcastReceiver.completeWakefulIntent(intent)
    }
}
