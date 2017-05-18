package zachwhaley.atwork

import android.content.Context
import android.content.Intent
import android.support.v4.content.WakefulBroadcastReceiver

class AtWorkLocationReceiver : WakefulBroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        AtWork.log("AtWorkLocationReceiver.onReceive")
        val locationService = Intent(context, AtWorkLocationService::class.java)
        WakefulBroadcastReceiver.startWakefulService(context, locationService)
    }
}
