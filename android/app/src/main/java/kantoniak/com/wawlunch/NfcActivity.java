package kantoniak.com.wawlunch;

import android.app.Activity;
import android.content.Intent;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.nfc.tech.Ndef;
import android.nfc.tech.NdefFormatable;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.facebook.AccessToken;
import com.facebook.Profile;
import com.loopj.android.http.TextHttpResponseHandler;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import cz.msebera.android.httpclient.Header;
import kantoniak.com.wawlunch.data.Api;
import kantoniak.com.wawlunch.nfc.NfcUtil;

public class NfcActivity extends Activity {

    private static final String TAG = NfcActivity.class.getSimpleName();
    private static int toWrite = 1;

    private View mNotLoggedInView;
    private View mProgressView;
    private View mDoneView;
    private ProgressBar mProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nfc);

        mNotLoggedInView = findViewById(R.id.nfc_not_logged_in);
        mProgressView = findViewById(R.id.nfc_getting_order);
        mDoneView = findViewById(R.id.nfc_done);
        mProgressBar = findViewById(R.id.nfc_progressbar);

        writeTag(getIntent());
    }

    private void showScreen(int i) {
        mNotLoggedInView.setVisibility(View.INVISIBLE);
        mProgressView.setVisibility(View.INVISIBLE);
        mDoneView.setVisibility(View.INVISIBLE);
        mProgressBar.setVisibility(View.INVISIBLE);

        if (i == 0) {
            mNotLoggedInView.setVisibility(View.VISIBLE);
        }

        if (i == 1) {
            mProgressView.setVisibility(View.VISIBLE);
            mProgressBar.setVisibility(View.VISIBLE);
        }

        if (i == 2) {
            mDoneView.setVisibility(View.VISIBLE);
        }
    }

    private void handleTable(int table) {
        AccessToken token = AccessToken.getCurrentAccessToken();

        if (token == null) {
            showScreen(0);
        }

        showScreen(1);

        try {
            JSONObject jsonParams = new JSONObject();
            jsonParams.put("facebookId", Profile.getCurrentProfile().getId());
            jsonParams.put("tableId", table);

            Api.getInstance().post(Api.Method.CHECK_IN, this, jsonParams, new TextHttpResponseHandler() {
                @Override
                public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                    Log.e(TAG, "FAIL HTTP " + statusCode, throwable);
                }

                @Override
                public void onSuccess(int statusCode, Header[] headers, String responseString) {
                    showScreen(2);
                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        writeTag(intent);

    }

    private void writeTag(Intent intent) {
        Log.i(TAG, "INTENT " + (intent == null ? 0 : 1));

        if (intent == null) {
            return;
        }

        Log.i(TAG, "ACTION " + intent.getAction());

        if (NfcAdapter.ACTION_TECH_DISCOVERED.equals(intent.getAction())) {
            Log.i(TAG, "DISCOVERED");

            Parcelable[] rawMsgs = intent.getParcelableArrayExtra(NfcAdapter.EXTRA_NDEF_MESSAGES);
            Log.i(TAG, "RAW_MSGS " + (rawMsgs == null ? 0 : 1));

            Tag detectedTag = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);

            if (NfcUtil.writeTag(NfcActivity.toWrite, detectedTag)) {
                Log.i(TAG, "OK");
                NfcActivity.toWrite++;
            } else {
                Log.e(TAG, "NIEOK");
            }
        }

        if (NfcAdapter.ACTION_NDEF_DISCOVERED.equals(intent.getAction())) {
            Log.i(TAG, "ACTION_NDEF_DISCOVERED");

            Integer tableId = NfcUtil.readId(intent);
            if (tableId != null) {
                Log.i(TAG, "TABLE ID " + tableId);
                handleTable(tableId);
            }
        }
    }
}
