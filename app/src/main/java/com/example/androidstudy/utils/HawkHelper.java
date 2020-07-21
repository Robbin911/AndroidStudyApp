package com.example.androidstudy.utils;

import com.example.androidstudy.Constants;
import com.example.androidstudy.view.LikeView;
import com.orhanobut.hawk.Hawk;
import java.util.ArrayList;
import java.util.List;

public class HawkHelper {
  //获取搜索历史列表
  public static List<String> getHistoryList() {
    return Hawk.get(Constants.SEARCH_HISTORY, new ArrayList<>());
  }
  //添加搜索历史
  public static void addHistory(String key) {
    List<String> list = getHistoryList();
    //超过最大记录数则删除最末尾记录
    if (list.size() >= Constants.MAX_HISTORY_COUNT) {
      list.remove(list.size() - 1);
    }
    list.add(0, key);
    Hawk.put(Constants.SEARCH_HISTORY, list);
  }
  //清空搜索历史
  public static List<String> clearHistory(){
    Hawk.put(Constants.SEARCH_HISTORY, new ArrayList<>());
    return new ArrayList<>();
  }
}
