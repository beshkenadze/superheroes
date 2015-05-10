package net.beshkenadze.mysuperheroes;

import android.app.Application;

import timber.log.Timber;

/**
 * Created by Aleksandr Beshkenadze <beshkenadze@gmail.com> on 09/05/15.
 */
public class App extends Application {
    @Override public void onCreate () {
        super.onCreate();

        if (BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());
        }
    }
}
