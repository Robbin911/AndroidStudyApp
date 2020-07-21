package com.example.androidstudy.app;

import android.app.Application;
import android.content.Context;
import com.squareup.leakcanary.LeakCanary;

public class App extends Application {

  private static Context context;

  public void onCreate() {
    super.onCreate();
    App.context = getApplicationContext();
    LeakCanary.install(this);
  }

  public static Context getAppContext() {
    return App.context;
  }
}
