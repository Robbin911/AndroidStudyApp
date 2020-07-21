package com.example.androidstudy.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.example.androidstudy.R;

public class TopBarView extends RelativeLayout {


  private ImageView mUserIcon;
  private TextView mTitle;
  private ImageView mSearchIcon;

  public TopBarView(Context context) {
    super(context);
    initView();
  }

  public TopBarView(Context context, AttributeSet attrs) {
    super(context, attrs);
    initView();
  }

  private void initView() {
    LayoutInflater.from(getContext()).inflate(R.layout.top_bar_layout, this, true);
    mUserIcon = findViewById(R.id.iv_user);
    mTitle = findViewById(R.id.tv_title);
    mSearchIcon = findViewById(R.id.iv_search);
  }

  public void setOnClick(OnClickListener listener) {
    mUserIcon.setOnClickListener(listener);
    mSearchIcon.setOnClickListener(listener);
  }

  public void setTitle(int index) {
    switch (index) {
      case 0:
        mTitle.setText(R.string.home);
        break;
      case 1:
        mTitle.setText(R.string.knowledge);
        break;
      case 2:
        mTitle.setText(R.string.wechat);
        break;
      case 3:
        mTitle.setText(R.string.project);
        break;
    }
  }

}
