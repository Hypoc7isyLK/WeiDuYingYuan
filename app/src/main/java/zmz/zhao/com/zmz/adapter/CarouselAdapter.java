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

import zmz.zhao.com.zmz.bean.ShowLunBoBean;

/**
 * date:2019/1/25
 * author:李阔(淡意衬优柔)
 * function:
 */
public class CarouselAdapter extends RecyclerView.Adapter<CarouselAdapter.ViewHolder> {
    Context context;
    List<ShowLunBoBean> mShowLunBoBeans;


    public CarouselAdapter(Context context) {
        this.context = context;
        mShowLunBoBeans = new ArrayList<>();
    }

    public void reset(List<ShowLunBoBean> result) {
        mShowLunBoBeans.clear();
        mShowLunBoBeans.addAll(result);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = View.inflate(context, R.layout.carousel_item, null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        viewHolder.carousel_simple.setImageURI(mShowLunBoBeans.get(i).getImageUrl());
        viewHolder.move_name.setText(mShowLunBoBeans.get(i).getName());
    }

    @Override
    public int getItemCount() {
        return mShowLunBoBeans.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        private SimpleDraweeView carousel_simple;
        private TextView move_name;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            carousel_simple = itemView.findViewById(R.id.carousel_simple);
            move_name = itemView.findViewById(R.id.move_name);

        }
    }
}
