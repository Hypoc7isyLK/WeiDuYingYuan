package zmz.zhao.com.zmz.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.bw.movie.R;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;
import java.util.List;

import zmz.zhao.com.zmz.activity.InsideDetailsActivity;

/**
 * date:2019/1/28 9:43
 * author:赵明珠(啊哈)
 * function:
 */
public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.MyHloder>{

    private Context context;
    List<String> list = new ArrayList<>();

    public ImageAdapter(Context context) {
        this.context = context;
    }

    public void addAll(List<String> posterList) {
        list.addAll(posterList);
    }

    @NonNull
    @Override
    public MyHloder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View view = View.inflate(context, R.layout.activity_stagg_item, null);

        return new MyHloder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHloder myHloder, int i) {
        Glide.with(context).load(list.get(i)).into(myHloder.simple);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class MyHloder extends RecyclerView.ViewHolder {
        SimpleDraweeView simple;
        public MyHloder(@NonNull View itemView) {
            super(itemView);

            simple = itemView.findViewById(R.id.stagg_image);
        }
    }
}
