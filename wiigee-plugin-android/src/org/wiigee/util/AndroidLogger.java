package org.wiigee.util;

public class AndroidLogger implements Log.Logger {
    
    private static final String TAG = AndroidLogger.class.getCanonicalName();

    public void write(int level, String message, Object caller) {
        switch(level){
            case 0:
                android.util.Log.i(TAG, message);
                break;
            case 1:
                android.util.Log.d(TAG, message);
                break;
        }
    }
    
}
