package zmz.zhao.com.zmz.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.AnimationSet;
import android.view.animation.TranslateAnimation;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.bw.movie.R;
import com.greendao.gen.DaoMaster;
import com.greendao.gen.UserInfoDao;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.umeng.analytics.MobclickAgent;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import zmz.zhao.com.zmz.adapter.CinemaAdapter;
import zmz.zhao.com.zmz.adapter.MineMovieAdapter;
import zmz.zhao.com.zmz.bean.Address;
import zmz.zhao.com.zmz.bean.Attention;
import zmz.zhao.com.zmz.bean.Result;
import zmz.zhao.com.zmz.bean.dao.UserInfo;
import zmz.zhao.com.zmz.exception.ApiException;
import zmz.zhao.com.zmz.presenter.MineMoviePresenter;
import zmz.zhao.com.zmz.presenter.MyCinemaPresenter;
import zmz.zhao.com.zmz.util.SpaceItemDecoration;
import zmz.zhao.com.zmz.view.DataCall;


public class FocusActivity extends BaseActivity implements XRecyclerView.LoadingListener {

    @BindView(R.id.movie_recycle)
    XRecyclerView movie_recycle;

    @BindView(R.id.cinema_recycle)
    XRecyclerView cinema_recycle;

    @BindView(R.id.mine_radio)
    RadioGroup mine_radio;
    @BindView(R.id.mine_movie)
    RadioButton mine_movie;
    @BindView(R.id.mine_cinema)
    RadioButton mine_cinema;

    MineMoviePresenter moviePresenter;
    MyCinemaPresenter cinemaPresenter;
    private MineMovieAdapter movieAdapter;

