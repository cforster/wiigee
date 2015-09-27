package org.wiigee.android;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.Toast;

import org.wiigee.control.AndroidWiigee;
import org.wiigee.event.GestureEvent;
import org.wiigee.event.GestureListener;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        AndroidWiigee androidWiigee = new AndroidWiigee(this);
        androidWiigee.setTrainButton((Button) findViewById(R.id.buttonTrain));
        androidWiigee.setRecognitionButton((Button) findViewById(R.id.buttonRecognize));
        androidWiigee.setCloseGestureButton((Button) findViewById(R.id.buttonClose));

        androidWiigee.addGestureListener(new GestureListener() {
            @Override
            public void gestureReceived(GestureEvent gestureEvent) {
                Toast.makeText(MainActivity.this, "Gesture Detected! " + gestureEvent.getId()
                        + ", prob: " + gestureEvent.getProbability(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
