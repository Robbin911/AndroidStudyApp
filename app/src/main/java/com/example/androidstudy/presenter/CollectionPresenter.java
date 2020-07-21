package com.example.androidstudy.presenter;

import com.example.androidstudy.model.bean.FeedArticleListData;
import com.example.androidstudy.https.BaseResponse;
import com.example.androidstudy.https.RetrofitManager;
import com.example.androidstudy.presenter.Interface.ICollectionPageView;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class CollectionPresenter {


  private ICollectionPageView mView;

  public CollectionPresenter(ICollectionPageView view) {
    mView = view;
  }

  public void getData(int page) {
    RetrofitManager.getInstance().getAppApis().getCollectList(page)
        .observeOn(AndroidSchedulers.mainThread()).subscribeOn(
        Schedulers.newThread()).subscribe(new Observer<BaseResponse<FeedArticleListData>>() {
      @Override
      public void onSubscribe(Disposable d) {

      }

      @Override
      public void onNext(BaseResponse<FeedArticleListData> value) {
        if (value.getErrorCode() == BaseResponse.SUCCESS) {
          mView.updateList(value.getData().getDatas());
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
