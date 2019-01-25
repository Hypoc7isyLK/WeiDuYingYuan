package zmz.zhao.com.zmz.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bw.movie.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import recycler.coverflow.RecyclerCoverFlow;
import zmz.zhao.com.zmz.exception.ApiException;
import zmz.zhao.com.zmz.presenter.ShowLunBoPresenter;

import zmz.zhao.com.zmz.view.DataCall;


public class HomeFragment extends BaseFragment {
    @BindView(R.id.dingwei)
    ImageView dingwei;
    @BindView(R.id.list)
    RecyclerCoverFlow list;
    Unbinder unbinder;
    private ShowLunBoPresenter mShowLunBoPresenter;

    @Override
    public void initView(View view) {

        unbinder = ButterKnife.bind(this, view);
        mShowLunBoPresenter = new ShowLunBoPresenter(new ShowLunboCall());
        mShowLunBoPresenter.reqeust();

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

    private class ShowLunboCall implements DataCall {
        @Override
        public void success(Object result) {

        }

        @Override
        public void fail(ApiException e) {

        }
    }

}
