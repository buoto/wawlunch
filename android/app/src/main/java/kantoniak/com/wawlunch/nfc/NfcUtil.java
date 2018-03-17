package kantoniak.com.wawlunch.nfc;

import android.content.Intent;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.nfc.tech.Ndef;
import android.nfc.tech.NdefFormatable;
import android.os.Parcelable;
import android.util.Log;

import java.io.IOException;

public class NfcUtil {

    private static final String TAG = NfcUtil.class.getSimpleName();
    public static final String TAG_MIME = "application/com.kantoniak.wawlunch";

    public static Integer readId(Intent intent) {
        Integer result = null;

        Parcelable[] rawMsgs = intent.getParcelableArrayExtra(NfcAdapter.EXTRA_NDEF_MESSAGES);

        if (rawMsgs == null || rawMsgs.length != 1) {
            return null;
        }

        NdefMessage message = (NdefMessage) rawMsgs[0];
        return Integer.parseInt(new String(message.getRecords()[0].getPayload()));
    }

    public static boolean writeTag(int tableId, Tag tag) {
        NdefRecord record = NdefRecord.createMime(TAG_MIME, Integer.toString(tableId).getBytes());
        NdefMessage message = new NdefMessage(new NdefRecord[] { record });
        return NfcUtil.writeTag(message, tag);
    }

    private static boolean writeTag(NdefMessage message, Tag tag) {
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
