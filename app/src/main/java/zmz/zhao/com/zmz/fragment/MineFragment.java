package zmz.zhao.com.zmz.fragment;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bw.movie.R;
import com.facebook.drawee.view.SimpleDraweeView;
import com.greendao.gen.DaoMaster;
import com.greendao.gen.UserDaoDao;

import java.io.File;
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
import zmz.zhao.com.zmz.presenter.HeadPresenter;
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

    private Uri tempUri;

    SignPresenter signPresenter;
    HeadPresenter headPresenter;
    PopupWindow popWindow;
    private static final int CHOOSE_PICTURE = 1000;
    private static final int TAKE_PICTURE = 1500;

    private AlertDialog.Builder mBuilder;
    private SharedPreferences sharedPreferences;
    private int userid;
    private String sessionId;

    @Override
    public void initView(View view) {
        userid = USERDAO.getUserId();
        sessionId = USERDAO.getSessionId();
        String headPic = USERDAO.getHeadPic();
        headPresenter = new HeadPresenter(new HeadCall());
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


    @OnClick({R.id.my_pic, R.id.my_sign, R.id.my_message, R.id.my_attention, R.id.my_goupiao, R.id.my_tickling, R.id.my_new_versions, R.id.my_finish})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.my_pic:
                View popView = View.inflate(getActivity(), R.layout.activity_mine_heard_image, null);
                popWindow = new PopupWindow(popView, ViewGroup.LayoutParams.WRAP_CONTENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT, true);
                popWindow.setTouchable(true);
                popWindow.setBackgroundDrawable(new BitmapDrawable());
                popWindow.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);

                View inflate = LayoutInflater.from(getActivity()).inflate(R.layout.activity_mine, null);

                popWindow.showAtLocation(inflate, Gravity.BOTTOM, 0, 0);

                //点击事件
                initPop(popView);
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

    private void initPop(View popView) {
        RelativeLayout camera = popView.findViewById(R.id.camera);
        RelativeLayout album = popView.findViewById(R.id.album);
        RelativeLayout cancel = popView.findViewById(R.id.cancel);

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //取消弹框
                popWindow.dismiss();
            }
        });

        //相册
        album.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (ContextCompat.checkSelfPermission(getActivity(),Manifest.permission.WRITE_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED){
                    //权限还没有授予，需要在这里写申请权限的代码
                    ActivityCompat.requestPermissions(getActivity(),
                            new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, CHOOSE_PICTURE);
                }else {
                    Intent openAlbumIntent = new Intent(
                            Intent.ACTION_PICK);
                    openAlbumIntent.setType("image/*");
                    //用startActivityForResult方法，待会儿重写onActivityResult()方法，拿到图片做裁剪操作
                    startActivityForResult(openAlbumIntent, CHOOSE_PICTURE);
                }

            }
        });

        //相机
        camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent openCameraIntent = new Intent(
                        MediaStore.ACTION_IMAGE_CAPTURE);
                tempUri = Uri.fromFile(new File(Environment
                        .getExternalStorageDirectory(), "temp_image.jpg"));
                // 将拍照所得的相片保存到SD卡根目录
                openCameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, tempUri);
                startActivityForResult(openCameraIntent, TAKE_PICTURE);
            }
        });

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        popWindow.dismiss();

        if (resultCode == getActivity().RESULT_OK) {
            switch (requestCode) {
                case TAKE_PICTURE:


                    File file = new File(Environment.getExternalStorageDirectory() + File.separator + "temp_image.jpg");

                    String path = file.getPath();

                    headPresenter.reqeust(userid,sessionId,path);

                    break;
                case CHOOSE_PICTURE:
                    Uri uri = data.getData();


                    //Log.e("zmz","============"+uri);

                    String[] proj = {MediaStore.Images.Media.DATA};

                    Cursor actualimagecursor = getActivity().managedQuery(uri, proj, null, null, null);

                    int actual_image_column_index = actualimagecursor
                            .getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                    actualimagecursor.moveToFirst();
                    String img_path = actualimagecursor
                            .getString(actual_image_column_index);

                    // 4.0以上平台会自动关闭cursor,所以加上版本判断,OK
                    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.ICE_CREAM_SANDWICH)
                        actualimagecursor.close();

                    headPresenter.reqeust(userid, sessionId, img_path);
                    break;
            }
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

    private class HeadCall implements DataCall<Result> {
        @Override
        public void success(Result result) {
            myPic.setImageURI(Uri.parse(result.getHeadPath()));
        }

        @Override
        public void fail(ApiException e) {

        }
    }
}
