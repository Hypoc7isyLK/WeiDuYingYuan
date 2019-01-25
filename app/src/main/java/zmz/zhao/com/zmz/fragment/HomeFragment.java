package zmz.zhao.com.zmz.fragment;

import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bw.movie.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import recycler.coverflow.CoverFlowLayoutManger;
import recycler.coverflow.RecyclerCoverFlow;
import zmz.zhao.com.zmz.adapter.CarouselAdapter;
import zmz.zhao.com.zmz.bean.Result;
import zmz.zhao.com.zmz.bean.ShowLunBoBean;
import zmz.zhao.com.zmz.exception.ApiException;
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
    private ShowLunBoPresenter mShowLunBoPresenter;
    private String mSessionId;
    private int mUserId;
    private CarouselAdapter mCarouselAdapter;
    private int mWidth;
    private int mItemCount;
    private int mCoun;

    @Override
    public void initView(View view) {
        mSessionId = USERDAO.getSessionId();
        mUserId = USERDAO.getUserId();

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

    }

    @Override
    public void initData(View view) {

    }

    @Override
    public int getContent() {
        return R.layout.activity_homepage;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.dingwei)
    public void onViewClicked() {

    }


    private class ShowLunboCall implements DataCall<Result<List<ShowLunBoBean>>> {

        private List<ShowLunBoBean> mResult;

        @Override
        public void success(Result<List<ShowLunBoBean>> result) {
            mResult = result.getResult();

            mCarouselAdapter.reset(mResult);
            mWidth = movieTextXian.getWidth();
            mItemCount = mCarouselAdapter.getItemCount();
            mCoun = mWidth/mItemCount;
        }

        @Override
        public void fail(ApiException e) {

        }
    }

}
