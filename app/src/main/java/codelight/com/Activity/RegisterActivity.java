package codelight.com.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import codelight.com.Adapter.R;

/**
 * Created by zouziyang on 2016/4/27.
 */
public class RegisterActivity extends AppCompatActivity {

    private Button Save;
    private Button Cannel;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        Cannel= (Button)findViewById(R.id.cancel);
        Cannel.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(RegisterActivity.this,LoginActivity.class);
                startActivity(intent);
            }
        });

    }
}
