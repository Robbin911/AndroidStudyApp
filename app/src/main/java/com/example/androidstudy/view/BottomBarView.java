package com.example.androidstudy.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.example.androidstudy.R;

public class BottomBarView extends RelativeLayout {

  private RelativeLayout mTabHome;
  private ImageView mIvHome;
  private TextView mTvHome;
  private RelativeLayout mTabKnowledge;
  private ImageView mIvKnowledge;
  private TextView mTvKnowledge;
  private RelativeLayout mTabWechat;
  private ImageView mIvWechat;
  private TextView mTvWechat;
  private RelativeLayout mTabProject;
  private ImageView mIvProject;
  private TextView mTvProject;

  public BottomBarView(Context context) {
    super(context);
    initView();
  }

  public BottomBarView(Context context, AttributeSet attrs) {
    super(context, attrs);
    initView();
  }

  private void initView() {
    LayoutInflater.from(getContext()).inflate(R.layout.bottom_bar_layout, this, true);
    mTabHome = findViewById(R.id.tab_home);
    mIvHome = findViewById(R.id.iv_home);
    mTvHome = findViewById(R.id.bottom_tv_home);

    mTabKnowledge = findViewById(R.id.tab_knowledge);
    mIvKnowledge = findViewById(R.id.iv_knowledge);
    mTvKnowledge = findViewById(R.id.bottom_tv_knowledge);

    mTabWechat = findViewById(R.id.tab_wechat);
    mIvWechat = findViewById(R.id.iv_wechat);
    mTvWechat = findViewById(R.id.bottom_tv_wechat);

    mTabProject = findViewById(R.id.tab_project);
    mIvProject = findViewById(R.id.iv_project);
    mTvProject = findViewById(R.id.bottom_tv_project);
  }

  private void resetSelect() {
    if (mIvHome != null && mIvKnowledge != null && mIvWechat != null && mIvProject != null) {
      mIvHome.setImageDrawable(getResources().getDrawable(R.drawable.home_unselected));
      mIvKnowledge.setImageDrawable(getResources().getDrawable(R.drawable.knowledge_unselected));
      mIvWechat.setImageDrawable(getResources().getDrawable(R.drawable.wechat_unselected));
      mIvProject.setImageDrawable(getResources().getDrawable(R.drawable.project_unselected));
    }

    if (mTvHome != null && mTvKnowledge != null && mTvProject != null && mTvWechat != null) {
      mTvHome.setTextColor(getResources().getColor(R.color.colorText1));
      mTvKnowledge.setTextColor(getResources().getColor(R.color.colorText1));
      mTvWechat.setTextColor(getResources().getColor(R.color.colorText1));
      mTvProject.setTextColor(getResources().getColor(R.color.colorText1));
    }
  }

  public void setSelect(int index) {
    resetSelect();
    switch (index) {
      case 0:
        mIvHome.setImageDrawable(getResources().getDrawable(R.drawable.home_selected));
        mTvHome.setTextColor(getResources().getColor(R.color.colorPrimary));
        break;
      case 1:
        mIvKnowledge.setImageDrawable(getResources().getDrawable(R.drawable.knowledge_selected));
        mTvKnowledge.setTextColor(getResources().getColor(R.color.colorPrimary));
        break;
      case 2:
        mIvWechat.setImageDrawable(getResources().getDrawable(R.drawable.wechat_selected));
        mTvWechat.setTextColor(getResources().getColor(R.color.colorPrimary));
        break;
      case 3:
        mIvProject.setImageDrawable(getResources().getDrawable(R.drawable.project_selected));
        mTvProject.setTextColor(getResources().getColor(R.color.colorPrimary));
        break;
    }
  }

  public void setOnClick(OnClickListener listener) {
    mTabHome.setOnClickListener(listener);
    mTabKnowledge.setOnClickListener(listener);
    mTabWechat.setOnClickListener(listener);
    mTabProject.setOnClickListener(listener);
  }

}