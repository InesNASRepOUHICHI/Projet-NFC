package edu.unice.messenger.smart_monument;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class HistoriqueVisitesActivity extends AppCompatActivity {

    private Button btnRetour;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_historique_visites);

        btnRetour = (Button) findViewById(R.id.btnReourH);
        btnRetour.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // Launching the HistoriqueVisitesActivity
                Intent intent = new Intent(HistoriqueVisitesActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}
