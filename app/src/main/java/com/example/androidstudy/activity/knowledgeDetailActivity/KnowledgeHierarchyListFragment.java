package com.example.androidstudy.activity.knowledgeDetailActivity;

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
import com.example.androidstudy.presenter.Interface.IKnowledgeDetailListView;
import com.example.androidstudy.presenter.KnowledgeDetailListPresenter;
import com.example.androidstudy.recycler.BaseArticleAdapter;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import es.dmoral.toasty.Toasty;
import java.util.ArrayList;
import java.util.List;

public class KnowledgeHierarchyListFragment extends Fragment implements IKnowledgeDetailListView {

  @BindView(R.id.knowledge_hierarchy_list_recycler_view)
  RecyclerView mRecyclerView;
  private BaseArticleAdapter mAdapter;
  @BindView(R.id.knowledge_detail_refresh)
  SmartRefreshLayout mRefresh;
  private KnowledgeDetailListPresenter mPresenter;
  private List<FeedArticleData> mFeedArticleDataList;
  private boolean isRefresh = true;
  private int id;
  private int mCurrentPage;
  private int articlePosition;

  Unbinder unbinder;

  //创建一个实例
  public static KnowledgeHierarchyListFragment getInstance(int id) {
    KnowledgeHierarchyListFragment fragment = new KnowledgeHierarchyListFragment(); //创建对象
    Bundle args = new Bundle();
    args.putInt(Constants.ARG_PARAM, id); //设置参数id
    fragment.setArguments(args); //将Bundle放入Fragment对象
    return fragment;
  }

  @Nullable
  @Override
  public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
      @Nullable Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.knowledge_hierarchy_detail_list_layout, container, false);
    unbinder = ButterKnife.bind(this, view);
    initView();
    return view;
  }

  @Override
  public void onDestroyView() {
    super.onDestroyView();
    unbinder.unbind();
  }

  private void initView() {
    setRefresh();
    Bundle bundle = getArguments();
    id = bundle.getInt(Constants.ARG_PARAM, 0);
    if (id == 0) {
      return;
    }
    mCurrentPage = 0;

    mPresenter = new KnowledgeDetailListPresenter(this);
    mFeedArticleDataList = new ArrayList<>();
    mAdapter = new BaseArticleAdapter(R.layout.base_article_item, mFeedArticleDataList);
    mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    mRecyclerView.setHasFixedSize(true);
    mRecyclerView.setAdapter(mAdapter);

    mPresenter.getData(0, id);
  }

  @Override
  public void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

  }

  @Override
  public void updateList(List<FeedArticleData> data) {
    if (isRefresh) {
      mAdapter.replaceData(data);
    } else {
      if (data.size() > 0) {
        mAdapter.addData(data);
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
        mCurrentPage = 0;
        if (id != 0) {
          mPresenter.getData(0, id);
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
          mPresenter.getData(mCurrentPage, id);
        }
        refreshLayout.finishLoadMore(1000);
      }
    });
  }
}
