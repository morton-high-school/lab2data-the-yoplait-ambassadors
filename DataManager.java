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

public class DataManager
{
    public final String COMPILED_DATA_FILE_NAME = "./data/compiled-data.csv";
    public final String TEMPERATURE_DATA_FILE_NAME = "./data/temperature-data.csv";
    public final String CARBON_DATA_FILE_NAME = "./data/carbon-data.csv";

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
                loadExistingData();
            }
        }
    }

    public void loadExistingData() throws FileNotFoundException
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
            downloadFile(temperatureFile, new URL(""));
        }
        if (!carbonFile.exists())
        {
            downloadFile(carbonFile, new URL("ftp://data.iac.ethz.ch/CMIP6/input4MIPs/UoM/GHGConc/CMIP/yr/atmos/UoM-CMIP-1-1-0/GHGConc/gr3-GMNHSH/v20160701/mole_fraction_of_carbon_dioxide_in_air_input4MIPs_GHGConcentrations_CMIP_UoM-CMIP-1-1-0_gr3-GMNHSH_0000-2014.csv"));
        }
    }

    public void compileData()
    {
        //TODO Need to load data from two separate files.
        FileWriter fileWriter = new FileWriter(COMPILED_DATA_FILE_NAME);
        PrintWriter printWriter = new PrintWriter(fileWriter);

        for (i = 0; i < data.size(); i++)
        {
            YearData year = data.get(i);
            double temp = year.averageTemp;
            double carbon = year.carbonEmissions;

            printWriter.println(year + "," + temp + "," + carbon);
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
}
