package zachwhaley.atwork

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

class BootReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        AtWork.log("BootReceiver.onReceive")
        if (intent.action == "android.intent.action.BOOT_COMPLETED") {
            AtWork.setAlarm(context)
        }
    }
}
