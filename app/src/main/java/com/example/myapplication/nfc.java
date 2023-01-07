package com.example.myapplication;

import static android.content.ContentValues.TAG;

import android.app.PendingIntent;
import android.content.Intent;
import android.content.IntentFilter;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.nfc.tech.IsoDep;
import android.nfc.tech.MifareClassic;
import android.nfc.tech.MifareUltralight;
import android.nfc.tech.Ndef;
import android.nfc.tech.NfcA;
import android.nfc.tech.NfcB;
import android.nfc.tech.NfcF;
import android.nfc.tech.NfcV;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.math.BigInteger;

public class nfc extends AppCompatActivity {
    private String tag = "123";
    Button nfc_result_btn;
    // list of NFC technologies detected:
    private final String[][] techList = new String[][] {
            new String[] {
                    NfcA.class.getName(),
                    NfcB.class.getName(),
                    NfcF.class.getName(),
                    NfcV.class.getName(),
                    IsoDep.class.getName(),
                    MifareClassic.class.getName(),
                    MifareUltralight.class.getName(), Ndef.class.getName()
            }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nfc);
    }
    @Override
    protected void onResume() {
        super.onResume();
        Log.d(tag +"onResume", "1");
        PendingIntent pendingIntent;
        Intent intent = new Intent(this, nfc.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP );
        pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_MUTABLE | PendingIntent.FLAG_UPDATE_CURRENT);
        IntentFilter filter = new IntentFilter();
        filter.addAction(NfcAdapter.ACTION_TAG_DISCOVERED);
        filter.addAction(NfcAdapter.ACTION_NDEF_DISCOVERED);
        filter.addAction(NfcAdapter.ACTION_TECH_DISCOVERED);
        // enabling foreground dispatch for getting intent from NFC event:
        NfcAdapter nfcAdapter = NfcAdapter.getDefaultAdapter(this);
        nfcAdapter.enableForegroundDispatch(this, pendingIntent, new IntentFilter[]{filter}, this.techList);
    }
    @Override
    protected void onPause() {
        super.onPause();
        Log.d(tag + "onPause", "1");
        // disabling foreground dispatch:
        NfcAdapter nfcAdapter = NfcAdapter.getDefaultAdapter(this);
        nfcAdapter.disableForegroundDispatch(this);
    }
    @Override
    protected void onNewIntent(Intent intent) {
        Log.d(tag +"onNewIntent", "1");
        if (intent.getAction().equals(NfcAdapter.ACTION_TAG_DISCOVERED)) {
            Log.d(tag +"onNewIntent", "2");
            //mTextView.setText("NFC Tag\n" + ByteArrayToHexString(intent.getByteArrayExtra(NfcAdapter.EXTRA_ID)));
            //if(getIntent().hasExtra(NfcAdapter.EXTRA_TAG)){
            Parcelable tagN = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
            if (tagN != null) {
                Log.d(TAG, "Parcelable OK");
                NdefMessage[] msgs;
                byte[] empty = new byte[0];
                byte[] id = intent.getByteArrayExtra(NfcAdapter.EXTRA_ID);
                byte[] payload = dumpTagData(tagN).getBytes();
                NdefRecord record = new NdefRecord(NdefRecord.TNF_UNKNOWN, empty, id, payload);
                NdefMessage msg = new NdefMessage(new NdefRecord[]{record});
                msgs = new NdefMessage[]{msg};
                //Log.d(TAG, msgs[0].toString());
            } else {
                Log.d(TAG, "Parcelable NULL");
            }
            Parcelable[] messages1 = intent.getParcelableArrayExtra(NfcAdapter.EXTRA_NDEF_MESSAGES);
            if (messages1 != null) {
                Log.d(TAG, "Found " + messages1.length + " NDEF messages");
            } else {
                Log.d(TAG, "Not EXTRA_NDEF_MESSAGES"); }
            Tag tag = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
            Ndef ndef = Ndef.get(tag);
            if (ndef != null) {
                Log.d("onNewIntent:", "NfcAdapter.EXTRA_TAG");
                Parcelable[] messages = intent.getParcelableArrayExtra(NfcAdapter.EXTRA_NDEF_MESSAGES);
                if (messages != null) {
                    Log.d(TAG, "Found " + messages.length + " NDEF messages");
                }
            } else {
                Log.d(TAG, "Write to an unformatted tag not implemented");
            }
            //mTextView.setText( "NFC Tag\n" + ByteArrayToHexString(intent.getByteArrayExtra(NfcAdapter.EXTRA_TAG)));
        }
    }
    private String dumpTagData(Parcelable p) {
        StringBuilder sb = new StringBuilder();
        Tag tag = (Tag) p;
        byte[] id = tag.getId();
        //TextView txt2 = findViewById(R.id.txt2);
        String user_id = getHex(id);
        nfc_result_btn = findViewById(R.id.nfc_result_btn);
        nfc_result_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(nfc.this,nfc_information.class);
                intent.putExtra("text", user_id);
                startActivity(intent);
            }
        });
        //입력한 input값을 intent로 전달한다.
        //startActivity(intent);
        Toast.makeText(getApplicationContext(), user_id, Toast.LENGTH_SHORT).show();
        //txt2.setText(n);
        sb.append("Tag ID (hex): ").append(getHex(id)).append("\n");
        //sb.append("Tag ID (dec): ").append(getDec(id)).append("\n");
        //sb.append("ID (reversed): ").append(getReversed(id)).append("\n");
        String prefix = "android.nfc.tech.";
        sb.append("Technologies: ");
        for (String tech : tag.getTechList()) {
            sb.append(tech.substring(prefix.length()));
            sb.append(", ");
        }
        sb.delete(sb.length() - 2, sb.length());
        for (String tech : tag.getTechList()) {
//            if (tech.equals(MifareClassic.class.getName())) {
//                sb.append('\n');
//                MifareClassic mifareTag = MifareClassic.get(tag);
//                String type = "Unknown";
//                switch (mifareTag.getType()) {
//                    case MifareClassic.TYPE_CLASSIC:
//                        type = "Classic";
//                        break;
//                    case MifareClassic.TYPE_PLUS:
//                        type = "Plus";
//                        break;
//                    case MifareClassic.TYPE_PRO:
//                        type = "Pro";
//                        break;
//                }
//                sb.append("Mifare Classic type: ");
//                sb.append(type);
//                sb.append('\n');
//
//                sb.append("Mifare size: ");
//                sb.append(mifareTag.getSize() + " bytes");
//                sb.append('\n');
//
//                sb.append("Mifare sectors: ");
//                sb.append(mifareTag.getSectorCount());
//                sb.append('\n');
//
//                sb.append("Mifare blocks: ");
//                sb.append(mifareTag.getBlockCount());
//            }

            if (tech.equals(MifareUltralight.class.getName())) {
                sb.append('\n');
                MifareUltralight mifareUlTag = MifareUltralight.get(tag);
                String type = "Unknown";
                switch (mifareUlTag.getType()) {
                    case MifareUltralight.TYPE_ULTRALIGHT:
                        type = "Ultralight";
                        break;
                    case MifareUltralight.TYPE_ULTRALIGHT_C:
                        type = "Ultralight C";
                        break;
                }
                sb.append("Mifare Ultralight type: ");
                sb.append(type);
            }
        }
        //Log.d("Datos: ", sb.toString());

        //DateFormat TIME_FORMAT = SimpleDateFormat.getDateTimeInstance();
        //Date now = new Date();

        //mTextView.setText(TIME_FORMAT.format(now) + '\n' + sb.toString());
        return sb.toString();
    }
    private String getHex(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (int i = bytes.length - 1; i >= 0; --i) {
            int b = bytes[i] & 0xff;
            if (b < 0x10)
                sb.append('0');
            sb.append(Integer.toHexString(b));
            if (i > 0) {
                sb.append("");
            }
        }
        return sb.toString();
    }
