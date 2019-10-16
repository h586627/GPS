package no.hvl.dat100ptc.oppgave2;

import no.hvl.dat100ptc.TODO;
import no.hvl.dat100ptc.oppgave1.GPSPoint;

public class GPSData {

	private GPSPoint[] gpspoints;
	protected int antall = 0;

	public GPSData(int n) {
		antall = 0;
		gpspoints = new GPSPoint[n]; // Først satt vi antall til 0. Videre lagde vi tabellen med element n. 
	
	}

	public GPSPoint[] getGPSPoints() {
		return this.gpspoints; 
	}
	
	protected boolean insertGPS(GPSPoint gpspoint) { 

		boolean inserted = false;  // Lahgde denne først. Denne er fals fordi vi ikke har sjekket om det er plass. 

		if ( antall < gpspoints.length) { // Hvis antall er mindre, så er det plass, og derfor blir den true her. 
			inserted = true;
			gpspoints[antall] = gpspoint; // Her settes verdiene til gpspoint inn i tabellen. 
		}
		antall ++; // for å komme gjennom hele tabellen. 
		
		return inserted; // returnerer om det er plass eller ikke, her er det true. 
	}

	public boolean insert(String time, String latitude, String longitude, String elevation) {

		GPSPoint gpspoint;

		gpspoint = GPSDataConverter.convert(time, latitude, longitude, elevation); //Kaller på metoden fra GPSDataConverter for å konvertere variablene. 
		
		return insertGPS(gpspoint);
		
	}

	public void print() {

		System.out.println("====== Konvertert GPS Data - START ======");

		for (int i = 0; i < gpspoints.length; i++) { // må lage for-løkke for å gå gjennom hele tabellen. 
			System.out.println(gpspoints[i].getTime() + " ("+gpspoints[i].getLatitude() + " ("+ gpspoints[i].getLongitude()+ " ( "+ gpspoints[i].getElevation());
		} // [i er en tellevariabel som står for elementene i tabellen, og med get henter infomrasjon for hvert av elementene i tabellen.
		
		System.out.println("====== Konvertert GPS Data - SLUTT ======");

	}
}


// time = tid
//latitude = breddegrad
//longitude = lengdegrad
//elevation = høydegrad