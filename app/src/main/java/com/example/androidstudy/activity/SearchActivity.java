package com.example.androidstudy.activity;

import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.example.androidstudy.R;
import com.example.androidstudy.model.bean.FeedArticleData;
import com.example.androidstudy.presenter.Interface.ISearchPageView;
import com.example.androidstudy.presenter.SearchPresenter;
import com.example.androidstudy.recycler.BaseArticleAdapter;
import com.example.androidstudy.recycler.HistoryAdapter;
import com.example.androidstudy.recycler.HistoryAdapter.OnHistoryClick;
import com.example.androidstudy.utils.HawkHelper;
import com.example.androidstudy.utils.ResponseUtils;
import com.rengwuxian.materialedittext.MaterialEditText;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import es.dmoral.toasty.Toasty;
import java.util.ArrayList;
import java.util.List;

public class SearchActivity extends AppCompatActivity implements ISearchPageView {


  @BindView(R.id.back_btn)
  ImageView mBackBtn;

  @BindView(R.id.search_history_layout)
  RelativeLayout mHistoryLayout;

  @BindView(R.id.btn_search)
  CardView mBtnSearch;
  @BindView(R.id.search_edit_view)
  MaterialEditText mEdit;

  @BindView(R.id.recycler_result)
  RecyclerView mRecyclerViewResult;

  @BindView(R.id.recycler_history)
  RecyclerView mRecyclerViewHistory;

  @BindView(R.id.empty_hint)
  TextView mEmptyHint;

  @BindView(R.id.clear_history)
  LinearLayout mClearHistory;


  private BaseArticleAdapter mAdapter; //搜索结果列表适配器(基本文章适配器)
  private HistoryAdapter mHistoryAdapter; //历史记录列表适配器

  @BindView(R.id.refresh)
  SmartRefreshLayout mRefresh;

  private int mCurrentPage = 0;
  private boolean isRefresh = true;

  private String mSearchText = "";

  private SearchPresenter mPresenter;
  private List<FeedArticleData> mFeedArticleDataList;
  private List<String> mHistoryList;


  OnClickListener mOnCLickListener = new OnClickListener() {
    @Override
    public void onClick(View v) {
      switch (v.getId()) {
        case R.id.btn_search:
          doSearch();
          break;
        case R.id.back_btn:
          finish();
          break;
        case R.id.clear_history:
          mPresenter.clearHistory();
          break;
      }
    }
  };


  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.search_layout);
    ButterKnife.bind(this);
    initView();
  }

  private void initView() {
    setRefresh();
    mHistoryLayout.setVisibility(View.VISIBLE);
    mRefresh.setVisibility(View.GONE);
    mEmptyHint.setVisibility(View.GONE);

    mPresenter = new SearchPresenter(this);
    mFeedArticleDataList = new ArrayList<>();
    mHistoryList = new ArrayList<>();
    mAdapter = new BaseArticleAdapter(R.layout.base_article_item, mFeedArticleDataList);
    mHistoryAdapter = new HistoryAdapter(R.layout.search_history_item, mHistoryList,
        new OnHistoryClick() {
          @Override
          public void onHistoryClick(int position) {
            //历史记录点击回调
            if (mHistoryAdapter.getData().size() <= 0
                || mHistoryAdapter.getData().size() < position) {
              return;
            }
            mSearchText = mHistoryAdapter.getData().get(position); //获取对应关键字
            mEdit.setText(mSearchText); //将输入框置为所选关键字
            mPresenter.getSearchData(0, mSearchText); //发起搜索请求
          }
        });

    mRecyclerViewResult.setLayoutManager(new LinearLayoutManager(this));
    mRecyclerViewResult.setHasFixedSize(true);
    mRecyclerViewResult.setAdapter(mAdapter);

    mRecyclerViewHistory.setLayoutManager(new LinearLayoutManager(this));
    mRecyclerViewHistory.setHasFixedSize(true);
    mRecyclerViewHistory.setAdapter(mHistoryAdapter);

    mPresenter.getHistory();

    initClick();
  }

  public void initClick() {
    mBtnSearch.setOnClickListener(mOnCLickListener);
    mBackBtn.setOnClickListener(mOnCLickListener);
    mClearHistory.setOnClickListener(mOnCLickListener);
  }

  //点击搜索后续操作
  private void doSearch() {
    //设置搜索关键字
    if (mEdit.getText() != null) {
      mSearchText = mEdit.getText().toString();
    } else {
      mSearchText = "";
    }
    mEdit.setText(mSearchText);
    if (!mSearchText.equals("")) {
      //关键字不为空，将其加入搜索历史记录，发起搜索请求
      HawkHelper.addHistory(mSearchText);
      mPresenter.getSearchData(0, mSearchText);
    } else {
      //关键字为空，隐藏搜索结果列表，展示历史记录列表
      mRefresh.setVisibility(View.GONE);
      mHistoryLayout.setVisibility(View.VISIBLE);
    }
    closeKeyBoard(); //关闭键盘
  }

  //关闭键盘
  private void closeKeyBoard() {
    //清除焦点
    mEdit.clearFocus();
    //获取输入管理对象InputMethodManager
    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
    if (imm != null) {
      //隐藏键盘
      imm.hideSoftInputFromWindow(getWindow().getDecorView().getWindowToken(), 0);
    }
  }

  @Override
  public void getData(List<FeedArticleData> data) {
    mHistoryLayout.setVisibility(View.GONE);
    mRefresh.setVisibility(View.VISIBLE);

    if (isRefresh) {
      mAdapter.replaceData(data);
    } else {
      if (data.size() > 0) {
        mAdapter.addData(data);
      } else {
        Toasty.info(this, "No More Data.", Toast.LENGTH_SHORT, true).show();
      }
    }

    mPresenter.getHistory();
  }

  @Override
  public void getHistory(List<String> data) {
    if (data.size() == 0) {
      mEmptyHint.setVisibility(View.VISIBLE);
    } else {
      mEmptyHint.setVisibility(View.GONE);
    }
    mHistoryAdapter.replaceData(data);
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
        mPresenter.getSearchData(mCurrentPage, mSearchText);
        refreshLayout.finishRefresh(1000);
      }
    });
    mRefresh.setOnLoadMoreListener(new OnLoadMoreListener() {
      @Override
      public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
        isRefresh = false;
        mCurrentPage++;
        mPresenter.getSearchData(mCurrentPage, mSearchText);
        refreshLayout.finishLoadMore(1000);
      }
    });
  }
}
