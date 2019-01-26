package zmz.zhao.com.zmz.fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bw.movie.R;
import com.facebook.drawee.view.SimpleDraweeView;
import com.greendao.gen.DaoMaster;
import com.greendao.gen.UserDaoDao;

import java.text.ParseException;
import java.util.Date;

import butterknife.BindView;
import butterknife.OnClick;
import zmz.zhao.com.zmz.activity.FocusActivity;
import zmz.zhao.com.zmz.activity.LoginActivity;
import zmz.zhao.com.zmz.activity.MineProfileActivity;
import zmz.zhao.com.zmz.activity.MyOpinion;
import zmz.zhao.com.zmz.activity.RecordActivity;
import zmz.zhao.com.zmz.bean.Result;
import zmz.zhao.com.zmz.exception.ApiException;
import zmz.zhao.com.zmz.presenter.SignPresenter;
import zmz.zhao.com.zmz.util.DateUtils;
import zmz.zhao.com.zmz.view.DataCall;


public class MineFragment extends BaseFragment {
    @BindView(R.id.my_pic)
    SimpleDraweeView myPic;
    @BindView(R.id.my_name)
    TextView myName;
    @BindView(R.id.my_sign)
    Button mySign;
    @BindView(R.id.my_message)
    LinearLayout myMessage;
    @BindView(R.id.my_attention)
    LinearLayout myAttention;
    @BindView(R.id.my_goupiao)
    LinearLayout myGoupiao;
    @BindView(R.id.my_tickling)
    LinearLayout myTickling;

    @BindView(R.id.my_new_versions)
    LinearLayout myNewVersions;

    @BindView(R.id.my_finish)
    LinearLayout myFinish;

    SignPresenter signPresenter;

    private AlertDialog.Builder mBuilder;
    private SharedPreferences sharedPreferences;
    private int userid;
    private String sessionId;

    @Override
    public void initView(View view) {
        userid = USERDAO.getUserId();
        sessionId = USERDAO.getSessionId();
        String headPic = USERDAO.getHeadPic();

        myPic.setImageURI(Uri.parse(headPic));
        myName.setText(USERDAO.getNickName());

        sharedPreferences = getActivity().getSharedPreferences(String.valueOf(userid), getActivity().MODE_PRIVATE);
        signPresenter = new SignPresenter(new SignCall());
        boolean sign = sharedPreferences.getBoolean("sign", false);
        int olduserId = sharedPreferences.getInt("userId", 0);
        String olddate = sharedPreferences.getString("date", "");
        try {
            String date = DateUtils.dateFormat(new Date(System.currentTimeMillis()), DateUtils.DATE_PATTERN);

            if (date.equals(olddate) && userid == olduserId) {
                if (sign) {
                    mySign.setText("已签到");
                }

            } else {
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putBoolean("sign", false);
                editor.commit();
            }

        } catch (ParseException e1) {
            e1.printStackTrace();
        }


    }

    @Override
    public void initData(View view) {
    }

    @Override
    public int getContent() {
        return R.layout.activity_mine;
    }


    @OnClick({R.id.my_pic, R.id.my_name, R.id.my_sign, R.id.my_message, R.id.my_attention, R.id.my_goupiao, R.id.my_tickling, R.id.my_new_versions, R.id.my_finish})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.my_pic:
                break;
            case R.id.my_name:
                break;
            case R.id.my_sign:
                boolean sign = sharedPreferences.getBoolean("sign", false);
                if (sign) {
                    Toast.makeText(getContext(), "今日已签到", Toast.LENGTH_SHORT).show();
                } else {

                    signPresenter.reqeust(userid, sessionId);
                }

                break;
            case R.id.my_message:
                Intent intent = new Intent(getContext(), MineProfileActivity.class);
                startActivity(intent);
                break;
            case R.id.my_attention:
                Intent intent1 = new Intent(getContext(), FocusActivity.class);
                startActivity(intent1);
                break;
            case R.id.my_goupiao:
                Intent intent3 = new Intent(getContext(), RecordActivity.class);
                startActivity(intent3);
                break;
            case R.id.my_tickling:
                Intent intent2 = new Intent(getContext(), MyOpinion.class);
                startActivity(intent2);
                break;
            case R.id.my_new_versions:
                break;
            case R.id.my_finish:
                mBuilder = new AlertDialog.Builder(getActivity());
                mBuilder.setIcon(R.mipmap.ic_launcher);
                mBuilder.setTitle("提示");
                mBuilder.setMessage("确定要退出吗？");
                mBuilder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        //Intent清除栈FLAG_ACTIVITY_CLEAR_TASK会把当前栈内所有Activity清空；
                        //FLAG_ACTIVITY_NEW_TASK配合使用，才能完成跳转

                        UserDaoDao userDaoDao = DaoMaster.newDevSession(getActivity(),UserDaoDao.TABLENAME).getUserDaoDao();

                        userDaoDao.deleteAll();

                        Intent intent = new Intent(getActivity(),LoginActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                    }
                }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(getActivity(), "取消了", Toast.LENGTH_SHORT).show();
                    }
                });
                mBuilder.create().show();
                break;
        }
    }

    private class SignCall implements DataCall<Result> {
        @Override
        public void success(Result result) {
            if (result.getStatus().equals("0000")) {

                try {
                    String date = DateUtils.dateFormat(new Date(System.currentTimeMillis()), DateUtils.DATE_PATTERN);
                    Toast.makeText(getContext(), "签到成功", Toast.LENGTH_SHORT).show();
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putBoolean("sign", true);
                    editor.putInt("userId", userid);
                    editor.putString("date", date);
                    editor.commit();

                } catch (ParseException e) {
                    e.printStackTrace();
                }

                mySign.setText("已签到");
            }
        }

        @Override
        public void fail(ApiException e) {


        }
    }
}
