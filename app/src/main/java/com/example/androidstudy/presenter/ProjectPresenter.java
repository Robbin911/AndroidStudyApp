package com.example.androidstudy.presenter;

import com.example.androidstudy.model.bean.ProjectClassifyData;
import com.example.androidstudy.https.BaseResponse;
import com.example.androidstudy.https.RetrofitManager;
import com.example.androidstudy.presenter.Interface.IProjectPageView;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import java.util.List;

public class ProjectPresenter {

  private IProjectPageView mView;

  public ProjectPresenter(IProjectPageView view) {
    mView = view;
  }

  //获取项目分类数据
  public void getProjectClassifyData() {
    RetrofitManager.getInstance().getAppApis().getProjectClassifyData()
        .observeOn(AndroidSchedulers.mainThread()).subscribeOn(
        Schedulers.newThread()).subscribe(new Observer<BaseResponse<List<ProjectClassifyData>>>() {
      @Override
      public void onSubscribe(Disposable d) {

      }
      @Override
      public void onNext(BaseResponse<List<ProjectClassifyData>> value) {
        if (value.getErrorCode() == BaseResponse.SUCCESS) {
          //数据获取正常，将数据通过View接口交给View层
          mView.getProjectTypeList(value.getData());
        }else {
          //数据获取错误，告知View层展示错误信息
          mView.requestFail(value.getErrorCode());
        }
      }
      @Override
      public void onError(Throwable e) {
        //请求出错，告知View层展示错误信息
        mView.requestFail(2);
      }

      @Override
      public void onComplete() {

      }
    });
  }


}
