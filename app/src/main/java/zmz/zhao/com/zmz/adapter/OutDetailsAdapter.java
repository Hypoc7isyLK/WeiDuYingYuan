package zmz.zhao.com.zmz.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bw.movie.R;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;
import java.util.List;

import zmz.zhao.com.zmz.bean.ShowLunBoBean;

/**
 * date:2019/1/26
 * author:李阔(淡意衬优柔)
 * function:
 */
public class OutDetailsAdapter extends RecyclerView.Adapter<OutDetailsAdapter.ViewHolder> {
    Context context;
    List<ShowLunBoBean> mBeanList;
    private int mFollowMovie;
    private String mImageUrl;
    private String mName;
    private int mId;


    public OutDetailsAdapter(Context context) {
        this.context = context;
        mBeanList = new ArrayList<>();
    }

    public void reset(List<ShowLunBoBean> result) {
        mBeanList.clear();
        mBeanList.addAll(result);
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = View.inflate(context, R.layout.outdetail_recycler_item, null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int i) {
        mImageUrl = mBeanList.get(i).getImageUrl();
        mName = mBeanList.get(i).getName();
        viewHolder.details_simple.setImageURI(mImageUrl);
        viewHolder.details_title.setText(mName);
        viewHolder.details_message.setText(mBeanList.get(i).getSummary());
        mFollowMovie = mBeanList.get(i).getFollowMovie();


        Log.e("lk", "onBindViewHolder: "+mId );

        if (mFollowMovie == 1){
            viewHolder.xiaoxinxin.setImageResource(R.mipmap.com_icon_collection_selected);
        }else {
            viewHolder.xiaoxinxin.setImageResource(R.mipmap.com_icon_collection_default);
        }

        viewHolder.xiaoxinxin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mOnclickFocuslitener.success(mId = mBeanList.get(i).getId());
            }
        });


        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mId = mBeanList.get(i).getId();
                mOnclicklitener.success(mId);
                Log.e("lk", "onBindViewHolder:+session"+mId );
            }
        });

    }

    @Override
    public int getItemCount() {
        return mBeanList.size();
    }



    public class ViewHolder extends RecyclerView.ViewHolder {
        private SimpleDraweeView details_simple;
        private TextView details_title;
        private TextView details_message;
        private ImageView xiaoxinxin;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            details_simple = itemView.findViewById(R.id.details_simple);
            details_title = itemView.findViewById(R.id.details_title);
            details_message = itemView.findViewById(R.id.details_message);
            xiaoxinxin = itemView.findViewById(R.id.xiaoxinxin);
        }
    }

    public interface Onclicklitener{
        void success(int id);
    }
    public interface OnclickFocuslitener{
        void success(int id);
    }

    private Onclicklitener mOnclicklitener;

    private OnclickFocuslitener mOnclickFocuslitener;

    public void setOnclickFocuslitener(OnclickFocuslitener onclickFocuslitener) {
        mOnclickFocuslitener = onclickFocuslitener;
    }
    public void setOnclicklitener(Onclicklitener onclicklitener) {
        mOnclicklitener = onclicklitener;
    }
}
