package cn.com.chaoba.rxjavademo.rxbinding;

import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jakewharton.rxbinding.support.v4.view.RxViewPager;

import java.util.ArrayList;

import cn.com.chaoba.rxjavademo.BaseActivity;
import cn.com.chaoba.rxjavademo.R;
import rx.functions.Action1;

public class RxBindViewPagerActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bind_viewpager);
        ArrayList<View> viewList = new ArrayList(5);
        LayoutInflater li = getLayoutInflater();
        for (int i = 0; i < 5; i++) {
            viewList.add(li.inflate(R.layout.viewpager_item, null));
        }
        ViewPager viewPager = (ViewPager) findViewById(R.id.view_pager);
        viewPager.setAdapter(new PagerAdapter() {
            @Override
            public Object instantiateItem(ViewGroup container, int position) {
                View child = viewList.get(position % 5);
                container.addView(child);
                TextView textView = (TextView) child.findViewById(R.id.item_text);
                textView.setText(String.valueOf(position));
                return child;
            }

            @Override
            public void destroyItem(ViewGroup container, int position, Object object) {
                container.removeView(viewList.get(position % 5));
            }

            @Override
            public int getCount() {
                return Integer.MAX_VALUE;
            }

            @Override
            public boolean isViewFromObject(View view, Object object) {
                return view == object;
            }
        });

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int
                    positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                log("onPageSelected:" + position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        RxViewPager.pageSelections(viewPager)
                .subscribe(new Action1<Integer>() {
                    @Override
                    public void call(Integer integer) {
                        log("RxViewPager onPageSelected:" + integer);
                    }
                });
        RxViewPager.currentItem(viewPager).call(Integer.MAX_VALUE / 2);
        RxViewPager.pageScrollStateChanges(viewPager)
                .subscribe(new Action1<Integer>() {
                    @Override
                    public void call(Integer integer) {
                        log("pageScrollStateChanges:" + integer);
                    }
                });
    }

}