package edu.unice.messenger.smart_monument;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import edu.unice.messenger.smart_monument.Database.SQLiteHandler;
import edu.unice.messenger.smart_monument.Model.Monument;
import edu.unice.messenger.smart_monument.NFCHelper.Utils;
import edu.unice.messenger.smart_monument.Service.RestClient;
import edu.unice.messenger.smart_monument.Service.ServerConfig;

public class NouveauMonumentActivity extends AppCompatActivity {


    private SQLiteHandler db;
    private Button btnSauvegarder;
    private Button btnRetour;
    private TextView edtIdMonument;
    private TextView edtTitreMonument;
    private TextView edtDescriptionMonument;
    private TextView edtImageLinkMonument;
    private ImageView iv;
    List<Monument> monuments = new ArrayList<Monument>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nouveau_monument);

        // SQLite database handler
        db = new SQLiteHandler(getApplicationContext());

        edtIdMonument = (TextView) findViewById(R.id.idIdMonument);
        edtTitreMonument = (TextView) findViewById(R.id.idTitre);
        edtDescriptionMonument = (TextView) findViewById(R.id.idDescription);
        edtImageLinkMonument = (TextView) findViewById(R.id.idImageLink);
        iv = (ImageView) findViewById(R.id.imageView1);

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

            @Override
            public void onClick(View v) {
                Monument m = Utils.monument;
                db.addMonument(m);

                Toast.makeText(getApplicationContext(),
                        "Monument bien sauvegardé", Toast.LENGTH_LONG).show();
                // Launching the HistoriqueVisitesActivity
                Intent intent = new Intent(NouveauMonumentActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        // Lecture des informations sur le monument:

        JsonArrayRequest request = new RestClient().createJsonArrayRequestWithHeaders(Request.Method.GET, ServerConfig.URL_GET_MONUMENTS, null, new Response.Listener<JSONArray>() {

            public void onResponse(JSONArray  response) {
                String tagReaded = Utils.tagUID;
                try {
                    for(int i=0; i<response.length(); i++){
                        // Get current json object
                        JSONObject message = response.getJSONObject(i);
                        Monument m = new Monument(message.getString("id"),message.getString("titre"),message.getString("description"),message.getString("image"));
                        monuments.add(m);

                        if (m.getId().equals(tagReaded)) {
                            edtIdMonument.setText("ID: "+m.getId());
                            edtTitreMonument.setText("Titre: "+m.getTitre());
                            edtDescriptionMonument.setText("Description: "+m.getDescription());
                            edtImageLinkMonument.setText("Lien vers l'image: "+m.getImage());
                            new DownLoadImageTask(iv).execute(m.getImage());
                            Utils.monument = m;
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
                        "Erreur de réception des monuments: "+error.getMessage(), Toast.LENGTH_LONG).show();
            }
        });

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(request, "GET Monument Datas");
    }
}

 class DownLoadImageTask extends AsyncTask<String,Void,Bitmap> {
    ImageView imageView;

    public DownLoadImageTask(ImageView imageView){
        this.imageView = imageView;
    }

    /*
        doInBackground(Params... params)
            Override this method to perform a computation on a background thread.
     */
    protected Bitmap doInBackground(String...urls){
        String urlOfImage = urls[0];
        Bitmap logo = null;
        try{
            InputStream is = new URL(urlOfImage).openStream();
                /*
                    decodeStream(InputStream is)
                        Decode an input stream into a bitmap.
                 */
            logo = BitmapFactory.decodeStream(is);
        }catch(Exception e){ // Catch the download exception
            e.printStackTrace();
        }
        return logo;
    }

    /*
        onPostExecute(Result result)
            Runs on the UI thread after doInBackground(Params...).
     */
    protected void onPostExecute(Bitmap result){
        imageView.setImageBitmap(result);
    }
}
