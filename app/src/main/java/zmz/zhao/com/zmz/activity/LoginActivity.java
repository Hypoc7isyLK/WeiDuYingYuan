package zmz.zhao.com.zmz.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import zmz.zhao.com.zmz.R;
import zmz.zhao.com.zmz.bean.LoginBean;
import zmz.zhao.com.zmz.bean.Result;
import zmz.zhao.com.zmz.exception.ApiException;
import zmz.zhao.com.zmz.presenter.LoginPresenter;
import zmz.zhao.com.zmz.util.EncryptUtil;
import zmz.zhao.com.zmz.view.DataCall;

public class LoginActivity extends BaseActivity {

    @BindView(R.id.edittext_phone)
    EditText edittextPhone;
    @BindView(R.id.edittext_pwd)
    EditText edittextPwd;
    @BindView(R.id.imageview_click)
    ImageView imageviewClick;
    @BindView(R.id.check_remember)
    CheckBox checkRemember;
    @BindView(R.id.check_autologin)
    CheckBox checkAutologin;
    @BindView(R.id.button_login)
    Button buttonLogin;
    @BindView(R.id.login_weixin)
    ImageView loginWeixin;
    private TextView textview_register;
    private LoginPresenter mLoginPresenter;
    private String edphone;
    private String edpwd;
    private String jiaedpwd;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    protected void initView() {
        ButterKnife.bind(this);
        textview_register = findViewById(R.id.textview_register);
        textview_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
            }
        });

        initData();
    }

    private void initData() {
        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });
    }

    private void login() {
        edphone = edittextPhone.getText().toString().trim();
        edpwd = edittextPwd.getText().toString().trim();
        jiaedpwd = EncryptUtil.encrypt(edpwd);
        Log.e("lk","加密"+jiaedpwd);
        mLoginPresenter = new LoginPresenter(new LoginCall());
        mLoginPresenter.reqeust(edphone,jiaedpwd);
    }



    private class LoginCall implements DataCall<Result<LoginBean>> {
        @Override
        public void success(Result<LoginBean> result) {

        }

        @Override
        public void fail(ApiException e) {

        }
    }
    @Override
    protected void destoryData() {
        mLoginPresenter = null;
    }
}
