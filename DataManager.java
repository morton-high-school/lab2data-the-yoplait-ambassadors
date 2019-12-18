import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import java.io.PrintWriter;
import java.io.FileWriter;
import org.apache.commons.math3.stat.regression.SimpleRegression;

public class DataManager
{
    public final String COMPILED_DATA_FILE_NAME = "./data/compiled-data.csv";
    public final String TEMPERATURE_DATA_FILE_NAME = "./data/temperature-data.csv";
    public final String CARBON_DATA_FILE_NAME = "./data/carbon-data.csv";

    public final int FIRST_YEAR_TEMP = 1750;
    public final int LAST_YEAR_TEMP = 2015;
    public final int FIRST_YEAR_CARBON = 0;
    public final int LAST_YEAR_CARBON = 2014;

    HashMap<Integer, YearData> data;

    public DataManager(boolean load) throws IOException
    {
        data = new HashMap<Integer, YearData>();

        if (load)
        {
            if (new File(COMPILED_DATA_FILE_NAME).exists())
            {
                loadExistingData();
                return;
            }
            else
            {
                downloadData();
                compileData();
            }
        }
    }

    public void loadExistingData() throws IOException
    {
        if (!new File(COMPILED_DATA_FILE_NAME).exists())
        {
            System.out.println("ERROR: Compiled data file not found! Exiting program!");
            throw new FileNotFoundException(COMPILED_DATA_FILE_NAME);
        }

        Scanner fileScanner = new Scanner(new File(COMPILED_DATA_FILE_NAME));

        try
        {
            while (fileScanner.hasNextLine())
            {
                String s = fileScanner.nextLine();
                String[] parts = s.split(",");

                int year = Integer.parseInt(parts[0]);
                double averageTemp = Double.parseDouble(parts[1]);
                double carbonTotal = Double.parseDouble(parts[2]);

                YearData yearData = new YearData(year, averageTemp, carbonTotal);
                data.putIfAbsent(year, yearData);
            }
        }
        catch (NumberFormatException e)
        {
            System.out.println("ERROR: Could not parse compiled data set. Please check that there are no accidentally typed characters in the file.");
        }
    }

    public void downloadData() throws IOException
    {
        File directory = new File("data");
        directory.mkdir(); //Does nothing if already exists.

        File temperatureFile = new File(TEMPERATURE_DATA_FILE_NAME);
        File carbonFile = new File(CARBON_DATA_FILE_NAME);

        if (!temperatureFile.exists())
        {
            //TODO Change
            downloadFile(temperatureFile, new URL("https://drive.google.com/uc?export=download&id=1xetpfmR-l6A0OWy_HCREU_YK2seLf9nv"));
        }
        if (!carbonFile.exists())
        {
            downloadFile(carbonFile, new URL("ftp://data.iac.ethz.ch/CMIP6/input4MIPs/UoM/GHGConc/CMIP/yr/atmos/UoM-CMIP-1-1-0/GHGConc/gr3-GMNHSH/v20160701/mole_fraction_of_carbon_dioxide_in_air_input4MIPs_GHGConcentrations_CMIP_UoM-CMIP-1-1-0_gr3-GMNHSH_0000-2014.csv"));
        }
    }

    public void compileData() throws IOException
    {
        //TODO Need to load data from two separate files.
        FileWriter fileWriter = new FileWriter(COMPILED_DATA_FILE_NAME);
        PrintWriter printWriter = new PrintWriter(fileWriter);


        //TEMPERATURE DATA PARSING
        HashMap<Integer, Double> tempTotal = new HashMap(); //The total "avg" global temp
        HashMap<Integer, Integer> tempEntriesNumber = new HashMap(); //Number of entries for each year

        Scanner tempScanner = new Scanner(new File(TEMPERATURE_DATA_FILE_NAME));

        //Store all of our data in tempTotal and tempEntriesNum
        tempScanner.nextLine(); //Skip a line.
        while (tempScanner.hasNextLine())
        {
            String s = tempScanner.nextLine();
            String[] tempParts = s.split(",");
            String[] dateParts = tempParts[0].split("-");
            int year = Integer.parseInt(dateParts[0]);

            try
            {
                if (tempParts[1].equals("")) //If there is no data for the specified time period
                {
                    continue;
                }
            }
            catch (ArrayIndexOutOfBoundsException e)
            {
                continue;
            }

            if (!tempTotal.containsKey(year))
            {
                tempTotal.put(year, Double.parseDouble(tempParts[1]));
                tempEntriesNumber.put(year, 1);
            }
            else
            {
                tempTotal.replace(year, tempTotal.get(year) + Double.parseDouble(tempParts[1]));
                tempEntriesNumber.replace(year, tempEntriesNumber.get(year) + 1);
            }
        }

        tempScanner.close(); //Free up a bunch of memory

        HashMap<Integer, Double> tempAverages = new HashMap();

        for (int yearNum : tempTotal.keySet())
        {
            double average = tempTotal.get(yearNum) / tempEntriesNumber.get(yearNum);
            tempAverages.put(yearNum, average);
        }

        //END OF TEMPERATURE DATA PARSING



        Scanner carbonScanner = new Scanner(new File(CARBON_DATA_FILE_NAME));
        carbonScanner.nextLine();
        while (carbonScanner.hasNext())
        {
            String s = carbonScanner.nextLine();
            String[] carbonParts = s.split(",");
            int year = Integer.parseInt(carbonParts[0]);

            if (year < FIRST_YEAR_TEMP || year > LAST_YEAR_CARBON)
            {
                continue;
            }

            data.put(year, new YearData(year, tempAverages.get(year), Double.parseDouble(carbonParts[1])));
        }

        for (int i = FIRST_YEAR_TEMP; i <= LAST_YEAR_CARBON; i++)
        {
            YearData year = data.get(i);
            double temp = year.averageTemp;
            double carbon = year.carbonEmissions;

            printWriter.println(year.year + "," + temp + "," + carbon);
        }

        printWriter.close();
    }

    public void downloadFile(File file, URL url) throws IOException
    {
        if (!file.exists())
        {
            file.createNewFile();
        }

        try
        {
            ReadableByteChannel readChannel = Channels.newChannel(url.openStream());
            FileOutputStream writer = new FileOutputStream(file);
            writer.getChannel().transferFrom(readChannel, 0, Long.MAX_VALUE);
        }
        catch (Exception e)
        {
            System.out.println("ERROR: Could not download file. Is it a wifi issue?");
            e.printStackTrace();
        }
    }

    public LinearRegression getDataRegression()
    {
        ArrayList<YearData> arrayListData = new ArrayList();
        for (int i : data.keySet())
        {
            arrayListData.add(data.get(i));
        }
        LinearRegression linearRegression = new LinearRegression(arrayListData);


        //Old code that used apache commons math's implementation of SimpleRegression
        /*SimpleRegression regression = new SimpleRegression();
        for (int i : data.keySet())
        {
            regression.addData(data.get(i).carbonEmissions, data.get(i).averageTemp);
        }*/
        return linearRegression;
    }
}