//    private long getDec(byte[] bytes) {
//        long result = 0;
//        long factor = 1;
//        for (int i = 0; i < bytes.length; ++i) {
//            long value = bytes[i] & 0xffl;
//            result += value * factor;
//            factor *= 256l;
//        }
//        return result;
//    }

//    private long getReversed(byte[] bytes) {
//        long result = 0;
//        long factor = 1;
//        for (int i = bytes.length - 1; i >= 0; --i) {
//            long value = bytes[i] & 0xffl;
//            result += value * factor;
//            factor *= 256l;
//        }
//        return result;
//    }
    private String ByteArrayToHexString(byte [] inarray) {

        Log.d("ByteArrayToHexString", inarray.toString());

        int i, j, in;
        String [] hex = {"0","1","2","3","4","5","6","7","8","9","A","B","C","D","E","F"};
        String out= "";

        for(j = 0 ; j < inarray.length ; ++j)
        {
            in = (int) inarray[j] & 0xff;
            i = (in >> 4) & 0x0f;
            out += hex[i];
            i = in & 0x0f;
            out += hex[i];
        }
//CE7AEED4
//EE7BEED4
        Log.d("ByteArrayToHexString", String.format("%0" + (inarray.length * 2) + "X", new BigInteger(1,inarray)));
        return out;
    }



}