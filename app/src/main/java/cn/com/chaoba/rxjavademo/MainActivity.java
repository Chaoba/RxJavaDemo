package cn.com.chaoba.rxjavademo;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import cn.com.chaoba.rxjavademo.Filtering.DebounceActivity;
import cn.com.chaoba.rxjavademo.Filtering.DistinctActivity;
import cn.com.chaoba.rxjavademo.Filtering.ElementAtAndFilterActivity;
import cn.com.chaoba.rxjavademo.Filtering.FirstActivity;
import cn.com.chaoba.rxjavademo.Filtering.SampleActivity;
import cn.com.chaoba.rxjavademo.Filtering.SkipAndTakeActivity;
import cn.com.chaoba.rxjavademo.creatingobserver.CreateAndRangeActivity;
import cn.com.chaoba.rxjavademo.creatingobserver.DeferAndJustActivity;
import cn.com.chaoba.rxjavademo.creatingobserver.FromActivity;
import cn.com.chaoba.rxjavademo.creatingobserver.IntervalActivity;
import cn.com.chaoba.rxjavademo.creatingobserver.RepeatAndTimerActivity;
import cn.com.chaoba.rxjavademo.transforming.BufferActivity;
import cn.com.chaoba.rxjavademo.transforming.FlatMapActivity;
import cn.com.chaoba.rxjavademo.transforming.GroupbyActivity;
import cn.com.chaoba.rxjavademo.transforming.MapAndCastActivity;
import cn.com.chaoba.rxjavademo.transforming.ScanActivity;
import cn.com.chaoba.rxjavademo.transforming.WindowActivity;

public class MainActivity extends ListActivity {
    ArrayList<Item> content = new ArrayList<>();
    MainAdapter mMainAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        content.add(new Item(CreateAndRangeActivity.class));
        content.add(new Item(DeferAndJustActivity.class));
        content.add(new Item(FromActivity.class));
        content.add(new Item(IntervalActivity.class));
        content.add(new Item(RepeatAndTimerActivity.class));
        content.add(new Item(BufferActivity.class));
        content.add(new Item(FlatMapActivity.class));
        content.add(new Item(GroupbyActivity.class));
        content.add(new Item(MapAndCastActivity.class));
        content.add(new Item(ScanActivity.class));
        content.add(new Item(WindowActivity.class));
        content.add(new Item(DebounceActivity.class));
        content.add(new Item(DistinctActivity.class));
        content.add(new Item(ElementAtAndFilterActivity.class));
        content.add(new Item(FirstActivity.class));
        content.add(new Item(SkipAndTakeActivity.class));
        content.add(new Item(SampleActivity.class));
        mMainAdapter = new MainAdapter();
        setListAdapter(mMainAdapter);
    }

    protected void onListItemClick(ListView l, View v, int position, long id) {
        startActivity(mMainAdapter.getItem(position).goIntent);
    }

    class MainAdapter extends BaseAdapter {

        @Override

        public int getCount() {
            return content.size();
        }

        @Override
        public Item getItem(int position) {
            return content.get(position);
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View v = View.inflate(MainActivity.this, android.R.layout.simple_list_item_1, null);
            TextView textView = (TextView) v.findViewById(android.R.id.text1);
            textView.setText(getItem(position).title);
            return v;
        }
    }

    class Item {
        public String title;
        public Intent goIntent;

        public Item(Class<? extends Activity> t) {
            this.title = t.getSimpleName();
            this.goIntent = new Intent(MainActivity.this, t);
        }

    }

}
