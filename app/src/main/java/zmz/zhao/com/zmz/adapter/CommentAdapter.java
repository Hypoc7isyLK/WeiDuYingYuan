package zmz.zhao.com.zmz.adapter;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
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
public class CommentAdapter extends XRecyclerView.Adapter<CommentAdapter.MyHolder> {

    private Context context;
    List<Comment> list = new ArrayList<>();
    private LayoutInflater inflater;

    public CommentAdapter(Context context) {
        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        //View view = View.inflate(context, , null);
        View view = inflater.inflate(R.layout.activity_movie_comment_item, viewGroup, false);
        MyHolder myHolder = new MyHolder(view);

        return myHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final MyHolder hloder, int i) {
        final Comment comment = list.get(i);
        int isGreat = comment.getIsGreat();
        hloder.simple.setImageURI(Uri.parse(comment.getCommentHeadPic()));
        hloder.name.setText(comment.getCommentUserName());
        hloder.text.setText(comment.getCommentContent());
        hloder.num.setText(String.valueOf(comment.getReplyNum()));

        try {
            hloder.time.setText(DateUtils.dateFormat(new Date(comment.getCommentTime()), DateUtils.MINUTE_PATTERN));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        hloder.praisenum.setText(String.valueOf(comment.getGreatNum()));


        if (isGreat == 1) {
            hloder.praise.setChecked(true);
        }
        if (isGreat == 0) {
            hloder.praise.setChecked(false);
        }


        hloder.praise.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int id = comment.getCommentId();
                if (comment.getIsGreat() == 1) {
                    int greatNum = comment.getGreatNum();
                    comment.setIsGreat(0);
                    comment.setGreatNum(greatNum - 1);
                    Log.e("zmz", "==================" + greatNum);

                    hloder.num.setText(String.valueOf(comment.getGreatNum()));
                } else {
                    int greatNum = comment.getGreatNum();
                    comment.setIsGreat(1);
                    comment.setGreatNum(greatNum + 1);

                    hloder.num.setText(String.valueOf(comment.getGreatNum()));
                }

                onItemClickListenter.onItemClick(id, comment.getIsGreat());

                notifyDataSetChanged();
            }
        });


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

    class MyHolder extends XRecyclerView.ViewHolder {

        SimpleDraweeView simple;
        TextView name,
                text,
                time,
                all,
                praisenum,
                num;
        ToggleButton praise;

        public MyHolder(@NonNull View itemView) {
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

    private OnItemClickListenter onItemClickListenter;

    public void setOnItemClickListenter(OnItemClickListenter onItemClickListenter) {
        this.onItemClickListenter = onItemClickListenter;
    }

    public interface OnItemClickListenter {
        void onItemClick(int id, int state);
    }
}
