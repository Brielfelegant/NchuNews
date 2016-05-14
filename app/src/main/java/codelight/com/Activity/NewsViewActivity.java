package codelight.com.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import codelight.com.Adapter.R;
import codelight.com.utils.NewsClient;
import cz.msebera.android.httpclient.Header;

/**
 * Created by zouziyang on 5/11/16.
 */
public class NewsViewActivity extends AppCompatActivity {

    private TextView tnewsMainTitle;
    private TextView tnewsSubTitle;
    private TextView tcategory;
    private TextView tnewsCreateTime;
    private TextView tnewsContent;
    private TextView tnewsCreatePerson;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewnews);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        tnewsMainTitle =(TextView)findViewById(R.id.newsMainTitle);
        tnewsSubTitle =(TextView)findViewById(R.id.newsSubTitle);
        tcategory =(TextView)findViewById(R.id.newscategoryname);
        tnewsCreateTime =(TextView)findViewById(R.id.newsCreateTime);
        tnewsContent =(TextView)findViewById(R.id.newsContent);
        tnewsCreatePerson =(TextView)findViewById(R.id.newsCreatePerson);


        Intent intent = getIntent();
        String News_id = intent.getStringExtra("News_id");
        newsview(News_id);


    }






    private void newsview(String News_id)  {

        String Baseurl = "/API/getNewsListById";

        RequestParams params = new RequestParams();
        params.put("News_id", News_id);

        NewsClient.get(Baseurl,params,new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                System.out.println(response.toString());
                if(response.length() != 0){
                    try {
                        JSONObject result = response.getJSONObject(0);

                        String MainTitle = result.getString("newsMainTitle");
                        String SubTitle = result.getString("newsSubTitle");
                        String categoryname = result.getString("categoryId");
                        String CreateTime = result.getString("newsCreateTime");
                        String Content = result.getString("newsContent");
                        String CreatePerson = result.getString("newsCreatePerson");

                        tnewsMainTitle.setText(MainTitle);
                        tnewsSubTitle.setText(SubTitle);
                        tcategory.setText(categoryname);
                        tnewsCreateTime.setText(CreateTime);
                        tnewsContent.setText(Content);
                        tnewsCreatePerson.setText(CreatePerson);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }else {
                    Toast.makeText(getApplicationContext(), "你的消息目前为空!", Toast.LENGTH_SHORT).show();
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
