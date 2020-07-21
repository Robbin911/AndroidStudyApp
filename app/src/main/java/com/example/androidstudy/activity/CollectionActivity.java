package com.example.androidstudy.activity;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.example.androidstudy.R;
import com.example.androidstudy.model.bean.FeedArticleData;
import com.example.androidstudy.presenter.CollectionPresenter;
import com.example.androidstudy.presenter.Interface.ICollectionPageView;
import com.example.androidstudy.recycler.BaseArticleAdapter;
import com.example.androidstudy.utils.ResponseUtils;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import es.dmoral.toasty.Toasty;
import java.util.ArrayList;
import java.util.List;

public class CollectionActivity extends AppCompatActivity implements ICollectionPageView {

  @BindView(R.id.back_btn)
  ImageView mBackBtn;
  @BindView(R.id.recycler_collection)
  RecyclerView mRecyclerView;
  private BaseArticleAdapter mAdapter;
  @BindView(R.id.refresh)
  SmartRefreshLayout mRefresh;

  private int mCurrentPage = 0;
  private boolean isRefresh = true;

  private CollectionPresenter mPresenter;
  private List<FeedArticleData> mFeedArticleDataList;


  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.collection_activity);
    ButterKnife.bind(this);
    initView();
  }


  private void initView() {
    setRefresh();
    mPresenter = new CollectionPresenter(this);
    mFeedArticleDataList = new ArrayList<>();
    mAdapter = new BaseArticleAdapter(R.layout.base_article_item, mFeedArticleDataList);
    mAdapter.setCollectPage(true);
    mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    mRecyclerView.setHasFixedSize(true);
    mRecyclerView.setAdapter(mAdapter);
    mPresenter.getData(0);

    mBackBtn.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View v) {
        finish();
      }
    });
  }

  @Override
  public void updateList(List<FeedArticleData> data) {
    if (isRefresh) {
      mAdapter.replaceData(data);
    } else {
      if (data.size() > 0) {
        mAdapter.addData(data);
      } else {
        Toasty.info(this, "No More Data.", Toast.LENGTH_SHORT, true).show();
      }
    }
  }

  @Override
  public void requestFail(int code) {
    ResponseUtils.handleErrorText(this, code);
  }

  private void setRefresh() {
    mRefresh.setPrimaryColors(getResources().getColor(R.color.colorPrimary));
    mRefresh.setOnRefreshListener(new OnRefreshListener() {
      @Override
      public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        isRefresh = true;
        mCurrentPage = 0;
        mPresenter.getData(mCurrentPage);
        refreshLayout.finishRefresh(1000);
      }
    });
    mRefresh.setOnLoadMoreListener(new OnLoadMoreListener() {
      @Override
      public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
        isRefresh = false;
        mCurrentPage++;
        mPresenter.getData(mCurrentPage);
        refreshLayout.finishLoadMore(1000);
      }
    });
  }
}
