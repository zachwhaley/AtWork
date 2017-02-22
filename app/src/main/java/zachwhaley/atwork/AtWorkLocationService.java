package zachwhaley.atwork;

import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;

public class AtWorkLocationService extends IntentService {

    public AtWorkLocationService(String name) {
        super(name);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        AtWork.log("AtWorkLocationService.onHandleIntent");
        // TODO: Figure out which one of these values is the location object.
        Bundle bundle = intent.getExtras();
        for (String key : bundle.keySet()) {
            AtWork.log(bundle.get(key).toString());
        }
        AtWork.sendNotification(this, "Location found!");
        AtWorkLocationReceiver.completeWakefulIntent(intent);
    }
}
