import core.data.*;

public class Welcome02_Object {
   public static void main(String[] args) {
      String id1 = "KATL";
      DataSource ds1 = DataSource.connect("http://weather.gov/xml/current_obs/" + id1 + ".xml");
      ds1.setCacheTimeout(15 * 60);
      ds1.load();
      //ds1.printUsageString();

      Observation ob1 = ds1.fetch("Observation", "weather", "temp_f", "wind_degrees");
      ob1.setName(id1);
      System.out.println(id1 + ": " + ob1);

      String id2 = "KSAV";
      DataSource ds2 = DataSource.connect("http://weather.gov/xml/current_obs/" + id2 + ".xml");
      ds2.setCacheTimeout(15 * 60);
      ds2.load();

      Observation ob2 = ds2.fetch("Observation", "weather", "temp_f", "wind_degrees");
      System.out.println(id2 + ": " + ob2);
      ob2.setName(id2);

      String id3 = "KPIA";
      DataSource ds3 = DataSource.connect("http://weather.gov/xml/current_obs/" + id3 + ".xml");
      ds3.setCacheTimeout(15 * 60);
      ds3.load();

      Observation ob3 = ds3.fetch("Observation", "weather", "temp_f", "wind_degrees");
      System.out.println(id3 + ": " + ob3);
      ob3.setName(id3);


      if (ob1.colderThan(ob2)) {
         System.out.println("Colder at " + id1);
      } else {
         System.out.println("Colder at " + id2);
      }

      Observation coldest = ob1;
      if (ob2.colderThan(coldest))
      {
          coldest = ob2;
      }

      if (ob3.colderThan(coldest))
      {
          coldest = ob3;
      }

      System.out.println("The coldest location is: " + coldest.locationName);
   }
}


/* Represents a weather observation */
class Observation {
   String locationName;
   float temp;    // in fahrenheit
   int windDir;   // in degrees
   String description;

   Observation(String description, float temp, int windDir) {
      this.description = description;
      this.temp = temp;
      this.windDir = windDir;
      this.locationName = locationName;
   }

   /* determine if the temperature of this observation is colder than 'that's */
   public boolean colderThan(Observation that) {
      return this.temp < that.temp;
   }

   /* produce a string describing this observation */
   public String toString() {
      return (temp + " degrees; " + description + " (wind: " + windDir + " degrees)");
   }

   public Observation setName(String name)
   {
       this.locationName = name;
       return this;
   }
}
