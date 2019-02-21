package zmz.zhao.com.zmz.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bw.movie.R;
import com.greendao.gen.DaoMaster;
import com.greendao.gen.DaoSession;
import com.greendao.gen.UserInfoDao;
import com.umeng.analytics.MobclickAgent;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import zmz.zhao.com.zmz.bean.Result;
import zmz.zhao.com.zmz.exception.ApiException;
import zmz.zhao.com.zmz.presenter.UpdatePwdPresenter;
import zmz.zhao.com.zmz.util.Validator;
import zmz.zhao.com.zmz.view.DataCall;

/**
 * date:2019/1/25 11:44
 * author:赵明珠(啊哈)
 * function:修改密码
 */
public class UpdatePwdActivity extends BaseActivity {


    @BindView(R.id.oldpwd)
    EditText oldpwd;
    @BindView(R.id.newpwd)
    EditText newpwd;
    @BindView(R.id.currentpwd)
    EditText current;
    @BindView(R.id.back)
    ImageView back;
    private int userid;
    private String sessionId;
    UpdatePwdPresenter pwdPresenter;
    private boolean mOldPassword;
    private boolean mNewPassword;
    private boolean mCountPassword;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_mine_update_pwd;
    }

    @Override
    protected void initView() {

        userid = USER_INFO.getUserId();

        sessionId = USER_INFO.getSessionId();
        Log.e("zmz", "==========" + sessionId);
        pwdPresenter = new UpdatePwdPresenter(new PwdCall());
    }

    @OnClick(R.id.updatePwd)
    public void updatePwd() {
        String old_pwd = oldpwd.getText().toString().trim();
        String new_pwd = newpwd.getText().toString().trim();
        String current_pwd = current.getText().toString().trim();

        mOldPassword = Validator.isPassword(old_pwd);
        mNewPassword = Validator.isPassword(new_pwd);
        mCountPassword = Validator.isPassword(current_pwd);
        Log.e("zmz", "==========" + sessionId);
        if (mOldPassword) {
            if (mNewPassword) {
                if (mCountPassword && mNewPassword == mCountPassword) {
                    pwdPresenter.reqeust(userid, sessionId, old_pwd, new_pwd, current_pwd);
                } else {
                    Toast.makeText(this, "两次密码要一致", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(this, "密码仅支持数字、字母、6-18位", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "密码仅支持数字、字母、6-18位", Toast.LENGTH_SHORT).show();
        }


    }

    @Override
    protected void destoryData() {

    }



    @OnClick(R.id.back)
    public void onViewClicked() {
        finish();
    }

    private class PwdCall implements DataCall<Result> {
        @Override
        public void success(Result result) {
            if (result.getStatus().equals("0000")) {
                DaoSession daoSession = DaoMaster.newDevSession(UpdatePwdActivity.this, UserInfoDao.TABLENAME);
                UserInfoDao userInfoDao = daoSession.getUserInfoDao();
                userInfoDao.deleteAll();

                Intent intent = new Intent(UpdatePwdActivity.this, LoginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        }

        @Override
        public void fail(ApiException e) {

        }
    }

    @Override
    public void onResume() {
        super.onResume();
        MobclickAgent.onPageStart("修改密码页面");
        MobclickAgent.onResume(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd("修改密码页面");
        MobclickAgent.onPause(this);
    }
}
