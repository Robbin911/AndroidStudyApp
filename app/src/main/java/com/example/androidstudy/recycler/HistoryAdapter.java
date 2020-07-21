package com.example.androidstudy.recycler;

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
import com.example.androidstudy.recycler.HistoryAdapter.HistoryHolder;
import java.util.List;

public class HistoryAdapter extends BaseQuickAdapter<String, HistoryHolder> {

  public interface OnHistoryClick {

    void onHistoryClick(int position);
  }

  private OnHistoryClick mListener;

  public HistoryAdapter(int layoutResId, @Nullable List<String> data) {
    super(layoutResId, data);
  }


  public HistoryAdapter(int layoutResId, @Nullable List<String> data, OnHistoryClick listener) {
    super(layoutResId, data);
    mListener = listener;
  }

  @Override
  public void onBindViewHolder(HistoryHolder holder, int position) {
    super.onBindViewHolder(holder, position);
    holder.mLayout.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View v) {
        mListener.onHistoryClick(position);
      }
    });

  }

  @Override
  protected void convert(HistoryHolder helper, String item) {
    helper.setText(R.id.history_name, item);
    helper.addOnClickListener(R.id.search_history_item_layout);
  }

  public class HistoryHolder extends BaseViewHolder {

    @BindView(R.id.search_history_item_layout)
    CardView mLayout;
    @BindView(R.id.history_name)
    TextView mName;

    public HistoryHolder(View view) {
      super(view);
      ButterKnife.bind(this, view);
    }
  }
}

