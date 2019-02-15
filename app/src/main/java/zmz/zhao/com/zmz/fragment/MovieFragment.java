package zmz.zhao.com.zmz.fragment;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.bw.movie.R;
import com.umeng.analytics.MobclickAgent;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import zmz.zhao.com.zmz.activity.CinemaActivity;
import zmz.zhao.com.zmz.adapter.CinemaListAdapter;
import zmz.zhao.com.zmz.bean.CinemaListBean;
import zmz.zhao.com.zmz.bean.Result;
import zmz.zhao.com.zmz.exception.ApiException;
import zmz.zhao.com.zmz.presenter.CinemaListPresenter;
import zmz.zhao.com.zmz.presenter.FocusCinemaOffPresenter;
import zmz.zhao.com.zmz.presenter.FocusCinemaPresenter;
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
    @BindView(R.id.dingweitext)
    TextView dingweitext;

    private CinemaListAdapter mCinemaListAdapter;
    private CinemaListPresenter mCinemaListPresenter;
    private NearbyCinemaPresenter mNearbyCinemaPresenter;
    private int userId;
    private String sessionId;
    FocusCinemaOffPresenter focusCinemaOffPresenter;
    FocusCinemaPresenter focusCinemaPresenter;
    public LocationClient mLocationClient = null;
    private MyLocationListener myListener = new MyLocationListener();

    @Override
    public void initView(View view) {
        mNearbyCinemaPresenter = new NearbyCinemaPresenter(new CinemalistCall());
        focusCinemaOffPresenter = new FocusCinemaOffPresenter(new FocusCinmaCall());
        focusCinemaPresenter = new FocusCinemaPresenter(new FocusOffCinmaCall());
        mCinemaListPresenter = new CinemaListPresenter(new CinemalistCall());

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
            public void success(int id, String address, String logo, String name) {
                Log.e("lk", "影院id" + id);
                Log.e("lk", "影院地址" + address);
                Log.e("lk", "影院logo" + logo);
                Log.e("lk", "影院name" + name);
                Intent intent = new Intent(getActivity(), CinemaActivity.class);
                intent.putExtra("id", id + "");
                intent.putExtra("address", address);
                intent.putExtra("logo", logo);
                intent.putExtra("name", name);
                startActivity(intent);
            }
        });

        mCinemaListAdapter.setOnclickFocuslitener(new CinemaListAdapter.OnclickFocuslitener() {
            @Override
            public void success(int id, int state) {
                if (USER_INFO != null) {

                    if (state == 1) {
                        focusCinemaOffPresenter.reqeust(userId, sessionId, id);
                    } else {
                        focusCinemaPresenter.reqeust(userId, sessionId, id);
                    }
                }
            }
        });
    }

    @Override
    public void initData(View view) {
        cinemaSearch.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

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

        dingWei();

    }

    private void dingWei() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(getActivity(), new String[]{
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.ACCESS_COARSE_LOCATION,
                        Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.READ_PHONE_STATE
                }, 100);
            } else {
                mLocationClient = new LocationClient(getContext());
                //声明LocationClient类
                mLocationClient.registerLocationListener(myListener);
                //注册监听函数
                LocationClientOption option = new LocationClientOption();
                option.setLocationMode(LocationClientOption.LocationMode.Battery_Saving);
                //可选，是否需要位置描述信息，默认为不需要，即参数为false
                //如果开发者需要获得当前点的位置信息，此处必须为true
                option.setIsNeedLocationDescribe(true);
                //可选，设置是否需要地址信息，默认不需要
                option.setIsNeedAddress(true);
                //可选，默认false,设置是否使用gps
                option.setOpenGps(true);
                //可选，默认false，设置是否当GPS有效时按照1S/1次频率输出GPS结果
                option.setLocationNotify(true);
                mLocationClient.setLocOption(option);
                mLocationClient.start();
            }
        }
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

        if (USER_INFO != null) {
            userId = USER_INFO.getUserId();
            sessionId = USER_INFO.getSessionId();
            mCinemaListPresenter.reqeust(userId, sessionId, "1", "20");
        } else {
            mCinemaListPresenter.reqeust(0, "", "1", "20");
        }

    }


    private void fjyy() {

        cinemaRadio.check(R.id.cinema_fjyy);
        cinemaRecycleFjyy.setVisibility(View.VISIBLE);//显示
        cinemaFjyy.setTextColor(getResources().getColorStateList(R.color.white));
        cinemaTjyy.setTextColor(getResources().getColorStateList(R.color.colorTextColor));
        cinemaRecycleTjyy.setVisibility(View.GONE);//隐藏
        cinemaRecycleFjyy.setLayoutManager(new LinearLayoutManager(getActivity(), OrientationHelper.VERTICAL, false));
        cinemaRecycleFjyy.setAdapter(mCinemaListAdapter);
        if (USER_INFO != null) {
            userId = USER_INFO.getUserId();
            sessionId = USER_INFO.getSessionId();
            mNearbyCinemaPresenter.reqeust(userId, sessionId, "1", "20");
        } else {
            mNearbyCinemaPresenter.reqeust(0, "", "1", "20");
        }
    }



    private class CinemalistCall implements DataCall<Result<List<CinemaListBean>>> {

        private List<CinemaListBean> mResult;

        @Override
        public void success(Result<List<CinemaListBean>> result) {
            if (result.getStatus().equals("0000")) {
                mResult = result.getResult();
                mCinemaListAdapter.reset(mResult);
            }
        }

        @Override
        public void fail(ApiException e) {

        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mCinemaListPresenter.unBind();
        mNearbyCinemaPresenter.unBind();
        focusCinemaOffPresenter.unBind();
        focusCinemaPresenter.unBind();
    }

    @Override
    public void onResume() {
        super.onResume();
        MobclickAgent.onPageStart("影院fragment");
        MobclickAgent.onResume(getActivity());
    }

    @Override
    public void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd("影院fragment");
        MobclickAgent.onPause(getActivity());
    }

    private class FocusCinmaCall implements DataCall<Result> {
        @Override
        public void success(Result result) {
            if (result.getStatus().equals("0000")) {
                if (cinemaTjyy.isChecked()) {
                    mCinemaListPresenter.reqeust(userId, sessionId, "1", "20");
                }
                if (cinemaFjyy.isChecked()) {
                    mNearbyCinemaPresenter.reqeust(userId, sessionId, "1", "20");
                }
            }
        }

        @Override
        public void fail(ApiException e) {

        }
    }

    private class FocusOffCinmaCall implements DataCall<Result> {
        @Override
        public void success(Result result) {
            if (result.getStatus().equals("0000")) {
                if (cinemaTjyy.isChecked()) {
                    mCinemaListPresenter.reqeust(userId, sessionId, "1", "20");
                }
                if (cinemaFjyy.isChecked()) {
                    mNearbyCinemaPresenter.reqeust(userId, sessionId, "1", "20");
                }
            }
        }

        @Override
        public void fail(ApiException e) {

        }
    }
    public class MyLocationListener implements BDLocationListener {
        @Override
        public void onReceiveLocation(BDLocation location) {
            //此处的BDLocation为定位结果信息类，通过它的各种get方法可获取定位相关的全部结果
            //以下只列举部分获取地址相关的结果信息
            //更多结果信息获取说明，请参照类参考中BDLocation类中的说明
            String locationDescribe = location.getLocationDescribe();    //获取位置描述信息
            String addr = location.getCity();    //获取详细地址信息
            if (addr != null && addr != "") {
                dingweitext.setText(addr);
            }

        }

    }
}
