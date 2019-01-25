package zmz.zhao.com.zmz.activity;

import android.net.Uri;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bw.movie.R;
import com.facebook.drawee.view.SimpleDraweeView;
import com.greendao.gen.UserDaoDao;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import zmz.zhao.com.zmz.app.MyApplication;
import zmz.zhao.com.zmz.bean.MyMessage;
import zmz.zhao.com.zmz.bean.Result;
import zmz.zhao.com.zmz.bean.dao.UserDao;
import zmz.zhao.com.zmz.exception.ApiException;
import zmz.zhao.com.zmz.presenter.MinePresenter;
import zmz.zhao.com.zmz.util.DaoUtils;
import zmz.zhao.com.zmz.util.DateUtils;
import zmz.zhao.com.zmz.view.DataCall;


/**
 * date:2019/1/23
 * author:赵明珠(啊哈)
 * function:
 */
public class MineProfileActivity extends BaseActivity {

    MinePresenter minePresenter;

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

    @BindView(R.id.nickpwd)
    ImageView nickpwd;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_mine_data;
    }

    @Override
    protected void initView() {
        minePresenter = new MinePresenter(new MineCall());

        int userid = DaoUtils.USERID();

        String sessionId = DaoUtils.SessionId();

        minePresenter.reqeust(userid,sessionId);

    }
    @OnClick(R.id.back)
    public void Back(){
        finish();
    }
    @Override
    protected void destoryData() {

    }

    private class MineCall implements DataCall<Result<MyMessage>> {
        @Override
        public void success(Result<MyMessage> result) {
            if (result.getStatus().equals("0000")) {

                MyMessage myMessage = result.getResult();

                heard_image.setImageURI(Uri.parse(myMessage.getHeadPic()));
                nickname.setText(myMessage.getNickName());
                nickphone.setText(myMessage.getPhone());
                if (myMessage.getSex() == 1){
                    minesex.setText("男");
                }else {
                    minesex.setText("女");
                }

                try {
                    minedate.setText(DateUtils.dateFormat(new Date(myMessage.getBirthday()),DateUtils.DATE_PATTERN));
                } catch (ParseException e) {
                    e.printStackTrace();
                }

            }


        }

        @Override
        public void fail(ApiException e) {

        }
    }
}
