package zmz.zhao.com.zmz.fragment;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bw.movie.R;
import com.facebook.drawee.view.SimpleDraweeView;
import com.greendao.gen.DaoMaster;
import com.greendao.gen.UserInfoDao;
import com.umeng.analytics.MobclickAgent;

import java.io.File;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import zmz.zhao.com.zmz.activity.FocusActivity;
import zmz.zhao.com.zmz.activity.LoginActivity;
import zmz.zhao.com.zmz.activity.MineProfileActivity;
import zmz.zhao.com.zmz.activity.MyOpinion;
import zmz.zhao.com.zmz.activity.RecordActivity;
import zmz.zhao.com.zmz.activity.SystemMassageActivity;
import zmz.zhao.com.zmz.bean.MineMassage;
import zmz.zhao.com.zmz.bean.Result;
import zmz.zhao.com.zmz.bean.dao.UserInfo;
import zmz.zhao.com.zmz.exception.ApiException;
import zmz.zhao.com.zmz.presenter.HeadPresenter;
import zmz.zhao.com.zmz.presenter.MessagePresenter;
import zmz.zhao.com.zmz.presenter.SignPresenter;
import zmz.zhao.com.zmz.util.FileUtils;
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
    MessagePresenter messagePresenter;
    PopupWindow popWindow;
    private static final int CHOOSE_PICTURE = 1000;
    private static final int TAKE_PICTURE = 1500;

    private AlertDialog.Builder mBuilder;
    private String sessionId;
    private boolean image;
    private int userId;

    @Override
    public void initView(View view) {
        headPresenter = new HeadPresenter(new HeadCall());
        messagePresenter = new MessagePresenter(new MassageCall());
        signPresenter = new SignPresenter(new SignCall());

        if (Build.VERSION.SDK_INT >= 23) {
            if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(getActivity(), new String[]{
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.READ_EXTERNAL_STORAGE}, 100);
            } else {
                image = FileUtils.createDirs("/image/bimap");
            }
        } else {
            image = FileUtils.createDirs("/image/bimap");
        }
        if (USER_INFO == null) {
            return;
        } else {

            userId = USER_INFO.getUserId();
            sessionId = USER_INFO.getSessionId();

            messagePresenter.reqeust(this.userId, sessionId);
        }
    }

    private void isLogin() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("提示");
        builder.setMessage("请先登录");
        builder.setPositiveButton("去登陆", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                Intent intent = new Intent(getActivity(), LoginActivity.class);
                intent.putExtra("login", true);
                startActivity(intent);

            }
        }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(getActivity(), "取消", Toast.LENGTH_SHORT).show();
            }
        });
        builder.create().show();
    }


    @Override
    public void onResume() {
        super.onResume();
        MobclickAgent.onPageStart("我的fragment");
        MobclickAgent.onResume(getActivity());

        UserInfoDao userInfoDao = DaoMaster.newDevSession(getActivity(), UserInfoDao.TABLENAME).getUserInfoDao();

        List<UserInfo> userInfoList = userInfoDao.loadAll();

        if (userInfoList != null && userInfoList.size() > 0) {

            USER_INFO = userInfoList.get(0);

            int userid = USER_INFO.getUserId();

            String sessionId = USER_INFO.getSessionId();

            messagePresenter.reqeust(userid, sessionId);

            Log.e("zmz", "========" + "返回成功");

            Toast.makeText(getActivity(), "返回成功", Toast.LENGTH_SHORT).show();
        }

    }


    @Override
    public void initData(View view) {
    }

    @Override
    public int getContent() {
        return R.layout.activity_mine;
    }


    @OnClick({R.id.my_pic, R.id.my_sign, R.id.system, R.id.my_message, R.id.my_attention, R.id.my_goupiao, R.id.my_tickling, R.id.my_new_versions, R.id.my_finish})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.my_pic:
                if (USER_INFO == null) {
                    isLogin();
                    return;
                } else {

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
                }
                break;
            case R.id.my_sign:
                if (USER_INFO == null) {
                    isLogin();
                    return;
                } else {
                    userId = USER_INFO.getUserId();

                    sessionId = USER_INFO.getSessionId();
                    signPresenter.reqeust(userId, sessionId);
                }


                break;
            case R.id.my_message:

                Intent datum = new Intent(getContext(), MineProfileActivity.class);
                startActivity(datum);

                break;
            case R.id.my_attention:

                Intent attention = new Intent(getContext(), FocusActivity.class);
                startActivity(attention);

                break;
            case R.id.my_goupiao:

                Intent buy = new Intent(getContext(), RecordActivity.class);
                startActivity(buy);


                break;
            case R.id.my_tickling:
                if (USER_INFO == null) {
                    isLogin();
                    return;
                }
                Intent tickling = new Intent(getContext(), MyOpinion.class);
                startActivity(tickling);


                break;
            case R.id.system:
                if (USER_INFO == null) {
                    isLogin();
                    return;
                }
                Intent intent_system = new Intent(getContext(), SystemMassageActivity.class);
                startActivity(intent_system);

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
                        USERINFODAO.deleteAll();
                        USER_INFO = null;
                        myName.setText("未登录");
                        mySign.setText("签到");
                        Uri parse = Uri.parse("res://" + getActivity().getPackageName() + "/" + R.mipmap.head);
                        myPic.setImageURI(parse);

                    }
                }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(getActivity(), "取消", Toast.LENGTH_SHORT).show();
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

        if (Build.VERSION.SDK_INT >= 23) {
            if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(getActivity(), new String[]{
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.READ_EXTERNAL_STORAGE}, 100);
            } else {
                image = FileUtils.createDirs("/image/bimap");
            }
        } else {
            image = FileUtils.createDirs("/image/bimap");
        }

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
                if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    //权限还没有授予，需要在这里写申请权限的代码
                    ActivityCompat.requestPermissions(getActivity(),
                            new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, CHOOSE_PICTURE);
                } else {
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
                tempUri = Uri.parse(FileUtils.getDir("/image/bimap") + "1.jpg");

                Log.e("zmz", "=====" + tempUri);
                //启动相机程序
                Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
                openCameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, tempUri);
                startActivityForResult(intent, TAKE_PICTURE);

            }
        });

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == getActivity().RESULT_OK) {
            switch (requestCode) {
                case TAKE_PICTURE:

                    File imageFile = FileUtils.getImageFile();
                    String path = imageFile.getPath();

                    headPresenter.reqeust(userId, sessionId, path);

                    break;
                case CHOOSE_PICTURE:
                    Uri uri = data.getData();

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
                    headPresenter.reqeust(userId, sessionId, img_path);
                    break;
            }
        }
        popWindow.dismiss();
    }

    private class SignCall implements DataCall<Result> {
        @Override
        public void success(Result result) {
            if (result.getStatus().equals("0000")) {

                Toast.makeText(getContext(), "签到成功", Toast.LENGTH_SHORT).show();

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
            result.getResult();

            Log.e("zmz",""+result.getMessage());

            USER_INFO.setHeadPic(result.getHeadPath());

            USERINFODAO.update(USER_INFO);

            myPic.setImageURI(Uri.parse(result.getHeadPath()));
        }

        @Override
        public void fail(ApiException e) {

        }
    }

    private class MassageCall implements DataCall<Result<MineMassage>> {
        @Override
        public void success(Result<MineMassage> result) {

            MineMassage mineMassage = result.getResult();

            if (result.getStatus().equals("0000")) {

                myPic.setImageURI(Uri.parse(mineMassage.getHeadPic()));
                myName.setText(mineMassage.getNickName());
                if (mineMassage.getUserSignStatus() == 2) {
                    mySign.setText("已签到");
                } else {
                    mySign.setText("签到");
                }
            }
        }

        @Override
        public void fail(ApiException e) {
            Toast.makeText(getContext(), "请先登录！", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd("我的fragment");
        MobclickAgent.onPause(getActivity());
    }
}
