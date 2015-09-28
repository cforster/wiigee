package org.wiigee.android;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.wiigee.control.AndroidWiigee;
import org.wiigee.event.GestureEvent;
import org.wiigee.event.GestureListener;

public class MainActivity extends AppCompatActivity {

    private final int GET_MODEL_INTENT_ID = 1;
    private AndroidWiigee androidWiigee;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        androidWiigee = new AndroidWiigee(this);
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

        findViewById(R.id.buttonSave).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int modelNumber = Integer.parseInt((
                        (EditText) findViewById(R.id.editTextModelNumber)).getText().toString());
                String modelName = ((EditText) findViewById(R.id.editTextFileName))
                        .getText().toString();
                String modelFilePath = getFilesDir().getAbsolutePath() + "/" + modelName;
                androidWiigee.getDevice().saveGesture(modelNumber, modelFilePath);
                Toast.makeText(MainActivity.this, "Gesture saved to " + modelName,
                        Toast.LENGTH_SHORT).show();
            }
        });

        findViewById(R.id.buttonLoad).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.addCategory(Intent.CATEGORY_OPENABLE);
                intent.putExtra(Intent.EXTRA_LOCAL_ONLY, true);
                if (intent.resolveActivity(getPackageManager()) != null) {
                    startActivityForResult(intent, GET_MODEL_INTENT_ID);
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == GET_MODEL_INTENT_ID && resultCode == RESULT_OK) {
            Uri modelFileUri = data.getData();
            String modelFilePath = modelFileUri.getPath();
            androidWiigee.getDevice().loadGesture(modelFilePath);
            Toast.makeText(MainActivity.this,
                    "Gesture loaded: " + modelFileUri.getLastPathSegment(),
                    Toast.LENGTH_SHORT).show();
        }
    }
}
