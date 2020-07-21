package com.example.androidstudy.presenter;

import com.example.androidstudy.model.bean.FeedArticleListData;
import com.example.androidstudy.https.BaseResponse;
import com.example.androidstudy.https.RetrofitManager;
import com.example.androidstudy.presenter.Interface.IKnowledgeDetailListView;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class KnowledgeDetailListPresenter {


  private IKnowledgeDetailListView mView;
  private int currentPage = 0;
  private boolean isRefresh = true;

  public KnowledgeDetailListPresenter(IKnowledgeDetailListView view) {
    mView = view;
  }

  public void getData(int page, int cid) {
    RetrofitManager.getInstance().getAppApis().getKnowledgeHierarchyDetailData(page, cid)
        .observeOn(AndroidSchedulers.mainThread()).subscribeOn(
        Schedulers.newThread()).subscribe(new Observer<BaseResponse<FeedArticleListData>>() {
      @Override
      public void onSubscribe(Disposable d) {

      }

      @Override
      public void onNext(BaseResponse<FeedArticleListData> value) {
        if (value != null && mView != null) {
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
