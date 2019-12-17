import java.io.IOException;
import java.util.Scanner;

public class ClimateExperiment
{
    public static void main(String[] args) throws IOException
    {
        DataManager dataManager = new DataManager(true);

        Runtime.getRuntime().exec("cls");
        System.out.print("Hello. We are the Yoplait Ambassadors, and we ran a climate experiment!\n" +
        "The earth is warming, and a majority of climate scientists believe this is due to rising carbon levels in the atmosphere.\n" +
        "Using data gathered from labs around the world, we created a linear regression that measures the relationship between total atmospheric carbon (in ppm) and average global temperature (celcius)\n" +
        "Using the regression model, we can predict the average global temperature of a year based on the amount of carbon in the atmosphere.\n" +
        "\n" +
        "\n" +
        "Enter the amount of carbon in the atmosphere (ppm) for a year to predict the temperature: ");

        Scanner scanner = new Scanner(System.in);
        double d = scanner.nextDouble();

        double a = dataManager.getDataRegression().predict(d);

        System.out.println("Predicted global temperature (celcius): " + a);
    }
}
