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
import com.example.androidstudy.Constants;
import com.example.androidstudy.R;
import com.example.androidstudy.model.bean.FeedArticleData;
import com.example.androidstudy.model.bean.ProjectListData;
import com.example.androidstudy.presenter.Interface.IProjectDetailPageView;
import com.example.androidstudy.presenter.ProjectDetailListPresenter;
import com.example.androidstudy.recycler.ProjectAdapter;
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

public class ProjectDetailFragment extends Fragment implements IProjectDetailPageView {

  @BindView(R.id.project_list_recycler_view)
  RecyclerView mRecyclerView;
  private ProjectAdapter mAdapter;
  @BindView(R.id.project_detail_refresh)
  SmartRefreshLayout mRefresh;
  private ProjectDetailListPresenter mPresenter;
  private List<FeedArticleData> mFeedArticleDataList;
  private List<FeedArticleData> mTempList = new ArrayList<>();

  private boolean isRefresh = true;
  private int id;
  private int mCurrentPage = 1;

  Unbinder unbinder;


  public static ProjectDetailFragment getInstance(int id) {
    ProjectDetailFragment fragment = new ProjectDetailFragment();
    Bundle args = new Bundle();
    args.putInt(Constants.ARG_PARAM, id);
    fragment.setArguments(args);
    return fragment;
  }

  @Nullable
  @Override
  public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
      @Nullable Bundle savedInstanceState) {
    EventBus.getDefault().register(this);
    View view = inflater.inflate(R.layout.project_detail_list_layout, container, false);
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
    Bundle bundle = getArguments();
    id = bundle.getInt(Constants.ARG_PARAM, 0);
    if (id == 0) {
      return;
    }
    mCurrentPage = 1;

    mPresenter = new ProjectDetailListPresenter(this);
    mFeedArticleDataList = new ArrayList<>();
    mAdapter = new ProjectAdapter(R.layout.project_detail_item, mFeedArticleDataList);
    mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    mRecyclerView.setHasFixedSize(true);
    mRecyclerView.setAdapter(mAdapter);

    mPresenter.getData(id, 1);
  }

  @Override
  public void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

  }

  @Override
  public void updateList(ProjectListData data) {
    mTempList = data.getDatas();
    for (int i = 0; i < mTempList.size(); i++) {
      if (mTempList.get(i).getTitle().contains("Wan") || mTempList.get(i).getTitle()
          .contains("玩")) {
        mTempList.remove(i);
        i--;
      }
    }
    if (isRefresh) {
      mAdapter.replaceData(mTempList);
    } else {
      if (data.getDatas().size() > 0) {
        mAdapter.addData(mTempList);
      } else {
        if (getContext() != null) {
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
        mCurrentPage = 1;
        if (id != 0) {
          mPresenter.getData(id, 1);

        }
        refreshLayout.finishRefresh(1000);
      }
    });
    mRefresh.setOnLoadMoreListener(new OnLoadMoreListener() {
      @Override
      public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
        isRefresh = false;
        mCurrentPage++;
        if (id != 0) {
          mPresenter.getData(id, mCurrentPage);

        }
        refreshLayout.finishLoadMore(1000);
      }
    });
  }
}
