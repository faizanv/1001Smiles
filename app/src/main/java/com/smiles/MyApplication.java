package com.smiles;

import android.app.Application;

import com.parse.Parse;
import com.parse.ParseInstallation;

/**
 * Created by ffas on 6/9/15.
 */
public class MyApplication extends Application {

    public void onCreate() {
        Parse.initialize(this, "PkhBXQHKFDqNVNgsnYrNYVLOiFfdWkPpVCx1173K", "8iVNR9wZ51g0iun8uuoOhuJxWOCOgTik1oIAwfaO");
        ParseInstallation.getCurrentInstallation().saveInBackground();
    }
}
