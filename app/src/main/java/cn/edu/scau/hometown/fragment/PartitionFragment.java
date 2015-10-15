package cn.edu.scau.hometown.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import cn.edu.scau.hometown.R;
import cn.edu.scau.hometown.activities.DetialHmtPostThreadsActivity;
import cn.edu.scau.hometown.activities.HmtPartitionActivity;
import cn.edu.scau.hometown.listener.RecyclerItemClickListener;
import cn.edu.scau.hometown.tools.HttpUtil;

/**
 * Created by Administrator on 2015/10/3 0003.
 */
public class PartitionFragment extends Fragment {
    private RecyclerView rcv_partition;

    private ArrayList<Integer> icon;
    private ArrayList<String> iconName;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_partition, null);
        initData();
        rcv_partition = (RecyclerView) view.findViewById(R.id.rcv_partition);
        rcv_partition.setLayoutManager(new GridLayoutManager(getActivity(), 3));
        rcv_partition.setAdapter(new PartitionAdapter());
        rcv_partition.addOnItemTouchListener(
                new RecyclerItemClickListener(getActivity(), new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        // TODO
                        Intent intent = new Intent(getActivity(), HmtPartitionActivity.class);
                        intent.putExtra("title", iconName.get(position));
                        startActivity(intent);
                        getActivity().overridePendingTransition(R.anim.in_from_right, R.anim.keep);
                    }
                })
        );
        return view;
    }

    private void initData() {
        icon = new ArrayList<>();
        icon.add(R.drawable.user_group_icon);
        icon.add(R.drawable.partition_work);
        icon.add(R.drawable.user_course_icon);
        icon.add(R.drawable.partition_sea);
        icon.add(R.drawable.partition_tiyu);
        icon.add(R.drawable.partition_music);
        icon.add(R.drawable.partition_movie);
        icon.add(R.drawable.partition_motion);
        icon.add(R.drawable.partition_literatal);
        icon.add(R.drawable.partition_why);
        icon.add(R.drawable.user_group_icon);
        icon.add(R.drawable.partition_advertise);
        icon.add(R.drawable.partition_weixiu);
        icon.add(R.drawable.partition_game);


        iconName = new ArrayList<>();
        iconName.add("新生专区");
        iconName.add("职场交流");
        iconName.add("课程交流");
        iconName.add("海洋馆");
        iconName.add("体坛风云");
        iconName.add("音乐坊");
        iconName.add("影视分享");
        iconName.add("情感宣泄");
        iconName.add("文学社");
        iconName.add("生活百科");
        iconName.add("同道堂");
        iconName.add("广而告之");
        iconName.add("电脑维修");
        iconName.add("游戏版");

    }


    public class PartitionAdapter extends RecyclerView.Adapter<PartitionAdapter.MyViewHolder> {

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            MyViewHolder holder = new MyViewHolder(LayoutInflater.from(
                    getActivity()).inflate(R.layout.gridview_item, parent,
                    false));
            return holder;
        }

        @Override
        public void onBindViewHolder(MyViewHolder holder, int position) {
            holder.partition_tv.setText(iconName.get(position));
            holder.partition_image.setImageDrawable(getResources().getDrawable(icon.get(position)));
        }

        @Override
        public int getItemCount() {
            return iconName.size();
        }

        class MyViewHolder extends RecyclerView.ViewHolder {

            TextView partition_tv;
            ImageView partition_image;

            public MyViewHolder(View view) {
                super(view);
                partition_tv = (TextView) view.findViewById(R.id.partition_text);
                partition_image = (ImageView) view.findViewById(R.id.partition_image);
            }
        }
    }
}
