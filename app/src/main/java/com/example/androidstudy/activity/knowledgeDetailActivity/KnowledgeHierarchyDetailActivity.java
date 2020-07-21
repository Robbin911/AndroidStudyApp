package com.example.androidstudy.activity.knowledgeDetailActivity;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.example.androidstudy.Constants;
import com.example.androidstudy.R;
import com.example.androidstudy.model.bean.KnowledgeHierarchyData;
import com.flyco.tablayout.SlidingTabLayout;
import java.util.ArrayList;
import java.util.List;

public class KnowledgeHierarchyDetailActivity extends AppCompatActivity {

  @BindView(R.id.knowledge_detail_title)
  TextView mTitleTv;
  @BindView(R.id.knowledge_hierarchy_detail_tab_layout)
  SlidingTabLayout mTabLayout;
  @BindView(R.id.knowledge_hierarchy_detail_viewpager)
  ViewPager mViewPager;
  @BindView(R.id.back_btn)
  ImageView mBackBtn;

  private List<KnowledgeHierarchyData> knowledgeHierarchyDataList;
  private List<Fragment> mFragments = new ArrayList<>();
  private String chapterName;


  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.knowledge_hierarchy_detail_layout);
    ButterKnife.bind(this);

    mBackBtn.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View v) {
        finish();
      }
    });
    initPageType();
    initViewPagerAndTabLayout();
  }

  private void initPageType() {
    if (getIntent().getBooleanExtra(Constants.IS_SINGLE_CHAPTER, false)) {
      startSingleChapterPager();
    } else {
      startNormalKnowledgeListPager();
    }
  }


  private void initViewPagerAndTabLayout() {
    //创建并设置适配器
    mViewPager.setAdapter(new FragmentStatePagerAdapter(getSupportFragmentManager()) {
      @Override
      public Fragment getItem(int position) {
        return mFragments.get(position);
      }

      @Override
      public int getCount() {
        return mFragments == null ? 0 : mFragments.size();
      }

      @Override
      public CharSequence getPageTitle(int position) {
        //给每个页面设置Tab上展示的标题
        if (getIntent().getBooleanExtra(Constants.IS_SINGLE_CHAPTER, false)) {
          return chapterName;
        } else {
          return knowledgeHierarchyDataList.get(position).getName();
        }
      }
    });
    mTabLayout.setViewPager(mViewPager);
  }

  private void startNormalKnowledgeListPager() {
    KnowledgeHierarchyData knowledgeHierarchyData =
        (KnowledgeHierarchyData) getIntent().getSerializableExtra(Constants.ARG_PARAM);
    if (knowledgeHierarchyData == null || knowledgeHierarchyData.getName() == null) {
      return;
    }
    mTitleTv.setText(knowledgeHierarchyData.getName().trim());
    knowledgeHierarchyDataList = knowledgeHierarchyData.getChildren();
    if (knowledgeHierarchyDataList == null) {
      return;
    }
    for (KnowledgeHierarchyData data : knowledgeHierarchyDataList) {
      mFragments.add(KnowledgeHierarchyListFragment.getInstance(data.getId()));
    }

  }

  private void startSingleChapterPager() {
    String superChapterName = getIntent().getStringExtra(Constants.SUPER_CHAPTER_NAME);
    chapterName = getIntent().getStringExtra(Constants.CHAPTER_NAME);
    int chapterId = getIntent().getIntExtra(Constants.CHAPTER_ID, 0);
    mTitleTv.setText(superChapterName);
    mFragments.add(KnowledgeHierarchyListFragment.getInstance(chapterId));
  }


}
