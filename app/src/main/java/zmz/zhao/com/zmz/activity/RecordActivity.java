package zmz.zhao.com.zmz.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.bw.movie.R;
import com.greendao.gen.DaoMaster;
import com.greendao.gen.UserInfoDao;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.umeng.analytics.MobclickAgent;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import zmz.zhao.com.zmz.adapter.DoneAdapter;
import zmz.zhao.com.zmz.adapter.UndoneAdapter;
import zmz.zhao.com.zmz.bean.PayBean;
import zmz.zhao.com.zmz.bean.Record;
import zmz.zhao.com.zmz.bean.Result;
import zmz.zhao.com.zmz.bean.dao.UserInfo;
import zmz.zhao.com.zmz.exception.ApiException;
import zmz.zhao.com.zmz.https.IRequest;
import zmz.zhao.com.zmz.https.NetworkManager;
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
    private IWXAPI api;


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
        /**
         * 微信支付
         */
        api = WXAPIFactory.createWXAPI(this, "wxb3852e6a6b7d9516");//第二个参数为APPID
        api.registerApp("wxb3852e6a6b7d9516");
        finish_recycle.setVisibility(View.GONE);

        recordPresenter = new RecordPresenter(new RecordCall());

        donePresenter = new RecordPresenter(new DoneCall());

        record_unfinished.setTextColor(getResources().getColorStateList(R.color.white));

        initData();

        if (USER_INFO == null) {
            Intent intent = new Intent(RecordActivity.this, LoginActivity.class);
            startActivity(intent);
            return;
        }else {
            userId = USER_INFO.getUserId();

            sessionId = USER_INFO.getSessionId();

            Log.e("zmz" + userId, "==00000000==" + sessionId);

            recordPresenter.reqeust(userId, sessionId, true,1);

            donePresenter.reqeust(userId, sessionId, true,2);

        }



    }

    @Override
    public void onResume() {

        super.onResume();

        MobclickAgent.onPageStart("购票记录");
        MobclickAgent.onResume(this);

        UserInfoDao userInfoDao = DaoMaster.newDevSession(this, UserInfoDao.TABLENAME).getUserInfoDao();

        List<UserInfo> userInfoList = userInfoDao.queryBuilder().where(UserInfoDao.Properties.Status.eq(1)).list();

        if (userInfoList != null && userInfoList.size() > 0) {
            USER_INFO = userInfoList.get(0);

            int userids = USER_INFO.getUserId();

            String sessionIds = USER_INFO.getSessionId();

            Log.e("zmz"+userids,"============="+sessionIds);

            recordPresenter.reqeust(userids, sessionIds, true,1);
            donePresenter.reqeust(userids, sessionIds, true,2);

            initData();
        }
    }


    private void initData() {

        record_radio.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.record_unfinished){

                    record_unfinished.setTextColor(getResources().getColorStateList(R.color.white));
                    record_finish.setTextColor(getResources().getColorStateList(R.color.colorTextColor));

                    //Toast.makeText(RecordActivity.this, "record_finish", Toast.LENGTH_SHORT).show();

                    finish_recycle.setVisibility(View.GONE);

                    unfinish_recycle.setVisibility(View.VISIBLE);

                }else if(checkedId == R.id.record_finish){

                    //Toast.makeText(RecordActivity.this, "record_finish", Toast.LENGTH_SHORT).show();

                    unfinish_recycle.setVisibility(View.GONE);

                    finish_recycle.setVisibility(View.VISIBLE);

                    record_finish.setTextColor(getResources().getColorStateList(R.color.white));

                    record_unfinished.setTextColor(getResources().getColorStateList(R.color.colorTextColor));


                }
            }
        });


        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);

        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        unfinish_recycle.addItemDecoration(new SpaceItemDecoration(10));

        unfinish_recycle.setLayoutManager(linearLayoutManager);

        undoneAdapter = new UndoneAdapter(RecordActivity.this);

        unfinish_recycle.setAdapter(undoneAdapter);

        LinearLayoutManager linearLayoutManager1 = new LinearLayoutManager(this);

        linearLayoutManager1.setOrientation(LinearLayoutManager.VERTICAL);

        finish_recycle.addItemDecoration(new SpaceItemDecoration(10));

        finish_recycle.setLayoutManager(linearLayoutManager1);

        doneAdapter = new DoneAdapter(RecordActivity.this);

        finish_recycle.setAdapter(doneAdapter);

        finish_recycle.setLoadingListener(this);

        unfinish_recycle.setLoadingListener(this);

        undoneAdapter.setOnClickL(new UndoneAdapter.OnClickL() {

            private PopupWindow mPopupWindow;

            @Override
            public void scuccess(final String orderId) {
                int height = getWindowManager().getDefaultDisplay().getHeight();
                View inflate = LayoutInflater.from(RecordActivity.this).inflate(R.layout.choose_pay_dialog, null);
                mPopupWindow = new PopupWindow(inflate, RelativeLayout.LayoutParams.MATCH_PARENT, height / 100 * 30);
//设置背景,这个没什么效果，不添加会报错
                mPopupWindow.setBackgroundDrawable(new BitmapDrawable());
                //设置点击弹窗外隐藏自身
                mPopupWindow.setFocusable(true);
                mPopupWindow.setOutsideTouchable(true);
                //设置位置
                mPopupWindow.showAtLocation(inflate, Gravity.BOTTOM, 0, 0);
                //设置PopupWindow的View点击事件
                Button pay = inflate.findViewById(R.id.btn_pay);
                RelativeLayout relative = inflate.findViewById(R.id.rl_alpay);
                relative.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(RecordActivity.this, "暂不支持支付宝功能!", Toast.LENGTH_SHORT).show();
                    }
                });
                pay.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        IRequest interfacea = NetworkManager.getInstance().create(IRequest.class);
                        interfacea.pay(USER_INFO.getUserId(), USER_INFO.getSessionId() + "", "1", orderId).subscribeOn(Schedulers.newThread())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe(new Consumer<PayBean>() {
                                    @Override
                                    public void accept(PayBean payBean) throws Exception {
                                        PayReq req = new PayReq();
                                        req.appId = payBean.getAppId();
                                        req.partnerId = payBean.getPartnerId();
                                        req.prepayId = payBean.getPrepayId();
                                        req.nonceStr = payBean.getNonceStr();
                                        req.timeStamp = payBean.getTimeStamp();
                                        req.packageValue = payBean.getPackageValue();
                                        req.sign = payBean.getSign();
                                        // 在支付之前，如果应用没有注册到微信，应该先调用IWXMsg.registerApp将应用注册到微信
                                        //3.调用微信支付sdk支付方法
                                        api.sendReq(req);
                                    }
                                });
                    }
                });

            }
        });

    }


    public void isLogin() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle("提示");
        builder.setMessage("请先登录");
        builder.setPositiveButton("去登陆", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                Intent intent = new Intent(RecordActivity.this, LoginActivity.class);
                intent.putExtra("login", true);
                startActivity(intent);

            }
        }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(RecordActivity.this, "取消了", Toast.LENGTH_SHORT).show();
            }
        });
        builder.create().show();
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
        if (record_unfinished.isChecked()){
            recordPresenter.reqeust(userId, sessionId, true, 1);
        }

        if (record_finish.isChecked()){
            recordPresenter.reqeust(userId, sessionId, true, 2);
        }

    }

    @Override
    public void onLoadMore() {

        if (recordPresenter.Running()) {

            unfinish_recycle.loadMoreComplete();
            finish_recycle.loadMoreComplete();
            return;
        }
        if (record_unfinished.isChecked()){
            recordPresenter.reqeust(userId, sessionId, false, 1);
        }

        if (record_finish.isChecked()){
            recordPresenter.reqeust(userId, sessionId, false, 2);
        }
    }

    class RecordCall implements DataCall<Result<List<Record>>> {
        @Override
        public void success(Result<List<Record>> result) {

            unfinish_recycle.refreshComplete();

            unfinish_recycle.loadMoreComplete();

            if (result.getStatus().equals("0000") && result.getResult().size()>0) {
                List<Record> records = result.getResult();
                if (recordPresenter.isResresh()) {
                    undoneAdapter.clearList();
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
                    doneAdapter.clearList();
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


    @Override
    public void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd("购票记录");
        MobclickAgent.onPause(this);
    }
}
