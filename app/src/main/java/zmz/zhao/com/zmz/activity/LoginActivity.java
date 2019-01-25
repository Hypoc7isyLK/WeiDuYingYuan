package zmz.zhao.com.zmz.activity;

import android.content.Intent;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.text.method.TransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.bw.movie.R;
import com.greendao.gen.UserDaoDao;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import zmz.zhao.com.zmz.app.MyApplication;
import zmz.zhao.com.zmz.bean.LoginBean;
import zmz.zhao.com.zmz.bean.Result;
import zmz.zhao.com.zmz.bean.dao.UserDao;
import zmz.zhao.com.zmz.exception.ApiException;
import zmz.zhao.com.zmz.presenter.LoginPresenter;
import zmz.zhao.com.zmz.util.DaoUtils;
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
    private boolean isHideFirst;
    boolean flag_num = false;
    boolean login_flag = false;
    private UserDaoDao mUserDaoDao;
    private UserDao mUserDao;

    @Override
    protected int getLayoutId() {
        boolean loginFlag = DaoUtils.LoginFlag();

        if (loginFlag){
            startActivity(new Intent(LoginActivity.this, HomeActivity.class));
            finish();
        }
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
        boolean flag = DaoUtils.Flag();

        if (flag){
            Log.e("zmz","======"+flag);
            edittextPhone.setText(DaoUtils.PHONE());
            edittextPwd.setText(DaoUtils.PWD());
            checkRemember.setChecked(flag);
        }
        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });

    }

    private void login() {

        flag_num = checkRemember.isChecked();
        login_flag = checkAutologin.isChecked();

        edphone = edittextPhone.getText().toString().trim();
        edpwd = edittextPwd.getText().toString().trim();
        mLoginPresenter = new LoginPresenter(new LoginCall());
        mLoginPresenter.reqeust(edphone, edpwd);
    }


    @OnClick(R.id.imageview_click)
    public void onViewClicked() {
        if (isHideFirst) {
            //密文
            HideReturnsTransformationMethod method1 = HideReturnsTransformationMethod.getInstance();
            edittextPwd.setTransformationMethod(method1);
            isHideFirst = false;
            imageviewClick.setImageResource(R.mipmap.biyan);
        } else {
            //密文
            TransformationMethod method = PasswordTransformationMethod.getInstance();
            edittextPwd.setTransformationMethod(method);
            isHideFirst = true;
            imageviewClick.setImageResource(R.mipmap.log_icon_eye_default);
        }
        // 光标的位置
        int index = edittextPwd.getText().toString().length();
        edittextPwd.setSelection(index);
    }

    private class LoginCall implements DataCall<Result<LoginBean>> {

        private LoginBean mResult;

        @Override
        public void success(Result<LoginBean> result) {
            if (result.getStatus().equals("0000")) {
                mResult = result.getResult();


                mUserDaoDao = MyApplication.getInstances().getDaoSession().getUserDaoDao();
                mUserDao = new UserDao(result.getResult().getSessionId(),
                        mResult.getUserId(),
                        mResult.getUserInfo().getBirthday(),
                        mResult.getUserInfo().getHeadPic(),
                        mResult.getUserInfo().getId(),
                        mResult.getUserInfo().getLastLoginTime(),
                        mResult.getUserInfo().getNickName(),
                        mResult.getUserInfo().getPhone(),
                        mResult.getUserInfo().getSex(),
                        flag_num,
                        login_flag,edpwd);

                Log.e("zmz","===00==="+flag_num);

                List<UserDao> userDaos = mUserDaoDao.loadAll();
                if (userDaos.size()>0){
                    mUserDaoDao.deleteAll();
                }

                mUserDaoDao.insertOrReplace(mUserDao);

                Toast.makeText(LoginActivity.this, result.getMessage(), Toast.LENGTH_SHORT).show();

                startActivity(new Intent(LoginActivity.this, HomeActivity.class));
                finish();
            } else {
                Toast.makeText(LoginActivity.this, result.getMessage(), Toast.LENGTH_SHORT).show();
            }

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
