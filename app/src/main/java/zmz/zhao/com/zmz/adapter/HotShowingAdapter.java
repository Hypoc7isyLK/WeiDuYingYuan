package zmz.zhao.com.zmz.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

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
public class HotShowingAdapter extends RecyclerView.Adapter<HotShowingAdapter.ViewHolder>{

    Context context;
    List<ShowLunBoBean> mBeanList;
    private HotOnClickListener hotOnClickListener;

    public void setHotOnClickListener(HotOnClickListener hotOnClickListener) {
        this.hotOnClickListener = hotOnClickListener;
    }

    public HotShowingAdapter(Context context) {
        this.context = context;
        mBeanList = new ArrayList<>();
    }



    public void reset(List<ShowLunBoBean> result) {

        mBeanList.addAll(result);
    }


    @NonNull
    @Override
    public HotShowingAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = View.inflate(context, R.layout.recyclergrild_item, null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HotShowingAdapter.ViewHolder viewHolder, final int i) {

        viewHolder.recycler_movie_simp.setImageURI(mBeanList.get(i).getImageUrl());
        viewHolder.recycler_movie_name.setText(mBeanList.get(i).getName());

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int id = mBeanList.get(i).getId();
                Toast.makeText(context, ""+id, Toast.LENGTH_SHORT).show();
                hotOnClickListener.Onclick(id);
            }
        });

    }

    @Override
    public int getItemCount() {
        return mBeanList.size();
    }

    public void Clear() {
        mBeanList.clear();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        private SimpleDraweeView recycler_movie_simp;
        private TextView recycler_movie_name;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            recycler_movie_simp = itemView.findViewById(R.id.recycler_movie_simp);
            recycler_movie_name = itemView.findViewById(R.id.recycler_movie_name);
        }
    }

    public interface HotOnClickListener{
        void Onclick(int id);
    }

}
