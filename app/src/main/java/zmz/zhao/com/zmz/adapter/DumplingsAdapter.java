package zmz.zhao.com.zmz.adapter;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.bw.movie.R;

import java.util.ArrayList;
import java.util.List;

import cn.jzvd.JZVideoPlayer;
import cn.jzvd.JZVideoPlayerStandard;
import zmz.zhao.com.zmz.activity.InsideDetailsActivity;
import zmz.zhao.com.zmz.bean.DetailsBean;

/**
 * date:2019/1/27 21:03
 * author:赵明珠(啊哈)
 * function:
 */
public class DumplingsAdapter extends RecyclerView.Adapter<DumplingsAdapter.MyHloder> {

    List<DetailsBean.ShortFilmListBean> list = new ArrayList<>();
    private Context context;

    public DumplingsAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public MyHloder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = View.inflate(context, R.layout.activity_dumplings_item, null);

        return new MyHloder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHloder hloder, int i) {

        Glide.with(context).load(list.get(i).getImageUrl()).into(hloder.dumplings.tinyBackImageView);

        hloder.dumplings.setUp(list.get(i).getVideoUrl(), JZVideoPlayerStandard.SCREEN_WINDOW_NORMAL, "");


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void addAll(List<DetailsBean.ShortFilmListBean> shortFilmList) {
        list.addAll(shortFilmList);
    }

    class MyHloder extends RecyclerView.ViewHolder {

        JZVideoPlayerStandard dumplings;

        public MyHloder(@NonNull View itemView) {
            super(itemView);
            dumplings = itemView.findViewById(R.id.dumplings);
        }
    }
}
