package cn.com.chaoba.rxjavademo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

public class BaseActivity extends AppCompatActivity {

    protected Button mGoButton, mSecondButton;
    protected TextView mResultView;
    protected String TAG;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);
        mGoButton = (Button) findViewById(R.id.go);
        mSecondButton = (Button) findViewById(R.id.second_go);
        mResultView = (TextView) findViewById(R.id.result);
        TAG = getLocalClassName();
    }

    protected void log(Object s) {
        Log.d(TAG, String.valueOf(s));
        mResultView.setText(mResultView.getText() + "\n" + s);
    }

}
