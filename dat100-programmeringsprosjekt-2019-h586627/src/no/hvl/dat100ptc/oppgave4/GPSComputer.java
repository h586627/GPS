package no.hvl.dat100ptc.oppgave4;

import no.hvl.dat100ptc.TODO;
import no.hvl.dat100ptc.oppgave1.GPSPoint;
import no.hvl.dat100ptc.oppgave2.GPSData;
import no.hvl.dat100ptc.oppgave2.GPSDataConverter;
import no.hvl.dat100ptc.oppgave2.GPSDataFileReader;
import no.hvl.dat100ptc.oppgave3.GPSUtils;

public class GPSComputer {

	private GPSPoint[] gpspoints;

	public GPSComputer(String filename) {

		GPSData gpsdata = GPSDataFileReader.readGPSFile(filename);
		gpspoints = gpsdata.getGPSPoints();

	}

	public GPSComputer(GPSPoint[] gpspoints) {
		this.gpspoints = gpspoints;
	}

	public GPSPoint[] getGPSPoints() {
		return this.gpspoints;
	}

	// beregn total distances (i meter)
	public double totalDistance() {

		double d = 0;
		for (int i = 0; i < gpspoints.length - 1; i++) {
			d += GPSUtils.distance(gpspoints[i], gpspoints[i + 1]);
		}
		return d;

	}

	// beregn totale høydemeter (i meter)
	public double totalElevation() {

		double elevation = 0;

		for (int i = 1; i < gpspoints.length; i++) {
			if (gpspoints[i].getElevation() > gpspoints[i - 1].getElevation()) {
				elevation += gpspoints[i].getElevation() - gpspoints[i - 1].getElevation();
			}
		}
		return elevation;

	}

	public int totalTime() {
		int tid = 0;

		return gpspoints[gpspoints.length - 1].getTime() - gpspoints[0].getTime();
	}

	// beregn gjennomsnitshastighets mellom hver av gps punktene

	public double[] speeds() {

		double[] speedtab = new double[gpspoints.length - 1];

		for (int i = 0; i < speedtab.length; i++) {
			speedtab[i] = GPSUtils.speed(gpspoints[i], gpspoints[i + 1]);
		}
		return speedtab;

	}

	public double maxSpeed() {

		double temp = 0;
		double maxspeed = GPSUtils.speed(gpspoints[0], gpspoints[1]);

		for (int i = 0; i < gpspoints.length - 1; i++) {
			temp = GPSUtils.speed(gpspoints[i], gpspoints[i + 1]);

			if (maxspeed < temp) {
				maxspeed = temp;
			}

		}
		return maxspeed;

	}

	public double averageSpeed() {

		double average = 0;
		double totaltime = totalTime();
		double totaldistance = totalDistance();
		average = totaldistance / totaltime;
		average = average * 3.6;

		return average;

	}

	/*
	 * bicycling, <10 mph, leisure, to work or for pleasure 4.0 bicycling, general
	 * 8.0 bicycling, 10-11.9 mph, leisure, slow, light effort 6.0 bicycling,
	 * 12-13.9 mph, leisure, moderate effort 8.0 bicycling, 14-15.9 mph, racing or
	 * leisure, fast, vigorous effort 10.0 bicycling, 16-19 mph, racing/not drafting
	 * or >19 mph drafting, very fast, racing general 12.0 bicycling, >20 mph,
	 * racing, not drafting 16.0
	 */

	// conversion factor m/s to miles per hour
	public static double MS = 2.236936;

	// beregn kcal gitt weight og tid der kjøres med en gitt hastighet
	public double kcal(double weight, int secs, double speed) {

		double kcal = 0;

		// MET: Metabolic equivalent of task angir (kcal x kg x h)
		double met = 0; 
		double speedmph = speed * MS;

		if (speedmph < 10) {
			met = 4;
		} else if (speedmph < 12 && speedmph >= 10) {
			met = 6;
		} else if (speedmph < 14 && speedmph >= 12) { // Skrev inn tabell i eclipse. 
			met = 8;
		} else if (speedmph < 16 && speedmph >= 14) {
			met = 10;
		} else if (speedmph < 20 && speedmph >= 16) {
			met = 12;
		} else {
			met = 16;
		}

		double timer = secs / 3600; // deler sekunder på 3600 for å få det i timer. 
		kcal = met * timer * weight; // formel for å finne antall kcal. 

		return kcal; // Returnerer kcal. 

	}

	public double totalKcal(double weight) {

		double totalkcal = 0;
		
		int met = 0;
		double mph;
		double time;
		
		for (int i = 0; i < gpspoints.length - 1; i++) {
			mph = GPSUtils.speed(gpspoints[i], gpspoints[i + 1]); // Kaller på metoden GPSUtils og klassen speed. Bruker [i] for å gå gjennom alt. Her er veriden i sekunder.  
			mph = mph * 3.6 * 0.62; // Gjør vwerdien om til mph

			if (mph < 10) {
				met = 4;
			} else if (mph < 12 && mph >= 10) { // Fortsatt samme tabell. 
				met = 6;
			} else if (mph < 14 && mph >= 12) {
				met = 8;
			} else if (mph < 16 && mph >= 14) {
				met = 10;
			} else if (mph < 20 && mph >= 16) {
				met = 12;
			} else {
				met = 16;
			}
			
		time = gpspoints[i+1].getTime()-gpspoints[i].getTime(); // Her finner tidsforskjellen mellom to punkt. [i] for vi skal gå gjennom ALLE punktene. 
		time = time/3600; // Gjør fra skunder til timer
		
		 totalkcal += met * weight * time; // Finner total kcal. 
		
		}
		return totalkcal; //Returnerer totalkcal. 
	

	}

	private static double WEIGHT = 80.0;

	public void displayStatistics() {
		

		System.out.println("==============================================");

		System.out.println("TotalDistance     : " + GPSUtils.formatDouble(totalDistance()/1000) + " km \n" + 
		"TotalElevation    :  " + GPSUtils.formatDouble(totalElevation()) + " m \n"  + 
		"TotalTime         :    " + GPSUtils.formatTime(totalTime()) + "\n" + 
		"MaxSpeed          : " + GPSUtils.formatDouble(maxSpeed()) + " km/t  \n" + 
		"AverageSpeed      : " + GPSUtils.formatDouble(averageSpeed()) + " km/t \n" + 
		"Energi            :   " +  GPSUtils.formatDouble(totalKcal(WEIGHT)) +" kcal");  
		
	} // Her printer vi det ut, Her henter vi verdier fra metoder og klasser innenfor samme prosjekt. 

}
