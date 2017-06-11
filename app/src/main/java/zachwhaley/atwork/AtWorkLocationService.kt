package zachwhaley.atwork

import android.app.IntentService
import android.content.Intent
import android.support.v4.content.WakefulBroadcastReceiver

class AtWorkLocationService : IntentService("AtWorkLocationService") {

    override fun onHandleIntent(intent: Intent?) {
        AtWork.log("AtWorkLocationService.onHandleIntent")
        // TODO: Figure out which one of these values is the location object.
        intent?.let {
            for (key in it.extras.keySet()) {
                AtWork.log(it.extras.get(key)?.toString())
            }
        }
        WakefulBroadcastReceiver.completeWakefulIntent(intent)
    }
}
