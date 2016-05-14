package codelight.com.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
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


public class MainActivity extends AppCompatActivity {

    private View mAboutButton;
    private View mUserInfo;
    private View mMessage;
    private View mexit;

    private String Uid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);



        Intent intent = getIntent();
        Uid = intent.getStringExtra("Uid");

        getNewsList();

        mAboutButton = findViewById(R.id.drawer_item_about);
        mAboutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,AboutActivity.class);
                startActivity(intent);

            }
        });
        mUserInfo = findViewById(R.id.userinfo);
        mUserInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,UserInfoActivity.class);
                intent.putExtra("Uid",Uid);
                startActivity(intent);

            }
        });
        mMessage = findViewById(R.id.drawer_item_message);
        mMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,MessageActivity.class);
                intent.putExtra("Uid",Uid);
                startActivity(intent);

            }
        });
        mexit = findViewById(R.id.bottom_drawer_exit);
        mexit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                System.exit(0);

            }
        });


    }

    private void getNewsList()  {

        String Baseurl = "/API/getNewsList";

        NewsClient.get(Baseurl,null,new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                System.out.println(response.toString());

                List<Map<String, Object>> resultlist = new ArrayList<Map<String, Object>>();

                for(int i =0; i < response.length();i++){
                    try {
                        JSONObject result = response.getJSONObject(i);
                        Map<String, Object> map = new HashMap<String, Object>();
                        map.put("newsId", result.getString("newsId"));
                        map.put("newstitle", result.getString("newsMainTitle"));
                        map.put("category", result.getString("categoryId"));
                        map.put("person", result.getString("newsCreatePerson"));
                        map.put("newstime", result.getString("newsCreateTime"));

                        resultlist.add(map);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                SimpleAdapter adapter = new SimpleAdapter(getApplicationContext(),resultlist, R.layout.list_news,
                        new String[] {"newstitle", "category","person","time"},
                        new int[] {R.id.newstitle, R.id.category,R.id.person,R.id.newstime});
                ListView mListView = (ListView)findViewById(R.id.newslistView);
                mListView.setAdapter(adapter);
                mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        // TODO Auto-generated method stub
                        Map<String, Object> map = (Map<String, Object>)parent.getItemAtPosition(position);
//                        Toast.makeText(getApplicationContext(), "你点击的是第:" + position + "个 ，" + map.get("newsId"), Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(MainActivity.this,NewsViewActivity.class);
                        intent.putExtra("News_id",map.get("newsId").toString());
                        startActivity(intent);
                }
                });

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
