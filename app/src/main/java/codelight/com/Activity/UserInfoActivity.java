package codelight.com.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import codelight.com.Adapter.R;
import codelight.com.utils.NewsClient;
import cz.msebera.android.httpclient.Header;

/**
 * Created by zouziyang on 5/8/16.
 */
public class UserInfoActivity extends AppCompatActivity {

    private TextView nametext;
    private TextView sextext;
    private TextView phonetext;
    private TextView emailtext;

    private Button button_cannel;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_userinfo);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        nametext =(TextView)findViewById(R.id.nametext);
        sextext =(TextView)findViewById(R.id.sextext);
        phonetext =(TextView)findViewById(R.id.phonetext);
        emailtext =(TextView)findViewById(R.id.emailtext);

        Intent intent = getIntent();
        final String Uid = intent.getStringExtra("Uid");

        button_cannel= (Button)findViewById(R.id.button_cannel);
        button_cannel.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(UserInfoActivity.this,MainActivity.class);
                intent.putExtra("Uid",Uid);
                startActivity(intent);
                finish();
            }
        });

        //System.out.println(Uid);
        getuserinfo(Uid);


    }

    private void getuserinfo(String Uid)  {

        String Baseurl = "/API/getUserInfo";

        RequestParams params = new RequestParams();
        params.put("Uid", Uid);
        NewsClient.get(Baseurl,params,new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                //System.out.println(response.toString());
                try {

                    JSONObject result = response.getJSONObject(0);
                    String sex = result.getString("sex");
                    String username = result.getString("username");
                    String phonenumber = result.getString("phonenumber");
                    String email = result.getString("email");

                    nametext.setText(username);
                    sextext.setText(sex);
                    phonetext.setText(phonenumber);
                    emailtext.setText(email);

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
            @Override
            public void onFailure(int statusCode, Header[] headers, String errorResponse, Throwable error){
                System.out.println("Wrong");
                System.out.println(statusCode);
                Toast.makeText(getApplicationContext(), "网络连接错误!", Toast.LENGTH_SHORT).show();
                error.printStackTrace(System.out);
            }
        });


    }
}
