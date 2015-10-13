package cn.edu.scau.hometown.fragment;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.view.ViewGroup.LayoutParams;

import java.util.ArrayList;
import java.util.List;

import cn.edu.scau.hometown.R;
import cn.edu.scau.hometown.activities.DetialUsedMarketActivity;
import cn.edu.scau.hometown.adapter.InitUsedMarketListAdapter;
import cn.edu.scau.hometown.bean.UsedGoodsBaseData;
import fab.FloatingActionButton;


/**
 * Created by acer on 2015/7/24.
 * @author simple
 */
public class SecondaryMarketFragment extends Fragment {
    TextView tv_classify,tv_track,tv_collection;
    Dialog dialog;
    View lvFoot;
    View layout;
    View contentview ;
    List<UsedGoodsBaseData> data = new ArrayList<>();
    private SwipeRefreshLayout lo_swiper;
    private View view;
    private ListView listView;
    //首页轮播的界面的资源
    private ArrayList<View> allListView;
    // 相片的载入
    InitUsedMarketListAdapter adapter;
    FloatingActionButton floatingActionButton;
    private PopupWindow pwMyPopWindow;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        for (int i = 0 ; i <10;i++){
            data.add(new UsedGoodsBaseData());
        }
    }

    public View onCreateView(LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_secondary_market,container,false);
        view.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                dialog.dismiss();
                return true;
            }
        });
        iniButtonPopupWindow();
        initLayoutSwiper();
        initListView();
        initDialog();
        contentview = view.findViewById(R.id.nu);

        floatingActionButton = (FloatingActionButton)view.findViewById(R.id.fab);
        floatingActionButton.attachToListView(listView);
        floatingActionButton.setFocusable(true);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (pwMyPopWindow.isShowing()) {

                    pwMyPopWindow.dismiss();// 关闭
                } else {
                    pwMyPopWindow.showAsDropDown(contentview, 0, 0);// 显示
                }
            }
        });

        return  view;
    }



    @SuppressLint("InflateParams")
    private void iniButtonPopupWindow() {

        LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        layout = inflater.inflate(R.layout.task_detail_popupwindow, null);


        tv_classify = (TextView)layout.findViewById(R.id.tv_classify);
        tv_classify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //实例化SelectPicPopupWindow
                //显示窗口
                pwMyPopWindow.dismiss();
                dialog.show();

            }
        });



        tv_collection = (TextView) layout.findViewById(R.id.tv_collection);
        tv_collection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pwMyPopWindow.dismiss();
            }
        });
        tv_track = (TextView)layout.findViewById(R.id.tv_track);
        tv_track.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pwMyPopWindow.dismiss();
            }
        });


        pwMyPopWindow = new PopupWindow(layout);
        pwMyPopWindow.setFocusable(true);// 加上这个popupwindow中的ListView才可以接收点击事件


        layout.measure(View.MeasureSpec.UNSPECIFIED,
                View.MeasureSpec.UNSPECIFIED);
        pwMyPopWindow.setWidth(layout.getMeasuredWidth());
        pwMyPopWindow.setHeight((layout.getMeasuredHeight()));


        pwMyPopWindow.setBackgroundDrawable(this.getResources().getDrawable(
                R.drawable.bg_popub_window1));// 设置背景图片，不能在布局中设置，要通过代码来设置
        pwMyPopWindow.setOutsideTouchable(true);// 触摸popupwindow外部，popupwindow消失。这个要求你的popupwindow要有背景图片才可以成功，如上
    }

    private void  initListView(){
        listView = (ListView) view.findViewById(R.id.lv_used_maket);
        lvFoot = getActivity().getLayoutInflater().inflate(R.layout.listview_foot_used_market,null);
        lvFoot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                refleshMoreData(data.size(), 10);
            }
        });
        listView.addFooterView(lvFoot);

        adapter = new InitUsedMarketListAdapter(getActivity(),data);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(), DetialUsedMarketActivity.class);
                startActivity(intent);
            }
        });

    }

    private void initDialog() {
        View view = getActivity().getLayoutInflater().inflate(R.layout.classify_dialog, null);
        dialog = new Dialog(getActivity(), R.style.transparentFrameWindowStyle);
        dialog.setContentView(view, new LayoutParams(LayoutParams.MATCH_PARENT,
                LayoutParams.WRAP_CONTENT));
        Window window = dialog.getWindow();
        window.setWindowAnimations(R.style.main_menu_animstyle);
        WindowManager.LayoutParams wl = window.getAttributes();
        wl.x = 0;
        wl.y = getActivity().getWindowManager().getDefaultDisplay().getHeight();
        wl.width = ViewGroup.LayoutParams.MATCH_PARENT;
        wl.height = ViewGroup.LayoutParams.WRAP_CONTENT;

        dialog.onWindowAttributesChanged(wl);
        dialog.setCanceledOnTouchOutside(true);
    }

    private void refleshMoreData(int size,int num){
        for (int i = size;i<size+num;i++){

            data.add(new UsedGoodsBaseData());
        }

        adapter.notifyDataSetChanged();
    }


    private void initLayoutSwiper() {
        lo_swiper = (SwipeRefreshLayout) view.findViewById(R.id.swipe_container_used_market);
        lo_swiper.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            public void onRefresh() {

            }
        });

        lo_swiper.setColorScheme(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
    }


}
