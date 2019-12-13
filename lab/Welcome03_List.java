/*
 * Arrays of objects
 */

import core.data.*;
import java.util.ArrayList;
import java.util.Scanner;

public class Welcome03_List {
   public static void main(String[] args) {
      DataSource ds = DataSource.connect("http://weather.gov/xml/current_obs/index.xml").load();
      ArrayList<WeatherStation> allstns = ds.fetchList("WeatherStation", "station/station_name",
             "station/station_id", "station/state",
             "station/latitude", "station/longitude");
      System.out.println("Total stations: " + allstns.size());

      Scanner sc = new Scanner(System.in);
      System.out.println("Enter a state abbreviation: ");
      String state = sc.next();
      System.out.println("Stations in " + state);
      int i = 0;
      for (WeatherStation ws : allstns) {
         if (ws.isLocatedInState(state)) {
             i++;
            System.out.println("  " + ws.getId() + ": " + ws.getName());
         }
      }
      System.out.println("Total number of stations in state: " + i);

      int j = 0;
      WeatherStation lowestLatStation = null;
      for (WeatherStation ws : allstns) {
         if (j == 0) {
             lowestLatStation = ws;
             j++;
             continue;
         }

         if (ws.getLattitude() < lowestLatStation.getLattitude()) {
             lowestLatStation = ws;
         }
      }

      System.out.println("The airport in the dataset with the lowest lattitude is: " + lowestLatStation.getName());
   }
}
