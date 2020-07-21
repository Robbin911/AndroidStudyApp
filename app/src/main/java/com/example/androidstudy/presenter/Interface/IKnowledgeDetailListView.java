package com.example.androidstudy.presenter.Interface;

import com.example.androidstudy.model.bean.FeedArticleData;
import com.example.androidstudy.model.bean.KnowledgeHierarchyData;
import java.util.List;

public interface IKnowledgeDetailListView {

  void updateList(List<FeedArticleData> data);
}
