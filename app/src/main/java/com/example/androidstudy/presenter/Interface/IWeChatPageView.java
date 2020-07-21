package com.example.androidstudy.presenter.Interface;

import com.example.androidstudy.model.bean.WxAuthor;
import java.util.List;

public interface IWeChatPageView {

  void getWeChatAuthorData(List<WxAuthor> data);
  void requestFail(int code);

}
