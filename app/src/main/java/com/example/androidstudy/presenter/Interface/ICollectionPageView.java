package com.example.androidstudy.presenter.Interface;

import com.example.androidstudy.model.bean.FeedArticleData;
import java.util.List;

public interface ICollectionPageView {

  void updateList(List<FeedArticleData> data);
  void requestFail(int code);

}
