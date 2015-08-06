package com.smiles;

import android.app.Application;
import android.util.Log;

import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseInstallation;
import com.parse.ParsePush;
import com.parse.SaveCallback;

/**
 * Created by ffas on 6/9/15.
 */
public class MyApplication extends Application {

    public void onCreate() {
        Parse.initialize(this, "PkhBXQHKFDqNVNgsnYrNYVLOiFfdWkPpVCx1173K", "8iVNR9wZ51g0iun8uuoOhuJxWOCOgTik1oIAwfaO");
        ParseInstallation.getCurrentInstallation().saveInBackground();
        ParsePush.subscribeInBackground("", new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if (e == null) {
                    Log.d("com.parse.push", "successfully subscribed to the broadcast channel.");
                } else {
                    Log.e("com.parse.push", "failed to subscribe for push", e);
                }
            }
        });
    }
}
