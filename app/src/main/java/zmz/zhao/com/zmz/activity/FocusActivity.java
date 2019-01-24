package zmz.zhao.com.zmz.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.AnimationSet;
import android.view.animation.TranslateAnimation;
import android.widget.Adapter;
import android.widget.RadioGroup;

import com.bw.movie.R;
import com.greendao.gen.DaoSession;
import com.greendao.gen.UserDaoDao;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import java.util.List;

import butterknife.BindView;
import butterknife.OnCheckedChanged;
import zmz.zhao.com.zmz.adapter.MineMovieAdapter;
import zmz.zhao.com.zmz.app.MyApplication;
import zmz.zhao.com.zmz.bean.Attention;
import zmz.zhao.com.zmz.bean.Result;
import zmz.zhao.com.zmz.bean.dao.UserDao;
import zmz.zhao.com.zmz.exception.ApiException;
import zmz.zhao.com.zmz.presenter.MineMoviePresenter;
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

    private MineMovieAdapter movieAdapter;

    private int userId;
    private String sessionId;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_focus;
    }

    @Override
    protected void initView() {

        moviePresenter = new MineMoviePresenter(new MovieCall());
        cinema_recycle.setVisibility(View.GONE);
        UserDaoDao userDaoDao = MyApplication.getInstances().getDaoSession().getUserDaoDao();

        List<UserDao> userDaos = userDaoDao.loadAll();
        if (userDaos != null && userDaos.size()>0){
            UserDao userDao = userDaos.get(0);
            userId = userDao.getUserId();
            sessionId = userDao.getSessionId();
        }

        moviePresenter.reqeust(userId, sessionId, true);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        movie_recycle.addItemDecoration(new SpaceItemDecoration(20));

        movie_recycle.setLayoutManager(linearLayoutManager);

        movieAdapter = new MineMovieAdapter(FocusActivity.this);

        movie_recycle.setAdapter(movieAdapter);

        initData();


    }

    @Override
    protected void onResume() {
        super.onResume();
        moviePresenter.reqeust(4634, sessionId, true);
    }

    private void initData() {
        mine_radio.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.mine_movie) {

                    cinema_recycle.setVisibility(View.GONE);//隐藏
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

                    movie_recycle.setAnimation(animationSet);

                } else if (checkedId == R.id.mine_cinema) {
                    cinema_recycle.setVisibility(View.VISIBLE);
                    movie_recycle.setVisibility(View.GONE);

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

    }


    @Override
    public void onRefresh() {
        if (moviePresenter.Running()) {

            movie_recycle.refreshComplete();

            return;
        }
        moviePresenter.reqeust(userId, sessionId, true);
    }

    @Override
    public void onLoadMore() {
        if (moviePresenter.Running()) {

            movie_recycle.loadMoreComplete();

            return;
        }

        moviePresenter.reqeust(userId, sessionId, false);

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

        }
    }
}
