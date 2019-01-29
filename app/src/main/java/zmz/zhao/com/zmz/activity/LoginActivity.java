package zmz.zhao.com.zmz.activity;

import android.content.Intent;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.text.method.TransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.bw.movie.R;
import com.bw.movie.wxapi.WXEntryActivity;
import com.greendao.gen.DaoMaster;
import com.greendao.gen.UserDao;
import com.greendao.gen.UserInfoDao;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import zmz.zhao.com.zmz.bean.LoginBean;
import zmz.zhao.com.zmz.bean.Result;
import zmz.zhao.com.zmz.exception.ApiException;
import zmz.zhao.com.zmz.presenter.LoginPresenter;

import zmz.zhao.com.zmz.presenter.WeChatLoginPresenter;
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
    private String isLogin;
    private WeChatLoginPresenter mWeChatLoginPresenter;

    @Override
    protected int getLayoutId() {
        if ( USER_INFO!= null){
            boolean loginFlag = USER_INFO.getLogin_flag();
            if (loginFlag){
                startActivity(new Intent(LoginActivity.this, HomeActivity.class));
                finish();
            }
        }

        return R.layout.activity_login;
    }

    @Override
    protected void initView() {

        Intent intent = getIntent();

        isLogin = intent.getStringExtra("isLogin");

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

        if (USER_INFO != null){
            boolean flag = USER_INFO.getFlag();

            if (flag){
                edittextPhone.setText(USER_INFO.getPhone());
                edittextPwd.setText(USER_INFO.getPwd());
                checkRemember.setChecked(flag);
            }
        }

        flag_num = checkRemember.isChecked();
        login_flag = checkAutologin.isChecked();

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


    @OnClick({R.id.imageview_click,R.id.login_weixin})
    public void onViewClicked(View view) {
        switch (view.getId()){
            case R.id.imageview_click:
                xiaoyanjing();
                break;
            case R.id.login_weixin:
                weixin();
                break;
        }

    }

    private void weixin() {
        IWXAPI mApi = WXAPIFactory.createWXAPI(this, WXEntryActivity.WEIXIN_APP_ID, true);
        mApi.registerApp(WXEntryActivity.WEIXIN_APP_ID);
        if (mApi != null && mApi.isWXAppInstalled()) {
            SendAuth.Req req = new SendAuth.Req();
            req.scope = "snsapi_userinfo";
            req.state = "wechat_sdk_demo_test_neng";
            mApi.sendReq(req);

            /*mWeChatLoginPresenter = new WeChatLoginPresenter(new WechatCall());
            mWeChatLoginPresenter.reqeust(code);*/

            if (isLogin != null &&isLogin.equals("1")){
                Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                startActivity(intent);
            }

            finish();


        } else {
            Toast.makeText(this, "用户未安装微信", Toast.LENGTH_SHORT).show();
        }
    }

    private void xiaoyanjing() {
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

                result.getResult().getUserInfo().setFlag(flag_num);

                result.getResult().getUserInfo().setLogin_flag(login_flag);

                result.getResult().getUserInfo().setPwd(edpwd);


                result.getResult().getUserInfo().setUserId(result.getResult().getUserId());
                result.getResult().getUserInfo().setSessionId(result.getResult().getSessionId());


                result.getResult().getUserInfo().setStatus(1);


                userInfoDao.insertOrReplace(result.getResult().getUserInfo());


                Toast.makeText(LoginActivity.this, result.getMessage(), Toast.LENGTH_SHORT).show();

                if (isLogin != null &&isLogin.equals("1")){
                    Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                    startActivity(intent);
                }

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
