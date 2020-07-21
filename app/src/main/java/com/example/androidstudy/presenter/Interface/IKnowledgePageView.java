package com.example.androidstudy.presenter.Interface;

import com.example.androidstudy.model.bean.FeedArticleData;
import com.example.androidstudy.model.bean.KnowledgeHierarchyData;
import java.util.List;

public interface IKnowledgePageView {

  void updateList(List<KnowledgeHierarchyData> data);
}
