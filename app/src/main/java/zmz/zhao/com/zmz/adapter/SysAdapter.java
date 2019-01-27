package zmz.zhao.com.zmz.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;

import com.jcodecraeer.xrecyclerview.XRecyclerView;

import java.util.ArrayList;
import java.util.List;

import zmz.zhao.com.zmz.activity.SystemMassageActivity;
import zmz.zhao.com.zmz.bean.SystemMassage;

/**
 * date:2019/1/26 20:56
 * author:赵明珠(啊哈)
 * function:
 */
public class SysAdapter extends XRecyclerView.Adapter<SysAdapter.MyHolder>{

    private Context context;
    private List<SystemMassage>list = new ArrayList<>();

    public SysAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder myHolder, int i) {

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void adAll(List<SystemMassage> massages) {
        list.addAll(massages);
    }

    public void clearList() {
        list.clear();

    }

    class MyHolder extends XRecyclerView.ViewHolder {

        public MyHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
