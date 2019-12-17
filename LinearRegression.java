import java.util.*;
public class LinearRegression
{
    ArrayList<YearData> data;

    public double slope = 0;
    public double yIntercept = 0;
    public double rSquaredValue = 0;
    public double prediction = 0;

    public double xSum = 0;
    public double ySum = 0;
    public double xMean = 0;
    public double yMean = 0;
    public double sumSquares = 0;
    public double sumProducts = 0;
    public double n = 0;

    public LinearRegression(ArrayList<YearData> data)
    {
        this.data = data;
        setSlope();
        setYIntercept();
    }

    public void setSlope()
    {
       n = data.size();

        for(int a = 0; a < data.size(); a++){
          xSum += data.get(a).carbonEmissions;
        }
        xMean = xSum/n;

        for(int b = 0; b < data.size(); b++){
          ySum += data.get(b).averageTemp;
        }
        yMean = ySum/n;

//      System.out.println(xMean);
//      System.out.println(yMean);

        for(int c = 0; c < data.size(); c++){
          sumSquares += ((data.get(c).carbonEmissions)-xMean)*((data.get(c).carbonEmissions)-xMean);
        }

        for(int d = 0; d < data.size(); d++){
          sumProducts += ((data.get(d).carbonEmissions)-xMean)*((data.get(d).averageTemp)-yMean);
        }
//      System.out.println(sumSquares);
//      System.out.println(sumProducts);

        slope = (sumProducts/sumSquares);

    }

    public void setYIntercept()
    {

        yIntercept = yMean - (slope*xMean);

    }

    public double carbonPrediction(double userInput)
    {
        prediction = (slope*userInput) + yIntercept;
        return prediction;
    }
}
