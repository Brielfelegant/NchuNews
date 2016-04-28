package codelight.com.Activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import codelight.com.Adapter.R;
import codelight.com.utils.CommonUtils;
import codelight.com.widget.GestureFrameLayout;

/**
 * Created by zouziyang on 2016/4/25.
 */
public class AboutActivity extends AppCompatActivity {

    private Toolbar mToolbar;
    private GestureFrameLayout mGestureFrameLayout;  //滑动返回
    private TextView mVersionTextView;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mToolbar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_arrow_back_black_18dp));
        setSupportActionBar(mToolbar);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                AboutActivity.this.finish();
            }
        });

        mGestureFrameLayout = (GestureFrameLayout) findViewById(R.id.about_gesture_layout);
        mGestureFrameLayout.attachToActivity(this);

        mVersionTextView = (TextView) findViewById(R.id.version);
        mVersionTextView.setText("版本：" + CommonUtils.getVersion(this));

    }

}
