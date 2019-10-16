package no.hvl.dat100ptc.oppgave2;

import no.hvl.dat100ptc.TODO;
import no.hvl.dat100ptc.oppgave1.GPSPoint;

public class GPSDataConverter {

	// konverter tidsinformasjon i gps data punkt til antall sekunder fra midnatt
	// dvs. ignorer information om dato og omregn tidspunkt til sekunder
	// Eksempel - tidsinformasjon (som String): 2017-08-13T08:52:26.000Z
    // skal omregnes til sekunder (som int): 8 * 60 * 60 + 52 * 60 + 26 
	
	private static int TIME_STARTINDEX = 11; // startindex for tidspunkt i timestr

	public static int toSeconds(String timestr) {
		
		int secs;
		int hr = Integer.parseInt(timestr.substring(11, 13));  //Gjorde om hr, min, sec til tall med parseInt. Bruker substring for å hente deler av String i parameteren. 
		int min = Integer.parseInt(timestr.substring(14, 16));
		int sec = Integer.parseInt(timestr.substring(17, 19));
		
		secs = hr * 3600 + (min*60) + sec; // gjør om til sekunder, returnerer verdien vi får slik at tid blir i sekunder. 
		
		return secs;
		
	}

	public static GPSPoint convert(String timeStr, String latitudeStr, String longitudeStr, String elevationStr) {

		int time = toSeconds(timeStr);
		double latitude = Double.parseDouble(latitudeStr); // Konverterer fra String til tall med parse. 
		double longitude = Double.parseDouble(longitudeStr); 
		double elevation = Double.parseDouble(elevationStr);
		
		GPSPoint gpspoint = new GPSPoint (time, latitude, longitude, elevation); //Lager ny tabell med de elementene som har blitt omgjort til tall. 
		
		return gpspoint; // returnerer den nye tabellen som da er GPSPoint. 

		
	    
	}
	
}
