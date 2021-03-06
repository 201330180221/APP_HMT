package cn.edu.scau.hometown.adapter;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.os.Parcel;
import android.support.v7.widget.RecyclerView;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.text.style.ImageSpan;
import android.text.style.StyleSpan;
import android.text.style.URLSpan;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import cn.edu.scau.hometown.R;
import cn.edu.scau.hometown.bean.HmtForumPostContent;
import cn.edu.scau.hometown.bean.HmtThreadsAttachment;
import cn.edu.scau.hometown.tools.DataUtil;
import cn.edu.scau.hometown.tools.EmotionUtils;
import cn.edu.scau.hometown.tools.HttpUtil;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Administrator on 2015/9/2 0002.
 * 用于渲染论坛帖子回帖列表，填充帖子回帖列表视图的Adapter类
 */
public class InitDetailHmtForumListViewAdapter extends RecyclerView.Adapter<InitDetailHmtForumListViewAdapter.ViewHolder> {


    // 数据集
    private Context context;
    private HmtForumPostContent hmtForumPostContent;
    private RequestQueue mRequestQueue;
    private HmtThreadsAttachment hmtThreadsAttachment;
    private WindowManager wm;
    private int width;
    private int height;

    private String tid;


    public InitDetailHmtForumListViewAdapter(Context context, HmtForumPostContent hmtForumPostContent, String tid) {
        super();
        this.hmtForumPostContent = hmtForumPostContent;
        this.context = context;
        this.mRequestQueue = Volley.newRequestQueue(this.context);
        this.tid = tid;
        wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        width = wm.getDefaultDisplay().getWidth();
        height = wm.getDefaultDisplay().getHeight();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = View.inflate(viewGroup.getContext(), R.layout.listview_item_detail_post_threads, null);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        HmtForumPostContent.PostsEntity postsEntity = hmtForumPostContent.getPosts().get(position);
        String message = postsEntity.getMessage();
        message = message.replaceFirst("\\[i=s\\]", "【");
        message = message.replaceFirst("\\[/i\\]", "】");
        message = message.replaceFirst("\\[quote.*?\\[color=.*?\\]", "【回复：");
        message = message.replaceFirst("\\[/color\\]\\[/url\\]\\[/size\\]", "");
        message = message.replaceFirst("\\[/quote\\]", "】");
        message = message.replaceAll("\\[attach\\]", "\n【attach】");
        message = message.replaceAll("\\[/attach\\]", "【/attach】");
        message = message.replaceAll("\\[.*?\\]", "");
        String name = postsEntity.getAuthor();
        String authorId = postsEntity.getAuthorid();
        String lastpost = postsEntity.getDateline();
        if (position == 0) {
            holder.tv_floor.setText("楼主");
            holder.tv_time_of_detail_forum_threads.setText("发表于 " + DataUtil.times(lastpost));
        } else {
            holder.tv_floor.setText((position + 1) + "楼");
            holder.tv_time_of_detail_forum_threads.setText("回复于 " + DataUtil.times(lastpost));
        }

        holder.tv_content_of_detial_forum_threads.setText(EditThreadsContent(holder.tv_content_of_detial_forum_threads, "\t\t" + message));
        holder.tv_huitie_author_name.setText(name);

        HttpUtil.setUserIconTask(mRequestQueue, HttpUtil.GET_USER_ICON_BY_USER_ID + authorId, holder.cig_huitie_author_icon);

    }

    @Override
    public int getItemCount() {
        return hmtForumPostContent.getPosts().size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView tv_floor;
        public TextView tv_content_of_detial_forum_threads;
        public TextView tv_huitie_author_name;
        public CircleImageView cig_huitie_author_icon;
        public TextView tv_time_of_detail_forum_threads;

        public ViewHolder(View itemView) {
            super(itemView);
            tv_floor = (TextView) itemView.findViewById(R.id.tv_floor);
            tv_content_of_detial_forum_threads = (TextView) itemView.findViewById(R.id.tv_content_of_detial_forum_threads);
            tv_huitie_author_name = (TextView) itemView.findViewById(R.id.tv_huitie_author_name);
            cig_huitie_author_icon = (CircleImageView) itemView.findViewById(R.id.cig_huitie_author_icon);
            tv_time_of_detail_forum_threads = (TextView) itemView.findViewById(R.id.tv_time_of_detail_forum_threads);
        }
    }


    private SpannableString EditThreadsContent(TextView tv, String source) {
        SpannableString spannableString = new SpannableString(source);
        EditQuote(spannableString);
        EditEmoj(spannableString, tv);
        EditUrl(spannableString);
        EditModified(spannableString);
        EditAttach(tv, spannableString);

        return spannableString;
    }

