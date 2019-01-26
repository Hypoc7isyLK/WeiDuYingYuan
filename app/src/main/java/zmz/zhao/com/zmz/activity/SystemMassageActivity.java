package zmz.zhao.com.zmz.activity;

import android.support.v7.widget.LinearLayoutManager;
import android.widget.TextView;

import com.bw.movie.R;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import java.util.List;

import butterknife.BindView;
import zmz.zhao.com.zmz.adapter.SysAdapter;
import zmz.zhao.com.zmz.bean.Result;
import zmz.zhao.com.zmz.bean.SystemMassage;
import zmz.zhao.com.zmz.exception.ApiException;
import zmz.zhao.com.zmz.presenter.SysPresenter;
import zmz.zhao.com.zmz.view.DataCall;

/**
 * date:2019/1/26 20:30
 * author:赵明珠(啊哈)
 * function:系统消息
 */
public class SystemMassageActivity extends BaseActivity implements XRecyclerView.LoadingListener {

    @BindView(R.id.sys_recycle)
    XRecyclerView sys_recycle;

    @BindView(R.id.sysText)
    TextView textView;

    SysPresenter sysPresenter;
    private int userId;
    private String sessionId;
    private SysAdapter sysAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_system_massage;
    }

    @Override
    protected void initView() {
        userId = USER_INFO.getUserId();
        sessionId = USER_INFO.getSessionId();
        sysPresenter = new SysPresenter(new SysCall());


        initData();
    }

    private void initData() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);

        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        sys_recycle.setLayoutManager(linearLayoutManager);

        sysAdapter = new SysAdapter(this);

        sys_recycle.setAdapter(sysAdapter);

        sysPresenter.reqeust(userId,sessionId,true);
    }

    @Override
    protected void destoryData() {

    }

    @Override
    public void onRefresh() {
        if (sysPresenter.Running()){
            sys_recycle.refreshComplete();
            return;
        }
        sysPresenter.reqeust(userId,sessionId,true);

    }

    @Override
    public void onLoadMore() {
        if (sysPresenter.Running()){
            sys_recycle.loadMoreComplete();
            return;
        }
    }

    private class SysCall implements DataCall<Result<List<SystemMassage>>> {
        @Override
        public void success(Result<List<SystemMassage>> result) {
            if (result.getStatus().equals("0000")){

                if (sysPresenter.isResresh()){
                    sysAdapter.clearList();
                }
                List<SystemMassage> massages = result.getResult();
                sysAdapter.adAll(massages);
                sysAdapter.notifyDataSetChanged();
            }
        }

        @Override
        public void fail(ApiException e) {

        }
    }
}
