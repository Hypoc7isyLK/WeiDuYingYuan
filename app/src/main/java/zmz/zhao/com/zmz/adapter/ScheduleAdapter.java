package zmz.zhao.com.zmz.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bw.movie.R;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;
import java.util.List;

import zmz.zhao.com.zmz.bean.ScheduleCinemaBean;

/**
 * date:2019/1/29
 * author:李阔(淡意衬优柔)
 * function:
 */
public class ScheduleAdapter extends RecyclerView.Adapter<ScheduleAdapter.ViewHolder> {
    Context context;
    List<ScheduleCinemaBean> mScheduleCinemaBeans;

    public ScheduleAdapter(Context context) {
        this.context = context;
        mScheduleCinemaBeans = new ArrayList<>();
    }

    public void reset(List<ScheduleCinemaBean> result) {
        mScheduleCinemaBeans.clear();
        mScheduleCinemaBeans.addAll(result);
        notifyDataSetChanged();
    }



    @NonNull
    @Override
    public ScheduleAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = View.inflate(context, R.layout.schedule_item, null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ScheduleAdapter.ViewHolder viewHolder, final int i) {
        viewHolder.carousel_simple.setImageURI(mScheduleCinemaBeans.get(i).getImageUrl());
        viewHolder.move_name.setText(mScheduleCinemaBeans.get(i).getName());
        viewHolder.move_time.setText(mScheduleCinemaBeans.get(i).getDuration());
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {

            private int mId;

            @Override
            public void onClick(View v) {
                mId = mScheduleCinemaBeans.get(i).getId();
                String name = mScheduleCinemaBeans.get(i).getName();
                mOnClickListener.scuccess(mId,name);
            }
        });

    }

    @Override
    public int getItemCount() {
        return mScheduleCinemaBeans.size();
    }



    public class ViewHolder extends RecyclerView.ViewHolder {

        private SimpleDraweeView carousel_simple;
        private TextView move_name;
        private TextView move_time;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            carousel_simple = itemView.findViewById(R.id.carousel_simple);
            move_name = itemView.findViewById(R.id.move_name);
            move_time = itemView.findViewById(R.id.move_time);
        }
    }
    public interface OnClickListener{
        void scuccess(int id,String name);
    }
    private OnClickListener mOnClickListener;

    public void setOnClickListener(OnClickListener onClickListener) {
        mOnClickListener = onClickListener;
    }
}
