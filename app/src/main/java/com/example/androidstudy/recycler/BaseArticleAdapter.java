package com.example.androidstudy.recycler;

import android.text.Html;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.androidstudy.R;
import com.example.androidstudy.model.bean.FeedArticleData;
import com.example.androidstudy.recycler.BaseArticleAdapter.BaseArticleHolder;
import com.example.androidstudy.utils.JumpUtils;
import com.example.androidstudy.view.LikeView;
import java.util.List;

public class BaseArticleAdapter extends BaseQuickAdapter<FeedArticleData, BaseArticleHolder> {

  private boolean isCollectPage = false;

  public BaseArticleAdapter(int layoutResId, @Nullable List<FeedArticleData> data) {
    super(layoutResId, data);
  }

  public void setCollectPage(boolean collectPage) {
    isCollectPage = collectPage;
  }

  @Override
  protected void convert(BaseArticleHolder helper, FeedArticleData item) {
    helper.setText(R.id.author_name, item.getAuthor());
    String classifyName = item.getSuperChapterName() + " / " + item.getChapterName();
    if (isCollectPage) {
      helper.setText(R.id.chapter_name, item.getChapterName());
    } else {
      helper.setText(R.id.chapter_name, classifyName);
    }
    helper.setText(R.id.article_desc, Html.fromHtml(item.getTitle()));
    helper.setText(R.id.time_text, item.getNiceDate());

    helper.mLike.setCollectPage(isCollectPage);
    helper.mLike.setId(item.getId());
    helper.mLike.setStatus(item.isCollect());

    helper.addOnClickListener(R.id.article_layout);
  }

  @Override
  public void onBindViewHolder(BaseArticleHolder holder, int position) {
    super.onBindViewHolder(holder, position);
    BaseArticleAdapter mAdapter = this;
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
            isCollectPage,
            false);
      }
    });

    holder.mChapterName.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View v) {
        if (mAdapter.getData().size() <= 0 || mAdapter.getData().size() < position) {
          return;
        }
        JumpUtils.startSingleKnowledgeHierarchyDetailActivity(holder.itemView.getContext(),
            true,
            mAdapter.getData().get(position).getSuperChapterName(),
            mAdapter.getData().get(position).getChapterName(),
            mAdapter.getData().get(position).getChapterId());
      }
    });
  }

  public class BaseArticleHolder extends BaseViewHolder {

    @BindView(R.id.article_layout)
    CardView mLayout;
    @BindView(R.id.author_name)
    TextView mAuthorName;
    @BindView(R.id.chapter_name)
    TextView mChapterName;
    @BindView(R.id.article_desc)
    TextView mDesc;
    @BindView(R.id.time_text)
    TextView mTime;
    @BindView(R.id.like_view)
    LikeView mLike;

    public BaseArticleHolder(View itemView) {
      super(itemView);
      ButterKnife.bind(this, itemView);
    }
  }
}