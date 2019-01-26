package zmz.zhao.com.zmz.activity;

import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;

import com.bw.movie.R;

import butterknife.BindView;
import butterknife.OnClick;
import zmz.zhao.com.zmz.bean.Result;
import zmz.zhao.com.zmz.exception.ApiException;
import zmz.zhao.com.zmz.presenter.OpinionPresenter;
import zmz.zhao.com.zmz.view.DataCall;

/**
 * date:2019/1/24 20:37
 * author:赵明珠(啊哈)
 * function:意见反馈
 */
public class MyOpinion extends BaseActivity {

    OpinionPresenter presenter;

    @BindView(R.id.opion)
    RelativeLayout opion;

    @BindView(R.id.opion_hide)
    RelativeLayout opion_hide;

    @BindView(R.id.mine_opinion)
    EditText mine_opinion;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_mine_opinion;
    }

    @Override
    protected void initView() {
        presenter = new OpinionPresenter(new OpinionCall());
        opion_hide.setVisibility(View.GONE);//隐藏


    }
    @OnClick({R.id.opinion_back,R.id.submit})
    public void OnClick(View view){
        switch (view.getId()){
            case R.id.opinion_back:
                finish();
                break;
            case R.id.submit:
                String opinion = mine_opinion.getText().toString().trim();
                int userid = USER_INFO.getUserId();

                String sessionId = USER_INFO.getSessionId();

                presenter.reqeust(userid,sessionId,opinion);
                break;
        }
    }
    @Override
    protected void destoryData() {
        presenter.unBind();
    }

    private class OpinionCall implements DataCall<Result> {
        @Override
        public void success(Result result) {
            if (result.getStatus().equals("0000")) {
                opion.setVisibility(View.GONE);
                opion_hide.setVisibility(View.VISIBLE);
            }
        }

        @Override
        public void fail(ApiException e) {

        }
    }
}
