package cn.com.chaoba.rxjavademo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

public class BaseActivity extends AppCompatActivity {

    protected Button mGoButton;
    protected TextView mResultView;
    protected String TAG;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);
        mGoButton = (Button) findViewById(R.id.go);
        mResultView = (TextView) findViewById(R.id.result);
        TAG = getLocalClassName();
    }

    protected void log(String s) {
        Log.d(TAG, s);
        mResultView.setText(mResultView.getText() + "\n" + s);
    }
}
