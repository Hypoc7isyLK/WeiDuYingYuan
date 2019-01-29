package zmz.zhao.com.zmz.adapter;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.bw.movie.R;
import com.facebook.drawee.view.SimpleDraweeView;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import zmz.zhao.com.zmz.activity.InsideDetailsActivity;
import zmz.zhao.com.zmz.bean.Comment;
import zmz.zhao.com.zmz.util.DateUtils;

/**
 * date:2019/1/29 13:51
 * author:赵明珠(啊哈)
 * function:
 */
public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.MyHloder> {

    private Context context;
    List<Comment> list = new ArrayList<>();

    public CommentAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public MyHloder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = View.inflate(context, R.layout.activity_movie_comment_item, null);

        return new MyHloder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHloder hloder, int i) {
        Comment comment = list.get(i);
        Log.e("zmz", "=======" + comment.getCommentUserName());

        hloder.simple.setImageURI(Uri.parse(comment.getCommentHeadPic()));
        hloder.name.setText(comment.getCommentUserName());
        hloder.text.setText(comment.getMovieComment());
        hloder.num.setText(String.valueOf(comment.getReplyNum()));
        try {
            hloder.time.setText(DateUtils.dateFormat(new Date(comment.getCommentTime()), DateUtils.MINUTE_PATTERN));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        hloder.praisenum.setText(String.valueOf(comment.getGreatNum()));

        if (comment.getIsGreat() == 1) {
            hloder.praise.setChecked(true);
        } else {
            hloder.praise.setChecked(false);
        }


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void clear() {
        list.clear();
    }

    public void addAll(List<Comment> comments) {
        list.addAll(comments);
    }

    class MyHloder extends RecyclerView.ViewHolder {

        SimpleDraweeView simple;
        TextView name,
                 text,
                 time,
                 all,
                 praisenum,
                 num;
        ToggleButton praise;

        public MyHloder(@NonNull View itemView) {
            super(itemView);

            simple = itemView.findViewById(R.id.comment_head);
            name = itemView.findViewById(R.id.comment_name);
            text = itemView.findViewById(R.id.comment_text);
            time = itemView.findViewById(R.id.comment_time);
            all = itemView.findViewById(R.id.commentAll);
            praisenum = itemView.findViewById(R.id.comment_praise);
            num = itemView.findViewById(R.id.comment_num);
            praise = itemView.findViewById(R.id.praise);

        }
    }
}
