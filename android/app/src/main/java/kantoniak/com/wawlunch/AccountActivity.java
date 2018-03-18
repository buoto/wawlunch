package kantoniak.com.wawlunch;

import android.os.Bundle;
import android.app.Activity;

public class AccountActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);
        super.setTitle("Konto");
    }

}
