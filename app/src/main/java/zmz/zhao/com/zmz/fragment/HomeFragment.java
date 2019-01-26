package zmz.zhao.com.zmz.fragment;

import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bw.movie.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import recycler.coverflow.CoverFlowLayoutManger;
import recycler.coverflow.RecyclerCoverFlow;
import zmz.zhao.com.zmz.activity.OutDetailsActivity;
import zmz.zhao.com.zmz.adapter.CarouselAdapter;
import zmz.zhao.com.zmz.adapter.CommingSunAdapter;
import zmz.zhao.com.zmz.adapter.HotShowingAdapter;
import zmz.zhao.com.zmz.adapter.PopularAdapter;
import zmz.zhao.com.zmz.bean.Result;
import zmz.zhao.com.zmz.bean.ShowLunBoBean;
import zmz.zhao.com.zmz.exception.ApiException;
import zmz.zhao.com.zmz.presenter.CommingSunPresenter;
import zmz.zhao.com.zmz.presenter.HotShowingPresenter;
import zmz.zhao.com.zmz.presenter.ShowLunBoPresenter;
import zmz.zhao.com.zmz.view.DataCall;


public class HomeFragment extends BaseFragment {
    @BindView(R.id.dingwei)
    ImageView dingwei;
    @BindView(R.id.list)
    RecyclerCoverFlow list;
    Unbinder unbinder;
    @BindView(R.id.movie_text_xian)
    TextView movieTextXian;
    @BindView(R.id.movie_text_dong)
    TextView movieTextDong;
    @BindView(R.id.rmdy)
    RelativeLayout rmdy;
    @BindView(R.id.recyclerview_movie)
    RecyclerView recyclerviewMovie;
    @BindView(R.id.zzry)
    RelativeLayout zzry;
    @BindView(R.id.recyclerview_hotshowing)
    RecyclerView recyclerviewHotshowing;
    @BindView(R.id.jjsy)
    RelativeLayout jjsy;
    @BindView(R.id.recyclerview_commingsun)
    RecyclerView recyclerviewCommingsun;
    private ShowLunBoPresenter mShowLunBoPresenter;
    private String mSessionId;
    private int mUserId;
    private CarouselAdapter mCarouselAdapter;
    private int mWidth;
    private int mItemCount;
    private int mCoun;
    private PopularAdapter mPopularAdapter;
    private HotShowingPresenter mHotShowingPresenter;
    private HotShowingAdapter mHotShowingAdapter;
    private CommingSunAdapter mCommingSunAdapter;
    private CommingSunPresenter mCommingSunPresenter;

    @Override
    public void initView(View view) {
        mSessionId = USER_INFO.getSessionId();
        mUserId = USER_INFO.getUserId();

        unbinder = ButterKnife.bind(this, view);
        mCarouselAdapter = new CarouselAdapter(getActivity());
        list.setAdapter(mCarouselAdapter);


        list.setOnItemSelectedListener(new CoverFlowLayoutManger.OnSelected() {
            @Override
            public void onItemSelected(int position) {
                int selectedPos = list.getSelectedPos();
                ObjectAnimator animator = ObjectAnimator.ofFloat(movieTextDong, "translationX", mCoun * (selectedPos));
                animator.setDuration(500);
                animator.start();
            }
        });


        mShowLunBoPresenter = new ShowLunBoPresenter(new ShowLunboCall());
        mShowLunBoPresenter.reqeust(mUserId, mSessionId, "1", "20");

        recyclerviewMovie.setLayoutManager(new LinearLayoutManager(getActivity(), OrientationHelper.HORIZONTAL, false));
        mPopularAdapter = new PopularAdapter(getActivity());
        recyclerviewMovie.setAdapter(mPopularAdapter);


        mHotShowingPresenter = new HotShowingPresenter(new HotShowingCall());
        mHotShowingPresenter.reqeust(mUserId, mSessionId, "1", "100");

        recyclerviewHotshowing.setLayoutManager(new LinearLayoutManager(getActivity(), OrientationHelper.HORIZONTAL, false));
        mHotShowingAdapter = new HotShowingAdapter(getActivity());
        recyclerviewHotshowing.setAdapter(mHotShowingAdapter);


        mCommingSunPresenter = new CommingSunPresenter(new CommingSunCall());
        mCommingSunPresenter.reqeust(mUserId, mSessionId, "1", "100");

        recyclerviewCommingsun.setLayoutManager(new LinearLayoutManager(getActivity(), OrientationHelper.HORIZONTAL, false));
        mCommingSunAdapter = new CommingSunAdapter(getActivity());
        recyclerviewCommingsun.setAdapter(mCommingSunAdapter);


    }

    @Override
    public void initData(View view) {

    }

    @Override
    public int getContent() {
        return R.layout.activity_homepage;
    }






    @OnClick({R.id.rmdy, R.id.zzry, R.id.jjsy,R.id.dingwei})
    public void onViewClicked(View view) {
        Intent intent = new Intent(getActivity(),OutDetailsActivity.class);
        switch (view.getId()) {
            case R.id.dingwei:
                break;
            case R.id.rmdy:

                intent.putExtra("hei","1");
                startActivity(intent);
                break;
            case R.id.zzry:

                intent.putExtra("hei","2");
                startActivity(intent);
                break;
            case R.id.jjsy:

                intent.putExtra("hei","3");
                startActivity(intent);
                break;
        }
    }


    private class ShowLunboCall implements DataCall<Result<List<ShowLunBoBean>>> {

        private List<ShowLunBoBean> mResult;

        @Override
        public void success(Result<List<ShowLunBoBean>> result) {
            mResult = result.getResult();

            mCarouselAdapter.reset(mResult);
            mPopularAdapter.reset(mResult);

            mWidth = movieTextXian.getWidth();
            mItemCount = mCarouselAdapter.getItemCount();
            mCoun = mWidth / mItemCount;
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
            mHotShowingAdapter.reset(mResult);
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
            mCommingSunAdapter.reset(mResult);
        }

        @Override
        public void fail(ApiException e) {

        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
        mHotShowingPresenter = null;
        mShowLunBoPresenter = null;
        mCommingSunPresenter = null;
    }
}
