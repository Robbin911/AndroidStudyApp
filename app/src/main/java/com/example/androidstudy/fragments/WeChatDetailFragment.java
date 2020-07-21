package com.example.androidstudy.fragments;

import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.RelativeLayout;
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
import com.example.androidstudy.presenter.WeChatDetailListPresenter;
import com.example.androidstudy.recycler.BaseArticleAdapter;
import com.rengwuxian.materialedittext.MaterialEditText;
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

public class WeChatDetailFragment extends Fragment implements IKnowledgeDetailListView {

  @BindView(R.id.search_edit)
  MaterialEditText mSearchEdit;
  @BindView(R.id.wechat_list_recycler_view)
  RecyclerView mRecyclerView;
  private BaseArticleAdapter mAdapter;
  @BindView(R.id.wechat_detail_refresh)
  SmartRefreshLayout mRefresh;
  private WeChatDetailListPresenter mPresenter;
  private List<FeedArticleData> mFeedArticleDataList;

  @BindView(R.id.background)
  RelativeLayout mBackground;

  private String mKey = "";
  private boolean isRefresh = true;
  private int id;
  private int mCurrentPage = 1;

  Unbinder unbinder;


  public static WeChatDetailFragment getInstance(int id) {
    WeChatDetailFragment fragment = new WeChatDetailFragment();
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
    View view = inflater.inflate(R.layout.wechat_detail_list_layout, container, false);
    unbinder = ButterKnife.bind(this, view);
    initView();
    return view;
  }


  @Subscribe(threadMode = ThreadMode.MAIN)
  public void handleEvent(String event){
    if(event.equals(Constants.REFRESH_PAGE)){
      if (mKey.equals("")) {
        mPresenter.getData(id, 1);
      } else {
        mPresenter.getSearchData(id, 1, mKey);
      }
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
    Bundle bundle = getArguments();
    id = bundle.getInt(Constants.ARG_PARAM, 0);
    if (id == 0) {
      return;
    }
    mCurrentPage = 1;

    mPresenter = new WeChatDetailListPresenter(this);
    mFeedArticleDataList = new ArrayList<>();
    mAdapter = new BaseArticleAdapter(R.layout.base_article_item, mFeedArticleDataList);
    mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    mRecyclerView.setHasFixedSize(true);
    mRecyclerView.setAdapter(mAdapter);

    mPresenter.getData(id, 1);

    mSearchEdit.addTextChangedListener(new TextWatcher() {
      @Override
      public void beforeTextChanged(CharSequence s, int start, int count, int after) { }
      @Override
      public void onTextChanged(CharSequence s, int start, int before, int count) {
        //输入框内容变化监听
        isRefresh = true;
        mKey = s.toString();
        mCurrentPage = 1;
        if (mKey.equals("")) {
          //内容为空则重新获取全部数据
          mPresenter.getData(id, mCurrentPage);
        } else {
          //内容不为空则发起搜索请求
          mPresenter.getSearchData(id, mCurrentPage, mKey);
        }
      }
      @Override
      public void afterTextChanged(Editable s) {

      }
    });

    mBackground.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View v) {
        mSearchEdit.clearFocus();
        InputMethodManager imm = (InputMethodManager) getActivity()
            .getSystemService(Context.INPUT_METHOD_SERVICE);
        // 隐藏软键盘
        if (imm != null) {
          imm.hideSoftInputFromWindow(
              getActivity().getWindow().getDecorView().getWindowToken(), 0);
        }
      }
    });
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
        mCurrentPage = 1;
        if (id != 0) {
          if (mKey.equals("")) {
            mPresenter.getData(id, 1);
          } else {
            mPresenter.getSearchData(id, 1, mKey);
          }
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
          if (mKey.equals("")) {
            mPresenter.getData(id, mCurrentPage);
          } else {
            mPresenter.getSearchData(id, mCurrentPage, mKey);
          }
        }
        refreshLayout.finishLoadMore(1000);
      }
    });
  }
}
