package com.example.androidstudy.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import com.example.androidstudy.Constants;
import com.example.androidstudy.R;
import com.example.androidstudy.model.bean.FeedArticleListData;
import com.example.androidstudy.https.BaseResponse;
import com.example.androidstudy.https.RetrofitManager;
import com.example.androidstudy.utils.JumpUtils;
import com.orhanobut.hawk.Hawk;
import es.dmoral.toasty.Toasty;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class LikeView extends LinearLayout {

  private ImageView mLike; //图标ImageView对象
  private int mId = 0; //对应文章id
  private boolean mStatus = false; //当前状态 true：已收藏 false：未收藏
  private boolean clicked = false; //防抖动
  private boolean isCollectPage = false; //是否在收藏页面

  private OnClickListener listener = new OnClickListener() {
    @Override
    public void onClick(View v) {
      switch (v.getId()) {
        case R.id.like_iv:
          //未登陆则跳转至登陆界面
          if (Hawk.get(Constants.ACCOUNT, "").equals("")) {
            JumpUtils.startLoginActivity(getContext());
            break;
          }
          //id初始化失败、防止重复点击判断
          if (mId != 0 && !clicked) {
            if (mStatus) {
              if (isCollectPage) {
                cancelCollectPageCollect();
              } else {
                cancelCollect();
              }
            } else {
              collect();
            }
          }
      }
    }
  };

  public LikeView(Context context) {
    super(context);
    initView();
  }

  public LikeView(Context context, AttributeSet attrs) {
    super(context, attrs);
    initView();
  }


  private void initView() {
    LayoutInflater.from(getContext()).inflate(R.layout.like_button, this, true);
    mLike = findViewById(R.id.like_iv);
    mLike.setOnClickListener(listener);
  }

  public void setCollectPage(boolean collectPage) {
    isCollectPage = collectPage;
  }

  public void setStatus(boolean like) {
    if (isCollectPage) {
      mStatus = true;
    } else {
      mStatus = like;
    }
    updateStatus();
  }

  public void updateStatus() {
    if (mStatus) {
      //已收藏状态
      mLike.setImageDrawable(getResources().getDrawable(R.drawable.like_selected));
    } else {
      //未收藏状态
      mLike.setImageDrawable(getResources().getDrawable(R.drawable.like_unselected));
    }
  }

  public void setId(int id) {
    mId = id;
  }

  private void collect() {
    RetrofitManager.getInstance().getAppApis().addCollectArticle(mId)
        .observeOn(AndroidSchedulers.mainThread()).subscribeOn(
        Schedulers.newThread()).subscribe(
        new Observer<BaseResponse<FeedArticleListData>>() {
          @Override
          public void onSubscribe(Disposable d) {

          }

          @Override
          public void onNext(
              BaseResponse<FeedArticleListData> feedArticleListDataBaseResponse) {
            if (feedArticleListDataBaseResponse.getErrorCode() == BaseResponse.SUCCESS) {
              mStatus = true;
              updateStatus();
              clicked = false;
            } else {
              Toasty.error(getContext(), "Collect Error").show();
              clicked = false;
            }
          }

          @Override
          public void onError(Throwable e) {
            Toasty.error(getContext(), "Collect Error").show();
            clicked = false;
          }

          @Override
          public void onComplete() {

          }
        });
  }


  private void collectPageCollect() {
    RetrofitManager.getInstance().getAppApis().addCollectArticle(mId)
        .observeOn(AndroidSchedulers.mainThread()).subscribeOn(
        Schedulers.newThread()).subscribe(
        new Observer<BaseResponse<FeedArticleListData>>() {
          @Override
          public void onSubscribe(Disposable d) {

          }

          @Override
          public void onNext(
              BaseResponse<FeedArticleListData> feedArticleListDataBaseResponse) {
            if (feedArticleListDataBaseResponse.getErrorCode() == BaseResponse.SUCCESS) {
              mStatus = true;
              updateStatus();
              clicked = false;
            } else {
              Toasty.error(getContext(), "Collect Error").show();
              clicked = false;
            }
          }

          @Override
          public void onError(Throwable e) {
            Toasty.error(getContext(), "Collect Error").show();
            clicked = false;
          }

          @Override
          public void onComplete() {

          }
        });
  }

  private void cancelCollect() {
    RetrofitManager.getInstance().getAppApis().cancelCollectArticle(mId, -1)
        .observeOn(AndroidSchedulers.mainThread()).subscribeOn(
        Schedulers.newThread()).subscribe(
        new Observer<BaseResponse<FeedArticleListData>>() {
          @Override
          public void onSubscribe(Disposable d) {

          }

          @Override
          public void onNext(
              BaseResponse<FeedArticleListData> feedArticleListDataBaseResponse) {
            if (feedArticleListDataBaseResponse.getErrorCode() == BaseResponse.SUCCESS) {
              mStatus = false;
              updateStatus();
              clicked = false;
            } else {
              Toasty.error(getContext(), "Cancel Collect Error").show();
              clicked = false;
            }
          }

          @Override
          public void onError(Throwable e) {
            Toasty.error(getContext(), "Cancel Collect Error").show();
            clicked = false;
          }

          @Override
          public void onComplete() {

          }
        });
  }


  private void cancelCollectPageCollect() {
    RetrofitManager.getInstance().getAppApis().cancelCollectPageArticle(mId, -1)
        .observeOn(AndroidSchedulers.mainThread()).subscribeOn(
        Schedulers.newThread()).subscribe(
        new Observer<BaseResponse<FeedArticleListData>>() {
          @Override
          public void onSubscribe(Disposable d) {

          }

          @Override
          public void onNext(
              BaseResponse<FeedArticleListData> feedArticleListDataBaseResponse) {
            if (feedArticleListDataBaseResponse.getErrorCode() == BaseResponse.SUCCESS) {
              mStatus = false;
              updateStatus();
              clicked = false;
            } else {
              Toasty.error(getContext(), "Cancel Collect Error").show();
              clicked = false;
            }
          }

          @Override
          public void onError(Throwable e) {
            Toasty.error(getContext(), "Cancel Collect Error").show();
            clicked = false;
          }

          @Override
          public void onComplete() {

          }
        });
  }
}
