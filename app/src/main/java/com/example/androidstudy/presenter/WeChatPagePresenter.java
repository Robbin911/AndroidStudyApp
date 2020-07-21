package com.example.androidstudy.presenter;

import com.example.androidstudy.model.bean.WxAuthor;
import com.example.androidstudy.https.BaseResponse;
import com.example.androidstudy.https.RetrofitManager;
import com.example.androidstudy.presenter.Interface.IWeChatPageView;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import java.util.List;

public class WeChatPagePresenter {


  private IWeChatPageView mView;

  public WeChatPagePresenter(IWeChatPageView view) {
    mView = view;
  }

  public void getData() {
    RetrofitManager.getInstance().getAppApis().getWxAuthorListData()
        .observeOn(AndroidSchedulers.mainThread()).subscribeOn(
        Schedulers.newThread()).subscribe(new Observer<BaseResponse<List<WxAuthor>>>() {
      @Override
      public void onSubscribe(Disposable d) {

      }

      @Override
      public void onNext(BaseResponse<List<WxAuthor>> value) {
        if (value.getErrorCode() == BaseResponse.SUCCESS) {
          mView.getWeChatAuthorData(value.getData());
        } else {
          mView.requestFail(value.getErrorCode());
        }
      }

      @Override
      public void onError(Throwable e) {
        mView.requestFail(2);
      }

      @Override
      public void onComplete() {

      }
    });
  }


}
