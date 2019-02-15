package zmz.zhao.com.zmz.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.RelativeSizeSpan;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bw.movie.R;

import java.util.ArrayList;
import java.util.List;

import zmz.zhao.com.zmz.bean.ScheduleListBean;

/**
 * date:2019/1/29
 * author:李阔(淡意衬优柔)
 * function:
 */
public class ScheduleListAdapter extends RecyclerView.Adapter<ScheduleListAdapter.ViewHolder> {
    Context context;
    List<ScheduleListBean> mListBeans;

    public ScheduleListAdapter(Context context) {
        this.context = context;
        mListBeans = new ArrayList<>();
    }

    public void reset(List<ScheduleListBean> result1) {
        mListBeans.clear();
        mListBeans.addAll(result1);
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = View.inflate(context, R.layout.schedulelist_item, null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int i) {
        viewHolder.begin_time.setText(mListBeans.get(i).getBeginTime());
        viewHolder.end_time.setText(mListBeans.get(i).getEndTime());
        SpannableString spannableString = changTVsize(mListBeans.get(i).getPrice() + "");
        viewHolder.price.setText(spannableString);
        if (mListBeans.get(i).getScreeningHall()!= null){
            viewHolder.hall.setText(mListBeans.get(i).getScreeningHall());
        }


        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mOnClickListener.scuccess(mListBeans.get(i).getId(),mListBeans.get(i).getPrice()+"",mListBeans.get(i).getScreeningHall(),mListBeans.get(i).getBeginTime(),mListBeans.get(i).getEndTime());
            }
        });
    }

    public static SpannableString changTVsize(String value) {
        SpannableString spannableString = new SpannableString(value);
        if (value.contains(".")) {
            spannableString.setSpan(new RelativeSizeSpan(0.6f), value.indexOf("."), value.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
        return spannableString;
    }

    @Override
    public int getItemCount() {
        return mListBeans.size();
    }



    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView begin_time;
        private TextView end_time;
        private TextView price;
        private TextView hall;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            begin_time = itemView.findViewById(R.id.begin_time);
            end_time = itemView.findViewById(R.id.end_time);
            price = itemView.findViewById(R.id.price);
            hall = itemView.findViewById(R.id.quanjing);
        }
    }
    public interface OnClickListener{
        void scuccess(int id,String price,String screeningHall,String begintime,String endtime);
    }
    private OnClickListener mOnClickListener;

    public void setOnClickListener(OnClickListener onClickListener) {
        mOnClickListener = onClickListener;
    }
}
