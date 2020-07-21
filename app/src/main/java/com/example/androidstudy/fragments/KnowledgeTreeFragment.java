package com.example.androidstudy.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import com.example.androidstudy.R;
import com.example.androidstudy.model.bean.KnowledgeHierarchyData;
import com.example.androidstudy.presenter.Interface.IKnowledgePageView;
import com.example.androidstudy.presenter.KnowledgeHierarchyPresenter;
import com.example.androidstudy.recycler.KnowledgeHierarchyAdapter;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import es.dmoral.toasty.Toasty;
import java.util.ArrayList;
import java.util.List;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class KnowledgeTreeFragment extends Fragment implements IKnowledgePageView {

  @BindView(R.id.knowledge_list_recycler_view)
  RecyclerView mRecyclerView;
  private KnowledgeHierarchyAdapter mAdapter;
  @BindView(R.id.knowledge_refresh)
  SmartRefreshLayout mRefresh;
  private KnowledgeHierarchyPresenter mPresenter;
  private List<KnowledgeHierarchyData> mKnowledgeHierarchyDataList;

  private boolean isRefresh = true;

  Unbinder unbinder;


  @Nullable
  @Override
  public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
      @Nullable Bundle savedInstanceState) {
    EventBus.getDefault().register(this);
    View view = inflater.inflate(R.layout.knowledge_tree_layout, container, false);
    unbinder = ButterKnife.bind(this, view);
    initView();
    return view;
  }

  @Subscribe(threadMode = ThreadMode.MAIN)
  public void handleEvent(String event){

  }

  @Override
  public void onDestroyView() {
    super.onDestroyView();
    EventBus.getDefault().unregister(this);
    unbinder.unbind();
  }

  private void initView() {
    setRefresh();
    mPresenter = new KnowledgeHierarchyPresenter(this);
    mKnowledgeHierarchyDataList = new ArrayList<>();
    mAdapter = new KnowledgeHierarchyAdapter(R.layout.knowledge_tree_item,
        mKnowledgeHierarchyDataList);
    mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    mRecyclerView.setHasFixedSize(true);
    mRecyclerView.setAdapter(mAdapter);
    mPresenter.getData();
  }

  @Override
  public void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

  }

  @Override
  public void updateList(List<KnowledgeHierarchyData> data) {
    if (mAdapter.getData().size() < data.size()) {
      mKnowledgeHierarchyDataList = data;
      mAdapter.replaceData(mKnowledgeHierarchyDataList); //替换数据
    } else {
      if (!isRefresh) {
        if (getContext() != null) {
          //展示Toast
          Toasty.info(getContext(), "No More Data.", Toast.LENGTH_SHORT, true).show();
        }
      }
    }
  }

  private void setRefresh() {
    mRefresh.setPrimaryColors(getResources().getColor(R.color.colorPrimary));
    mRefresh.setOnRefreshListener(new OnRefreshListener() {
      @Override
      public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        isRefresh = true;
        mPresenter.getData();
        refreshLayout.finishRefresh(1000);
      }
    });
    mRefresh.setOnLoadMoreListener(new OnLoadMoreListener() {
      @Override
      public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
        isRefresh = false;
        mPresenter.getData();
        refreshLayout.finishLoadMore(1000);
      }
    });
  }
}
