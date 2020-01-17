package com.demo.demosomosoh;

import android.app.Application;
import com.microsoft.appcenter.AppCenter;
import com.microsoft.appcenter.analytics.Analytics;
import com.microsoft.appcenter.crashes.Crashes;

public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        AppCenter.start(this, "f016e875-bf51-40c1-9d6e-2c5c7019af72",
                Analytics.class, Crashes.class);
    }
}
