package com.example.androidstudy.presenter.Interface;

public interface IMainPageView {
  void autoLoginSuccess();
  void autoLoginFail(int code);
  void logoutSuccess();
  void logoutFail();
}
