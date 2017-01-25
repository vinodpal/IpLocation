/**
 * 
 */
package com.ipTrack;
/**
 * @author vinod<vinodpal458@gmail.com
 * 
 */
import java.io.File;
 import java.io.IOException;
import java.util.ResourceBundle;

import com.maxmind.geoip.Location;
 import com.maxmind.geoip.LookupService;
 import com.maxmind.geoip.regionName;
 //import com.mkyong.analysis.location.mode.ServerLocation;

 public class GetLocation {

   private static File file = null;
   public static void main(String[] args) {
	   GetLocation obj = new GetLocation();
 	ServerLocation location = obj.getLocation("172.16.0.11");
 	System.out.println();
 	System.out.println(location);
   }
   public static String getCurrentLocation(String ipAddress){

	   GetLocation obj = new GetLocation();
 	ServerLocation location = obj.getLocation(ipAddress);
 	//System.out.println(location);
 	return location.city.toString();
   }
   
	public ServerLocation getLocation(String ipAddress) {
		getFile();
		return getLocation(ipAddress, this.file);
	}

	private void getFile() {
		String classpath = null;
		if (this.file == null) {
			try {
				String catalina_home = System.getenv("CATALINA_HOME");
				ClassLoader classLoader = getClass().getClassLoader();
				String databasePathName = null;
				if (catalina_home.contains("/"))
					databasePathName = "/database/GeoLiteCity.dat";
				else {
					databasePathName = "\\database\\GeoLiteCity.dat";
				}
				this.file = new File(classLoader.getClass().getResource(databasePathName).getFile());
			} catch (Exception ex) {

			}
		}
	}
	public ServerLocation getLocation(String ipAddress, File file) {

		ServerLocation serverLocation = null;
		try {
			serverLocation = new ServerLocation();
			LookupService lookup = new LookupService(file, LookupService.GEOIP_MEMORY_CACHE);
			Location locationServices = lookup.getLocation(ipAddress);
			serverLocation.setCountryCode(locationServices.countryCode);
			serverLocation.setCountryName(locationServices.countryName);
			serverLocation.setRegion(locationServices.region);
			serverLocation
					.setRegionName(regionName.regionNameByCode(locationServices.countryCode, locationServices.region));
			serverLocation.setCity(locationServices.city);
			serverLocation.setPostalCode(locationServices.postalCode);
			serverLocation.setLatitude(String.valueOf(locationServices.latitude));
			serverLocation.setLongitude(String.valueOf(locationServices.longitude));
		} catch (IOException e) {
			System.err.println(e.getMessage());
		}

		return serverLocation;

	}
 }
