package zmz.zhao.com.zmz.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import zmz.zhao.com.zmz.R;

public class LoginActivity extends BaseActivity {

    private TextView textview_register;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    protected void initView() {
        textview_register = findViewById(R.id.textview_register);
        textview_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this,RegisterActivity.class));
            }
        });
    }

    @Override
    protected void destoryData() {

    }


}
