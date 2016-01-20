package com.ei3info.gsun;
public class Temps {
	public int jour;
	public int mois;
	public double heure;
	
	public Temps(int jour, int mois) {
		super();
		this.jour = jour;
		this.mois = mois;
		this.heure = 0;
	}

	public int getJour() {
		return jour;
	}

	public void setJour(int jour) {
		this.jour = jour;
	}

	public int getMois() {
		return mois;
	}

	public void setMois(int mois) {
		this.mois = mois;
	}

	public double getHeure() {
		return heure;
	}

	public void setHeure(double heure) {
		this.heure = heure;
	}
	
	public int getQuantiemeAnnee(){
		int jour_entier = 0;
		if (this.mois == 2 && this.jour==29) jour_entier = 60;
		else{
		float jour_decimal = (float) ((this.mois - 1)*365.25/12 + this.jour);
		// round renvoie la partie entiere la plus proche
		jour_entier = Math.round(jour_decimal);
		}
		return jour_entier;
	}
	
	public double getDeclinaisonSolaire(){
		double arg = 0.986 * (this.getQuantiemeAnnee() + 284) * Math.PI /180;
		double delta = 23.45 * Math.sin(arg);
		return delta;
	}
	
	
	
	
	
	
}
