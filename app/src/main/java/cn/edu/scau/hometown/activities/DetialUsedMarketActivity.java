package cn.edu.scau.hometown.activities;


import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import java.util.ArrayList;
import java.util.List;

import cn.edu.scau.hometown.R;
import cn.edu.scau.hometown.adapter.DetialUsedMarketAdapter;


public class DetialUsedMarketActivity extends AppCompatActivity {
    private ViewPager pagers;
    private RadioGroup dots;
    private Toolbar toolbar ;
    private int[] imageIds = new int[]{
            R.drawable.menu_1,
            R.drawable.menu_1_0,
            R.drawable.menu_1_1,
            R.drawable.menu_1_2,
            R.drawable.menu_1_3
    };
    private List images;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detial_used_market);
        pagers = (ViewPager) findViewById(R.id.viewpager);
        dots = (RadioGroup) findViewById(R.id.radiogroup);
        toolbar = (Toolbar)findViewById(R.id.toolbar);
        toolbar.setTitle("商品信息");
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar!=null){
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeButtonEnabled(true);
        }
        images = new ArrayList<ImageView>();
        for(int i = 0; i < imageIds.length; i++){
            ImageView imageView = new ImageView(this);
            imageView.setBackgroundResource(imageIds[i]);
            images.add(imageView);
        }
        pagers.setAdapter(new DetialUsedMarketAdapter(images));
        pagers.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {
                ((RadioButton) dots.getChildAt(i)).setChecked(true);
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home){
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
