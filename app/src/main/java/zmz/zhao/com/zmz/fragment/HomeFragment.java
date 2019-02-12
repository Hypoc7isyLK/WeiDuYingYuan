package zmz.zhao.com.zmz.fragment;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.Intent;
import android.os.Environment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.bw.movie.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import recycler.coverflow.CoverFlowLayoutManger;
import recycler.coverflow.RecyclerCoverFlow;
import zmz.zhao.com.zmz.activity.InsideDetailsActivity;
import zmz.zhao.com.zmz.activity.OutDetailsActivity;
import zmz.zhao.com.zmz.adapter.CarouselAdapter;
import zmz.zhao.com.zmz.adapter.HotShowingAdapter;
import zmz.zhao.com.zmz.bean.Result;
import zmz.zhao.com.zmz.bean.ShowLunBoBean;
import zmz.zhao.com.zmz.exception.ApiException;
import zmz.zhao.com.zmz.presenter.CommingSunPresenter;
import zmz.zhao.com.zmz.presenter.HotShowingPresenter;
import zmz.zhao.com.zmz.presenter.ShowLunBoPresenter;
import zmz.zhao.com.zmz.util.FileUtils;
import zmz.zhao.com.zmz.util.WifiUtils;
import zmz.zhao.com.zmz.view.BackNum;
import zmz.zhao.com.zmz.view.DataCall;


public class HomeFragment extends BaseFragment {
    @BindView(R.id.dingwei)
    ImageView dingwei;
    @BindView(R.id.list)
    RecyclerCoverFlow list;
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
    @BindView(R.id.details_search)
    SearchView search;

    private ShowLunBoPresenter mShowLunBoPresenter;
    private CarouselAdapter mCarouselAdapter;
    private int mWidth;
    private int mItemCount;
    private int mCoun;

    private HotShowingAdapter mPopularAdapter;
    private HotShowingPresenter mHotShowingPresenter;
    private HotShowingAdapter mHotShowingAdapter;

    private HotShowingAdapter mCommingSunAdapter;
    private CommingSunPresenter mCommingSunPresenter;

    private BackNum backNum;

    public void setBackNum(BackNum backNum) {
        this.backNum = backNum;
    }

    @Override
    public void initView(View view) {
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


        recyclerviewMovie.setLayoutManager(new LinearLayoutManager(getActivity(), OrientationHelper.HORIZONTAL, false));
        mPopularAdapter = new HotShowingAdapter(getActivity());
        recyclerviewMovie.setAdapter(mPopularAdapter);


        mHotShowingPresenter = new HotShowingPresenter(new HotShowingCall());


        recyclerviewHotshowing.setLayoutManager(new LinearLayoutManager(getActivity(), OrientationHelper.HORIZONTAL, false));
        mHotShowingAdapter = new HotShowingAdapter(getActivity());
        recyclerviewHotshowing.setAdapter(mHotShowingAdapter);


        mCommingSunPresenter = new CommingSunPresenter(new CommingSunCall());

        recyclerviewCommingsun.setLayoutManager(new LinearLayoutManager(getActivity(), OrientationHelper.HORIZONTAL, false));
        mCommingSunAdapter = new HotShowingAdapter(getActivity());
        recyclerviewCommingsun.setAdapter(mCommingSunAdapter);


        if (WifiUtils.getInstance(getContext()).getNetype() == -1){

            String dataBean = FileUtils.loadDataFromFile(getContext(), "DataBean");
            String HotShowBean = FileUtils.loadDataFromFile(getContext(), "HotShowBean");
            String CommingBean = FileUtils.loadDataFromFile(getContext(), "CommingBean");



            Gson gson = new Gson();

            List<ShowLunBoBean> mResult = gson.fromJson(dataBean, new TypeToken<List<ShowLunBoBean>>(){}.getType());
            List<ShowLunBoBean> mHotShowBean = gson.fromJson(HotShowBean, new TypeToken<List<ShowLunBoBean>>(){}.getType());
            List<ShowLunBoBean> mCommingBean = gson.fromJson(CommingBean, new TypeToken<List<ShowLunBoBean>>(){}.getType());


            if(mResult != null){
                mCarouselAdapter.reset(mResult);

                mWidth = movieTextXian.getWidth();
                mItemCount = mCarouselAdapter.getItemCount();
                mCoun = mWidth / mItemCount;
                mPopularAdapter.Clear();

                mPopularAdapter.reset(mResult);
                mPopularAdapter.notifyDataSetChanged();
            }
            if(mHotShowBean != null){
                mHotShowingAdapter.Clear();
                mHotShowingAdapter.reset(mHotShowBean);
                mHotShowingAdapter.notifyDataSetChanged();
            }
            if(mCommingBean != null){
                mCommingSunAdapter.Clear();
                mCommingSunAdapter.reset(mCommingBean);
                mCommingSunAdapter.notifyDataSetChanged();
            }


        }else {
            mShowLunBoPresenter.reqeust(0, "", "1", "20");
            mCommingSunPresenter.reqeust(0, "", "1", "100");
            mHotShowingPresenter.reqeust(0, "", "1", "100");
        }

    }

