package com.example.androidstudy.presenter;

import com.example.androidstudy.model.bean.FeedArticleListData;
import com.example.androidstudy.https.BaseResponse;
import com.example.androidstudy.https.RetrofitManager;
import com.example.androidstudy.presenter.Interface.ISearchPageView;
import com.example.androidstudy.utils.HawkHelper;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class SearchPresenter {

  private ISearchPageView mView;

  public SearchPresenter(ISearchPageView view) {
    mView = view;
  }

  public void getSearchData(int page, String key) {
    RetrofitManager.getInstance().getAppApis().getSearchList(page, key)
        .observeOn(AndroidSchedulers.mainThread()).subscribeOn(
        Schedulers.newThread()).subscribe(new Observer<BaseResponse<FeedArticleListData>>() {
      @Override
      public void onSubscribe(Disposable d) {

      }

      @Override
      public void onNext(BaseResponse<FeedArticleListData> value) {
        if (value.getErrorCode() == BaseResponse.SUCCESS) {
          mView.getData(value.getData().getDatas());
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

  public void getHistory() {
    mView.getHistory(HawkHelper.getHistoryList());
  }

  public void clearHistory(){
    mView.getHistory(HawkHelper.clearHistory());
  }


}
