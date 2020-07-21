package com.example.androidstudy.activity.webActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.example.androidstudy.Constants;
import com.example.androidstudy.R;
import com.example.androidstudy.view.LikeView;
import com.just.agentweb.AgentWeb;

public class ArticleDetailActivity extends AppCompatActivity {

  @BindView(R.id.detail_title)
  TextView mTitle;
  @BindView(R.id.back_btn)
  ImageView mBackBtn;
  @BindView(R.id.article_detail_web_view)
  FrameLayout mWebContent;
  @BindView(R.id.detail_like_view)
  LikeView mLike;


  private Bundle bundle;
  private int articleId;
  private String articleLink;
  private String title;

  private boolean isCollectPage;
  private boolean isCollect;
  private AgentWeb mAgentWeb;

  @Override
  protected void onPause() {
    mAgentWeb.getWebLifeCycle().onPause();
    super.onPause();
  }

  @Override
  protected void onResume() {
    mAgentWeb.getWebLifeCycle().onResume();
    super.onResume();
  }

  @Override
  public void onDestroy() {
    mAgentWeb.getWebLifeCycle().onDestroy();
    super.onDestroy();
  }

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.article_detail_layout);
    ButterKnife.bind(this);

    getBundleData();

    mTitle.setText(title);

    mLike.setCollectPage(isCollectPage);
    mLike.setId(articleId);
    mLike.setStatus(isCollect);

    mBackBtn.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View v) {
        finish();
      }
    });
    initAgentWeb();
  }

  private void initAgentWeb() {
    //初始化AgentWeb
    mAgentWeb = AgentWeb.with(this)
        .setAgentWebParent(mWebContent,
            new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT))
        .useDefaultIndicator()
        .setMainFrameErrorView(R.layout.webview_error_view, -1)
        .createAgentWeb()
        .ready()
        .go(articleLink);
  }

  @Override
  public boolean onKeyDown(int keyCode, KeyEvent event) {
    return mAgentWeb.handleKeyEvent(keyCode, event) || super.onKeyDown(keyCode, event);
  }

  //从Bundle中取出数据
  private void getBundleData() {
    bundle = getIntent().getExtras();
    if (bundle != null) {
      title = (String) bundle.get(Constants.ARTICLE_TITLE);
      articleLink = (String) bundle.get(Constants.ARTICLE_LINK);
      articleId = ((int) bundle.get(Constants.ARTICLE_ID));
      isCollect = ((boolean) bundle.get(Constants.IS_COLLECT));
      isCollectPage = ((boolean) bundle.get(Constants.IS_COLLECT_PAGE));
    }
  }


}

