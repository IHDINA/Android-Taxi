package com.example.asus.taxiagadirconducteur;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;

public class Demande {

	private Long dem_Id;
	private Date date_Enrg;
	private double Log_Rencontre;
	private double lat_Rencontre;
	private String adress;
	private String nom;
	private String prenom;
	private String tel;

	public Demande() {
		super();
	}

	public Demande(Long dem_Id,Date date_Enrg, double Log_Rencontre, double lat_Rencontre, 
			String adress, String nom, String prenom, String tel) {
		super();
		this.dem_Id = dem_Id;
		this.date_Enrg = date_Enrg;
		this.Log_Rencontre = Log_Rencontre;
		this.lat_Rencontre = lat_Rencontre;
		this.adress = adress;
		this.setTel(tel);
		this.nom=nom;
		this.prenom=prenom;
	}

	public void jsonToDemande(JSONObject jsonObject){
		try{
			this.dem_Id = jsonObject.getLong("dem_Id");
			this.Log_Rencontre = jsonObject.getDouble("Log_Rencontre");
			this.lat_Rencontre = jsonObject.getDouble("lat_Rencontre");
			this.adress = jsonObject.getString("adress");
			this.nom =jsonObject.getString("nom");
			this.prenom =jsonObject.getString("prenom");
			this.setTel(jsonObject.getString("tel"));

		}catch(JSONException e){
			e.printStackTrace();
		}
	}

	public Long getDem_Id() {
		return dem_Id;
	}

	public void setDem_Id(Long dem_Id) {
		this.dem_Id = dem_Id;
	}

	public Date getDate_Enrg() {
		return date_Enrg;
	}

	public void setDate_Enrg(Date date_Enrg) {
		this.date_Enrg = date_Enrg;
	}
	
	public double getLog_Rencontre() {
		return Log_Rencontre;
	}

	public void setLog_Rencontre(double log_Rencontre) {
		Log_Rencontre = log_Rencontre;
	}

	public double getLat_Rencontre() {
		return lat_Rencontre;
	}

	public void setLat_Rencontre(double lat_Rencontre) {
		this.lat_Rencontre = lat_Rencontre;
	}

	public String getAdress() {
		return adress;
	}

	public void setAdress(String adress) {
		this.adress = adress;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public String getPrenom() {
		return prenom;
	}
	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}
}
