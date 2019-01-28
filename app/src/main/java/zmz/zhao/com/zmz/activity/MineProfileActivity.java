package zmz.zhao.com.zmz.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.bw.movie.R;
import com.facebook.drawee.view.SimpleDraweeView;
import com.greendao.gen.DaoMaster;
import com.greendao.gen.UserDao;
import com.greendao.gen.UserInfoDao;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

import zmz.zhao.com.zmz.bean.MyMessage;
import zmz.zhao.com.zmz.bean.Result;
import zmz.zhao.com.zmz.bean.dao.UserInfo;
import zmz.zhao.com.zmz.exception.ApiException;
import zmz.zhao.com.zmz.presenter.MinePresenter;
import zmz.zhao.com.zmz.presenter.UpdatePresenter;
import zmz.zhao.com.zmz.util.DateUtils;
import zmz.zhao.com.zmz.view.DataCall;


/**
 * date:2019/1/23
 * author:赵明珠(啊哈)
 * function:我的信息
 */
public class MineProfileActivity extends BaseActivity {

    MinePresenter minePresenter;
    UpdatePresenter presenter;

    @BindView(R.id.heard_image)
    SimpleDraweeView heard_image;

    @BindView(R.id.nickname)
    TextView nickname;

    @BindView(R.id.minesex)
    TextView minesex;

    @BindView(R.id.minedate)
    TextView minedate;

    @BindView(R.id.nickphone)
    TextView nickphone;

    @BindView(R.id.nickmailbox)
    TextView nickmailbox;

    int sex = 0;
    private int userid;
    private String sessionId;
    private MyMessage myMessage;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_mine_data;
    }

    @Override
    protected void initView() {
        minePresenter = new MinePresenter(new MineCall());
        presenter = new UpdatePresenter(new UpdateCall());
        if (USER_INFO == null) {
            isLogin();
            return;
        }
        userid = USER_INFO.getUserId();

        sessionId = USER_INFO.getSessionId();

        minePresenter.reqeust(userid, sessionId);

    }

    @Override
    public void onResume() {

        super.onResume();

        UserInfoDao userInfoDao = DaoMaster.newDevSession(this, UserDao.TABLENAME).getUserInfoDao();

        List<UserInfo> userInfoList = userInfoDao.queryBuilder().where(UserInfoDao.Properties.Status.eq(1)).list();

        if (userInfoList != null && userInfoList.size() > 0) {
            UserInfo userInfo = userInfoList.get(0);

            int userids = userInfo.getUserId();

            String sessionIds = userInfo.getSessionId();

            Log.e("zmz"+userids,"============="+sessionIds);

            minePresenter.reqeust(userids, sessionIds);
        }

    }

    @OnClick({R.id.back, R.id.update,R.id.nickpwd})
    public void OnClick(View view) {
        switch (view.getId()) {
            case R.id.back:
                //返回
                finish();
                break;

            /**
             * @作者 啊哈
             * @date 2019/1/24
             * 修改信息
             */
            case R.id.update:

                if (USER_INFO == null) {
                    isLogin();
                    return;
                }
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                final View view1 = View.inflate(this, R.layout.activity_mine_dialog, null);


                builder.setTitle("修改信息");
                builder.setView(view1);
                builder.setPositiveButton("修改", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        EditText newName = view1.findViewById(R.id.newname);
                        EditText newBox = view1.findViewById(R.id.newbox);

                        RadioButton man = view1.findViewById(R.id.man);
                        RadioButton woman = view1.findViewById(R.id.woman);

                        if (man.isChecked()){
                            sex = 1;
                        }
                        if (woman.isChecked()){
                            sex = 2;
                        }

                        String name = newName.getText().toString().trim();
                        String box = newBox.getText().toString().trim();

                        presenter.reqeust(userid, sessionId, name, sex, box);

                    }
                });
                builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                builder.show();

                break;
                /**
                 * @作者 啊哈
                 * @date 2019/1/25
                 * 重置密码
                 */
            case R.id.nickpwd:
                if (USER_INFO == null) {
                    isLogin();
                    return;
                }

                Intent intent = new Intent(MineProfileActivity.this, UpdatePwdActivity.class);
                startActivity(intent);
                break;

        }
    }


    public void isLogin() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle("提示");
        builder.setMessage("请先登录");
        builder.setPositiveButton("去登陆", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                Intent intent = new Intent(MineProfileActivity.this, LoginActivity.class);
                intent.putExtra("login", true);
                startActivity(intent);

            }
        }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(MineProfileActivity.this, "取消了", Toast.LENGTH_SHORT).show();
            }
        });
        builder.create().show();
    }

    @Override
    protected void destoryData() {

    }

    private class MineCall implements DataCall<Result<MyMessage>> {
        @Override
        public void success(Result<MyMessage> result) {
            if (result.getStatus().equals("0000")) {

                myMessage = result.getResult();

                heard_image.setImageURI(Uri.parse(myMessage.getHeadPic()));
                nickname.setText(myMessage.getNickName());
                nickphone.setText(myMessage.getPhone());
                nickmailbox.setText(myMessage.getEmail());
                if (myMessage.getSex() == 1) {
                    minesex.setText("男");
                } else {
                    minesex.setText("女");
                }

                try {
                    minedate.setText(DateUtils.dateFormat(new Date(myMessage.getBirthday()), DateUtils.DATE_PATTERN));
                } catch (ParseException e) {
                    e.printStackTrace();
                }

            }


        }

        @Override
        public void fail(ApiException e) {

        }
    }

    private class UpdateCall implements DataCall<Result<MyMessage>> {
        @Override
        public void success(Result<MyMessage> result) {
            minePresenter.reqeust(userid, sessionId);
        }

        @Override
        public void fail(ApiException e) {

        }
    }
}