    @Override
    public void initData(View view) {
        search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            //输入完成后，提交时触发的方法
            @Override
            public boolean onQueryTextSubmit(String query) {
                if (query.isEmpty()) {
                    Toast.makeText(getContext(), "请输入查找内容！", Toast.LENGTH_SHORT).show();
                    return false;
                }

                Toast.makeText(getContext(), "搜索成功" + query, Toast.LENGTH_SHORT).show();

                return false;
            }

            //在输入时触发的方法，当字符真正显示到searchView中才触发，像是拼音，在输入法组词的时候不会触发
            @Override
            public boolean onQueryTextChange(String newText) {


                return false;
            }
        });

        mHotShowingAdapter.setHotOnClickListener(new HotShowingAdapter.HotOnClickListener() {
            @Override
            public void Onclick(int id) {
                backNum.getNum(id);

            }
        });
        mCarouselAdapter.setHotOnClickListener(new CarouselAdapter.HotOnClickListener() {
            @Override
            public void Onclick(int id) {
                backNum.getNum(id);
            }
        });
        mPopularAdapter.setHotOnClickListener(new HotShowingAdapter.HotOnClickListener() {
            @Override
            public void Onclick(int id) {
                backNum.getNum(id);
            }
        });
        mCommingSunAdapter.setHotOnClickListener(new HotShowingAdapter.HotOnClickListener() {
            @Override
            public void Onclick(int id) {
                backNum.getNum(id);
            }
        });

    }

    @Override
    public int getContent() {
        return R.layout.activity_homepage;
    }


    @OnClick({R.id.rmdy, R.id.zzry, R.id.jjsy, R.id.dingwei})
    public void onViewClicked(View view) {
        Intent intent = new Intent(getActivity(), OutDetailsActivity.class);
        switch (view.getId()) {
            case R.id.dingwei:
                break;
            case R.id.rmdy:

                intent.putExtra("hei", "1");
                startActivity(intent);
                break;
            case R.id.zzry:

                intent.putExtra("hei", "2");
                startActivity(intent);
                break;
            case R.id.jjsy:

                intent.putExtra("hei", "3");
                startActivity(intent);
                break;
        }
    }


    private class ShowLunboCall implements DataCall<Result<List<ShowLunBoBean>>> {

        private List<ShowLunBoBean> mResult;

        @Override
        public void success(Result<List<ShowLunBoBean>> result) {

            mResult = result.getResult();

            Gson gson = new Gson();

            String toJson = gson.toJson(mResult);

            FileUtils.saveDataToFile(getContext(), toJson, "DataBean");


            mCarouselAdapter.reset(mResult);

            mWidth = movieTextXian.getWidth();
            mItemCount = mCarouselAdapter.getItemCount();
            mCoun = mWidth / mItemCount;


            mPopularAdapter.Clear();

            mPopularAdapter.reset(mResult);
            mPopularAdapter.notifyDataSetChanged();


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

            Gson gson = new Gson();

            String toJson = gson.toJson(mResult);

            FileUtils.saveDataToFile(getContext(), toJson, "HotShowBean");

            mHotShowingAdapter.Clear();
            mHotShowingAdapter.reset(mResult);
            mHotShowingAdapter.notifyDataSetChanged();
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

            Gson gson = new Gson();

            String toJson = gson.toJson(mResult);

            FileUtils.saveDataToFile(getContext(), toJson, "CommingBean");

            mCommingSunAdapter.Clear();
            mCommingSunAdapter.reset(mResult);
            mCommingSunAdapter.notifyDataSetChanged();
        }

        @Override
        public void fail(ApiException e) {

        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        backNum = (BackNum) context;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mHotShowingPresenter = null;
        mShowLunBoPresenter = null;
        mCommingSunPresenter = null;
    }
}
