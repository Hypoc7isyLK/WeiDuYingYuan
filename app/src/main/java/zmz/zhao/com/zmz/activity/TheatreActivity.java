package zmz.zhao.com.zmz.activity;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.bw.movie.R;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import zmz.zhao.com.zmz.adapter.CinemaListAdapter;
import zmz.zhao.com.zmz.adapter.TheatreAdapter;
import zmz.zhao.com.zmz.bean.CinemaListBean;
import zmz.zhao.com.zmz.bean.Result;
import zmz.zhao.com.zmz.exception.ApiException;
import zmz.zhao.com.zmz.presenter.TheatrePresnter;
import zmz.zhao.com.zmz.util.SpaceItemDecoration;
import zmz.zhao.com.zmz.view.DataCall;

/**
 * date:2019/1/29 20:45
 * author:赵明珠(啊哈)
 * function:选择影院
 */
public class TheatreActivity extends BaseActivity {


    private String title;
    private String mid;

    @BindView(R.id.theatre_title)
    TextView theatre_title;

    @BindView(R.id.recycle)
    RecyclerView recyclerView;
    TheatrePresnter theatrePresnter;
    private CinemaListAdapter cinemaListAdapter;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_choose_cinema;
    }

    @Override
    protected void initView() {

        theatrePresnter = new TheatrePresnter(new TheatreCall());

        Intent intent = getIntent();
        title = intent.getStringExtra("title");
        mid = intent.getStringExtra("id");

        theatre_title.setText(title);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        //recyclerView.addItemDecoration(new SpaceItemDecoration());
        cinemaListAdapter = new CinemaListAdapter(this);
        recyclerView.setAdapter(cinemaListAdapter);
        theatrePresnter.reqeust(mid);

        cinemaListAdapter.setOnclicklitener(new CinemaListAdapter.Onclicklitener() {
            @Override
            public void success(int id, String address, String logo, String name) {
                Intent intent1 = new Intent(TheatreActivity.this, MovieScheduleActivity.class);

                intent1.putExtra("mid",mid);

                intent1.putExtra("name",name);
                intent1.putExtra("cid",String.valueOf(id));
                intent1.putExtra("address",address);

                startActivity(intent1);

            }
        });


    }
    @OnClick(R.id.theatre_back)
    public void Onclick(){
        finish();
    }
    @Override
    protected void destoryData() {
        theatrePresnter.unBind();
    }

    private class TheatreCall implements DataCall<Result<List<CinemaListBean>>> {
        @Override
        public void success(Result<List<CinemaListBean>> result) {
            if (result.getStatus().equals("0000")){

                List<CinemaListBean> cinemaListBeans = result.getResult();

                cinemaListAdapter.reset(cinemaListBeans);
            }
        }

        @Override
        public void fail(ApiException e) {

        }
    }
}
