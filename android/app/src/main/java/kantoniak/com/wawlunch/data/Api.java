package kantoniak.com.wawlunch.data;

import android.content.Context;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.TextHttpResponseHandler;

import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

import cz.msebera.android.httpclient.entity.StringEntity;

public class Api {

    public interface Method {
        String PLACES = "places/";
        String MENUS_PLACE_ID = "menus/?placeId=";
        String CHECK_IN = "checkin/";
    }

    private final static String API_HOST = "http://buoto.me:8080/";

    private static Api sInstance;
    private final AsyncHttpClient client = new AsyncHttpClient();

    public static Api getInstance() {
        if (sInstance == null) {
            sInstance = new Api();
        }
        return sInstance;
    }

    public void get(String method, TextHttpResponseHandler handler) {
        client.get(API_HOST + method, handler);
    }

    public void post(String method, Context context, JSONObject jsonObject, TextHttpResponseHandler handler) {
        StringEntity entity = null;
        try {
            entity = new StringEntity(jsonObject.toString());
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        client.post(context, API_HOST + method, entity, "application/json", handler);
    }
}
