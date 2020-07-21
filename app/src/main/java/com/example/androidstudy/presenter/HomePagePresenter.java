package com.example.androidstudy.presenter;

import com.example.androidstudy.model.bean.FeedArticleListData;
import com.example.androidstudy.https.BaseResponse;
import com.example.androidstudy.https.RetrofitManager;
import com.example.androidstudy.presenter.Interface.IHomePageView;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class HomePagePresenter {

  private IHomePageView mView; //View层接口

  public HomePagePresenter(IHomePageView view) {
    mView = view;
  }

  public void getData(int page) {
    //获取文章列表数据
    RetrofitManager.getInstance().getAppApis().getFeedArticleList(page)
        .observeOn(AndroidSchedulers.mainThread()).subscribeOn(
        Schedulers.newThread()).subscribe(new Observer<BaseResponse<FeedArticleListData>>() {
      @Override
      public void onSubscribe(Disposable d) { }
      @Override
      public void onNext(BaseResponse<FeedArticleListData> value) {
        if (value.getErrorCode() == BaseResponse.SUCCESS) {
          //数据获取正常，将数据通过View接口交给View层
          mView.updateList(value.getData().getDatas());
        } else {
          //数据获取错误，告知View层展示错误信息
          mView.requestFail(value.getErrorCode());
        }
      }
      @Override
      public void onError(Throwable e) {
        //请求错误，告知View层展示错误信息
        mView.requestFail(BaseResponse.FAIL);
      }
      @Override
      public void onComplete() { }
    });
  }
}
