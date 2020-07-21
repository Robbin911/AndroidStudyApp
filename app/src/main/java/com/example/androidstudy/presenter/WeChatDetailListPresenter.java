package com.example.androidstudy.presenter;

import com.example.androidstudy.model.bean.FeedArticleListData;
import com.example.androidstudy.https.BaseResponse;
import com.example.androidstudy.https.RetrofitManager;
import com.example.androidstudy.presenter.Interface.IKnowledgeDetailListView;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class WeChatDetailListPresenter {

  private IKnowledgeDetailListView mView;

  public WeChatDetailListPresenter(IKnowledgeDetailListView view) {
    mView = view;
  }

  public void getData(int id, int page) {
    RetrofitManager.getInstance().getAppApis().getWxSumData(id, page)
        .observeOn(AndroidSchedulers.mainThread()).subscribeOn(
        Schedulers.newThread()).subscribe(new Observer<BaseResponse<FeedArticleListData>>() {
      @Override
      public void onSubscribe(Disposable d) {

      }

      @Override
      public void onNext(BaseResponse<FeedArticleListData> value) {
        if (value.getErrorCode() == BaseResponse.SUCCESS) {
          mView.updateList(value.getData().getDatas());
        }
      }

      @Override
      public void onError(Throwable e) {

      }

      @Override
      public void onComplete() {

      }
    });
  }


  public void getSearchData(int id, int page, String key) {
    RetrofitManager.getInstance().getAppApis().getWxSearchSumData(id, page, key)
        .observeOn(AndroidSchedulers.mainThread()).subscribeOn(
        Schedulers.newThread()).subscribe(new Observer<BaseResponse<FeedArticleListData>>() {
      @Override
      public void onSubscribe(Disposable d) {

      }

      @Override
      public void onNext(BaseResponse<FeedArticleListData> value) {
        if (value.getErrorCode() == BaseResponse.SUCCESS) {
          mView.updateList(value.getData().getDatas());
        }
      }

      @Override
      public void onError(Throwable e) {

      }

      @Override
      public void onComplete() {

      }
    });
  }

}
