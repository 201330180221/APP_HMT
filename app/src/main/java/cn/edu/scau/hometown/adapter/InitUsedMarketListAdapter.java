package cn.edu.scau.hometown.adapter;

import android.content.Context;
import android.media.Image;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.List;

import cn.edu.scau.hometown.R;
import cn.edu.scau.hometown.bean.UsedGoodsBaseData;
import cn.edu.scau.hometown.listener.UsedMarketMianImageListener;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * @author simple
 * Created by acer on 2015/9/20.
 */
public class InitUsedMarketListAdapter extends RecyclerView.Adapter<InitUsedMarketListAdapter.ViewHolder> {
    private List<UsedGoodsBaseData> datas;
    UsedMarketMianImageListener mListener;
    public InitUsedMarketListAdapter(List<UsedGoodsBaseData> data,UsedMarketMianImageListener listener){
        this.datas = data;
        this.mListener = listener;
    }

    @Override
    public InitUsedMarketListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = View.inflate(parent.getContext(),R.layout.adapter_listview_ware,null);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    public void onBindViewHolder(InitUsedMarketListAdapter.ViewHolder holder, int position) {
        UsedGoodsBaseData data = datas.get(position);
        holder.secondgoods_bewrite.setText(data.getSecondgoods_bewrite());
        holder.secondgoods_price.setText("￥:"+data.getSecondgoods_price()+"");
        holder.secondgoods_views.setText(data.getSecondgoods_views()+"");
        holder.secondgoods_name.setText(data.getSecondgoods_name());
        mListener.setImage(holder.secondgoods_picture,data.getSecondgoods_picture());

    }

    public int getItemCount() {
        return datas.size();
    }
    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView secondgoods_price,secondgoods_bewrite,secondgoods_views,secondgoods_name;
        ImageView secondgoods_picture;
        public ViewHolder(View itemView) {
            super(itemView);
            secondgoods_bewrite = (TextView) itemView.findViewById(R.id.secondgoods_bewrite);
            secondgoods_price = (TextView) itemView.findViewById(R.id.secondgoods_price);
            secondgoods_views = (TextView) itemView.findViewById(R.id.secondgoods_views);
            secondgoods_name = (TextView) itemView.findViewById(R.id.secondgoods_name);
            secondgoods_picture = (ImageView) itemView.findViewById(R.id.secondgoods_picture);
        }
    }

}
