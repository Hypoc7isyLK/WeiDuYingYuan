package zmz.zhao.com.zmz.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SearchView;
import android.widget.Toast;

import com.bw.movie.R;
import com.umeng.analytics.MobclickAgent;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import zmz.zhao.com.zmz.adapter.OutDetailsAdapter;
import zmz.zhao.com.zmz.bean.Result;
import zmz.zhao.com.zmz.bean.ShowLunBoBean;
import zmz.zhao.com.zmz.exception.ApiException;
import zmz.zhao.com.zmz.presenter.CommingSunPresenter;
import zmz.zhao.com.zmz.presenter.FocusMovieOffPresenter;
import zmz.zhao.com.zmz.presenter.FocusMoviePresenter;
import zmz.zhao.com.zmz.presenter.HotShowingPresenter;
import zmz.zhao.com.zmz.presenter.ShowLunBoPresenter;
import zmz.zhao.com.zmz.view.DataCall;

public class OutDetailsActivity extends BaseActivity {


    @BindView(R.id.dingwei)
    ImageView dingwei;

    @BindView(R.id.move_rmdy)
    RadioButton moveRmdy;

    @BindView(R.id.move_zzry)
    RadioButton moveZzry;

    @BindView(R.id.move_jjsy)
    RadioButton moveJjsy;

    @BindView(R.id.movie_recycle_rmdy)
    RecyclerView movieRecycleRmdy;
    @BindView(R.id.movie_recycle_zzry)
    RecyclerView movieRecycleZzry;
    @BindView(R.id.movie_recycle_jjsy)
    RecyclerView movieRecycleJjsy;
    @BindView(R.id.Focus_back)
    ImageView FocusBack;
    @BindView(R.id.move_radio)
    RadioGroup moveRadio;
    @BindView(R.id.details_search)
    SearchView detailsSearch;

