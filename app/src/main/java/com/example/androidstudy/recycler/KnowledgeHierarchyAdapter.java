package com.example.androidstudy.recycler;

import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.androidstudy.Constants;
import com.example.androidstudy.R;
import com.example.androidstudy.activity.knowledgeDetailActivity.KnowledgeHierarchyDetailActivity;
import com.example.androidstudy.model.bean.KnowledgeHierarchyData;
import com.example.androidstudy.recycler.KnowledgeHierarchyAdapter.KnowledgeHierarchyViewHolder;
import com.example.androidstudy.utils.JumpUtils;
import java.util.List;

public class KnowledgeHierarchyAdapter extends
    BaseQuickAdapter<KnowledgeHierarchyData, KnowledgeHierarchyViewHolder> {

  public KnowledgeHierarchyAdapter(int layoutResId, @Nullable List<KnowledgeHierarchyData> data) {
    super(layoutResId, data);
  }

  @Override
  public void onBindViewHolder(KnowledgeHierarchyViewHolder holder, int position) {
    super.onBindViewHolder(holder, position);
    KnowledgeHierarchyAdapter mAdapter = this;
    holder.mLayout.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View v) {

        if (mAdapter.getData().size() <= 0 || mAdapter.getData().size() < position) {
          return;
        }
        JumpUtils.startKnowledgeHierarchyDetailActivity(holder.itemView.getContext(),
            mAdapter.getData().get(position));
      }
    });
  }

  @Override
  protected void convert(KnowledgeHierarchyViewHolder helper, KnowledgeHierarchyData item) {
    if (item.getName() == null) {
      return;
    }
    helper.setText(R.id.item_knowledge_hierarchy_title, item.getName());
    if (item.getChildren() == null) {
      return;
    }
    StringBuilder content = new StringBuilder();
    for (KnowledgeHierarchyData data : item.getChildren()) {
      content.append(data.getName()).append("   ");
    }
    helper.setText(R.id.item_knowledge_hierarchy_content, content.toString());
  }


  public class KnowledgeHierarchyViewHolder extends BaseViewHolder {

    @BindView(R.id.item_knowledge_hierarchy_title)
    TextView mTitle;
    @BindView(R.id.item_knowledge_hierarchy_content)
    TextView mContent;
    @BindView(R.id.knowledge_item_layout)
    CardView mLayout;

    public KnowledgeHierarchyViewHolder(View view) {
      super(view);
      ButterKnife.bind(this, view);
    }
  }

}
