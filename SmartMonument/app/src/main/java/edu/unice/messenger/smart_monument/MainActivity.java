package edu.unice.messenger.smart_monument;

import android.app.Activity;
import android.content.Intent;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import edu.unice.messenger.smart_monument.NFCHelper.Utils;

public class MainActivity extends Activity {

    private String tagUID;
    private Tag tag;

    private Button btnHistoriqueVisites;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnHistoriqueVisites = (Button) findViewById(R.id.btnHistoriqueVisites);
        btnHistoriqueVisites.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // Launching the HistoriqueVisitesActivity
                Intent intent = new Intent(MainActivity.this, HistoriqueVisitesActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }


    //Méthode invoquée lors de la détection d'un tag/périphérique NFC
    @Override
    public void onNewIntent(Intent intent) {
        //Détection d'un périphérique NFC (tag ou autre)
        if ((intent.getAction() != null) &&
                ((NfcAdapter.ACTION_TAG_DISCOVERED.equals(intent.getAction()) ||
                        NfcAdapter.ACTION_NDEF_DISCOVERED.equals(intent.getAction()) ||
                        NfcAdapter.ACTION_TECH_DISCOVERED.equals(intent.getAction())))) {

            tag = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
            byte[] id = tag.getId();
            tagUID = byteArrayToHex(id).toUpperCase();
            Utils utils = new Utils();
            utils.setTagUID(tagUID);
        }
    }


    public static String byteArrayToHex(byte[] a) {
        StringBuilder sb = new StringBuilder(a.length * 2);
        for (byte b : a)
            sb.append(String.format("%02x", b));
        return sb.toString();
    }

}
