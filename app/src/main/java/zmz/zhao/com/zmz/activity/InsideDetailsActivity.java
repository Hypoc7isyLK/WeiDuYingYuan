package zmz.zhao.com.zmz.activity;

import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.bw.movie.R;
import com.facebook.drawee.view.SimpleDraweeView;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.umeng.analytics.MobclickAgent;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.jzvd.JZVideoPlayer;
import zmz.zhao.com.zmz.adapter.CommentAdapter;
import zmz.zhao.com.zmz.adapter.DumplingsAdapter;
import zmz.zhao.com.zmz.adapter.ImageAdapter;
import zmz.zhao.com.zmz.bean.Comment;
import zmz.zhao.com.zmz.bean.DetailsBean;
import zmz.zhao.com.zmz.bean.Result;
import zmz.zhao.com.zmz.exception.ApiException;
import zmz.zhao.com.zmz.presenter.CommentPresenter;
import zmz.zhao.com.zmz.presenter.DetailsPresenter;
import zmz.zhao.com.zmz.presenter.FocusMoviePresenter;
import zmz.zhao.com.zmz.presenter.StatePresenter;
import zmz.zhao.com.zmz.util.ExpandableTextView;
import zmz.zhao.com.zmz.util.SpaceItemDecoration;
import zmz.zhao.com.zmz.view.DataCall;

public class InsideDetailsActivity extends BaseActivity implements XRecyclerView.LoadingListener {


    @BindView(R.id.xiaoxin)
    ImageView xiaoxin;
    @BindView(R.id.insidetails_title)
    TextView insidetailsTitle;
    @BindView(R.id.insidetails_simple)
    SimpleDraweeView insidetailsSimple;
    @BindView(R.id.insidetails_details)
    Button insidetailsDetails;
    @BindView(R.id.insidetails_foreshow)
    Button insidetailsForeshow;
    @BindView(R.id.insidetails_photo)
    Button insidetailsPhoto;
    @BindView(R.id.insidetails_discuss)
    Button insidetailsDiscuss;
    @BindView(R.id.movir_back)
    ImageView movir_back;
    @BindView(R.id.insidetails_buy)
    ImageView insidetailsBuy;
    @BindView(R.id.bg)
    SimpleDraweeView bg;
    StatePresenter statePresenter;

    private DetailsBean mResult;
    private DetailsPresenter mDetailsPresenter;
    private String mId1;
    CommentPresenter commentPresenter;

    PopupWindow spopWindow;
    private XRecyclerView comment;
    private int userId;
    private String sessionId;
    private CommentAdapter commentAdapter;
    FocusMoviePresenter focusMoviePresenter;
    @Override
    protected int getLayoutId() {
        return R.layout.activity_inside_details;
    }

    @Override
    protected void initView() {

        Intent mIntent = getIntent();
        mId1 = mIntent.getStringExtra("id");
        Log.e("lk", "inside" + mId1);

        /*userId = USER_INFO.getUserId();

        sessionId = USER_INFO.getSessionId();*/
        mDetailsPresenter = new DetailsPresenter(new DetailsCall());
        commentPresenter = new CommentPresenter(new CommentCall());
        focusMoviePresenter = new FocusMoviePresenter(new FocusCall());
        mDetailsPresenter.reqeust(0, "", mId1);

    }

    @Override
    protected void destoryData() {
        mDetailsPresenter.unBind();
        focusMoviePresenter.unBind();
        commentPresenter.unBind();
    }

    @OnClick({R.id.xiaoxin, R.id.insidetails_details, R.id.insidetails_foreshow, R.id.movir_back, R.id.insidetails_photo, R.id.insidetails_discuss, R.id.insidetails_buy})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.xiaoxin:

                if(USER_INFO != null){

                    userId = USER_INFO.getUserId();
                    sessionId = USER_INFO.getSessionId();
                    Toast.makeText(this, "guanzhu", Toast.LENGTH_SHORT).show();
                    if (mResult.getFollowMovie() == 2){
                        focusMoviePresenter.reqeust(userId,sessionId,mResult.getId());
                    }else {

                    }
                }

                break;
            case R.id.insidetails_details:
                int height = getWindowManager().getDefaultDisplay().getHeight();

                View popView = View.inflate(this, R.layout.activity_mine_pop_item, null);
                PopupWindow popWindow = new PopupWindow(popView, ViewGroup.LayoutParams.WRAP_CONTENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT, true);

