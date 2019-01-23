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
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import butterknife.BindView;
import butterknife.OnCheckedChanged;
import zmz.zhao.com.zmz.adapter.MineMovieAdapter;
import zmz.zhao.com.zmz.bean.Attention;
import zmz.zhao.com.zmz.bean.Result;
import zmz.zhao.com.zmz.exception.ApiException;
import zmz.zhao.com.zmz.presenter.MineMoviePresenter;
import zmz.zhao.com.zmz.util.SpaceItemDecoration;
import zmz.zhao.com.zmz.view.DataCall;


public class FocusActivity extends BaseActivity implements XRecyclerView.LoadingListener {

    @BindView(R.id.movie_recycle)
    XRecyclerView movie_recycle;

    @BindView(R.id.cinema_recycle)
    RecyclerView cinema_recycle;
    @BindView(R.id.mine_radio)
    RadioGroup mine_radio;

    MineMoviePresenter moviePresenter;
    private MineMovieAdapter movieAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_focus;
    }

    @Override
    protected void initView() {
        moviePresenter = new MineMoviePresenter(new MovieCall());
        initData();
        initDataView();
    }

    private void initDataView() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        movie_recycle.addItemDecoration(new SpaceItemDecoration(R.dimen.dip_10));

        movie_recycle.setLayoutManager(linearLayoutManager);
        movieAdapter = new MineMovieAdapter();
    }

    private void initData() {
        mine_radio.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.mine_movie) {

                    cinema_recycle.setVisibility(View.GONE);//隐藏
                    movie_recycle.setVisibility(View.VISIBLE);//显示

                    //平移
                    TranslateAnimation translateAnimation = new TranslateAnimation(-movie_recycle.getMeasuredWidth(), 0.0f, 0.0f, 0.0f);
                    translateAnimation.setDuration(2000);
                    //透明度
                    AlphaAnimation alphaAnimation = new AlphaAnimation(0.0f, 1.0f);
                    alphaAnimation.setDuration(2000);
                    //组合动画
                    AnimationSet animationSet = new AnimationSet(true);
                    animationSet.addAnimation(translateAnimation);
                    animationSet.addAnimation(alphaAnimation);

                    movie_recycle.setAnimation(animationSet);

                } else if (checkedId == R.id.mine_cinema) {
                    cinema_recycle.setVisibility(View.VISIBLE);
                    movie_recycle.setVisibility(View.GONE);

                    //平移
                    TranslateAnimation translateAnimation = new TranslateAnimation(-movie_recycle.getMeasuredWidth(), 0.0f, 0.0f, 0.0f);
                    translateAnimation.setDuration(2000);
                    //透明度
                    AlphaAnimation alphaAnimation = new AlphaAnimation(0.0f, 1.0f);
                    alphaAnimation.setDuration(2000);
                    //组合动画
                    AnimationSet animationSet = new AnimationSet(true);
                    animationSet.addAnimation(translateAnimation);
                    animationSet.addAnimation(alphaAnimation);

                    cinema_recycle.setAnimation(animationSet);
                }
            }
        });
    }

    @Override
    protected void destoryData() {
        moviePresenter.unBind();
    }

    /**
     * @作者 啊哈
     * @date 2019/1/23
     *
     * "sessionId":"15482473651874634","userId":4634
     */

    @Override
    public void onRefresh() {
        if (moviePresenter.Running()) {

            movie_recycle.refreshComplete();

            return;
        }
        moviePresenter.reqeust(4634,"15482473651874634",true);
    }

    @Override
    public void onLoadMore() {
        if (moviePresenter.Running()) {

            movie_recycle.loadMoreComplete();

            return;
        }
        moviePresenter.reqeust(4634,"15482473651874634",false);

    }

    private class MovieCall implements DataCall<Result<Attention>> {

        @Override
        public void success(Result<Attention> result) {
            if (result.getStatus().equals("0000")) {
                Attention attention = result.getResult();


            }
        }

        @Override
        public void fail(ApiException e) {

        }
    }
}
