package zachwhaley.atwork

import android.content.Context
import android.content.Intent
import android.support.v4.content.WakefulBroadcastReceiver

class AtWorkAlarmReceiver : WakefulBroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        AtWork.log("AtWorkAlarmReceiver.onReceive")

        val alarmService = Intent(context, AtWorkAlarmService::class.java)
        alarmService.action = intent.action
        WakefulBroadcastReceiver.startWakefulService(context, alarmService)
    }
}