    private ShowLunBoPresenter mShowLunBoPresenter;
    private OutDetailsAdapter mOutDetailsAdapter;
    private String mHei;
    private HotShowingPresenter mHotShowingPresenter;
    private CommingSunPresenter mCommingSunPresenter;
    private int userId;
    private String sessionId;
    FocusMoviePresenter focusMoviePresenter;
    FocusMovieOffPresenter focusMovieOffPresenter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_out_details;
    }

    @Override
    protected void initView() {
        ButterKnife.bind(this);
        /*mSessionId = USER_INFO.getSessionId();
        mUserId = USER_INFO.getUserId();*/
        focusMoviePresenter = new FocusMoviePresenter(new FocusCall());
        focusMovieOffPresenter = new FocusMovieOffPresenter(new FocusMovieOffCall());
        mOutDetailsAdapter = new OutDetailsAdapter(this);
        mHei = getIntent().getStringExtra("hei");
        if (mHei.equals("1")) {
            rmdy();
        } else if (mHei.equals("2")) {
            zzry();
        } else {
            jjsy();
        }
        moveRadio.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.move_rmdy:
                        rmdy();
                        break;
                    case R.id.move_zzry:
                        zzry();
                        break;
                    case R.id.move_jjsy:
                        jjsy();
                        break;
                }
            }
        });

        mOutDetailsAdapter.setOnclickFocuslitener(new OutDetailsAdapter.OnclickFocuslitener() {
            @Override
            public void success(int id, int state) {
                if (USER_INFO != null) {

                    if (state == 1) {
                        focusMovieOffPresenter.reqeust(userId, sessionId, id);
                    } else {
                        focusMoviePresenter.reqeust(userId, sessionId, id);
                    }
                }else {
                    Toast.makeText(OutDetailsActivity.this, "您还未登录", Toast.LENGTH_SHORT).show();
                }
            }
        });

        mOutDetailsAdapter.setOnclicklitener(new OutDetailsAdapter.Onclicklitener() {
            @Override
            public void success(int id) {
                Intent intent = new Intent(OutDetailsActivity.this, InsideDetailsActivity.class);
                intent.putExtra("id", id + "");
                Log.e("lk", "outdetailsid" + id);
                startActivity(intent);
            }
        });
    }

    private void rmdy() {
        moveRadio.check(R.id.move_rmdy);
        movieRecycleRmdy.setVisibility(View.VISIBLE);//显示
        moveRmdy.setTextColor(getResources().getColorStateList(R.color.white));
        moveZzry.setTextColor(getResources().getColorStateList(R.color.colorTextColor));
        moveJjsy.setTextColor(getResources().getColorStateList(R.color.colorTextColor));
        movieRecycleZzry.setVisibility(View.GONE);//隐藏
        movieRecycleJjsy.setVisibility(View.GONE);//隐藏
        mShowLunBoPresenter = new ShowLunBoPresenter(new ShowLunboCall());
        if (USER_INFO != null) {

            userId = USER_INFO.getUserId();
            sessionId = USER_INFO.getSessionId();
            mShowLunBoPresenter.reqeust(userId, sessionId, "1", "20");
        } else {
            mShowLunBoPresenter.reqeust(0, "", "1", "20");
        }


        movieRecycleRmdy.setLayoutManager(new LinearLayoutManager(OutDetailsActivity.this, OrientationHelper.VERTICAL, false));
        movieRecycleRmdy.setAdapter(mOutDetailsAdapter);
    }

    private void zzry() {
        moveRadio.check(R.id.move_zzry);
        movieRecycleZzry.setVisibility(View.VISIBLE);//显示
        moveZzry.setTextColor(getResources().getColorStateList(R.color.white));
        moveRmdy.setTextColor(getResources().getColorStateList(R.color.colorTextColor));
        moveJjsy.setTextColor(getResources().getColorStateList(R.color.colorTextColor));
        movieRecycleRmdy.setVisibility(View.GONE);//隐藏
        movieRecycleJjsy.setVisibility(View.GONE);//隐藏
        mHotShowingPresenter = new HotShowingPresenter(new HotShowingCall());
        if (USER_INFO != null) {

            userId = USER_INFO.getUserId();
            sessionId = USER_INFO.getSessionId();
            mHotShowingPresenter.reqeust(userId, sessionId, "1", "20");
        } else {
            mHotShowingPresenter.reqeust(0, "", "1", "100");
        }

        movieRecycleZzry.setLayoutManager(new LinearLayoutManager(OutDetailsActivity.this, OrientationHelper.VERTICAL, false));
        movieRecycleZzry.setAdapter(mOutDetailsAdapter);
    }

    private void jjsy() {
        moveRadio.check(R.id.move_jjsy);
        movieRecycleJjsy.setVisibility(View.VISIBLE);//显示
        moveJjsy.setTextColor(getResources().getColorStateList(R.color.white));
        moveRmdy.setTextColor(getResources().getColorStateList(R.color.colorTextColor));
        moveZzry.setTextColor(getResources().getColorStateList(R.color.colorTextColor));
        movieRecycleRmdy.setVisibility(View.GONE);//隐藏
        movieRecycleZzry.setVisibility(View.GONE);//隐藏
        mCommingSunPresenter = new CommingSunPresenter(new CommingSunCall());
        if (USER_INFO != null) {

            userId = USER_INFO.getUserId();
            sessionId = USER_INFO.getSessionId();
            mCommingSunPresenter.reqeust(userId, sessionId, "1", "20");
        } else {
            mCommingSunPresenter.reqeust(0, "", "1", "100");
        }

        movieRecycleJjsy.setLayoutManager(new LinearLayoutManager(OutDetailsActivity.this, OrientationHelper.VERTICAL, false));
        movieRecycleJjsy.setAdapter(mOutDetailsAdapter);
    }


    @Override
    protected void destoryData() {
        mCommingSunPresenter = null;
        mHotShowingPresenter = null;
        mShowLunBoPresenter = null;

    }

    @OnClick({R.id.dingwei, R.id.Focus_back})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.dingwei:
                break;

            case R.id.Focus_back:
                finish();
                break;
        }
    }


    private class ShowLunboCall implements DataCall<Result<List<ShowLunBoBean>>> {

        private List<ShowLunBoBean> mResult;

        @Override
        public void success(Result<List<ShowLunBoBean>> result) {
            mResult = result.getResult();
            mOutDetailsAdapter.reset(mResult);
        }

        @Override
        public void fail(ApiException e) {

        }
    }

    private class HotShowingCall implements DataCall<Result<List<ShowLunBoBean>>> {
        private List<ShowLunBoBean> mResult;

        @Override
        public void success(Result<List<ShowLunBoBean>> result) {
            mResult = result.getResult();
            mOutDetailsAdapter.reset(mResult);
        }

        @Override
        public void fail(ApiException e) {

        }
    }

    private class CommingSunCall implements DataCall<Result<List<ShowLunBoBean>>> {
        private List<ShowLunBoBean> mResult;

        @Override
        public void success(Result<List<ShowLunBoBean>> result) {
            mResult = result.getResult();
            mOutDetailsAdapter.reset(mResult);
        }

        @Override
        public void fail(ApiException e) {

        }
    }

    @Override
    public void onResume() {
        super.onResume();
        MobclickAgent.onPageStart("电影列表");
        MobclickAgent.onResume(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd("电影列表");
        MobclickAgent.onPause(this);
    }

    private class FocusCall implements DataCall<Result> {
        @Override
        public void success(Result result) {
            if (result.getStatus().equals("0000")) {

                if (moveRmdy.isChecked()){
                    mShowLunBoPresenter.reqeust(userId, sessionId, "1", "20");
                }
                if (moveZzry.isChecked()){
                    mHotShowingPresenter.reqeust(userId, sessionId, "1", "20");
                }
                if (moveJjsy.isChecked()){
                    mCommingSunPresenter.reqeust(userId, sessionId, "1", "20");
                }
            }

        }

        @Override
        public void fail(ApiException e) {

        }
    }

    private class FocusMovieOffCall implements DataCall<Result> {
        @Override
        public void success(Result result) {
            if (result.getStatus().equals("0000")) {

                if (moveRmdy.isChecked()){
                    mShowLunBoPresenter.reqeust(userId, sessionId, "1", "20");
                }
                if (moveZzry.isChecked()){
                    mHotShowingPresenter.reqeust(userId, sessionId, "1", "20");
                }
                if (moveJjsy.isChecked()){
                    mCommingSunPresenter.reqeust(userId, sessionId, "1", "20");
                }
            }
        }

        @Override
        public void fail(ApiException e) {

        }
    }
}
