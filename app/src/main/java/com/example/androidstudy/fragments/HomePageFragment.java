package com.example.androidstudy.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import com.example.androidstudy.Constants;
import com.example.androidstudy.R;
import com.example.androidstudy.model.bean.FeedArticleData;
import com.example.androidstudy.presenter.Interface.IHomePageView;
import com.example.androidstudy.presenter.HomePagePresenter;
import com.example.androidstudy.recycler.BaseArticleAdapter;
import com.example.androidstudy.utils.ResponseUtils;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import java.util.ArrayList;
import java.util.List;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class HomePageFragment extends Fragment implements IHomePageView {

  @BindView(R.id.recycler_home)
  RecyclerView mRecyclerView; //列表对象
  private BaseArticleAdapter mAdapter; //基本文章适配器
  @BindView(R.id.refresh)
  SmartRefreshLayout mRefresh; //刷新组件对象
  private HomePagePresenter mPresenter; //界面Presenter
  private List<FeedArticleData> mFeedArticleDataList; //文章数据列表
  private int currentPage = 0; //当前页码
  private boolean isRefresh = true; //是否为刷新状态
  private Unbinder unbinder; //ButterKnife绑定


  @Nullable
  @Override
  public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
      @Nullable Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.home_page_layout, container, false);
    unbinder = ButterKnife.bind(this, view);
    EventBus.getDefault().register(this);
    initView();
    return view;
  }

  @Subscribe(threadMode = ThreadMode.MAIN)
  public void handleEvent(String event) {
    if (event.equals(Constants.REFRESH_PAGE)) {
      //重新获取数据，状态重置为刷新状态
      isRefresh = true;
      currentPage = 0;
      mPresenter.getData(currentPage);
    }
  }

  @Override
  public void onDestroyView() {
    super.onDestroyView();
    EventBus.getDefault().unregister(this);
    unbinder.unbind();
  }

  private void initView() {
    setRefresh();
    mPresenter = new HomePagePresenter(this);
    mFeedArticleDataList = new ArrayList<>();
    mAdapter = new BaseArticleAdapter(R.layout.base_article_item, mFeedArticleDataList);
    mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    mRecyclerView.setHasFixedSize(true);
    mRecyclerView.setAdapter(mAdapter);
    mPresenter.getData(currentPage);
  }

  @Override
  public void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

  }

  @Override
  public void updateList(List<FeedArticleData> data) {
    if (isRefresh) {
      mFeedArticleDataList = data;
      mAdapter.replaceData(data);
    } else {
      mFeedArticleDataList.addAll(data);
      mAdapter.addData(data);
    }
  }

  @Override
  public void requestFail(int code) {
    ResponseUtils.handleErrorText(getContext(), code);
  }

  private void setRefresh() {
    //设置颜色
    mRefresh.setPrimaryColors(getResources().getColor(R.color.colorPrimary));
    mRefresh.setOnRefreshListener(new OnRefreshListener() {
      @Override
      public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        //刷新操作回调
        isRefresh = true;
        currentPage = 0;
        mPresenter.getData(0);
        refreshLayout.finishRefresh(1000); //动画效果1s
      }
    });
    mRefresh.setOnLoadMoreListener(new OnLoadMoreListener() {
      @Override
      public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
        //加载操作回调
        isRefresh = false;
        currentPage++;
        mPresenter.getData(currentPage);
        refreshLayout.finishLoadMore(1000); //动画效果1s
      }
    });
  }
}
