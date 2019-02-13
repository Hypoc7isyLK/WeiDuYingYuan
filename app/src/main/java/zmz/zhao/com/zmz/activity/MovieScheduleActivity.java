package zmz.zhao.com.zmz.activity;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.TextView;

import com.bw.movie.R;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.List;

import butterknife.BindView;
import zmz.zhao.com.zmz.adapter.ScheduleListAdapter;
import zmz.zhao.com.zmz.bean.DetailsBean;
import zmz.zhao.com.zmz.bean.Result;
import zmz.zhao.com.zmz.bean.ScheduleListBean;
import zmz.zhao.com.zmz.exception.ApiException;
import zmz.zhao.com.zmz.presenter.DetailsPresenter;
import zmz.zhao.com.zmz.presenter.FilmSchePresenter;
import zmz.zhao.com.zmz.view.DataCall;

/**
 * date:2019/1/30 10:42
 * author:赵明珠(啊哈)
 * function:影片排期
 */
public class MovieScheduleActivity extends BaseActivity {

    private String name;
    private String mid;
    private String address;

    @BindView(R.id.cinema)
    TextView cinema;

    @BindView(R.id.mimage)
    SimpleDraweeView mimage;

    @BindView(R.id.cinema_text)
    TextView cinema_text;

    @BindView(R.id.movie_title)
    TextView title;

    @BindView(R.id.movie_type)
    TextView type;

    @BindView(R.id.movie_director)
    TextView director;

    @BindView(R.id.movie_min)
    TextView min;

    @BindView(R.id.movie_place)
    TextView place;

    @BindView(R.id.chooage_time)
    RecyclerView recyclerView;

    FilmSchePresenter filmSchePresenter;
    ScheduleListAdapter mScheduleListAdapter;

    private String cid;
    private DetailsPresenter mDetailsPresenter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_choose_time;
    }

    @Override
    protected void initView() {

        mDetailsPresenter = new DetailsPresenter(new DetailsCall());
        filmSchePresenter = new FilmSchePresenter(new FilmScheCall());
        Intent intent = getIntent();

        name = intent.getStringExtra("name");

        mid = intent.getStringExtra("mid");

        cid = intent.getStringExtra("cid");

        address = intent.getStringExtra("address");

        cinema.setText(name);

        cinema_text.setText(address);

        addAdapter();

        filmSchePresenter.reqeust(Integer.parseInt(mid),Integer.parseInt(cid));

        mDetailsPresenter.reqeust(0, "", mid);

    }

    private void addAdapter() {
        recyclerView.setLayoutManager(new LinearLayoutManager(this, OrientationHelper.VERTICAL, false));

        mScheduleListAdapter = new ScheduleListAdapter(this);

        recyclerView.setAdapter(mScheduleListAdapter);


        mScheduleListAdapter.setOnClickListener(new ScheduleListAdapter.OnClickListener() {
            @Override
            public void scuccess(int id, String price, String screeningHall) {
                Log.e("lk", "cinid" + id);
                Intent intent = new Intent(MovieScheduleActivity.this, ChooseActivity.class);
                intent.putExtra("name", title.getText().toString());
                intent.putExtra("address", address);
                intent.putExtra("id", id + "");
                intent.putExtra("price", price);
                intent.putExtra("screeningHall", screeningHall);
                intent.putExtra("cinemaname", name);
                startActivity(intent);
            }
        });

    }

    private class DetailsCall implements DataCall<Result<DetailsBean>> {


        @Override
        public void success(Result<DetailsBean> result) {
            if (result.getStatus().equals("0000")){
                DetailsBean mResult = result.getResult();

                mimage.setImageURI(Uri.parse(mResult.getImageUrl()));
                type.setText(mResult.getMovieTypes());
                director.setText(mResult.getDirector());
                min.setText(mResult.getDuration());
                place.setText(mResult.getPlaceOrigin());
                title.setText(mResult.getName());
            }

        }

        @Override
        public void fail(ApiException e) {

        }
    }

    @Override
    protected void destoryData() {
        mDetailsPresenter.unBind();
        filmSchePresenter.unBind();
    }

    class FilmScheCall implements DataCall<Result<List<ScheduleListBean>>> {
        @Override
        public void success(Result<List<ScheduleListBean>> result) {
            if (result.getStatus().equals("0000")){
                List<ScheduleListBean> scheList = result.getResult();
                mScheduleListAdapter.reset(scheList);
            }
        }

        @Override
        public void fail(ApiException e) {

        }
    }
}
