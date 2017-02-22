package zachwhaley.atwork;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.WakefulBroadcastReceiver;

public class AtWorkAlarmReceiver extends WakefulBroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        AtWork.log("AtWorkAlarmReceiver.onReceive");
        Intent alarmService = new Intent(context, AtWorkAlarmService.class);
        startWakefulService(context, alarmService);
    }
}