package cn.edu.scau.hometown.fragment;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.LruCache;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cn.edu.scau.hometown.R;
import cn.edu.scau.hometown.activities.DetialHmtPostThreadsActivity;
import cn.edu.scau.hometown.adapter.InitHmtForumListViewAdapter;
import cn.edu.scau.hometown.bean.HmtForumPostContent;
import cn.edu.scau.hometown.bean.HmtForumPostList;
import cn.edu.scau.hometown.bean.ImagesGuideToThreads;
import cn.edu.scau.hometown.listener.RecyclerItemClickListener;
import cn.edu.scau.hometown.tools.HttpUtil;
import me.imid.swipebacklayout.lib.SwipeBackLayout;


/**
 * Created by acer on 2015/7/24.
 */
public class HmtForumFragment extends Fragment {
    private SwipeRefreshLayout lo_swiper;
    //总视图
    private View view;
    //显示热门帖子的RecycleView
    private RecyclerView rcv_hmt_forum;
    //渲染热门帖子列表视图的自定义Adapter
    private InitHmtForumListViewAdapter initHmtForumListViewAdapter;
    //Volley网络请求会用到的工具
    private RequestQueue mRequestQueue;
    //网络申请得到帖子数据后转化而成的帖子数据类
    private HmtForumPostList hmtForumPostList;
    //论坛图片信息类
    private ImagesGuideToThreads imagesGuideToThreads;
    private String tid;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mRequestQueue = Volley.newRequestQueue(getActivity());
    }
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_hmt_forum, container, false);
        lo_swiper = (SwipeRefreshLayout) view.findViewById(R.id.swipe_container_hmt_forum);
        lo_swiper.setEnabled(false);
        VolleyRequestString(HttpUtil.GET_HMT_FORUM_POSTS_CONTENT_BY_FID + "36", 1);
        return view;
    }
    /**
     * 初始化热门帖子列表视图
     */
    private void initHmtForumListView() {
        rcv_hmt_forum = (RecyclerView) view.findViewById(R.id.lv_hmt_forum);
        rcv_hmt_forum.setLayoutManager(new LinearLayoutManager(getActivity()));
        initHmtForumListViewAdapter = new InitHmtForumListViewAdapter(getActivity(), hmtForumPostList);
        rcv_hmt_forum.setAdapter(initHmtForumListViewAdapter);
        rcv_hmt_forum.addOnItemTouchListener(
                new RecyclerItemClickListener(getActivity(), new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        tid = hmtForumPostList.getThreads().get(position).getTid();
                        VolleyRequestString(HttpUtil.GET_HMT_FORUM_POSTS_CONTENT_BY_TID + tid, 2);
                    }
                })
        );
    }
    private void VolleyRequestString(String url, final int searchType) {
        lo_swiper.setRefreshing(true);
        JsonObjectRequest mJsonRequest = new JsonObjectRequest(url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        String json = response.toString();

                        switch (searchType) {
                            case 1:
                                SearchHmtForumDataTask(json);
                                break;
                            case 2:
                                SearchPostContentTask(json);
                                break;
                            case 3:
                                SerchPicturesAndThreadsTask(json);
                                break;
                            default:
                                break;
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        lo_swiper.setRefreshing(false);

                        Boolean connected1 = HttpUtil.isNetworkConnected(getActivity());
                        Boolean connected2 = HttpUtil.isWifiConnected(getActivity());
                        if (connected1 == false && connected2 == false)
                            Toast.makeText(getActivity(), "请检查网络！", Toast.LENGTH_LONG).show();
                        else
                            Toast.makeText(getActivity(), "(*@ο@*) 哇～  很抱歉！服务器出问题了～", Toast.LENGTH_LONG).show();
                    }
                }
        );

        mRequestQueue.add(mJsonRequest);
    }
    /**
     * 获取热门帖子列表数据任务
     */
    private void SearchHmtForumDataTask(String json) {
        Gson gson = new Gson();
        java.lang.reflect.Type type = new TypeToken<HmtForumPostList>() {
        }.getType();
        hmtForumPostList = gson.fromJson(json, type);
        initHmtForumListView();
    }
    /**
     * 获取指定的帖子的回帖数据的任务
     */
    private void SearchPostContentTask(String json) {
        Gson gson = new Gson();
        java.lang.reflect.Type type = new TypeToken<HmtForumPostContent>() {
        }.getType();
        HmtForumPostContent hmtForumPostContent = gson.fromJson(json, type);

        Intent intent = new Intent(getActivity(), DetialHmtPostThreadsActivity.class);
        intent.putExtra("hmtForumPostContent", hmtForumPostContent);
        intent.putExtra("tid", tid);
        lo_swiper.setRefreshing(false);
        startActivity(intent);
        getActivity().overridePendingTransition(R.anim.in_from_right, R.anim.keep);
    }
    private void SerchPicturesAndThreadsTask(String json) {

        Gson gson = new Gson();
        java.lang.reflect.Type type = new TypeToken<ImagesGuideToThreads>() {
        }.getType();
        imagesGuideToThreads = gson.fromJson(json, type);
    }
}
