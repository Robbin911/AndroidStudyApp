package com.example.androidstudy.presenter;

import com.example.androidstudy.Constants;
import com.example.androidstudy.model.bean.LoginData;
import com.example.androidstudy.https.BaseResponse;
import com.example.androidstudy.https.RetrofitManager;
import com.example.androidstudy.presenter.Interface.IRegisterPageView;
import com.orhanobut.hawk.Hawk;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class RegisterPresenter {

  private IRegisterPageView mView;

  public RegisterPresenter(IRegisterPageView view) {
    mView = view;
  }

  public void register(String username, String pw) {
    RetrofitManager.getInstance().getAppApis().getRegisterData(username, pw, pw)
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
          mView.onRegisterSuccess();

        } else {
          mView.onRegisterFail(loginDataBaseResponse.getErrorCode());
        }

      }

      @Override
      public void onError(Throwable e) {
        mView.onRegisterFail(2);
      }

      @Override
      public void onComplete() {

      }
    });
  }


}
