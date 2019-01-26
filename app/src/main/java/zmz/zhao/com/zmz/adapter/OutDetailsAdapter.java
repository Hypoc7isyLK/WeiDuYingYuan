package zmz.zhao.com.zmz.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
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
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        viewHolder.details_simple.setImageURI(mBeanList.get(i).getImageUrl());
        viewHolder.details_title.setText(mBeanList.get(i).getName());
        viewHolder.details_message.setText(mBeanList.get(i).getSummary());
        mFollowMovie = mBeanList.get(i).getFollowMovie();
        if (mFollowMovie == 1){
            viewHolder.xiaoxinxin.setImageResource(R.mipmap.com_icon_collection_selected);
        }else {
            viewHolder.xiaoxinxin.setImageResource(R.mipmap.com_icon_collection_default);
        }
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
}