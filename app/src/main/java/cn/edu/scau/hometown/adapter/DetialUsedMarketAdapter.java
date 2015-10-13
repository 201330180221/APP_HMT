package cn.edu.scau.hometown.adapter;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * @author simple
 * Created by lenovo on 2015/9/4.
 */
public class DetialUsedMarketAdapter extends PagerAdapter {
    private List images;
    public DetialUsedMarketAdapter(List images){
    this.images = images;
    }

    @Override
    public int getCount() {
        return images.size();
    }

    @Override
    public boolean isViewFromObject(View arg0, Object arg1) {
        return arg0 == arg1;
    }

    @Override
    public void destroyItem(ViewGroup view, int position, Object object) {
       view.removeView((View) images.get(position));
    }

    @Override
    public Object instantiateItem(ViewGroup view, int position) {
        view.addView((View) images.get(position));

        return images.get(position);
    }

}

