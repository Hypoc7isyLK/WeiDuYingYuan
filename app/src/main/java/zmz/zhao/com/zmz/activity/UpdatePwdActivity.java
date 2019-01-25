package zmz.zhao.com.zmz.activity;

import android.widget.Button;
import android.widget.EditText;

import com.bw.movie.R;

import butterknife.BindView;
import butterknife.OnClick;
import zmz.zhao.com.zmz.bean.Result;
import zmz.zhao.com.zmz.exception.ApiException;
import zmz.zhao.com.zmz.presenter.UpdatePwdPresenter;
import zmz.zhao.com.zmz.util.DaoUtils;
import zmz.zhao.com.zmz.util.EncryptUtil;
import zmz.zhao.com.zmz.view.DataCall;

/**
 * date:2019/1/25 11:44
 * author:赵明珠(啊哈)
 * function:
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
        userid = DaoUtils.USERID();
        sessionId = DaoUtils.SessionId();
        pwdPresenter = new UpdatePwdPresenter(new PwdCall());
    }
    @OnClick(R.id.updatePwd)
    public void updatePwd(){
        String old_pwd = oldpwd.getText().toString().trim();
        String new_pwd = newpwd.getText().toString().trim();
        String current_pwd = current.getText().toString().trim();

        String jmold_pwd = EncryptUtil.encrypt(old_pwd);
        String jmnew_pwd = EncryptUtil.encrypt(new_pwd);
        String jmcurrent_pwd = EncryptUtil.encrypt(current_pwd);

        pwdPresenter.reqeust(userid,sessionId,jmold_pwd,jmnew_pwd,jmcurrent_pwd);
    }
    @Override
    protected void destoryData() {

    }

    private class PwdCall implements DataCall<Result> {
        @Override
        public void success(Result result) {

        }

        @Override
        public void fail(ApiException e) {

        }
    }
}
