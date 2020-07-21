package com.example.androidstudy.presenter;

import com.example.androidstudy.model.bean.ProjectListData;
import com.example.androidstudy.https.BaseResponse;
import com.example.androidstudy.https.RetrofitManager;
import com.example.androidstudy.presenter.Interface.IProjectDetailPageView;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class ProjectDetailListPresenter {


  private IProjectDetailPageView mView;

  public ProjectDetailListPresenter(IProjectDetailPageView view) {
    mView = view;
  }

  public void getData(int id, int page) {
    RetrofitManager.getInstance().getAppApis().getProjectListData(page, id)
        .observeOn(AndroidSchedulers.mainThread()).subscribeOn(
        Schedulers.newThread()).subscribe(new Observer<BaseResponse<ProjectListData>>() {
      @Override
      public void onSubscribe(Disposable d) {

      }

      @Override
      public void onNext(BaseResponse<ProjectListData> value) {
        if (value.getErrorCode() == BaseResponse.SUCCESS) {
          mView.updateList(value.getData());
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
