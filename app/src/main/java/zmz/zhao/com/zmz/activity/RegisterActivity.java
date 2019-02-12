package zmz.zhao.com.zmz.activity;

import android.Manifest;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Point;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.text.InputType;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.bw.movie.R;
import com.umeng.analytics.MobclickAgent;

import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;
import zmz.zhao.com.zmz.bean.Result;
import zmz.zhao.com.zmz.exception.ApiException;
import zmz.zhao.com.zmz.presenter.RegisterPresenter;
import zmz.zhao.com.zmz.util.EncryptUtil;
import zmz.zhao.com.zmz.util.SystemUtil;
import zmz.zhao.com.zmz.view.DataCall;

public class RegisterActivity extends BaseActivity {


    @BindView(R.id.edittext_name)
    EditText edittextName;
    @BindView(R.id.edittext_gender)
    EditText edittextGender;
    @BindView(R.id.edittext_data)
    EditText edittextData;
    @BindView(R.id.edittext_phone)
    EditText edittextPhone;
    @BindView(R.id.edittext_email)
    EditText edittextEmail;
    @BindView(R.id.edittext_login)
    EditText edittextLogin;
    @BindView(R.id.button_register)
    Button buttonRegister;
    private String edname;
    private String edgender;
    private String eddata;
    private String edphone;
    private String edemail;
    private String edlogin;
    private int nan = 1;
    private RegisterPresenter mRegisterPresenter;
    private String mIMEI;
    private String mSystemModel;
    private String mSystemVersion;
    private static double mInch = 0;
    private String edjiami;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_register;
    }

    @Override
    protected void initView() {
        ButterKnife.bind(this);
        buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rigister();
            }
        });

        initData();
    }



    public static double getScreenInch(Activity context) {
            if (mInch != 0.0d) {
                return mInch;
            }

            try {
                int realWidth = 0, realHeight = 0;
                Display display = context.getWindowManager().getDefaultDisplay();
                DisplayMetrics metrics = new DisplayMetrics();
                display.getMetrics(metrics);
                if (android.os.Build.VERSION.SDK_INT >= 17) {
                    Point size = new Point();
                    display.getRealSize(size);
                    realWidth = size.x;
                    realHeight = size.y;
                } else if (android.os.Build.VERSION.SDK_INT < 17
                        && android.os.Build.VERSION.SDK_INT >= 14) {
                    Method mGetRawH = Display.class.getMethod("getRawHeight");
                    Method mGetRawW = Display.class.getMethod("getRawWidth");
                    realWidth = (Integer) mGetRawW.invoke(display);
                    realHeight = (Integer) mGetRawH.invoke(display);
                } else {
                    realWidth = metrics.widthPixels;
                    realHeight = metrics.heightPixels;
                }

                mInch =formatDouble(Math.sqrt((realWidth/metrics.xdpi) * (realWidth /metrics.xdpi) + (realHeight/metrics.ydpi) * (realHeight / metrics.ydpi)),1);


            } catch (Exception e) {
                e.printStackTrace();
            }

            return mInch;
        }

    private static double formatDouble(double sqrt, int i) {
        BigDecimal bd = new BigDecimal(sqrt);
        return bd.setScale(i, BigDecimal.ROUND_HALF_UP).doubleValue();
    }



    private void rigister() {
        edname = edittextName.getText().toString().trim();
        edgender = edittextGender.getText().toString().trim();
        eddata = edittextData.getText().toString().trim();
        edphone = edittextPhone.getText().toString().trim();
        edemail = edittextEmail.getText().toString().trim();
        edlogin = edittextLogin.getText().toString().trim();
        edjiami = EncryptUtil.encrypt(edlogin);
        mIMEI = SystemUtil.getIMEI(getApplicationContext());
        mSystemModel = SystemUtil.getSystemModel();
        mSystemVersion = SystemUtil.getSystemVersion();
        if (edgender.equals("男")){
            nan = 1;
        }else if (edgender.equals("女")){
            nan = 2;
        }else {
            nan = 1;
            Toast.makeText(this, "默认为男", Toast.LENGTH_SHORT).show();
        }
        Log.e("lk","name"+edname);
        Log.e("lk","name"+edgender+"变为"+nan);
        Log.e("lk","日期"+eddata);
        Log.e("lk","手机号"+edphone);
        Log.e("lk","邮箱"+edemail);
        Log.e("lk","输入的密码"+edlogin);
        Log.e("lk","加密"+edjiami);
        Log.e("lk","IMEI码"+mIMEI);
        Log.e("lk","型号"+mSystemModel);
        Log.e("lk","版本号"+mSystemVersion);
        Log.e("lk","屏幕尺寸"+String.valueOf(mInch));
        mRegisterPresenter = new RegisterPresenter(new RegisterCall());
        mRegisterPresenter.reqeust(edname,edphone,edjiami,edjiami,nan,eddata,mIMEI,mSystemModel,String.valueOf(mInch),mSystemVersion,edemail);

    }

    @Override
    protected void destoryData() {
        mRegisterPresenter = null;
    }


    private class RegisterCall implements DataCall<Result> {
        @Override
        public void success(Result result) {
            if (result.getStatus().equals("0000")){
                Toast.makeText(RegisterActivity.this, result.getMessage(), Toast.LENGTH_SHORT).show();
                startActivity(new Intent(RegisterActivity.this,LoginActivity.class));
                finish();
            }else {
                Toast.makeText(RegisterActivity.this, result.getMessage(), Toast.LENGTH_SHORT).show();
            }

        }

        @Override
        public void fail(ApiException e) {

        }
    }

    private void initData() {
        edittextData.setInputType(InputType.TYPE_NULL); //不显示系统输入键盘
        edittextData.setOnFocusChangeListener(new View.OnFocusChangeListener() {

            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                // TODO Auto-generated method stub
                if(hasFocus){
                    Calendar c = Calendar.getInstance();
                    new DatePickerDialog(RegisterActivity.this, new DatePickerDialog.OnDateSetListener() {

                        @Override
                        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                            // TODO Auto-generated method stub
                            edittextData.setText(year+"/"+(monthOfYear+1)+"/"+dayOfMonth);
                        }
                    }, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH)).show();

                }
            }
        });

        edittextData.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Calendar c = Calendar.getInstance();
                new DatePickerDialog(RegisterActivity.this, new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        // TODO Auto-generated method stub
                        edittextData.setText(year+"/"+(monthOfYear+1)+"/"+dayOfMonth);
                    }
                }, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH)).show();

            }
        });



    }
    @Override
    public void onResume() {
        super.onResume();
        MobclickAgent.onPageStart("注册");
        MobclickAgent.onResume(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd("注册");
        MobclickAgent.onPause(this);
    }
}
