package com.example.androidstudy.utils;

import android.content.Context;
import android.widget.Toast;
import com.example.androidstudy.https.BaseResponse;
import es.dmoral.toasty.Toasty;

public class ResponseUtils {


  public static void handleErrorText(Context context, int msg) {
    if (context == null) {
      return;
    }
    String result = "Unexpected Error";
    switch (msg) {

      case BaseResponse.FAIL:
        result = "Request fail";
        break;
    }
    Toasty.error(context, result, Toast.LENGTH_SHORT, true).show();
  }

}
