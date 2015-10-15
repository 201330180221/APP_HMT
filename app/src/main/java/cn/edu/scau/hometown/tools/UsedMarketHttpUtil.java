package cn.edu.scau.hometown.tools;


import android.graphics.Bitmap;
import android.widget.ImageView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cn.edu.scau.hometown.R;
import cn.edu.scau.hometown.bean.GoodsInfo;
import cn.edu.scau.hometown.bean.UsedGoodsBaseData;
import cn.edu.scau.hometown.listener.UsedMarkGoodDetialSearchListener;
import cn.edu.scau.hometown.listener.UsedMarketBaseSearchListener;

/**
 * @author
 * Created by Administrator on 2015/10/14.
 */
public class UsedMarketHttpUtil {

    public static Request getClassifyData(int cateLog, final UsedMarketBaseSearchListener listener){

        final List<UsedGoodsBaseData> datas = new ArrayList<>();

        StringRequest request = new StringRequest(HttpUtil.GET_GOOD_LIST_BY_CASE+cateLog, new Response.Listener<String>() {

            @Override
            public void onResponse(String arg0) {

                try {
                    JSONObject jsonObject = new JSONObject(arg0);
                    JSONArray goodsArray = jsonObject.getJSONArray("goods");
                    UsedGoodsBaseData data = null;
                    for (int i = 0 ; i <goodsArray.length() ; i++){
                        JSONObject response = goodsArray.getJSONObject(i);
                        data = new UsedGoodsBaseData();
                        data.setSecondgoods_id(response.getInt("secondgoods_id"));
                        data.setSecondgoods_price(response.getDouble("secondgoods_price"));
                        data.setSecondgoods_name(response.getString("secondgoods_name"));
                        data.setSecondgoods_views(response.getInt("secondgoods_views"));
                        data.setSecondgoods_bewrite(response.getString("secondgoods_bewrite"));
                        data.setSecondgoods_postdate(response.getString("secondgoods_postdate"));
                        data.setSecondgoods_picture(response.getString("secondgoods_picture"));
                        data.setSecondgoods_pastdate(response.getString("secondgoods_pastdate"));
                        datas.add(data);
                    }

                    listener.success(datas);

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                listener.error(error);
            }
        });
        return request;

    }

    public static Request getMainData(final UsedMarketBaseSearchListener listener) {

        final List<UsedGoodsBaseData> datas = new ArrayList<>();

        Request stringRequsest = new StringRequest(HttpUtil.BASE_URL_OF_USED_MARKET, new Response.Listener<String>() {
            @Override
            public void onResponse(String arg0) {

                try {
                    UsedGoodsBaseData data;
                    JSONArray jsonArray = new JSONArray(arg0);
                    JSONObject response1 = jsonArray.getJSONObject(0);
                    JSONArray goods = response1.getJSONArray("goods");

                    for (int i = 0 ; i <goods.length() ; i++){
                        JSONObject response = goods.getJSONObject(i);
                        data = new UsedGoodsBaseData();
                        data.setSecondgoods_id(response.getInt("secondgoods_id"));
                        data.setSecondgoods_price(response.getDouble("secondgoods_price"));
                        data.setSecondgoods_name(response.getString("secondgoods_name"));
                        data.setSecondgoods_views(response.getInt("secondgoods_views"));
                        data.setSecondgoods_bewrite(response.getString("secondgoods_bewrite"));
                        data.setSecondgoods_postdate(response.getString("secondgoods_postdate"));
                        data.setSecondgoods_picture(response.getString("secondgoods_picture"));
                        data.setSecondgoods_pastdate(response.getString("secondgoods_pastdate"));
                        datas.add(data);
                    }

                    listener.success(datas);


                } catch (JSONException e) {
                    listener.failor(e);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                listener.error(error);
            }
        });
        return stringRequsest;
    }

    public static Request getUMKMainImage(String url,int weight,int height,Bitmap.Config config, final ImageView imageView){

        Request imageRequest = new ImageRequest(url, new Response.Listener<Bitmap>() {
            @Override
            public void onResponse(Bitmap response) {
                imageView.setImageBitmap(response);
            }
        }, weight, height, config, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                imageView.setImageResource(R.drawable.bike);
            }
        });

        return imageRequest;
    }

    public static Request getUMKGoodDetialInfo(int id, final UsedMarkGoodDetialSearchListener listener){
        Request request = new JsonObjectRequest(HttpUtil.DETIAL_GOODS_URL_OF_USED_MARKET + id,
                null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                GoodsInfo data = new GoodsInfo();
                try {
                    data.setSecondgoods_delivertype(response.getString("secondgoods_delivertype"));
                    data.setSecondgoods_paidtype(response.getString("secondgoods_paidtype"));
                    data.setSecondgoods_goodsnums(response.getInt("secondgoods_goodsnums"));
                    data.setSecondgoods_hownew(response.getString("secondgoods_hownew"));
                    data.setSecondgoods_id(response.getInt("secondgoods_id"));
                    data.setSecondgoods_efficiency(response.getString("secondgoods_efficiency"));
                    data.setSecondgoods_tradetype(response.getString("secondgoods_tradetype"));
                    data.setSecondgoods_price(response.getDouble("secondgoods_price"));
                    data.setSecondgoods_name(response.getString("secondgoods_name"));
                    data.setSecondgoods_views(response.getInt("secondgoods_views"));
                    data.setSecondgoods_bewrite(response.getString("secondgoods_bewrite"));
                    data.setSecondgoods_postdate(response.getString("secondgoods_postdate"));
                    data.setSecondgoods_pastdate(response.getString("secondgoods_pastdate"));
                    listener.succee(data);
                } catch (JSONException e) {
                    listener.failor(e);
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                listener.error(error);
            }
        });
        return  request;
    }
}
