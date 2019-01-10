package edu.unice.messenger.smart_monument;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

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
}
