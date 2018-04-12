package com.example.asus.taxiagadirconducteur;

import org.json.JSONException;
import org.json.JSONObject;

public class Client{
	private Long cl_Id;
	private String nom;
	private String preNom;
	private String tel;
	private double lat;
	private double log;
	
	public Client(){
		super();
	}

	public Client(String nom, String preNom, String tel, double lat, double log) {
		super();
		this.nom = nom;
		this.preNom = preNom;
		this.tel = tel;
		this.setLat(lat);
		this.setLog(log);
	}
	
	public Client jsonToClient(JSONObject json){
		Client client=new Client();
		try{
			client.cl_Id = json.getLong("cl_id");
	        client.nom = json.getString("nom");
	        client.preNom = json.getString("prenom");
	        client.tel = json.getString("tel");
	        client.lat = json.getDouble("lat");
	        client.log = json.getDouble("log");

		}catch(JSONException e){
			e.printStackTrace();
		}
        
		return null;
	}

	public Long getCl_Id() {
		return cl_Id;
	}

	public void setCl_Id(Long cl_Id) {
		this.cl_Id = cl_Id;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public String getPreNom() {
		return preNom;
	}

	public void setPreNom(String preNom) {
		this.preNom = preNom;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public double getLat() {
		return lat;
	}

	public void setLat(double lat) {
		this.lat = lat;
	}

	public double getLog() {
		return log;
	}

	public void setLog(double log) {
		this.log = log;
	}
}
