package com.example.androidstudy.presenter.Interface;

import com.example.androidstudy.model.bean.ProjectClassifyData;
import java.util.List;

public interface IProjectPageView {

  void getProjectTypeList(List<ProjectClassifyData> data);
  void requestFail(int code);

}
