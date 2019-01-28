package zmz.zhao.com.zmz.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SearchView;

import com.bw.movie.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import zmz.zhao.com.zmz.activity.ChooseActivity;
import zmz.zhao.com.zmz.activity.CinemaActivity;
import zmz.zhao.com.zmz.adapter.CinemaListAdapter;
import zmz.zhao.com.zmz.bean.CinemaListBean;
import zmz.zhao.com.zmz.bean.Result;
import zmz.zhao.com.zmz.exception.ApiException;
import zmz.zhao.com.zmz.presenter.CinemaListPresenter;
import zmz.zhao.com.zmz.presenter.NearbyCinemaPresenter;
import zmz.zhao.com.zmz.view.DataCall;


public class MovieFragment extends BaseFragment {
    @BindView(R.id.dingwei)
    ImageView dingwei;
    @BindView(R.id.cinema_search)
    SearchView cinemaSearch;
    @BindView(R.id.cinema_tjyy)
    RadioButton cinemaTjyy;
    @BindView(R.id.cinema_fjyy)
    RadioButton cinemaFjyy;
    @BindView(R.id.cinema_radio)
    RadioGroup cinemaRadio;
    @BindView(R.id.cinema_recycle_tjyy)
    RecyclerView cinemaRecycleTjyy;
    @BindView(R.id.cinema_recycle_fjyy)
    RecyclerView cinemaRecycleFjyy;

    private String mSessionId;
    private int mUserId;
    private CinemaListAdapter mCinemaListAdapter;
    private CinemaListPresenter mCinemaListPresenter;
    private NearbyCinemaPresenter mNearbyCinemaPresenter;

    @Override
    public void initView(View view) {
        mSessionId = USER_INFO.getSessionId();
        mUserId = USER_INFO.getUserId();
        mCinemaListAdapter = new CinemaListAdapter(getActivity());
        tjyy();
        cinemaRadio.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.cinema_tjyy:
                        tjyy();
                        break;
                    case R.id.cinema_fjyy:
                        fjyy();
                        break;

                }
            }
        });
        mCinemaListAdapter.setOnclicklitener(new CinemaListAdapter.Onclicklitener() {
            @Override
            public void success(int id) {
                Log.e("lk","影院id"+id);
                Intent intent = new Intent(getActivity(),CinemaActivity.class);
                intent.putExtra("id",id+"");
                startActivity(intent);
            }
        });
    }

    @Override
    public void initData(View view) {

    }

    @Override
    public int getContent() {
        return R.layout.activity_movie;
    }

    private void tjyy() {
        cinemaRadio.check(R.id.cinema_tjyy);
        cinemaRecycleTjyy.setVisibility(View.VISIBLE);//显示
        cinemaTjyy.setTextColor(getResources().getColorStateList(R.color.white));
        cinemaFjyy.setTextColor(getResources().getColorStateList(R.color.colorTextColor));
        cinemaRecycleFjyy.setVisibility(View.GONE);//隐藏
        cinemaRecycleTjyy.setLayoutManager(new LinearLayoutManager(getActivity(), OrientationHelper.VERTICAL, false));
        cinemaRecycleTjyy.setAdapter(mCinemaListAdapter);
        mCinemaListPresenter = new CinemaListPresenter(new CinemalistCall());
        mCinemaListPresenter.reqeust(mUserId, mSessionId, "1", "20");


    }



    private void fjyy() {
        cinemaRadio.check(R.id.cinema_fjyy);
        cinemaRecycleFjyy.setVisibility(View.VISIBLE);//显示
        cinemaFjyy.setTextColor(getResources().getColorStateList(R.color.white));
        cinemaTjyy.setTextColor(getResources().getColorStateList(R.color.colorTextColor));
        cinemaRecycleTjyy.setVisibility(View.GONE);//隐藏
        cinemaRecycleFjyy.setLayoutManager(new LinearLayoutManager(getActivity(), OrientationHelper.VERTICAL, false));
        cinemaRecycleFjyy.setAdapter(mCinemaListAdapter);
        mNearbyCinemaPresenter = new NearbyCinemaPresenter(new CinemalistCall());
        mNearbyCinemaPresenter.reqeust(mUserId, mSessionId, "1", "20");
    }

    private class CinemalistCall implements DataCall<Result<List<CinemaListBean>>> {

        private List<CinemaListBean> mResult;

        @Override
        public void success(Result<List<CinemaListBean>> result) {
            mResult = result.getResult();
            mCinemaListAdapter.reset(mResult);

        }

        @Override
        public void fail(ApiException e) {

        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mCinemaListPresenter = null;
        mNearbyCinemaPresenter = null;
    }
}
