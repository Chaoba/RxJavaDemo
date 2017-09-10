package cn.com.chaoba.rxjavademo.creatingobserver;

import android.os.Bundle;
import android.view.View;

import cn.com.chaoba.rxjavademo.BaseActivity;

public class FlowableActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mLButton.setText("Create");
        mLButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        mRButton.setText("Range");
        mRButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

//    private Flowable<Integer> createObserver() {
//    }


}
