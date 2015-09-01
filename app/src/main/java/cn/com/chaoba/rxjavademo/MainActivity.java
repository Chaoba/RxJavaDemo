package cn.com.chaoba.rxjavademo;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import cn.com.chaoba.rxjavademo.creatingobserver.CreateActivity;
import cn.com.chaoba.rxjavademo.creatingobserver.DeferAndJustActivity;
import cn.com.chaoba.rxjavademo.creatingobserver.FromActivity;

public class MainActivity extends ListActivity {
    ArrayList<Item> content = new ArrayList<>();
    MainAdapter mMainAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        content.add(new Item("Create", new Intent(this, CreateActivity.class)));
        content.add(new Item("DeferAndJust", new Intent(this, DeferAndJustActivity.class)));
        content.add(new Item("From", new Intent(this, FromActivity.class)));
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

        public Item(String title, Intent goIntent) {
            this.title = title;
            this.goIntent = goIntent;
        }
    }

}
