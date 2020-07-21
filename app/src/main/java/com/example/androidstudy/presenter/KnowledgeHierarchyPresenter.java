package com.example.androidstudy.presenter;

import com.example.androidstudy.model.bean.KnowledgeHierarchyData;
import com.example.androidstudy.https.BaseResponse;
import com.example.androidstudy.https.RetrofitManager;
import com.example.androidstudy.presenter.Interface.IKnowledgePageView;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import java.util.List;

public class KnowledgeHierarchyPresenter {

  private IKnowledgePageView mView;

  public KnowledgeHierarchyPresenter(IKnowledgePageView view) {
    mView = view;
  }

  public void getData() {
    RetrofitManager.getInstance().getAppApis().getKnowledgeHierarchyData()
        .observeOn(AndroidSchedulers.mainThread()).subscribeOn(
        Schedulers.newThread()).subscribe(
        new Observer<BaseResponse<List<KnowledgeHierarchyData>>>() {
          @Override
          public void onSubscribe(Disposable d) {

          }

          @Override
          public void onNext(BaseResponse<List<KnowledgeHierarchyData>> listBaseResponse) {
            mView.updateList(listBaseResponse.getData());
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
