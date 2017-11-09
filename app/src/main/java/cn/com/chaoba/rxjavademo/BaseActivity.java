package cn.com.chaoba.rxjavademo;

import android.os.Bundle;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ScrollView;
import android.widget.TextView;

import com.trello.rxlifecycle.components.RxActivity;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;

public class BaseActivity extends RxActivity {

    protected Button mLButton, mRButton;
    protected TextView mResultView;
    protected ScrollView mScrollView;
    protected ViewGroup mBodyView;
    protected String TAG;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);
        mLButton = (Button) findViewById(R.id.left);
        mRButton = (Button) findViewById(R.id.right);
        mResultView = (TextView) findViewById(R.id.result);
        mBodyView = (ViewGroup) findViewById(R.id.body);
        mScrollView = (ScrollView) findViewById(R.id.scroll_view);
        TAG = getLocalClassName();
    }

    protected void log(Object s) {
        Log.i(TAG, String.valueOf(s));
        Observable.just(s).observeOn(AndroidSchedulers.mainThread()).compose(bindToLifecycle()).subscribe(i -> {
            mResultView.setText(mResultView.getText() + "\n" + i);
        });

    }

}
