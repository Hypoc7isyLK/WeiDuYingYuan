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


/**
 * date:2019/1/25 20:13
 * author:赵明珠(啊哈)
 * function:
 */
public class UndoneAdapter extends XRecyclerView.Adapter<UndoneAdapter.MyHolder> {

    private Context context;
    List<Record> list = new ArrayList<>();
    private Record mRecord;

    public UndoneAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = View.inflate(context, R.layout.activity_mine_ticket_record, null);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyHolder holder, final int i) {
        mRecord = list.get(i);

        holder.title.setText(mRecord.getMovieName());
        holder.indent.setText(mRecord.getOrderId());
        holder.theatre.setText(mRecord.getCinemaName());
        holder.amount.setText(String.valueOf(mRecord.getAmount()));
        holder.hall.setText(mRecord.getScreeningHall());
        holder.money.setText(String.valueOf(mRecord.getPrice()));
        holder.time.setText(mRecord.getBeginTime() + " " + mRecord.getEndTime());

        holder.payment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String orderid = mRecord.getOrderId();
                Log.e("lk","orderid"+ orderid);
                mOnClickL.scuccess(orderid);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void addAll(List<Record> records) {
        list.addAll(records);
    }

    public void clearList() {
        list.clear();
    }

    class MyHolder extends RecyclerView.ViewHolder {

        TextView title, indent, theatre, hall, time, money, amount;
        Button payment;

        public MyHolder(@NonNull View itemView) {
            super(itemView);
            theatre = itemView.findViewById(R.id.theatre);
            title = itemView.findViewById(R.id.title);
            time = itemView.findViewById(R.id.time);
            money = itemView.findViewById(R.id.money);
            hall = itemView.findViewById(R.id.hall);
            indent = itemView.findViewById(R.id.indent);
            amount = itemView.findViewById(R.id.amount);
            payment = itemView.findViewById(R.id.payment);
        }
    }


    public interface OnClickL{
        void scuccess(String orderId);
    }
    private OnClickL mOnClickL;

    public void setOnClickL(OnClickL onClickL) {
        mOnClickL = onClickL;
    }
}
