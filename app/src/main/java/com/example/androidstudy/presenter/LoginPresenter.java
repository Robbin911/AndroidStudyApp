package com.example.androidstudy.presenter;

import com.example.androidstudy.Constants;
import com.example.androidstudy.model.bean.LoginData;
import com.example.androidstudy.https.BaseResponse;
import com.example.androidstudy.https.RetrofitManager;
import com.example.androidstudy.presenter.Interface.ILoginPageView;
import com.orhanobut.hawk.Hawk;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class LoginPresenter {


  private ILoginPageView mView;

  public LoginPresenter(ILoginPageView view) {
    mView = view;
  }

  public void login(String username, String pw) {
    RetrofitManager.getInstance().getAppApis().getLoginData(username, pw)
        .observeOn(AndroidSchedulers.mainThread()).subscribeOn(
        Schedulers.newThread()).subscribe(new Observer<BaseResponse<LoginData>>() {
      @Override
      public void onSubscribe(Disposable d) {

      }

      @Override
      public void onNext(BaseResponse<LoginData> loginDataBaseResponse) {
        if (loginDataBaseResponse.getErrorCode() == BaseResponse.SUCCESS) {
          Hawk.put(Constants.ACCOUNT, username);
          Hawk.put(Constants.PASSWORD, pw);
          mView.onLoginSuccess();
        } else {
          mView.onLoginFail(loginDataBaseResponse.getErrorCode());
        }

      }

      @Override
      public void onError(Throwable e) {
        mView.onLoginFail(2);
      }

      @Override
      public void onComplete() {

      }
    });
  }


}
