package zmz.zhao.com.zmz.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

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

        public MineHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
