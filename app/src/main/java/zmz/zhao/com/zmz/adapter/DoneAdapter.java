package zmz.zhao.com.zmz.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.bw.movie.R;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import zmz.zhao.com.zmz.activity.RecordActivity;
import zmz.zhao.com.zmz.bean.Record;
import zmz.zhao.com.zmz.util.DateUtils;

/**
 * date:2019/1/26 10:57
 * author:赵明珠(啊哈)
 * function:
 */
public class DoneAdapter extends XRecyclerView.Adapter<DoneAdapter.MyHolder> {

    private Context context;
    List<Record> list = new ArrayList<>();

    public DoneAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View view = View.inflate(context, R.layout.activity_mine_ticket_finish, null);

        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int i) {
        Record record = list.get(i);
        Log.e("zmz","========"+record.toString());
        holder.title.setText(record.getMovieName());
        holder.indent.setText(record.getOrderId());
        holder.theatre.setText(record.getCinemaName());
        holder.amount.setText(String.valueOf(record.getAmount()));
        holder.hall.setText(record.getScreeningHall());
        holder.money.setText(String.valueOf(record.getPrice()));
        holder.startTime.setText(record.getBeginTime());
        holder.endTime.setText(record.getEndTime());
        try {
            holder.order_time.setText(DateUtils.dateFormat(new Date(record.getCreateTime()),DateUtils.HOUR_PATTERN));
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
    public void addAll(List<Record> records) {
        list.addAll(records);
    }

    public void clear() {
        list.clear();
    }
    class MyHolder extends RecyclerView.ViewHolder {

        TextView title,
                indent,
                theatre,
                hall, money,
                amount,
                startTime,
                endTime,
                order_time;


        public MyHolder(@NonNull View itemView) {
            super(itemView);
            theatre = itemView.findViewById(R.id.theatre);
            title = itemView.findViewById(R.id.title);
            money = itemView.findViewById(R.id.money);
            hall = itemView.findViewById(R.id.hall);
            indent = itemView.findViewById(R.id.indent);
            amount = itemView.findViewById(R.id.amount);
            startTime = itemView.findViewById(R.id.startTime);
            endTime = itemView.findViewById(R.id.endTime);
            order_time = itemView.findViewById(R.id.order_time);
        }
    }
}
