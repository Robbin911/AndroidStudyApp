package com.example.androidstudy.activity;

import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import androidx.appcompat.app.AppCompatActivity;
import com.example.androidstudy.R;
import com.example.androidstudy.utils.JumpUtils;
import com.orhanobut.hawk.Hawk;

public class WelcomeActivity extends AppCompatActivity {


  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    Hawk.init(this).build();
    requestWindowFeature(Window.FEATURE_NO_TITLE);
    setContentView(R.layout.welcome_activity_layout);

  }

  @Override
  protected void onResume() {
    super.onResume();

    new Handler().postDelayed(new Runnable() {
      @Override
      public void run() {
        JumpUtils.startMainPagerActivity(WelcomeActivity.this);
      }
    }, 2000);

  }
}
