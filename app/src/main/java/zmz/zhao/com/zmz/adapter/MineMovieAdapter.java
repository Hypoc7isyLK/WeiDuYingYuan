package zmz.zhao.com.zmz.adapter;
import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.bw.movie.R;
import com.facebook.drawee.view.SimpleDraweeView;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import zmz.zhao.com.zmz.bean.Attention;
import zmz.zhao.com.zmz.util.DateUtils;

/**
 * date:2019/1/23 21:06
 * author:赵明珠(啊哈)
 * function:
 */
public class MineMovieAdapter extends XRecyclerView.Adapter<MineMovieAdapter.MineHolder>{

    private Context context;
    List<Attention>list = new ArrayList<>();

    public MineMovieAdapter(Context context) {
            this.context = context;
    }

    @NonNull
    @Override
    public MineHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = View.inflate(context, R.layout.activity_mine_movie, null);
        return new MineHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MineHolder holder, int i) {
        final Attention attention = list.get(i);

        Log.e("zmz","===="+attention.getSummary());

        holder.simple.setImageURI(Uri.parse(attention.getImageUrl()));
        try {
            holder.date.setText(DateUtils.dateFormat(new Date(attention.getReleaseTime()),DateUtils.MINUTE_PATTERN));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        holder.movie_text.setText(attention.getSummary());

        holder.title.setText(attention.getName());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int id = attention.getId();
                mOnclicklitener.success(id);
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void addAll(List<Attention> attention) {
        list.addAll(attention);
    }

    public void clearList() {
        list.clear();
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
    private Onclicklitener mOnclicklitener;
    public void setOnclicklitener(Onclicklitener onclicklitener) {
        mOnclicklitener = onclicklitener;
    }
    public interface Onclicklitener{
        void success(int id);
    }
}