    private int userId;
    private String sessionId;
    private CinemaAdapter cinemaAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_focus;
    }

    @Override
    protected void initView() {

        moviePresenter = new MineMoviePresenter(new MovieCall());
        cinemaPresenter = new MyCinemaPresenter(new CinemaCall());
        cinema_recycle.setVisibility(View.GONE);
        mine_movie.setTextColor(getResources().getColorStateList(R.color.white));

        initRecycle();

        initData();
        if (USER_INFO == null) {
            Intent intent = new Intent(FocusActivity.this, LoginActivity.class);
            startActivity(intent);
            return;
        }else {
            userId = USER_INFO.getUserId();

            sessionId = USER_INFO.getSessionId();

            moviePresenter.reqeust(userId, this.sessionId, true);
            cinemaPresenter.reqeust(userId, sessionId, true);
        }

    }

    private void initRecycle() {

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);

        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        movie_recycle.addItemDecoration(new SpaceItemDecoration(10));

        movie_recycle.setLayoutManager(linearLayoutManager);

        movieAdapter = new MineMovieAdapter(FocusActivity.this);

        movie_recycle.setAdapter(movieAdapter);

        LinearLayoutManager linearLayoutManager1 = new LinearLayoutManager(this);

        linearLayoutManager1.setOrientation(LinearLayoutManager.VERTICAL);

        cinema_recycle.addItemDecoration(new SpaceItemDecoration(10));

        cinema_recycle.setLayoutManager(linearLayoutManager1);

        cinemaAdapter = new CinemaAdapter(FocusActivity.this);

        cinema_recycle.setAdapter(cinemaAdapter);
        cinema_recycle.setLoadingListener(this);
        movie_recycle.setLoadingListener(this);
        movieAdapter.setOnclicklitener(new MineMovieAdapter.Onclicklitener() {
            @Override
            public void success(int id) {
                Intent intent = new Intent(FocusActivity.this, InsideDetailsActivity.class);
                intent.putExtra("id", id + "");
                Log.e("lk", "outdetailsid" + id);
                startActivity(intent);
            }
        });
        cinemaAdapter.setOnclicklitener(new CinemaAdapter.Onclicklitener() {
            @Override
            public void success(int id, String address, String logo, String name) {
                Intent intent = new Intent(FocusActivity.this,CinemaActivity.class);
                intent.putExtra("id",id+"");
                intent.putExtra("address",address);
                intent.putExtra("logo",logo);
                intent.putExtra("name",name);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onResume() {

        super.onResume();
        MobclickAgent.onPageStart("关注的影院和电影页面");
        MobclickAgent.onResume(this);

        UserInfoDao userInfoDao = DaoMaster.newDevSession(this, UserInfoDao.TABLENAME).getUserInfoDao();

        List<UserInfo> userInfoList = userInfoDao.queryBuilder().where(UserInfoDao.Properties.Status.eq(1)).list();

        if (userInfoList != null && userInfoList.size() > 0) {
            USER_INFO = userInfoList.get(0);

            int userids = USER_INFO.getUserId();

            String sessionIds = USER_INFO.getSessionId();

            Log.e("zmz"+userids,"============="+sessionIds);

            moviePresenter.reqeust(userids, sessionIds, true);
            cinemaPresenter.reqeust(userids, sessionIds, true);

            initRecycle();

            initData();
        }

    }
    /**
     * @作者 啊哈
     * @date 2019/1/24
     * 动画切换
     */
    private void initData() {
        mine_radio.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.mine_movie) {

                    movie_recycle.setVisibility(View.VISIBLE);//显示
                    int width = movie_recycle.getMeasuredWidth();

                    mine_movie.setTextColor(getResources().getColorStateList(R.color.white));

                    mine_cinema.setTextColor(getResources().getColorStateList(R.color.colorTextColor));


                    //平移
                    TranslateAnimation translateAnimation = new TranslateAnimation(width, 0.0f, 0.0f, 0.0f);
                    translateAnimation.setDuration(500);
                    //透明度
                    AlphaAnimation alphaAnimation = new AlphaAnimation(0.0f, 1.0f);
                    alphaAnimation.setDuration(1000);
                    //组合动画
                    AnimationSet animationSet = new AnimationSet(true);
                    animationSet.addAnimation(translateAnimation);
                    animationSet.addAnimation(alphaAnimation);

                    AlphaAnimation calphaAnimation = new AlphaAnimation(1.0f, 0.0f);
                    calphaAnimation.setDuration(500);
                    cinema_recycle.setAnimation(calphaAnimation);
                    cinema_recycle.setVisibility(View.GONE);//隐藏
                    movie_recycle.setAnimation(animationSet);

                } else if (checkedId == R.id.mine_cinema) {

                    mine_movie.setTextColor(getResources().getColorStateList(R.color.colorTextColor));

                    mine_cinema.setTextColor(getResources().getColorStateList(R.color.white));

                    cinema_recycle.setVisibility(View.VISIBLE);



                    //平移
                    TranslateAnimation translateAnimation = new TranslateAnimation(movie_recycle.getMeasuredWidth(), 0.0f, 0.0f, 0.0f);
                    translateAnimation.setDuration(500);
                    //透明度
                    AlphaAnimation alphaAnimation = new AlphaAnimation(0.0f, 1.0f);
                    alphaAnimation.setDuration(1000);
                    //组合动画
                    AnimationSet animationSet = new AnimationSet(true);

                    animationSet.addAnimation(translateAnimation);

                    animationSet.addAnimation(alphaAnimation);

                    AlphaAnimation calphaAnimation = new AlphaAnimation(1.0f, 0.0f);

                    calphaAnimation.setDuration(500);

                    movie_recycle.setAnimation(calphaAnimation);

                    movie_recycle.setVisibility(View.GONE);

                    cinema_recycle.setAnimation(animationSet);
                }
            }
        });

    }

    /**
     * @作者 啊哈
     * @date 2019/1/24
     * <p>
     * 交给基类统一释放
     */
    @Override
    protected void destoryData() {
        moviePresenter.unBind();
        cinemaPresenter.unBind();
    }

    @OnClick(R.id.Focus_back)
    public void Focus(){
        finish();
    }
    @Override
    public void onRefresh() {
        if (moviePresenter.Running()) {
            movie_recycle.refreshComplete();
            cinema_recycle.refreshComplete();
            return;
        }

        cinemaPresenter.reqeust(userId, sessionId, true);
        moviePresenter.reqeust(userId, sessionId, true);
    }

    @Override
    public void onLoadMore() {
        if (moviePresenter.Running()) {

            movie_recycle.loadMoreComplete();
            cinema_recycle.loadMoreComplete();
            return;
        }

        moviePresenter.reqeust(userId, sessionId, false);
        cinemaPresenter.reqeust(userId, sessionId, false);

    }

    private class MovieCall implements DataCall<Result<List<Attention>>> {

        @Override
        public void success(Result<List<Attention>> result) {
            movie_recycle.refreshComplete();

            movie_recycle.loadMoreComplete();

            if (result.getStatus().equals("0000")) {

                if (moviePresenter.isResresh()) {

                    movieAdapter.clearList();
                }

                List<Attention> attentions = result.getResult();

                movieAdapter.addAll(attentions);

                movieAdapter.notifyDataSetChanged();
            }
        }

        @Override
        public void fail(ApiException e) {
            Toast.makeText(FocusActivity.this, "请求失败", Toast.LENGTH_SHORT).show();
        }
    }

    private class CinemaCall implements DataCall<Result<List<Address>>> {
        @Override
        public void success(Result<List<Address>> result) {

            cinema_recycle.refreshComplete();

            cinema_recycle.loadMoreComplete();

            if (result.getStatus().equals("0000")) {

                if (cinemaPresenter.isResresh()) {

                    cinemaAdapter.clearList();
                }

                List<Address> addresses = result.getResult();

                cinemaAdapter.addAll(addresses);

                cinemaAdapter.notifyDataSetChanged();
            }
        }

        @Override
        public void fail(ApiException e) {

        }
    }


    @Override
    public void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd("关注的影院和电影页面");
        MobclickAgent.onPause(this);
    }
}
