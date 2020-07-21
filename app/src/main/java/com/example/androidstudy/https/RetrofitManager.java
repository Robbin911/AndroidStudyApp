package com.example.androidstudy.https;

import com.example.androidstudy.app.App;
import com.franmontiel.persistentcookiejar.PersistentCookieJar;
import com.franmontiel.persistentcookiejar.cache.SetCookieCache;
import com.franmontiel.persistentcookiejar.persistence.SharedPrefsCookiePersistor;
import java.util.concurrent.TimeUnit;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitManager {

  private static final int READ_TIMEOUT = 60;//读取超时时间,单位  秒
  private static final int CONN_TIMEOUT = 12;//连接超时时间,单位  秒
  private static RetrofitManager RetrofitManager;
  private AppApis mAppApis;
  private static Retrofit mRetrofit;
  public static PersistentCookieJar cookieJar;


  private RetrofitManager() {
    //初始化cookieJar
    cookieJar = new PersistentCookieJar(new SetCookieCache(), new
        SharedPrefsCookiePersistor(App.getAppContext()));
    //初始化OkHttp
    OkHttpClient client = new OkHttpClient.Builder()
        .connectTimeout(30L, TimeUnit.SECONDS)
        .readTimeout(30L, TimeUnit.SECONDS)
        .writeTimeout(30L, TimeUnit.SECONDS)
        .cookieJar(cookieJar)
        .build();
    //Retrofit框架构建
    mRetrofit = new Retrofit.Builder()
        .client(client)
        .baseUrl(AppApis.HOST)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .build();
    //接口实例化
    mAppApis = mRetrofit.create(AppApis.class);
  }

  public static RetrofitManager getInstance() {
    if (RetrofitManager == null) {
      RetrofitManager = new RetrofitManager();
    }
    return RetrofitManager;
  }

  public AppApis getAppApis() {
    return mAppApis;
  }
}