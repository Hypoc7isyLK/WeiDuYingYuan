package zmz.zhao.com.zmz.adapter;

import android.content.Context;
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

import zmz.zhao.com.zmz.bean.CinemaListBean;

/**
 * date:2019/1/27
 * author:李阔(淡意衬优柔)
 * function:
 */
public class CinemaListAdapter extends RecyclerView.Adapter<CinemaListAdapter.ViewHolder> {

    Context context;
    List<CinemaListBean> mListBeans;
    private int mFollowCinema;
    private int mId;
    private String mName;
    private String mAddress;
    private String mLogo;

    public CinemaListAdapter(Context context) {
        this.context = context;
        mListBeans = new ArrayList<>();
    }

    public void reset(List<CinemaListBean> result) {
        mListBeans.clear();
        mListBeans.addAll(result);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = View.inflate(context, R.layout.cinemalist_item, null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int i) {
        viewHolder.details_simple.setImageURI(mListBeans.get(i).getLogo());
        viewHolder.details_title.setText(mListBeans.get(i).getName());
        viewHolder.details_message.setText(mListBeans.get(i).getAddress());
        viewHolder.details_km.setText(mListBeans.get(i).getDistance()+"m");
        mFollowCinema = mListBeans.get(i).getFollowCinema();
        if (mFollowCinema == 1){
            viewHolder.xiaoxinxin.setImageResource(R.mipmap.com_icon_collection_selected);
        }else {
            viewHolder.xiaoxinxin.setImageResource(R.mipmap.com_icon_huisexin);
        }
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                mId = mListBeans.get(i).getId();
                mAddress = mListBeans.get(i).getAddress();
                mLogo = mListBeans.get(i).getLogo();
                mName = mListBeans.get(i).getName();
                mOnclicklitener.success(mId,mAddress,mLogo,mName);
                Log.e("lk", "onBindViewHolder:+session"+mId );
            }
        });


    }

    @Override
    public int getItemCount() {
        return mListBeans.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        private SimpleDraweeView details_simple;
        private TextView details_title;
        private TextView details_message;
        private TextView details_km;
        private ImageView xiaoxinxin;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            details_simple = itemView.findViewById(R.id.details_simple);
            details_title = itemView.findViewById(R.id.details_title);
            details_message = itemView.findViewById(R.id.details_message);
            xiaoxinxin = itemView.findViewById(R.id.xiaoxinxin);
            details_km = itemView.findViewById(R.id.details_km);
        }
    }
    public interface Onclicklitener{
        void success(int id, String address, String logo, String name);
    }

    private Onclicklitener mOnclicklitener;

    public void setOnclicklitener(Onclicklitener onclicklitener) {
        mOnclicklitener = onclicklitener;
    }
}
