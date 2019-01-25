package zmz.zhao.com.zmz.activity;

import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.AnimationSet;
import android.view.animation.TranslateAnimation;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.bw.movie.R;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import zmz.zhao.com.zmz.adapter.CinemaAdapter;
import zmz.zhao.com.zmz.adapter.MineMovieAdapter;
import zmz.zhao.com.zmz.bean.Address;
import zmz.zhao.com.zmz.bean.Attention;
import zmz.zhao.com.zmz.bean.Result;
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

        userId = USERDAO.getUserId();
        sessionId = USERDAO.getSessionId();

        moviePresenter.reqeust(userId, this.sessionId, true);


        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);

        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        movie_recycle.addItemDecoration(new SpaceItemDecoration(20));

        movie_recycle.setLayoutManager(linearLayoutManager);

        movieAdapter = new MineMovieAdapter(FocusActivity.this);

        movie_recycle.setAdapter(movieAdapter);

        LinearLayoutManager linearLayoutManager1 = new LinearLayoutManager(this);

        linearLayoutManager1.setOrientation(LinearLayoutManager.VERTICAL);

        cinema_recycle.addItemDecoration(new SpaceItemDecoration(20));

        cinema_recycle.setLayoutManager(linearLayoutManager1);

        cinemaAdapter = new CinemaAdapter(FocusActivity.this);

        cinema_recycle.setAdapter(cinemaAdapter);

        initData();

    }

    @Override
    protected void onResume() {
        super.onResume();
        moviePresenter.reqeust(userId, sessionId, true);
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
                    cinemaPresenter.reqeust(userId, sessionId, true);

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
}
