package zachwhaley.atwork

import android.app.IntentService
import android.app.NotificationManager
import android.content.Intent

class AtWorkNotificationService : IntentService("AtWorkNotificationService") {

    override fun onHandleIntent(intent: Intent?) {
        AtWork.log("Handle Notification")
        intent?.let {
            when (it.action) {
                AtWork.AT_WORK_Q -> {
                    val notificationManager = this.getSystemService(NOTIFICATION_SERVICE) as NotificationManager
                    notificationManager.cancel(1)
                    AtWork.sendText(this, AtWork.AT_WORK_A)
                }
                AtWork.LEAVING_Q -> {
                    val notificationManager = this.getSystemService(NOTIFICATION_SERVICE) as NotificationManager
                    notificationManager.cancel(1)
                    AtWork.sendText(this, AtWork.LEAVING_A)
                }
                else -> {
                    AtWork.err("No action found")
                }
            }
        }
    }
}