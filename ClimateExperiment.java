import java.io.IOException;
import java.util.Scanner;

public class ClimateExperiment
{
    public static void main(String[] args) throws IOException, InterruptedException
    {
        System.out.println("Downloading and parsing data. This will take a minute.");
        DataManager dataManager = new DataManager(true);

        new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();

        System.out.print("Hello. We are the Yoplait Ambassadors, and we ran a climate experiment!\n" +
        "The earth is warming, and climate scientists believe this is due to rising carbon levels in the atmosphere.\n" +
        "Using data gathered from labs around the world, we created a linear regression that measures the relationship between total atmospheric carbon (in ppm) and average global temperature (celcius)\n" +
        "Using the regression model, we can predict the annual average global temperature based on the amount of carbon in the atmosphere.\n" +
        "\n" +
        "\n" +
        "\nFor reference, a few years and their carbon levels in ppm: \n" +
        "1750: 277.147, 1800: 280.8339999404, 1900: 295.674998441674, 2014: 397.546976931623\n\n\n" +
        "Enter a theoretical amount of carbon in the atmosphere (ppm) to predict the average annual temperature (celcius): ");

        Scanner scanner = new Scanner(System.in);
        double d = scanner.nextDouble();

        double a = dataManager.getDataRegression().carbonPrediction(d);

        System.out.println("Predicted global temperature (celcius): " + a);
    }
}
