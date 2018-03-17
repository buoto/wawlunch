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

            NdefRecord record = NdefRecord.createMime("application/com.kantoniak.wawlunch", "42".getBytes());
            NdefMessage message = new NdefMessage(new NdefRecord[] { record });

            if (NfcActivity.writeTag(message, detectedTag)) {
                Log.i(TAG, "OK");
            } else {
                Log.e(TAG, "NIEOK");
            }
        }

        if (NfcAdapter.ACTION_NDEF_DISCOVERED.equals(intent.getAction())) {
            Log.i(TAG, "DISCOVERED");

            Parcelable[] rawMsgs = intent.getParcelableArrayExtra(NfcAdapter.EXTRA_NDEF_MESSAGES);
            Log.i(TAG, "RAW_MSGS " + (rawMsgs == null ? 0 : 1));

            if (rawMsgs == null) {
                return;
            }

            NdefMessage[] msgs = new NdefMessage[rawMsgs.length];
            for (int i = 0; i < rawMsgs.length; i++) {
                msgs[i] = (NdefMessage) rawMsgs[i];
                for (NdefRecord rec : msgs[i].getRecords()) {
                    Log.i(TAG, "PAYLOAD " + new String(rec.getPayload()));
                }
            }
        }
    }

    public static boolean writeTag(NdefMessage message, Tag tag) {
        int size = message.toByteArray().length;
        try {
            Ndef ndef = Ndef.get(tag);
            if (ndef != null) {
                ndef.connect();
                if (!ndef.isWritable()) {
                    return false;
                }
                if (ndef.getMaxSize() < size) {
                    return false;
                }
                ndef.writeNdefMessage(message);
                return true;
            } else {
                NdefFormatable format = NdefFormatable.get(tag);
                if (format != null) {
                    try {
                        format.connect();
                        format.format(message);
                        return true;
                    } catch (IOException e) {
                        return false;
                    }
                } else {
                    return false;
                }
            }
        } catch (Exception e) {
            return false;
        }
    }
}
