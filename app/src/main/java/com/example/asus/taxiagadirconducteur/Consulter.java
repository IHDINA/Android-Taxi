package com.example.asus.taxiagadirconducteur;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import cz.msebera.android.httpclient.Header;

public class Consulter extends Activity {

    private ProgressDialog prgDialog;
    private Demande demande;
    private ListView mListView;
    private double lat;
    private double log;
    private List<HashMap<String, String>> listedemandes;
    List<Demande> demandes;
    private DemandeAdapter demandeAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.liste_demande);
        lat= getIntent().getExtras().getDouble("lat");
        log= getIntent().getExtras().getDouble("log");
        mListView = (ListView) findViewById(R.id.listView);
        // Instantier Progress Dialog object
        prgDialog = new ProgressDialog(this);
        prgDialog.setCancelable(false);
        //listedemandes = new ArrayList<HashMap<String, String>>();
        demandes=new ArrayList<Demande>();

        ConsulterDemande();
    }
    public void ConsulterDemande(){
        RequestParams params = new RequestParams();
        params.put("IdTaxi",  "9");
        params.put("lat",  String.valueOf(lat));
        params.put("log",  String.valueOf(log));
        invokeWS(params);
    }

    /**
     * Method that performs RESTful webservice invocations
     *
     * @param params
     */
    public void invokeWS(RequestParams params){
        String Adrress = "http://10.9.20.37:71";
        // Show Progress Dialog
        prgDialog.setMessage("consulter demandes");
        prgDialog.show();

        // Make RESTful webservice call using AsyncHttpClient object
        AsyncHttpClient client = new AsyncHttpClient();
        client.get(Adrress+"/useraccount/localiser2/localiserTaxi",params ,new TextHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                // Hide Progress Dialog
                prgDialog.hide();
                // When Http response code is '404'
                if(statusCode == 404){
                    Toast.makeText(getApplicationContext(), "Ressource n'est pas trouvée", Toast.LENGTH_LONG).show();
                }
                // When Http response code is '500'
                else if(statusCode == 500){
                    Toast.makeText(getApplicationContext(), "Serveur ne repond pas", Toast.LENGTH_LONG).show();
                }
                // When Http response code other than 404, 500
                else{
                    Toast.makeText(getApplicationContext(), "Problème de connexion", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String response) {
                // Hide Progress Dialog
                prgDialog.hide();
                try {
                    System.out.println("inside onsucess()");
                    // JSON Object
                    JSONObject obj = new JSONObject(response);
                    JSONArray arraydemande=new JSONArray();


                    // When the JSON response has status boolean value assigned with true
                    if(obj.getBoolean("status")){
                        System.out.println("inside obj.getbool()");
                        arraydemande=obj.getJSONArray("demandes");
                        Toast.makeText(getApplicationContext(), "Liste des demandes récupéré", Toast.LENGTH_LONG).show();
                             /*for (int i = 0; i < arraydemande.length(); i++) {
                            	  demande=new Demande();
                            	  demande.jsonToDemande(arraydemande.getJSONObject(i));
                            	  System.out.println(demande.getNom()+"  "+demande.getPrenom()+" "+demande.getAdress()+"\n");
                            	  demandes.add(demande);
                              	}
                              System.out.println(demandes.get(2).getNom()+"  "+demandes.get(2).getPrenom()+" "+demandes.get(2).getAdress()+"\n");

                             listedemandes=genererDemandes(demandes);
                            ListAdapter adapter = new SimpleAdapter(Consulter.this, listedemandes, R.layout.demande_row,
                                            new String[] { "nom", "prenom", "adress" },
                                            new int[] { R.id.nom,R.id.prenom, R.id.adress });
                            mListView.setAdapter(adapter);*/

                        //afficherListeDemandes();

                        demandeAdapter= new DemandeAdapter(getApplicationContext(), R.layout.demande_row);
                        mListView.setAdapter(demandeAdapter);


                        int count =0;
                        while (count<arraydemande.length()) {
                            System.out.println("inside while");
                            demande=new Demande();
                            JSONObject j_obj=arraydemande.getJSONObject(count);
                            System.out.println("after JSONObject");
                            demande.jsonToDemande(j_obj);

                            System.out.println(demande.getNom()+"  "+demande.getPrenom()+" "+demande.getAdress()+"\n");


                            demandeAdapter.add(demande);
                            count++;
                        }

                    }
                    // Else afficher message d'erreur
                    else{
                        Toast.makeText(getApplicationContext(), obj.getString("error_msg"), Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    // TODO Auto-generated catch block
                    Toast.makeText(getApplicationContext(), "JSON Invalide !", Toast.LENGTH_LONG).show();
                    e.printStackTrace();

                }
            }

            private List<HashMap<String, String>> listedemandes;

            // Si la réponse returnée par REST  Http response a le code '200'



        });
    }
    public List<HashMap<String, String>> genererDemandes(List<Demande> demandes){
        List<HashMap<String, String>> DemandeMap=new ArrayList<HashMap<String, String>>();
        for (Demande demande : demandes) {

            HashMap<String, String> map = new HashMap<String, String>();

            map.put("nom", demande.getNom());
            map.put("prenom", demande.getPrenom());
            map.put("adress", demande.getAdress());

            DemandeMap.add(map);
        }
        return DemandeMap;

    }


   /* public void afficherListeDemandes(){
        List<Demande> demandes = genererDemandes(demandes);

        DemandeAdapter adapter = new DemandeAdapter(Consulterdemande.this, demandes);
        mListView.setAdapter(adapter);
    }*/
}


