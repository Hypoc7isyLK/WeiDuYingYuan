package zmz.zhao.com.zmz.fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bw.movie.R;
import com.greendao.gen.UserDaoDao;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import zmz.zhao.com.zmz.activity.LoginActivity;
import zmz.zhao.com.zmz.activity.MainActivity;
import zmz.zhao.com.zmz.app.MyApplication;


public class MineFragment extends BaseFragment {
    @BindView(R.id.my_pic)
    ImageView myPic;
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
    Unbinder unbinder;
    private AlertDialog.Builder mBuilder;

    @Override
    public void initView(View view) {
        unbinder = ButterKnife.bind(this, view);
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
                break;
            case R.id.my_message:
                break;
            case R.id.my_attention:
                break;
            case R.id.my_goupiao:
                break;
            case R.id.my_tickling:
                break;
            case R.id.my_new_versions:
                break;
            case R.id.my_finish:
                mBuilder = new AlertDialog.Builder(getActivity());
                mBuilder.setIcon(R.mipmap.ic_launcher);
                mBuilder.setTitle("提示");
                mBuilder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        UserDaoDao userDaoDao = MyApplication.getInstances().getDaoSession().getUserDaoDao();
                        userDaoDao.deleteAll();
                        //Intent清除栈FLAG_ACTIVITY_CLEAR_TASK会把当前栈内所有Activity清空；
                        //FLAG_ACTIVITY_NEW_TASK配合使用，才能完成跳转
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


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
