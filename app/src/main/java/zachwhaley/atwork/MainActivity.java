package zachwhaley.atwork;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Toast;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        AtWork.setAlarm(this);
        Toast.makeText(this, "At Work Set!", Toast.LENGTH_LONG).show();
    }
}
