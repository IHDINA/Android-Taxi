package com.example.asus.taxiagadirconducteur;

import java.util.Date;

public class Taxi {
	private Long taxi_Id;
	private String num_Taxi;
	private String num_Serie;
	private String num_Voiture;
	private String modele;
	private int mode_Status;
	private Date date_Enrg;
	private Date date_Update;
	private String licence;
	private String permis;
	private double lat;
	private double log;
	private int note;
	
	public Taxi() {
		super();
	}
	
	public Taxi(Long taxi_Id, String num_Taxi, String num_Serie, String num_Voiture, String modele, int mode_Status,
			Date date_Enrg, Date date_Update, String licence, String permis, double lat, double log, int note) {
		
		this.taxi_Id = taxi_Id;
		this.num_Taxi = num_Taxi;
		this.num_Serie = num_Serie;
		this.num_Voiture = num_Voiture;
		this.modele = modele;
		this.mode_Status = mode_Status;
		this.date_Enrg = date_Enrg;
		this.date_Update = date_Update;
		this.licence = licence;
		this.permis = permis;
		this.lat = lat;
		this.log = log;
		this.note = note;
	}
	

	public Long getTaxi_Id() {
		return taxi_Id;
	}
	public void setTaxi_Id(Long taxi_Id) {
		this.taxi_Id = taxi_Id;
	}
	public String getNum_Taxi() {
		return num_Taxi;
	}
	public void setNum_Taxi(String num_Taxi) {
		this.num_Taxi = num_Taxi;
	}
	public String getNum_Serie() {
		return num_Serie;
	}
	public void setNum_Serie(String num_Serie) {
		this.num_Serie = num_Serie;
	}
	public String getNum_Voiture() {
		return num_Voiture;
	}
	public void setNum_Voiture(String num_Voiture) {
		this.num_Voiture = num_Voiture;
	}
	public String getModele() {
		return modele;
	}
	public void setModele(String modele) {
		this.modele = modele;
	}
	public int getMode_Status() {
		return mode_Status;
	}
	public void setMode_Status(int mode_Status) {
		this.mode_Status = mode_Status;
	}
	public Date getDate_Enrg() {
		return date_Enrg;
	}
	public void setDate_Enrg(Date date_Enrg) {
		this.date_Enrg = date_Enrg;
	}
	public Date getDate_Update() {
		return date_Update;
	}
	public void setDate_Update(Date date_Update) {
		this.date_Update = date_Update;
	}
	public String getLicence() {
		return licence;
	}
	public void setLicence(String licence) {
		this.licence = licence;
	}
	public String getPermis() {
		return permis;
	}
	public void setPermis(String permis) {
		this.permis = permis;
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
	public int getNote() {
		return note;
	}
	public void setNote(int note) {
		this.note = note;
	}
}
