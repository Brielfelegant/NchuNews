package codelight.com.utils;

/**
 * Created by zouziyang on 5/3/16.
 */

import org.json.*;
import com.loopj.android.http.*;

import cz.msebera.android.httpclient.Header;

public class NewsClientUse {



    public void getJson(String url,RequestParams params) throws JSONException {

        NewsClient.get(url, params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                // If the response is JSONObject instead of expected JSONArray
                System.out.println("JSONObject");
                System.out.println(statusCode);
                JSONObject firstEvent = response;
                System.out.println(firstEvent.toString());

            }

            public void onFailure(int statusCode, Header[] headers, JSONObject response, Throwable
                    error) {
                System.out.println("Wrong");
                System.out.println(statusCode);
                error.printStackTrace(System.out);
            }
        /*
        @Override
        public void onSuccess(int statusCode, Header[] headers, JSONArray timeline) {
            // Pull out the first event on the public timeline
            try {
                JSONObject firstEvent = timeline.getJSONObject(0);
                System.out.println(firstEvent.toString());
            } catch (JSONException e) {
                e.printStackTrace();
            }

            // Do something with the response

        }*/
        });
    }

}
