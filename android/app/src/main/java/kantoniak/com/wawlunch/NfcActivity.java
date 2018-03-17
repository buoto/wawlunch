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
import android.widget.TextView;

import java.io.IOException;

import kantoniak.com.wawlunch.nfc.NfcUtil;

public class NfcActivity extends Activity {

    private static final String TAG = NfcActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nfc);
        writeTag(getIntent());
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

            if (NfcUtil.writeTag(42, detectedTag)) {
                Log.i(TAG, "OK");
            } else {
                Log.e(TAG, "NIEOK");
            }
        }

        if (NfcAdapter.ACTION_NDEF_DISCOVERED.equals(intent.getAction())) {
            Log.i(TAG, "ACTION_NDEF_DISCOVERED");

            Integer tableId = NfcUtil.readId(intent);
            if (tableId != null) {
                Log.i(TAG, "TABLE ID " + tableId);
            }
        }
    }
}
