package kantoniak.com.wawlunch.data;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.TextHttpResponseHandler;

public class Api {

    public interface Method {
        String PLACES = "places/";
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
}
