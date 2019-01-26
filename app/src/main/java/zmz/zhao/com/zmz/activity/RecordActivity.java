package zmz.zhao.com.zmz.activity;

import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.bw.movie.R;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import zmz.zhao.com.zmz.adapter.DoneAdapter;
import zmz.zhao.com.zmz.adapter.UndoneAdapter;
import zmz.zhao.com.zmz.bean.Record;
import zmz.zhao.com.zmz.bean.Result;
import zmz.zhao.com.zmz.exception.ApiException;
import zmz.zhao.com.zmz.presenter.RecordPresenter;
import zmz.zhao.com.zmz.util.SpaceItemDecoration;
import zmz.zhao.com.zmz.view.DataCall;

/**
 * date:2019/1/25 8:28
 * author:赵明珠(啊哈)
 * function:购票记录
 */
public class RecordActivity extends BaseActivity implements XRecyclerView.LoadingListener {

    @BindView(R.id.record_radio)
    RadioGroup record_radio;

    @BindView(R.id.finish_recycle)
    XRecyclerView finish_recycle;

    @BindView(R.id.record_unfinished)
    RadioButton record_unfinished;

    @BindView(R.id.record_finish)
    RadioButton record_finish;

    RecordPresenter recordPresenter;
    RecordPresenter donePresenter;

    @BindView(R.id.unfinish_recycle)
    XRecyclerView unfinish_recycle;

    private int userId;
    private String sessionId;
    private UndoneAdapter undoneAdapter;
    private DoneAdapter doneAdapter;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_mine_record;
    }
    @OnClick(R.id.record_back)
    public void Onclick(){
        finish();
    }
    @Override
    protected void initView() {
        finish_recycle.setVisibility(View.GONE);

        recordPresenter = new RecordPresenter(new RecordCall());
        donePresenter = new RecordPresenter(new DoneCall());
        userId = USERDAO.getUserId();
        sessionId = USERDAO.getSessionId();

        initData();
        record_unfinished.setTextColor(getResources().getColorStateList(R.color.white));
        record_radio.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.record_unfinished){
                    record_unfinished.setTextColor(getResources().getColorStateList(R.color.white));
                    record_finish.setTextColor(getResources().getColorStateList(R.color.colorTextColor));

                    finish_recycle.setVisibility(View.GONE);

                    unfinish_recycle.setVisibility(View.VISIBLE);

                }else if(checkedId == R.id.record_finish){
                    record_finish.setTextColor(getResources().getColorStateList(R.color.white));

                    record_unfinished.setTextColor(getResources().getColorStateList(R.color.colorTextColor));

                    unfinish_recycle.setVisibility(View.GONE);

                    finish_recycle.setVisibility(View.VISIBLE);
                }
            }
        });

    }

    private void initData() {

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);

        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        unfinish_recycle.addItemDecoration(new SpaceItemDecoration(20));

        unfinish_recycle.setLayoutManager(linearLayoutManager);

        undoneAdapter = new UndoneAdapter(RecordActivity.this);

        unfinish_recycle.setAdapter(undoneAdapter);

        recordPresenter.reqeust(userId, sessionId, true, 1);

        LinearLayoutManager linearLayoutManager1 = new LinearLayoutManager(this);

        linearLayoutManager1.setOrientation(LinearLayoutManager.VERTICAL);

        finish_recycle.addItemDecoration(new SpaceItemDecoration(20));

        finish_recycle.setLayoutManager(linearLayoutManager1);

        doneAdapter = new DoneAdapter(RecordActivity.this);

        finish_recycle.setAdapter(doneAdapter);

        donePresenter.reqeust(userId, sessionId, true, 2);

    }

    @Override
    protected void destoryData() {
        recordPresenter.unBind();
        donePresenter.unBind();
    }

    @Override
    public void onRefresh() {
        if (recordPresenter.Running()) {
            unfinish_recycle.refreshComplete();
            finish_recycle.refreshComplete();
            return;
        }

        recordPresenter.reqeust(userId, sessionId, true, 1);
    }

    @Override
    public void onLoadMore() {

        if (recordPresenter.Running()) {

            unfinish_recycle.loadMoreComplete();
            finish_recycle.loadMoreComplete();
            return;
        }
        recordPresenter.reqeust(userId, sessionId, false, 1);
    }

    class RecordCall implements DataCall<Result<List<Record>>> {
        @Override
        public void success(Result<List<Record>> result) {

            unfinish_recycle.refreshComplete();

            unfinish_recycle.loadMoreComplete();

            if (result.getStatus().equals("0000") && result.getResult().size()>0) {
                List<Record> records = result.getResult();
                if (recordPresenter.isResresh()) {
                    undoneAdapter.clear();
                }

                undoneAdapter.addAll(records);

                undoneAdapter.notifyDataSetChanged();
            }
        }

        @Override
        public void fail(ApiException e) {

        }
    }

    class DoneCall implements DataCall<Result<List<Record>>> {
        @Override
        public void success(Result<List<Record>> result) {

            finish_recycle.refreshComplete();
            finish_recycle.loadMoreComplete();

            if (result.getStatus().equals("0000")) {
                List<Record> records = result.getResult();

                if (donePresenter.isResresh()) {
                    doneAdapter.clear();
                }
                Record record = new Record(1,"14:30:00","eee",15225423354L,"16:30:00",1,"啊哈","21616546163161",325.0,"发送",2,1703);
                records.add(record);


                doneAdapter.addAll(records);

                doneAdapter.notifyDataSetChanged();
            }
        }

        @Override
        public void fail(ApiException e) {

        }
    }
}
