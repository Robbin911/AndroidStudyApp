package com.example.androidstudy.presenter.Interface;

import com.example.androidstudy.model.bean.FeedArticleData;
import java.util.List;

public interface ISearchPageView {

  void getHistory(List<String> data);
  void getData(List<FeedArticleData> dataList);
  void requestFail(int code);

}
