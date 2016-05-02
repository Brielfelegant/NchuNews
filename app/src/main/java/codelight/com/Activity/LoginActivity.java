package codelight.com.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONException;
import org.json.JSONObject;

import codelight.com.Adapter.R;
import codelight.com.utils.NewsClient;
import cz.msebera.android.httpclient.Header;


/**
 * Created by zouziyang on 2016/4/25.
 */
public class LoginActivity extends AppCompatActivity {



    private Button Login;
    private Button Register;

    private TextView username;
    private TextView password;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        username =(TextView)findViewById(R.id.login_account_et);
        password =(TextView)findViewById(R.id.login_password_cet);

        Login = (Button)findViewById(R.id.login);
        Login.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //Intent intent = new Intent(LoginActivity.this,MainActivity.class);
                //startActivity(intent);
                try {
                    System.out.println("Begin");
                    checklogin(username.getText().toString(),password.getText().toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
        Register= (Button)findViewById(R.id.register);
        Register.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this,RegisterActivity.class);
                startActivity(intent);
            }
        });
    }
    private void checklogin(String username,String password) throws  JSONException {

        String Baseurl = "/API/logincheck";

        RequestParams params = new RequestParams();
        params.put("username", username);
        params.put("password", password);
        NewsClient.get(Baseurl,params,new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {

                //System.out.println(response.toString());
                if(response.isNull("msg"))
                {
                    Toast.makeText(getApplicationContext(), "登录成功!", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(LoginActivity.this,MainActivity.class);
                    startActivity(intent);
                    finish();
                }else{
                    Toast.makeText(getApplicationContext(), "账号密码错误!", Toast.LENGTH_SHORT).show();
                }

            }
            public void onFailure(int statusCode, Header[] headers, JSONObject response, Throwable
                    error) {
                System.out.println("Wrong");
                System.out.println(statusCode);
                Toast.makeText(getApplicationContext(), "网络连接错误!", Toast.LENGTH_SHORT).show();
                error.printStackTrace(System.out);
            }
        });


    }
}