    /**
     * 对帖子内容的url设置超链接
     *
     * @param spannableString
     */
    private void EditUrl(SpannableString spannableString) {
        String regexUrl = "\\[url\\].+\\[/url\\]";
        Pattern patternUrl = Pattern.compile(regexUrl);
        Matcher matcherUrl = patternUrl.matcher(spannableString);
        while (matcherUrl.find()) {
            String url = matcherUrl.group();
            int start = matcherUrl.start();
            int end = start + url.length();
            URLSpan urlSpan = new URLSpan(url);
            spannableString.setSpan(urlSpan, start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
    }

    /**
     * \对表情字符串进行转换
     *
     * @param spannableString
     * @param tv
     */
    private void EditEmoj(SpannableString spannableString, TextView tv) {
        Resources res = context.getResources();
        //找到匹配的表情字符串
        ArrayList<String> regexs = new ArrayList<String>();
        regexs.add("\\{:\\d+_\\d+:\\}");
        regexs.add(":\\w+:");


        for (int i = 0; i < 2; i++) {
            String regexEmotion1 = regexs.get(i);
            Pattern patternEmotion = Pattern.compile(regexEmotion1);
            Matcher matcherEmotion = patternEmotion.matcher(spannableString);
            while (matcherEmotion.find()) {
                // 获取匹配到的具体字符
                String key = matcherEmotion.group();
                // 匹配字符串的开始位置
                int start = matcherEmotion.start();
                // 利用表情名字获取到对应的图片
                Integer imgRes = EmotionUtils.getImgByName(key);
                if (imgRes != null) {
                    // 压缩表情图片
                    int size = 3 * (int) tv.getTextSize();
                    Bitmap bitmap = BitmapFactory.decodeResource(res, imgRes);
                    try {
                        Bitmap scaleBitmap = Bitmap.createScaledBitmap(bitmap, size, size, true);

                        ImageSpan span = new ImageSpan(context, scaleBitmap);
                        spannableString.setSpan(span, start, start + key.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                    } catch (Exception e) {
                    }
                }
            }
        }
    }

    /**
     * 对【回复：[\s\S]*】这种形式的内容进行标记
     *
     * @param spannableString
     */
    private void EditQuote(SpannableString spannableString) {
        String regexQuote = "【回复：[\\s\\S]*】";
        Pattern patternQuote = Pattern.compile(regexQuote);
        Matcher matcherQuote = patternQuote.matcher(spannableString);
        while (matcherQuote.find()) {
            int start = matcherQuote.start();
            int end = matcherQuote.end();
            ForegroundColorSpan foreColorSpan = new ForegroundColorSpan(0xbb009688);
            Parcel p = Parcel.obtain();
            p.writeInt(Typeface.BOLD_ITALIC);
            p.setDataPosition(0);
            StyleSpan ss = new StyleSpan(p);
            spannableString.setSpan(foreColorSpan, start, end, Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
            spannableString.setSpan(ss, start, end, Spannable.SPAN_EXCLUSIVE_INCLUSIVE);

        }
    }

    /**
     * 对【本帖最后由[\s\S]+编辑】这种内容进行标记
     *
     * @param spannableString 帖子的内容
     * @return
     */
    private SpannableString EditModified(SpannableString spannableString) {

        String regexQuote = "【 本帖最后由[\\s\\S]+编辑 】";
        Pattern patternQuote = Pattern.compile(regexQuote);
        Matcher matcherQuote = patternQuote.matcher(spannableString);
        while (matcherQuote.find()) {
            int start = matcherQuote.start();
            int end = matcherQuote.end();
            ForegroundColorSpan foreColorSpan = new ForegroundColorSpan(0xbb9575cd);
            Parcel p = Parcel.obtain();
            p.writeInt(Typeface.BOLD_ITALIC);
            p.setDataPosition(0);
            StyleSpan ss = new StyleSpan(p);
            spannableString.setSpan(foreColorSpan, start, end, Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
            spannableString.setSpan(ss, start, end, Spannable.SPAN_EXCLUSIVE_INCLUSIVE);

        }
        return spannableString;
    }

    private void EditAttach(TextView tv, SpannableString spannableString) {

        String regexAttach = "【attach】\\d+【/attach】";
        Pattern patternAttach = Pattern.compile(regexAttach);
        Matcher matcherAttach = patternAttach.matcher(spannableString);
        while (matcherAttach.find()) {
            int startAttach = matcherAttach.start();
            int endAttach = matcherAttach.end();
            String aid = matcherAttach.group();
            aid = aid.substring(8, aid.lastIndexOf("【"));

            getAttachInfoTask(tv, spannableString, startAttach, endAttach, aid);

        }
    }


    private void getAttachInfoTask(final TextView tv, final SpannableString spannableString, final int startAttach, final int endAttach, String aid) {
        final String url = HttpUtil.GET_POST_THREADS_ATTACHMENT_BY_TID_AND_AID + tid + "&aid=" + aid;

        JsonObjectRequest mJsonRequest = new JsonObjectRequest(url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        String json = response.toString();
                        Gson gson = new Gson();
                        java.lang.reflect.Type type = new TypeToken<HmtThreadsAttachment>() {
                        }.getType();
                        hmtThreadsAttachment = gson.fromJson(json, type);

                        String attachImageUrl = hmtThreadsAttachment.getData().getAttachment();
                        getAttachContentTask(tv, spannableString, startAttach, endAttach, attachImageUrl);


                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }
        );


        mRequestQueue.add(mJsonRequest);

    }


    private void getAttachContentTask(final TextView tv, final SpannableString spannableString, final int startAttach, final int endAttach, String attachImageUrl) {
        String url = attachImageUrl;

        ImageRequest imageRequest = new ImageRequest(url, new Response.Listener<Bitmap>() {
            @Override
            public void onResponse(Bitmap response) {

                Bitmap bitmap = Bitmap.createScaledBitmap(response, (int) (width * 2 / 6), (int) (height * 1 / 6), true);
                ImageSpan span = new ImageSpan(context, bitmap);
                spannableString.setSpan(span, startAttach, endAttach, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                tv.setText(spannableString);
                //tv.append(span,startAttach, endAttach);

            }
        }, 600, 900, Bitmap.Config.ARGB_4444,
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });
        mRequestQueue.add(imageRequest);
    }
}
