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
import android.widget.TextView;

import com.facebook.AccessToken;
import com.facebook.Profile;

import java.io.IOException;

import kantoniak.com.wawlunch.nfc.NfcUtil;

public class NfcActivity extends Activity {

    private static final String TAG = NfcActivity.class.getSimpleName();
    private static int toWrite = 1;

    private View mNotLoggedInView;
    private View mProgressView;
    private View mDoneView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nfc);

        mNotLoggedInView = findViewById(R.id.nfc_not_logged_in);
        mProgressView = findViewById(R.id.nfc_getting_order);
        mDoneView = findViewById(R.id.nfc_done);

        writeTag(getIntent());
    }

    private void showScreen(int i) {
        mNotLoggedInView.setVisibility(View.GONE);
        mProgressView.setVisibility(View.GONE);
        mDoneView.setVisibility(View.GONE);

        switch (i) {
            case 0: mNotLoggedInView.setVisibility(View.VISIBLE); return;
            case 1: mProgressView.setVisibility(View.VISIBLE); return;
            case 2: mDoneView.setVisibility(View.VISIBLE); return;
        }
    }

    private void handleTable(int table) {
        AccessToken token = AccessToken.getCurrentAccessToken();

        if (token == null) {
            showScreen(0);
        }
        showScreen(1);
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
