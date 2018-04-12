package com.example.asus.taxiagadirconducteur;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;

import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;


public class DemandeAdapter extends ArrayAdapter<Demande> {
    List<Demande> list =new ArrayList<Demande>();
    private Context context;
    public boolean accept=true ;
    DemandeViewHolder viewHolder;
    public DemandeAdapter(Context context, int resource) {
        super(context, resource);
    }

    private Context mContext;
    public void add(Demande object) {
        super.add(object);


        list.add(object);

    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View view = convertView;

        final Demande d1=new Demande(this.getItem(position).getDem_Id(),this.getItem(position).getDate_Enrg(),this.getItem(position).getLog_Rencontre(),this.getItem(position).getLat_Rencontre(),
                this.getItem(position).getAdress(),this.getItem(position).getNom(),this.getItem(position).getPrenom(),this.getItem(position).getTel());
        //Récupérer ID de la demande
        final Long id=this.getItem(position).getDem_Id();
        final double lat=this.getItem(position).getLat_Rencontre();
        final double log=this.getItem(position).getLog_Rencontre();
        final String  nom=this.getItem(position).getNom();
        final String prenom=this.getItem(position).getPrenom();





        if(view == null){
            LayoutInflater layoutInflater = (LayoutInflater) this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            //chargement du fchier XML
            view = layoutInflater.inflate(R.layout.demande_row,parent, false);

            //remplir l'enveloppe de données (DemandeViewHolder)

            viewHolder = new DemandeViewHolder();
            viewHolder.nom = (TextView) view.findViewById(R.id.nom);
            viewHolder.prenom = (TextView) view.findViewById(R.id.prenom);
            viewHolder.adress = (TextView) view.findViewById(R.id.adress);
            viewHolder.userimage = (ImageView) view.findViewById(R.id.userimage);
            viewHolder.btnrefuser = (ImageButton) view.findViewById(R.id.btnrefuser);
            viewHolder.btnaccepter = (ImageButton) view.findViewById(R.id.btnaccepter);

            viewHolder.btnaccepter.setOnClickListener(new View.OnClickListener() {



                @Override
                public void onClick(View arg0) {
                    accept=true;
                    Log.d("tag","btn accepter");
                    ReponderDemande(accept,id);
                    Intent intent = new Intent(getContext(),AccepterDemande.class)
                            .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.putExtra("IdTaxi",id);
                    intent.putExtra("lat",lat);
                    intent.putExtra("log",log);
                    intent.putExtra("nom",nom);
                    intent.putExtra("prenom",prenom);


                    getContext().startActivity(intent);

                }
            });
            viewHolder.btnrefuser.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View arg0) {
                    accept=false;
                    Log.d("tag","btn resuser");
                    ReponderDemande(accept,id);

                }
            });
            //relier l'enveloppe au vue.
            view.setTag(viewHolder);
        }else{
            //récupérer l'enveloppe depuis une vue déja créée
            viewHolder = (DemandeViewHolder) view.getTag();
        }
        //récupérer les données contenues dans la position "position«
        Demande demande = (Demande) this.getItem(position);
        //Affecter les données aux vues d'un item
        viewHolder.nom.setText(demande.getNom());
        viewHolder.prenom.setText(demande.getPrenom());
        viewHolder.adress.setText(demande.getAdress());

        return view;
    }
    @Override
    public int getCount() {

        return list.size();
    }
    @Override
    public Demande getItem(int position) {

        return list.get(position);
    }
    //créer une classe pour encapsuler les donnees
    static class DemandeViewHolder{
        public TextView nom;
        public TextView prenom;
        public TextView adress;
        public ImageView userimage;
        public ImageButton btnaccepter;
        public ImageButton btnrefuser;

    }
    public void ReponderDemande(boolean a,Long id){
        RequestParams params = new RequestParams();
        params.put("IdTaxi",  "9");
        params.put("IdDemande", String.valueOf(id));
        if(a==true){
            params.put("accepte",  "1");
        }else{
            params.put("accepte",  "0");
        }

        invokeWS(params);
    }

    /**
     * Method that performs RESTful webservice invocations
     *
     * @param params
     */
    public void invokeWS(RequestParams params){
        String Adrress = "http://10.9.20.37:71";

        // Make RESTful webservice call using AsyncHttpClient object
        AsyncHttpClient client = new AsyncHttpClient();
        client.get(Adrress+"/useraccount/confirmer/confirmerDemande",params ,new TextHttpResponseHandler() {


            @Override
            public void onFailure(int statusCode, Header[] headers, String content, Throwable throwable) {
                // Hide Progress Dialog

                // When Http response code is '404'
                if(statusCode == 404){
                    System.out.println("Ressource n'est pas trouvée");
                }
                // When Http response code is '500'
                else if(statusCode == 500){
                    System.out.println("Serveur ne repond pas");
                }
                // When Http response code other than 404, 500
                else{
                    System.out.println("Problème de connexion");
                }
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String response) {
                System.out.println("Reponse récu");

            }

            // Si la réponse returnée par REST  Http response a le code '200'


            // Si la réponse returnée par REST  Http response est différent du code '200'

        });
    }


}
