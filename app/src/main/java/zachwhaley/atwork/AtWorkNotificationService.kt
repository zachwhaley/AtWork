package zachwhaley.atwork

import android.app.IntentService
import android.app.NotificationManager
import android.content.Intent

class AtWorkNotificationService : IntentService("AtWorkNotificationService") {

    override fun onHandleIntent(intent: Intent?) {
        AtWork.log("Handle Notification")
        intent?.let {
            val yes = this.resources.getString(R.string.yes_message)
            when (it.action) {
                yes -> {
                    AtWork.log("Ok button pressed")
                    val notificationManager = this.getSystemService(NOTIFICATION_SERVICE) as NotificationManager
                    notificationManager.cancel(1)
                    AtWork.sendText(this)
                }
                else -> {
                    AtWork.err("No action found")
                }
            }
        }
    }
}