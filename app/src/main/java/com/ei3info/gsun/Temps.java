package com.ei3info.gsun;

/**
 * class referring to the exact date (day, month and hour)
 */
public class Temps {
	public int jour;//day
	public int mois;//month
	public double heure;//hour

	/**
	 * constructor with day and month parameters, the heure parameter its initialise in 0.
	 * Also calls the super constructor
	 * @param jour the day
	 * @param mois the month
	 */
	public Temps(int jour, int mois) {
		super();
		this.jour = jour;
		this.mois = mois;
		this.heure = 0;
	}

	/**
	 * Day's Getter
	 * @return day
	 */
	public int getJour() {
		return jour;
	}

	/**
	 * Day's Setter
	 * @param jour the day
	 */
	public void setJour(int jour) {
		this.jour = jour;
	}

	/**
	 * Month's Getter
	 * @return month
	 */
	public int getMois() {
		return mois;
	}

	/**
	 * Month's Setter
	 * @param mois the month
	 */
	public void setMois(int mois) {
		this.mois = mois;
	}

	/**
	 * Hour's Getter
	 * @return hour
	 */
	public double getHeure() {
		return heure;
	}

	/**
	 * Hour's Setter
	 * @param heure the hour
	 */
	public void setHeure(double heure) {
		this.heure = heure;
	}

	/**
	 * Returns the number of days between the 1st January and the current date
	 * @return Quantieme
	 */
	public int getQuantiemeAnnee(){
		int jour_entier = 0;
		//if we are the 29th February, the month has only 29 days and the number of days between the 1st January and the 29th February is 60
		if (this.mois == 2 && this.jour==29) jour_entier = 60;
		//else we have to count the number of days
		else{
		float jour_decimal = (float) ((this.mois - 1)*365.25/12 + this.jour);
		// round returns the nearest integer portion
		jour_entier = Math.round(jour_decimal);
		}
		return jour_entier;
	}

	/**
	 * this method reterns the solar angle in the sky in radian
	 * @return Solar Angle
	 */
	public double getDeclinaisonSolaire(){
		double arg = 0.986 * (this.getQuantiemeAnnee() + 284) * Math.PI /180;
		double delta = 23.45 * Math.sin(arg);
		return delta;
	}


}