                popWindow.setHeight(height*3/4);
                popWindow.setTouchable(true);
                popWindow.setBackgroundDrawable(new BitmapDrawable());
                popWindow.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);

                initData(popView, popWindow);

                popWindow.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);

                View inflate = View.inflate(this, R.layout.activity_inside_details, null);

                popWindow.showAtLocation(inflate, Gravity.BOTTOM, 0, 0);

                break;
            case R.id.insidetails_foreshow:

                popup("预告片", 1);

                break;
            case R.id.insidetails_photo:
                popup("剧照", 2);
                break;
            case R.id.insidetails_discuss:
                popup("影评", 3);
                break;
            case R.id.movir_back:
                finish();
                break;
            case R.id.insidetails_buy:
                Intent intent = new Intent(this, TheatreActivity.class);

                intent.putExtra("title",mResult.getName());
                intent.putExtra("id",String.valueOf(mResult.getId()));
                startActivity(intent);
                break;
        }
    }

    private void initData(View popView, final PopupWindow popWindow) {

        ExpandableTextView expandableTextView = popView.findViewById(R.id.etv);

        SimpleDraweeView details_image = popView.findViewById(R.id.details_image);

        TextView type = popView.findViewById(R.id.type);
        TextView director = popView.findViewById(R.id.director);
        TextView place = popView.findViewById(R.id.place);
        TextView min = popView.findViewById(R.id.min);
        ImageView cancel = popView.findViewById(R.id.cancel);

        details_image.setImageURI(Uri.parse(mResult.getImageUrl()));
        type.setText(mResult.getMovieTypes());
        director.setText(mResult.getDirector());
        min.setText(mResult.getDuration());
        place.setText(mResult.getPlaceOrigin());
        expandableTextView.setText(mResult.getSummary());

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popWindow.dismiss();
            }
        });

    }

    private void popup(String name,int page) {
        int height = getWindowManager().getDefaultDisplay().getHeight();
        View popView = View.inflate(this, R.layout.activity_mine_btn_pop_item, null);
        spopWindow = new PopupWindow(popView, ViewGroup.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT, true);
        spopWindow.setHeight(height*3/4);
        spopWindow.setTouchable(true);
        spopWindow.setBackgroundDrawable(new BitmapDrawable());
        spopWindow.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);

        TextView details_title = popView.findViewById(R.id.details_title);
        ImageView cancel= popView.findViewById(R.id.cance);



        details_title.setText(name);

        View inflate = View.inflate(this, R.layout.activity_inside_details, null);

        spopWindow.showAtLocation(inflate, Gravity.BOTTOM, 0, 0);

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                spopWindow.dismiss();
                JZVideoPlayer.releaseAllVideos();
            }
        });


        if (page == 1){
            XRecyclerView stage = popView.findViewById(R.id.stage);
            stage.setLayoutManager(new LinearLayoutManager(this, OrientationHelper.VERTICAL, false));
            List<DetailsBean.ShortFilmListBean> shortFilmList = mResult.getShortFilmList();
            DumplingsAdapter dumplingsAdapter = new DumplingsAdapter(this);
            stage.addItemDecoration(new SpaceItemDecoration(20));
            dumplingsAdapter.addAll(shortFilmList);
            stage.setAdapter(dumplingsAdapter);

        }else if (page == 2){

            XRecyclerView stag = popView.findViewById(R.id.stage);
            StaggeredGridLayoutManager recyclerViewLayoutManager =
                    new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
            stag.setLayoutManager(recyclerViewLayoutManager);

            stag.addItemDecoration(new SpaceItemDecoration(10));
            List<String> posterList = mResult.getPosterList();

            ImageAdapter imageAdapter = new ImageAdapter(this);

            imageAdapter.addAll(posterList);

            stag.setAdapter(imageAdapter);
        }else if(page == 3){

            comment = popView.findViewById(R.id.stage);

            statePresenter = new StatePresenter(new StateCall());

            comment.setLayoutManager(new LinearLayoutManager(this, OrientationHelper.VERTICAL, false));

            commentAdapter = new CommentAdapter(this);

            comment.setAdapter(commentAdapter);

            commentPresenter.reqeust(0, "",mResult.getId(),true);

            commentAdapter.setOnItemClickListenter(new CommentAdapter.OnItemClickListenter() {
                @Override
                public void onItemClick(int id, int state) {
                    if (state != 1) {
                        //如果点赞，则取消点赞
                        //Toast.makeText(InsideDetailsActivity.this, "不能重复点赞", Toast.LENGTH_SHORT).show();

                    } else {
                        userId = USER_INFO.getUserId();
                        sessionId = USER_INFO.getSessionId();
                        statePresenter.reqeust(userId, sessionId,id);

                    }//否则点赞

                    commentAdapter.notifyDataSetChanged();


                }
            });

        }

    }

    @Override
    public void onRefresh() {
        if (commentPresenter.Running()){
            comment.refreshComplete();
            return;
        }
        commentPresenter.reqeust(0,"",mResult.getId(),true);
    }

    @Override
    public void onLoadMore() {
        if (commentPresenter.Running()){
            comment.loadMoreComplete();
            return;
        }
        commentPresenter.reqeust(0,"",mResult.getId(),false);
    }

    private class DetailsCall implements DataCall<Result<DetailsBean>> {


        @Override
        public void success(Result<DetailsBean> result) {
            mResult = result.getResult();
            insidetailsSimple.setImageURI(mResult.getImageUrl());
            insidetailsTitle.setText(mResult.getName());
            bg.setImageURI(mResult.getImageUrl());

            if (mResult.getFollowMovie() == 1){
                xiaoxin.setImageResource(R.mipmap.com_icon_collection_selected);
            }else {
                xiaoxin.setImageResource(R.mipmap.com_icon_collection_default);
            }
        }

        @Override
        public void fail(ApiException e) {

        }
    }

    private class CommentCall implements DataCall<Result<List<Comment>>> {
        @Override
        public void success(Result<List<Comment>> result) {


            if(result.getStatus().equals("0000")){
                List<Comment> comments = result.getResult();
                if (commentPresenter.isResresh()){
                    commentAdapter.clear();
                }

                commentAdapter.addAll(comments);

                commentAdapter.notifyDataSetChanged();
            }
        }

        @Override
        public void fail(ApiException e) {

        }
    }

    private class FocusCall implements DataCall<Result> {
        @Override
        public void success(Result result) {

            if (result.getStatus().equals("0000")){
                Toast.makeText(InsideDetailsActivity.this, ""+result.getMessage(), Toast.LENGTH_SHORT).show();
                xiaoxin.setImageResource(R.mipmap.com_icon_collection_selected);
            }


        }

        @Override
        public void fail(ApiException e) {

        }
    }
    @Override
    public void onResume() {
        super.onResume();
        MobclickAgent.onPageStart("电影详情");
        MobclickAgent.onResume(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd("电影详情");
        MobclickAgent.onPause(this);
    }

    private class StateCall implements DataCall<Result> {
        @Override
        public void success(Result result) {

        }

        @Override
        public void fail(ApiException e) {

        }
    }
}
