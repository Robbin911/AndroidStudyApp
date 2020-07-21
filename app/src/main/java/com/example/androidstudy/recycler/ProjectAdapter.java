package com.example.androidstudy.recycler;

import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.androidstudy.R;
import com.example.androidstudy.model.bean.FeedArticleData;
import com.example.androidstudy.recycler.ProjectAdapter.ProjectHolder;
import com.example.androidstudy.utils.JumpUtils;
import java.util.List;

public class ProjectAdapter extends BaseQuickAdapter<FeedArticleData, ProjectHolder> {

  public ProjectAdapter(int layoutResId, @Nullable List<FeedArticleData> data) {
    super(layoutResId, data);
  }

  @Override
  protected void convert(ProjectHolder helper, FeedArticleData item) {
    if (!TextUtils.isEmpty(item.getEnvelopePic())) {
      Glide.with(helper.itemView).load(item.getEnvelopePic())
          .into((ImageView) helper.getView(R.id.item_project_list_iv));
    }
    if (!TextUtils.isEmpty(item.getTitle())) {
      helper.setText(R.id.item_project_list_title_tv, item.getTitle());
    }
    if (!TextUtils.isEmpty(item.getDesc())) {
      helper.setText(R.id.item_project_list_content_tv, item.getDesc());
    }
    if (!TextUtils.isEmpty(item.getNiceDate())) {
      helper.setText(R.id.item_project_list_time_tv, item.getNiceDate());
    }
    if (!TextUtils.isEmpty(item.getAuthor())) {
      helper.setText(R.id.item_project_list_author_tv, item.getAuthor());
    }
    helper.addOnClickListener(R.id.project_layout);
  }

  @Override
  public void onBindViewHolder(ProjectHolder holder, int position) {
    super.onBindViewHolder(holder, position);
    ProjectAdapter mAdapter = this;
    holder.mLayout.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View v) {
        if (mAdapter.getData().size() <= 0 || mAdapter.getData().size() < position) {
          return;
        }
        JumpUtils.startArticleDetailActivity(holder.itemView.getContext(),
            mAdapter.getData().get(position).getId(),
            mAdapter.getData().get(position).getTitle(),
            mAdapter.getData().get(position).getLink(),
            mAdapter.getData().get(position).isCollect(),
            false,
            false);
      }
    });
  }

  public class ProjectHolder extends BaseViewHolder {

    @BindView(R.id.project_layout)
    CardView mLayout;
    @BindView(R.id.item_project_list_iv)
    ImageView mProjectIv;
    @BindView(R.id.item_project_list_title_tv)
    TextView mTitleTv;
    @BindView(R.id.item_project_list_content_tv)
    TextView mContentTv;
    @BindView(R.id.item_project_list_time_tv)
    TextView mTimeTv;
    @BindView(R.id.item_project_list_author_tv)
    TextView mAuthorTv;

    public ProjectHolder(View view) {
      super(view);
      ButterKnife.bind(this, view);
    }
  }


}
