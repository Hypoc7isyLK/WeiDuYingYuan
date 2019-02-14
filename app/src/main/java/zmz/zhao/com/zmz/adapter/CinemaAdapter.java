package zmz.zhao.com.zmz.adapter;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import com.bw.movie.R;
import com.facebook.drawee.view.SimpleDraweeView;
import java.util.ArrayList;
import java.util.List;

import zmz.zhao.com.zmz.bean.Address;

/**
 * date:2019/1/24 12:00
 * author:赵明珠(啊哈)
 * function:
 */
public class CinemaAdapter extends RecyclerView.Adapter<CinemaAdapter.MineHolder>{


    private Context context;
    List<Address>list_cinema = new ArrayList<>();

    public CinemaAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public MineHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = View.inflate(context, R.layout.activity_mine_cinema, null);
        return new MineHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MineHolder holder, int i) {
        final Address address = list_cinema.get(i);

        holder.simple.setImageURI(Uri.parse(address.getLogo()));
        holder.title.setText(address.getName());
        holder.address.setText(address.getAddress());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int id = address.getId();
                String mAddress = address.getAddress();
                String mLogo = address.getLogo();
                String mName = address.getName();

                mOnclicklitener.success(id,mAddress,mLogo,mName);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list_cinema.size();
    }
    public void addAll(List<Address> addresses) {
        list_cinema.addAll(addresses);
    }

    public void clearList() {
        list_cinema.clear();
    }

    class MineHolder extends RecyclerView.ViewHolder {
        SimpleDraweeView simple;
        TextView title;
        TextView address;

        public MineHolder(@NonNull View itemView) {
            super(itemView);
            simple = itemView.findViewById(R.id.cinema_image);
            title = itemView.findViewById(R.id.cinema_title);
            address = itemView.findViewById(R.id.cinema_address);
        }
    }
    private Onclicklitener mOnclicklitener;
    public void setOnclicklitener(Onclicklitener onclicklitener) {
        mOnclicklitener = onclicklitener;
    }
    public interface Onclicklitener{
        void success(int id, String address, String logo, String name);
    }
}
