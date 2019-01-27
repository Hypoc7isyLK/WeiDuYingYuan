package zmz.zhao.com.zmz.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bw.movie.R;
import com.facebook.drawee.view.SimpleDraweeView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class InsideDetailsActivity extends BaseActivity {


    @BindView(R.id.xiaoxinxin)
    ImageView xiaoxinxin;
    @BindView(R.id.insidetails_title)
    TextView insidetailsTitle;
    @BindView(R.id.insidetails_simple)
    SimpleDraweeView insidetailsSimple;
    @BindView(R.id.insidetails_details)
    Button insidetailsDetails;
    @BindView(R.id.insidetails_foreshow)
    Button insidetailsForeshow;
    @BindView(R.id.insidetails_photo)
    Button insidetailsPhoto;
    @BindView(R.id.insidetails_discuss)
    Button insidetailsDiscuss;
    @BindView(R.id.Focus_back)
    ImageView FocusBack;
    @BindView(R.id.insidetails_buy)
    ImageView insidetailsBuy;
    private Intent mIntent;
    private String mImage;
    private String mName;
    private String mFollow;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_inside_details;
    }

    @Override
    protected void initView() {
        ButterKnife.bind(this);
        mIntent = getIntent();
        mImage = mIntent.getStringExtra("image");
        mName = mIntent.getStringExtra("name");
        mFollow = mIntent.getStringExtra("follow");
        Log.e("lk","fllow"+mFollow);
        insidetailsSimple.setImageURI(mImage);
        insidetailsTitle.setText(mName);
        /*if (mFollow == 1){
            xiaoxinxin.setImageResource(R.mipmap.com_icon_collection_selected);
        }else {
            xiaoxinxin.setImageResource(R.mipmap.com_icon_collection_default);
        }*/
    }

    @Override
    protected void destoryData() {

    }

    @OnClick({R.id.xiaoxinxin, R.id.insidetails_details, R.id.insidetails_foreshow, R.id.insidetails_photo, R.id.insidetails_discuss, R.id.Focus_back, R.id.insidetails_buy})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.xiaoxinxin:
                break;
            case R.id.insidetails_details:
                break;
            case R.id.insidetails_foreshow:
                break;
            case R.id.insidetails_photo:
                break;
            case R.id.insidetails_discuss:
                break;
            case R.id.Focus_back:
                finish();
                break;
            case R.id.insidetails_buy:
                break;
        }
    }
}
