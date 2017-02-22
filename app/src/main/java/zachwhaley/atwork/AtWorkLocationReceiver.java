package zachwhaley.atwork;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.WakefulBroadcastReceiver;

public class AtWorkLocationReceiver extends WakefulBroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        AtWork.log("AtWorkLocationReceiver.onReceive");
        Intent locationService = new Intent(context, AtWorkLocationService.class);
        startWakefulService(context, locationService);
    }
}
