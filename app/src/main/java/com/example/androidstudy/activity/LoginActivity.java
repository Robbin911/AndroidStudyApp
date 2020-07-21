package com.example.androidstudy.activity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.RelativeLayout;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.example.androidstudy.Constants;
import com.example.androidstudy.R;
import com.example.androidstudy.presenter.Interface.ILoginPageView;
import com.example.androidstudy.presenter.LoginPresenter;
import com.example.androidstudy.utils.JumpUtils;
import com.example.androidstudy.utils.ResponseUtils;
import com.orhanobut.hawk.Hawk;
import com.rengwuxian.materialedittext.MaterialEditText;
import es.dmoral.toasty.Toasty;

public class LoginActivity extends AppCompatActivity implements ILoginPageView {

  @BindView(R.id.root_layout)
  RelativeLayout root;
  @BindView(R.id.username)
  MaterialEditText mUsernameEdit;
  @BindView(R.id.password)
  MaterialEditText mPasswordEdit;
  @BindView(R.id.btn_login)
  CardView mLogin;
  @BindView(R.id.btn_register)
  CardView mRegister;

  OnClickListener mListener;
  LoginPresenter mPresenter;

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.login_layout);
    ButterKnife.bind(this);

    mPresenter = new LoginPresenter(this);
    mListener = new OnClickListener() {
      @Override
      public void onClick(View v) {
        switch (v.getId()) {
          case R.id.root_layout:
            if (mUsernameEdit != null && mPasswordEdit != null) {
              mUsernameEdit.clearFocus();
              mPasswordEdit.clearFocus();
              InputMethodManager imm = (InputMethodManager) LoginActivity.this
                  .getSystemService(Context.INPUT_METHOD_SERVICE);
              // 隐藏软键盘
              if (imm != null) {
                imm.hideSoftInputFromWindow(
                    LoginActivity.this.getWindow().getDecorView().getWindowToken(), 0);
              }
            }
            break;
          case R.id.btn_login:
            String account = mUsernameEdit.getText().toString();
            String pw = mPasswordEdit.getText().toString();

            if (!account.equals("") && !pw.equals("")) {

              mPresenter.login(account, pw);
            } else {
              Toasty.error(LoginActivity.this, "Information Incomplete!", Toast.LENGTH_SHORT, true)
                  .show();
            }
            break;
          case R.id.btn_register:
            JumpUtils.startRegisterActivity(LoginActivity.this);
            break;
        }
      }
    };
    mLogin.setOnClickListener(mListener);
    mRegister.setOnClickListener(mListener);
    root.setOnClickListener(mListener);
  }

  @Override
  public void onLoginSuccess() {
    JumpUtils.startMainPagerActivity(this);
  }

  @Override
  public void onLoginFail(int msg) {
    ResponseUtils.handleErrorText(this, msg);
  }
}
