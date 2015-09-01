package cn.com.chaoba.rxjavademo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

public class BaseActivity extends AppCompatActivity {

    protected Button mLButton, mRButton;
    protected TextView mResultView;
    protected String TAG;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);
        mLButton = (Button) findViewById(R.id.left);
        mRButton = (Button) findViewById(R.id.right);
        mResultView = (TextView) findViewById(R.id.result);
        TAG = getLocalClassName();
    }

    protected void log(Object s) {
        Log.d(TAG, String.valueOf(s));
        mResultView.setText(mResultView.getText() + "\n" + s);
    }

}
