package com.example.androidstudy.utils;

import android.content.Context;
import android.content.Intent;
import com.example.androidstudy.Constants;
import com.example.androidstudy.activity.CollectionActivity;
import com.example.androidstudy.activity.LoginActivity;
import com.example.androidstudy.activity.MainActivity;
import com.example.androidstudy.activity.RegisterActivity;
import com.example.androidstudy.activity.SearchActivity;
import com.example.androidstudy.activity.knowledgeDetailActivity.KnowledgeHierarchyDetailActivity;
import com.example.androidstudy.activity.webActivity.ArticleDetailActivity;
import com.example.androidstudy.model.bean.KnowledgeHierarchyData;

public class JumpUtils {

  public static void startArticleDetailActivity(Context mActivity, int id, String articleTitle,
      String articleLink, boolean isCollect,
      boolean isCollectPage, boolean isCommonSite) {
    Intent intent = new Intent(mActivity, ArticleDetailActivity.class);
    intent.putExtra(Constants.ARTICLE_ID, id);
    intent.putExtra(Constants.ARTICLE_TITLE, articleTitle);
    intent.putExtra(Constants.ARTICLE_LINK, articleLink);
    intent.putExtra(Constants.IS_COLLECT, isCollect);
    intent.putExtra(Constants.IS_COLLECT_PAGE, isCollectPage);
    intent.putExtra(Constants.IS_COMMON_SITE, isCommonSite);
    mActivity.startActivity(intent);

  }


  public static void startSingleKnowledgeHierarchyDetailActivity(Context mActivity,
      boolean isSingleChapter,
      String superChapterName, String chapterName, int chapterId) {
    Intent intent = new Intent(mActivity, KnowledgeHierarchyDetailActivity.class);
    intent.putExtra(Constants.IS_SINGLE_CHAPTER, isSingleChapter);
    intent.putExtra(Constants.SUPER_CHAPTER_NAME, superChapterName);
    intent.putExtra(Constants.CHAPTER_NAME, chapterName);
    intent.putExtra(Constants.CHAPTER_ID, chapterId);
    mActivity.startActivity(intent);
  }


  //跳转至知识体系详情页面
  public static void startKnowledgeHierarchyDetailActivity(Context mActivity,
      KnowledgeHierarchyData data) {
    //初始化intent对象
    Intent intent = new Intent(mActivity, KnowledgeHierarchyDetailActivity.class);
    intent.putExtra(Constants.ARG_PARAM, data); //设置参数
    mActivity.startActivity(intent); //启动页面
  }


  public static void startLoginActivity(Context context) {
    Intent intent = new Intent(context, LoginActivity.class);
    context.startActivity(intent);
  }


  public static void startRegisterActivity(Context context) {
    Intent intent = new Intent(context, RegisterActivity.class);
    context.startActivity(intent);
  }


  public static void startMainPagerActivity(Context context) {
    Intent intent = new Intent(context, MainActivity.class);
    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
    context.startActivity(intent);
  }


  public static void startCollectionActivity(Context context) {
    Intent intent = new Intent(context, CollectionActivity.class);
    context.startActivity(intent);
  }

  public static void startSearchActivity(Context mActivity) {
    Intent intent = new Intent(mActivity, SearchActivity.class);
    mActivity.startActivity(intent);
  }


}
