package zmz.zhao.com.zmz.activity;

import android.content.Intent;
import android.util.Log;
import android.widget.EditText;

import com.bw.movie.R;
import com.greendao.gen.DaoMaster;
import com.greendao.gen.DaoSession;
import com.greendao.gen.UserInfoDao;

import butterknife.BindView;
import butterknife.OnClick;
import zmz.zhao.com.zmz.bean.Result;
import zmz.zhao.com.zmz.exception.ApiException;
import zmz.zhao.com.zmz.presenter.UpdatePwdPresenter;
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
    private int userid;
    private String sessionId;
    UpdatePwdPresenter pwdPresenter;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_mine_update_pwd;
    }

    @Override
    protected void initView() {

        userid = USER_INFO.getUserId();

        sessionId = USER_INFO.getSessionId();
        Log.e("zmz","=========="+sessionId);
        pwdPresenter = new UpdatePwdPresenter(new PwdCall());
    }
    @OnClick(R.id.updatePwd)
    public void updatePwd(){
        String old_pwd = oldpwd.getText().toString().trim();
        String new_pwd = newpwd.getText().toString().trim();
        String current_pwd = current.getText().toString().trim();

        Log.e("zmz","=========="+sessionId);
        pwdPresenter.reqeust(userid,sessionId,old_pwd,new_pwd,current_pwd);
    }
    @Override
    protected void destoryData() {

    }

    private class PwdCall implements DataCall<Result> {
        @Override
        public void success(Result result) {
            if (result.getStatus().equals("0000")){
                DaoSession daoSession = DaoMaster.newDevSession(UpdatePwdActivity.this, UserInfoDao.TABLENAME);
                UserInfoDao userInfoDao = daoSession.getUserInfoDao();
                userInfoDao.deleteAll();

                Intent intent = new Intent(UpdatePwdActivity.this,LoginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        }

        @Override
        public void fail(ApiException e) {

        }
    }
}
