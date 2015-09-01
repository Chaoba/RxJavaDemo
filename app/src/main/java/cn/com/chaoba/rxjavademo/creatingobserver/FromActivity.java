package cn.com.chaoba.rxjavademo.creatingobserver;

import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

import cn.com.chaoba.rxjavademo.BaseActivity;
import rx.Observable;

public class FromActivity extends BaseActivity {
    Integer[] arrays = {0, 1, 2, 3, 4, 5};
    List<Integer> list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        for (int i = 0; i <= 5; i++) {
            list.add(i);
        }
        mLButton.setText("FromArray");
        mRButton.setText("FromIterable");
        mLButton.setOnClickListener(e -> FromArray().subscribe(i -> log("FromArray:" + i)));
        mRButton.setOnClickListener(e -> FromIterable().subscribe(i -> log("FromIterable:" + i)));
    }

    private Observable<Integer> FromArray() {
        return Observable.from(arrays);

    }

    private Observable<Integer> FromIterable() {
        return Observable.from(list);
    }
}
