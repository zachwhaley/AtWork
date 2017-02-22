package zachwhaley.atwork;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;

public class AtWorkAlarmService extends IntentService {

    public AtWorkAlarmService() {
        super("AtWorkAlarmService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        AtWork.log("AtWorkAlarmService.onHandleIntent");
        LocationManager locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        Location loc = null;
        try {
            loc = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        } catch (SecurityException e) {
            AtWork.err(e.getMessage());
        }
        if (loc != null) {
            AtWork.sendText(this, loc);
        }
        else {
            AtWork.setLocationRequest(this);
        }
        AtWorkAlarmReceiver.completeWakefulIntent(intent);
    }
}
