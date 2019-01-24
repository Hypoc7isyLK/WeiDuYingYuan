package zmz.zhao.com.zmz.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bw.movie.R;
import com.facebook.drawee.view.SimpleDraweeView;

/**
 * date:2019/1/23 21:06
 * author:赵明珠(啊哈)
 * function:
 */
public class MineMovieAdapter extends RecyclerView.Adapter<MineMovieAdapter.MineHolder>{


    @NonNull
    @Override
    public MineHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull MineHolder mineHolder, int i) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    class MineHolder extends RecyclerView.ViewHolder {
        SimpleDraweeView simple;
        TextView title;
        TextView movie_text;
        TextView date;
        public MineHolder(@NonNull View itemView) {
            super(itemView);
            simple = itemView.findViewById(R.id.movie_image);
            title = itemView.findViewById(R.id.title);
            movie_text = itemView.findViewById(R.id.movie_text);
            date = itemView.findViewById(R.id.date);
        }
    }
}
