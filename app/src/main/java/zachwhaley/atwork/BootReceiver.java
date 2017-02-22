package zachwhaley.atwork;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class BootReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        AtWork.log("BootReceiver.onRecieve");
        if (intent.getAction().equals("android.intent.action.BOOT_COMPLETED")) {
            AtWork.setAlarm(context);
            AtWork.sendNotification(context, "Boot receiver");
        }
    }
}
