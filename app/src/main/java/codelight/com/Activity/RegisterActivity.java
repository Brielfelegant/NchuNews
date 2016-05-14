package codelight.com.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONException;
import org.json.JSONObject;



import codelight.com.Adapter.R;
import codelight.com.utils.NewsClient;
import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.entity.StringEntity;
import cz.msebera.android.httpclient.protocol.HTTP;

/**
 * Created by zouziyang on 2016/4/27.
 */
public class RegisterActivity extends AppCompatActivity {

    private Button Save;
    private Button Cannel;

    private TextView username;
    private TextView password;
    private TextView email;
    private TextView phone;

    private RadioGroup radioGroup;
    private RadioButton radioButton;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        username =(TextView)findViewById(R.id.username);
        password =(TextView)findViewById(R.id.password);

        email = (TextView)findViewById(R.id.email);
        phone = (TextView)findViewById(R.id.phoneNum);

        radioGroup = (RadioGroup)findViewById(R.id.radioGroup1);
        radioButton = (RadioButton)findViewById(radioGroup.getCheckedRadioButtonId());
        //System.out.print(radioButton.getText().toString());

        Cannel= (Button)findViewById(R.id.cancel);
        Cannel.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(RegisterActivity.this,LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });
        Save= (Button)findViewById(R.id.save);
        Save.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                    System.out.println("Begin");
                    registeruser();


            }
        });
    }
    private  void registeruser(){
        String Baseurl = "/API/register";
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("username",username.getText().toString());
            jsonObject.put("password",password.getText().toString());
            //jsonObject.put("sex","男");
            jsonObject.put("sex",radioButton.getText().toString());
            jsonObject.put("phonenumber",phone.getText().toString());
            jsonObject.put("email",email.getText().toString());
            jsonObject.put("userallow",1);
            StringEntity stringEntity = new StringEntity(jsonObject.toString(),HTTP.UTF_8);
            System.out.println(jsonObject);

            NewsClient.post(RegisterActivity.this, Baseurl,stringEntity, "application/json", new JsonHttpResponseHandler(){
                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONObject response){
                    System.out.println(response.toString());
                    //response.getString("success");
                    try {
                        if(response.getString("success").equals("true")){
                            System.out.println(response.getString("success"));
                            Toast.makeText(getApplicationContext(), "注册成功!", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(RegisterActivity.this,LoginActivity.class);
                            startActivity(intent);
                            finish();
                        }else
                        {
                            Toast.makeText(getApplicationContext(), "系统错误!", Toast.LENGTH_SHORT).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

            });
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
