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

/**
 * Created by zouziyang on 5/8/16.
 */
public class MessageActivity extends AppCompatActivity {


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Intent intent = getIntent();
        String Uid = intent.getStringExtra("Uid");
        getmessage(Uid);


    }

    private void getmessage(String Uid)  {

        String Baseurl = "/API/getMessgae";

        RequestParams params = new RequestParams();
        params.put("Uid", Uid);

        NewsClient.get(Baseurl,params,new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                System.out.println(response.toString());
                if(response.length() != 0){
                    List<Map<String, Object>> resultlist = new ArrayList<Map<String, Object>>();

                    for (int i = 0; i < response.length(); i++) {
                        try {
                            JSONObject result = response.getJSONObject(i);
                            Map<String, Object> map = new HashMap<String, Object>();
                            map.put("sender", result.getString("sender"));
                            map.put("createtime", result.getString("notifyCreateTime"));
                            map.put("content", result.getString("content"));
                            resultlist.add(map);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    SimpleAdapter adapter = new SimpleAdapter(getApplicationContext(), resultlist, R.layout.list_message,
                            new String[]{"sender", "createtime", "content"},
                            new int[]{R.id.Sender, R.id.Createtime, R.id.MessageContent});
                    ListView mListView = (ListView) findViewById(R.id.messagelistView);
                    mListView.setAdapter(adapter);
//                mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//                    @Override
//                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                        // TODO Auto-generated method stub
//                        Map<String, Object> map = (Map<String, Object>)parent.getItemAtPosition(position);
//                        Toast.makeText(getApplicationContext(), "你点击的是第:" + position + "个 ，" + map.get("sender"), Toast.LENGTH_SHORT).show();
//                    }
//                });
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
