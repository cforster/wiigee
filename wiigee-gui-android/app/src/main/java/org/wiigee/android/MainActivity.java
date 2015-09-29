package org.wiigee.android;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.wiigee.control.AndroidWiigee;
import org.wiigee.event.GestureEvent;
import org.wiigee.event.GestureListener;

import java.io.File;
import java.io.FilenameFilter;

public class MainActivity extends AppCompatActivity {

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
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                final String[] files = getFilesDir().list(new FilenameFilter() {
                    @Override
                    public boolean accept(File dir, String filename) {
                        return filename.endsWith(".txt");
                    }
                });
                builder.setTitle("Pick a Model").setItems(files,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                String modelFilePath = files[which];
                                // Wiigee appends .txt to model file paths, so strip it out here.
                                androidWiigee.getDevice().loadGesture(modelFilePath.substring(0, modelFilePath.length() - 4));
                                Toast.makeText(MainActivity.this,
                                        "Gesture loaded: " + modelFilePath,
                                        Toast.LENGTH_SHORT).show();
                                dialog.dismiss();
                            }
                        });
                builder.create().show();
            }
        });
    }

    ;
}
