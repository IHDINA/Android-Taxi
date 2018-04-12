package com.example.asus.taxiagadirconducteur;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;

import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class LoginActivity extends Activity {
    // Progress Dialog Object
    ProgressDialog prgDialog;

    // Error Msg TextView Object
    TextView errorMsg;

    // Email Edit View Object
    EditText emailET;
    TextView sign;

    // Password Edit View Object
    EditText pwdET;

    int IdTaxi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Trouver Msg d'Error Text View control by ID
        //errorMsg = (TextView)findViewById(R.id.login_error);

        // Trouver Email Edit View control by ID
        emailET = (EditText)findViewById(R.id.username);
        emailET.setCompoundDrawablesWithIntrinsicBounds(R.mipmap.mails, 0, 0, 0);

        // Trouver Password Edit View control by ID
        pwdET = (EditText)findViewById(R.id.password);

        // Instantier Progress Dialog object
        prgDialog = new ProgressDialog(this);

        // Modifier Text Progress Dialog
        prgDialog.setMessage("Veuillez Patientez...");

        // Modifier l'option Cancelable vers False
        prgDialog.setCancelable(false);
    }
    /**
     * Method gets triggered si Login button est clickée
     *
     * @param view
     */
    public void loginUser(View view){
        // Get Email Edit View Value
        String email = emailET.getText().toString();

        // Get Password Edit View Value
        String password = pwdET.getText().toString();

        // Instantiate Http Request Param Object
        RequestParams params = new RequestParams();

        // When Email Edit View and Password Edit View have values other than Null
        if(Utility.isNotNull(email) && Utility.isNotNull(password)){

            // When Email entered is Valid
            if(Utility.validate(email)){
                // Put Http parameter username with value of Email Edit View control
                params.put("username", email);

                // Put Http parameter password with value of Password Edit Value control
                params.put("password", password);

                // Invoke RESTful Web Service with Http parameters
                invokeWS(params);

            }
            // When Email is invalid
            else{
                Toast.makeText(getApplicationContext(), "Veuillez entrer un Email valide", Toast.LENGTH_LONG).show();
            }
        } else{
            Toast.makeText(getApplicationContext(), "Veuillez remplir tous les champs", Toast.LENGTH_LONG).show();
        }

    }

    /**
     * Method that performs RESTful webservice invocations
     *
     * @param params
     */
    public void invokeWS(RequestParams params){
        String Adrress = "http://10.9.20.37:71";

        // Show Progress Dialog
        prgDialog.show();

        // Make RESTful webservice call using AsyncHttpClient object
        AsyncHttpClient client = new AsyncHttpClient();
        client.get(Adrress+"/useraccount/login2/dologin",params ,new TextHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, String content, Throwable error) {
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
                    System.out.println("Err : -"+statusCode+"-"+content+"-");
                }
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String response) {
                prgDialog.hide();

                try {
                    // JSON Object
                    JSONObject obj = new JSONObject(response);

                    // When the JSON response has status boolean value assigned with true
                    if(obj.getBoolean("status")){
                        Toast.makeText(getApplicationContext(), "Connecté ...", Toast.LENGTH_LONG).show();
                        IdTaxi = obj.getInt("Id");
                        // aller vers Hole screen
                        navigatetoHomeActivity(IdTaxi);
                    }
                    // Else afficher message d'erreur
                    else{
                        //errorMsg.setText(obj.getString("error_msg"));
                        Toast.makeText(getApplicationContext(), obj.getString("error_msg"), Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    // TODO Auto-generated catch block
                    Toast.makeText(getApplicationContext(), "JSON invalide!", Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                }
            }
            // Si la réponse returnée par REST  Http response a le code '200'
            // Si la réponse returnée par REST  Http response est différent du code '200'
        });
    }

    /**
     * Method which navigates from Login Activity to Home Activity
     */
    public void navigatetoHomeActivity(int Id){
        Intent homeIntent = new Intent(getApplicationContext(),MapsActivity.class);

        homeIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        homeIntent.putExtra("IdTaxi", Id);

        startActivity(homeIntent);
         /*FragmentManager manager = getFragmentManager();
       FragmentTransaction transaction = manager.beginTransaction();
        MapsActivity ma=new MapsActivity();
        Bundle b=new Bundle();
        b.putInt("IdTaxi", Id);
        ma.setArguments(b);
        transaction.add(R.id.fl,ma);
        transaction.commit();*/
    }
    public void about(View v){
        Intent i = new Intent(getApplicationContext(),about.class);

        startActivity(i);
    }


}
