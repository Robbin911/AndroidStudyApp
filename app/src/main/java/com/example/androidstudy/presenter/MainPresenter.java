package com.example.androidstudy.presenter;

import com.example.androidstudy.Constants;
import com.example.androidstudy.model.bean.LoginData;
import com.example.androidstudy.https.BaseResponse;
import com.example.androidstudy.https.RetrofitManager;
import com.example.androidstudy.presenter.Interface.IMainPageView;
import com.orhanobut.hawk.Hawk;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class MainPresenter {

  private IMainPageView mView;

  public MainPresenter(IMainPageView view) {
    mView = view;
  }

  public void autoLogin() {
    String account = Hawk.get(Constants.ACCOUNT, "");
    String pw = Hawk.get(Constants.PASSWORD, "");
    if (account.equals("") || pw.equals("")) {
      mView.autoLoginFail(3);
      return;
    }
    RetrofitManager.getInstance().getAppApis().getLoginData(account, pw)
        .observeOn(AndroidSchedulers.mainThread()).subscribeOn(
        Schedulers.newThread()).subscribe(new Observer<BaseResponse<LoginData>>() {
      @Override
      public void onSubscribe(Disposable d) {

      }

      @Override
      public void onNext(BaseResponse<LoginData> loginDataBaseResponse) {
        if (loginDataBaseResponse.getErrorCode() == BaseResponse.SUCCESS) {
          mView.autoLoginSuccess();
        } else {
          mView.autoLoginFail(loginDataBaseResponse.getErrorCode());
        }
      }

      @Override
      public void onError(Throwable e) {
        mView.autoLoginFail(2);
      }

      @Override
      public void onComplete() {

      }
    });
  }

  public void logout() {
    RetrofitManager.getInstance().getAppApis().logout()
        .observeOn(AndroidSchedulers.mainThread()).subscribeOn(
        Schedulers.newThread()).subscribe(new Observer<BaseResponse<LoginData>>() {
      @Override
      public void onSubscribe(Disposable d) {

      }

      @Override
      public void onNext(BaseResponse<LoginData> loginDataBaseResponse) {
        if (loginDataBaseResponse.getErrorCode() == BaseResponse.SUCCESS) {
          mView.logoutSuccess();
        } else {
          mView.logoutFail();
        }
      }

      @Override
      public void onError(Throwable e) {
        mView.logoutFail();
      }

      @Override
      public void onComplete() {

      }
    });
  }

}
