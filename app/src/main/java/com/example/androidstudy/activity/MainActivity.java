package com.example.androidstudy.activity;

import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager.widget.ViewPager.OnPageChangeListener;
import com.example.androidstudy.Constants;
import com.example.androidstudy.R;
import com.example.androidstudy.fragments.HomePageFragment;
import com.example.androidstudy.fragments.KnowledgeTreeFragment;
import com.example.androidstudy.fragments.ProjectPageFragment;
import com.example.androidstudy.fragments.WeChatPageFragment;
import com.example.androidstudy.https.RetrofitManager;
import com.example.androidstudy.presenter.Interface.IMainPageView;
import com.example.androidstudy.presenter.MainPresenter;
import com.example.androidstudy.utils.JumpUtils;
import com.example.androidstudy.view.BottomBarView;
import com.example.androidstudy.view.TopBarView;
import com.orhanobut.hawk.Hawk;
import es.dmoral.toasty.Toasty;
import java.util.ArrayList;
import java.util.List;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class MainActivity extends AppCompatActivity implements IMainPageView {

  private ViewPager mViewPager;
  private FragmentPagerAdapter mAdapter;
  private List<Fragment> mFragments;

  private TextView mAccount;
  private DrawerLayout mDrawer;
  private LinearLayout mCollection;
  private CardView mLogout;

  private TopBarView mTopBar;
  private BottomBarView mBottomBar;

  private MainPresenter mPresenter;

  OnClickListener mOnCLickListener = new OnClickListener() {
    @Override
    public void onClick(View v) {
      switch (v.getId()) {
        case R.id.tab_home:
          selectTab(0);
          break;
        case R.id.tab_knowledge:
          selectTab(1);
          break;
        case R.id.tab_wechat:
          selectTab(2);
          break;
        case R.id.tab_project:
          selectTab(3);
          break;
        case R.id.iv_user:
          mDrawer.openDrawer(Gravity.LEFT);
          break;
        case R.id.iv_search:
          JumpUtils.startSearchActivity(MainActivity.this);
          break;
        case R.id.tv_username:
          if (Hawk.get(Constants.ACCOUNT, "").equals("")) {
            JumpUtils.startLoginActivity(MainActivity.this);
            mDrawer.closeDrawer(Gravity.LEFT);
          }
          break;
        case R.id.tv_collection:
          collectionClick();
          break;
        case R.id.btn_logout:
          logoutClick();
          break;
      }
    }
  };

  private void collectionClick() {
    if (Hawk.get(Constants.ACCOUNT, "").equals("")) {
      JumpUtils.startLoginActivity(MainActivity.this);
      mDrawer.closeDrawer(Gravity.LEFT);
    } else {
      JumpUtils.startCollectionActivity(MainActivity.this);
      mDrawer.closeDrawer(Gravity.LEFT);
    }
  }

  private void logoutClick() {
    if (Hawk.get(Constants.ACCOUNT, "").equals("")) {
      Toasty.info(this, "Not Login").show();
      return;
    }
    mPresenter.logout();
  }


  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    EventBus.getDefault().register(this);

    requestWindowFeature(Window.FEATURE_NO_TITLE);
    setContentView(R.layout.activity_main);
    initViews();
    initFragments();
    initClickEvents();
    selectTab(0);
    mPresenter = new MainPresenter(this);
    mPresenter.autoLogin();
  }


  @Subscribe(threadMode = ThreadMode.MAIN)
  public void handleEvent(String event) {

  }

  @Override
  protected void onDestroy() {
    super.onDestroy();
    EventBus.getDefault().unregister(this);
  }

  @Override
  protected void onResume() {
    super.onResume();
  }

  private void initFragments() {
    //创建4个主要模块Fragment对象实例
    mFragments = new ArrayList<>();
    mFragments.add(new HomePageFragment());
    mFragments.add(new KnowledgeTreeFragment());
    mFragments.add(new WeChatPageFragment());
    mFragments.add(new ProjectPageFragment());
    //创建适配器
    mAdapter = new FragmentPagerAdapter(getSupportFragmentManager()) {
      @Override
      public Fragment getItem(int position) {
        return mFragments.get(position);
      }

      @Override
      public int getCount() {
        return mFragments.size();
      }
    };
    mViewPager.setAdapter(mAdapter); //设置适配器
    //添加页面切换监听
    mViewPager.addOnPageChangeListener(new OnPageChangeListener() {
      @Override
      public void onPageSelected(int position) {
        //当页面切换时，根据下标position刷新状态
        mViewPager.setCurrentItem(position);
        selectTab(position);
      }

      @Override
      public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) { }
      @Override
      public void onPageScrollStateChanged(int state) { }
    });
  }

  private void initClickEvents() {
    if (mOnCLickListener != null) {
      if (mTopBar != null) {
        mTopBar.setOnClick(mOnCLickListener);
      }
      if (mBottomBar != null) {
        mBottomBar.setOnClick(mOnCLickListener);
      }

      mAccount.setOnClickListener(mOnCLickListener);
      mCollection.setOnClickListener(mOnCLickListener);
      mLogout.setOnClickListener(mOnCLickListener);
    }
  }

  //初始化控件
  private void initViews() {
    mViewPager = findViewById(R.id.view_pager);
    mTopBar = findViewById(R.id.top_bar);
    mBottomBar = findViewById(R.id.bottom_bar);

    mAccount = findViewById(R.id.tv_username);

    mLogout = findViewById(R.id.btn_logout);
    mCollection = findViewById(R.id.tv_collection);

    mDrawer = (DrawerLayout) findViewById(R.id.drawer);

    mAccount.setText(Hawk.get(Constants.ACCOUNT, "Click To Login"));

  }

  private void selectTab(int position) {
    if (mViewPager != null) {
      mTopBar.setTitle(position);
      mBottomBar.setSelect(position);
      mViewPager.setCurrentItem(position);
    }
  }

  @Override
  public void autoLoginSuccess() {
    mAccount.setText(Hawk.get(Constants.ACCOUNT, "Click To Login"));
    EventBus.getDefault().post(Constants.REFRESH_PAGE);
  }

  @Override
  public void autoLoginFail(int code) {
    mAccount.setText("Click To Login");
  }

  @Override
  public void logoutSuccess() {
    Hawk.put(Constants.ACCOUNT, "");
    Hawk.put(Constants.PASSWORD, "");
    mAccount.setText("Click To Login");

    RetrofitManager.cookieJar.clear();
    EventBus.getDefault().post(Constants.REFRESH_PAGE);
    JumpUtils.startLoginActivity(MainActivity.this);
  }

  @Override
  public void logoutFail() {
    Toasty.error(this, "Log Out Error!").show();
  }
}