package edu.unice.messenger.smart_monument;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import edu.unice.messenger.smart_monument.Model.Monument;
import edu.unice.messenger.smart_monument.NFCHelper.TagReaded;
import edu.unice.messenger.smart_monument.Service.RestClient;
import edu.unice.messenger.smart_monument.Service.ServerConfig;

public class NouveauMonumentActivity extends AppCompatActivity {

    private Button btnSauvegarder;
    private Button btnRetour;
    private TextView edtIdMonument;
    private TextView edtTitreMonument;
    private TextView edtDescriptionMonument;
    private TextView edtImageLinkMonument;
    List<Monument> monuments = new ArrayList<Monument>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nouveau_monument);

        edtIdMonument = (TextView) findViewById(R.id.idIdMonument);
        edtTitreMonument = (TextView) findViewById(R.id.idTitre);
        edtDescriptionMonument = (TextView) findViewById(R.id.idTitre);
        edtImageLinkMonument = (TextView) findViewById(R.id.idImageLink);

        btnRetour = (Button) findViewById(R.id.btnReour);
        btnRetour.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // Launching the HistoriqueVisitesActivity
                Intent intent = new Intent(NouveauMonumentActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });


        btnSauvegarder = (Button) findViewById(R.id.btnSauvegarder);
        btnSauvegarder.setOnClickListener(new View.OnClickListener() {

            /* TODO : méthode de sauvegrde*/
            @Override
            public void onClick(View v) {
                // Launching the HistoriqueVisitesActivity
                Intent intent = new Intent(NouveauMonumentActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        // Lecture des informations sur le monument:

        JsonArrayRequest request = new RestClient().createJsonArrayRequestWithHeaders(Request.Method.GET, ServerConfig.URL_GET_MONUMENTS, null, new Response.Listener<JSONArray>() {

            public void onResponse(JSONArray  response) {
                String tagReaded = new TagReaded().getTagUID();
                try {
                    for(int i=0; i<response.length(); i++){
                        // Get current json object
                        JSONObject message = response.getJSONObject(i);
                        Monument m = new Monument(message.getString("id"),message.getString("titre"),message.getString("description"),message.getString("image"));
                        monuments.add(m);
                        if (m.getId().equals(tagReaded)) {
                            edtIdMonument.setText(m.getId());
                            edtTitreMonument.setText(m.getTitre());
                            edtDescriptionMonument.setText(m.getDescription());
                            edtImageLinkMonument.setText(m.getImage());
                        }
                    }

                } catch (JSONException e) {
                    // JSON error
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(), "Erreur de décodage des monuments", Toast.LENGTH_LONG).show();
                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(),
                        "Erreur de réception des messages: "+error.getMessage(), Toast.LENGTH_LONG).show();
            }
        }, null);


    }
}
