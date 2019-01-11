package edu.unice.messenger.smart_monument;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import edu.unice.messenger.smart_monument.Database.SQLiteHandler;
import edu.unice.messenger.smart_monument.Model.CustomListAdapter;
import edu.unice.messenger.smart_monument.Model.Monument;

public class HistoriqueVisitesActivity extends AppCompatActivity {

    private Button btnRetour;
    private SQLiteHandler db;
    private CustomListAdapter adapter;
    private List<Monument> monumentList = new ArrayList<Monument>();
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_historique_visites);

        listView = (ListView) findViewById(R.id.list);


        // SQLite database handler
        db = new SQLiteHandler(getApplicationContext());

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

        monumentList = db.getMonuments();


        adapter = new CustomListAdapter(this, monumentList);
        listView.setAdapter(adapter);

        // notifying list adapter about data changes
        // so that it renders the list view with updated data
        adapter.notifyDataSetChanged();

    }
}
