package com.example.androidstudy.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import com.example.androidstudy.R;
import com.example.androidstudy.model.bean.WxAuthor;
import com.example.androidstudy.https.BaseResponse;
import com.example.androidstudy.presenter.Interface.IWeChatPageView;
import com.example.androidstudy.presenter.WeChatPagePresenter;
import com.example.androidstudy.utils.ResponseUtils;
import com.flyco.tablayout.SlidingTabLayout;
import java.util.ArrayList;
import java.util.List;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class WeChatPageFragment extends Fragment implements IWeChatPageView {

  @BindView(R.id.wechat_detail_tab_layout)
  SlidingTabLayout mTabLayout;
  @BindView(R.id.wechat_detail_viewpager)
  ViewPager mViewPager;


  private WeChatPagePresenter mPresenter;
  private List<Fragment> mFragments = new ArrayList<>();

  Unbinder unbinder;

  @Nullable
  @Override
  public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
      @Nullable Bundle savedInstanceState) {
    EventBus.getDefault().register(this);
    View view = inflater.inflate(R.layout.wechat_page_layout, container, false);
    unbinder = ButterKnife.bind(this, view);

    mPresenter = new WeChatPagePresenter(this);
    mPresenter.getData();
    return view;
  }

  @Subscribe(threadMode = ThreadMode.MAIN)
  public void handleEvent(String event){

  }

  @Override
  public void onDestroyView() {
    super.onDestroyView();
    EventBus.getDefault().unregister(this);
    unbinder.unbind();
  }

  @Override
  public void getWeChatAuthorData(List<WxAuthor> data) {

    mFragments.clear();
    for (WxAuthor author : data) {
      mFragments.add(WeChatDetailFragment.getInstance(author.getId()));
    }
    initViewPagerAndTabLayout(data);

  }

  @Override
  public void requestFail(int code) {
    ResponseUtils.handleErrorText(getContext(), code);
  }

  private void initViewPagerAndTabLayout(List<WxAuthor> mWeChatAutherList) {
    mViewPager.setAdapter(new FragmentStatePagerAdapter(getChildFragmentManager()) {
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

        return mWeChatAutherList.get(position).getName();
      }
    });
    mTabLayout.setViewPager(mViewPager);
  }

}
