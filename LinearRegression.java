public class LinearRegression
{
    ArrayList<YearData> data;

    public double slope = 0;
    public double yIntercept = 0;
    public double rSquaredValue = 0;
    public double prediction = 0;

    public double xSum = 0;
    public double ySum = 0;
    public double xySum = 0;
    public double x2Sum = 0;
    public double y2Sum = 0;
    public double n = 0;

    public LinearRegression(ArrayList<YearData> data)
    {
        this.data = data;
        setSlope(data);
        setYIntercept(data);
    }

    public setSlope()
    {
       n = data.size();

        for(int a = 0; a < data.size(); a++){
          xSum += data[a].carbonEmissions;
        }

        for(int b = 0; b < data.size(); b++){
          ySum += data[b].averageTemp;
        }

        for(int c = 0; c < data.size(); c++){
        }

        for(int d = 0; d < data.size(); d++){
          x2Sum += (data[d].carbonEmissions)*(data[d].carbonEmissions);
        }

        for(int e = 0; e < data.size(); e++){
          y2Sum += (data[d].averageTemp)*(data[d].averageTemp);
        }

        slope = ((n*xySum)-(xSum*ySum))/((n*x2Sum)-(xSum*xSum));

    }

    public setYIntercept()
    {
        n = data.size();

        //x value will be carbon emissions
        for(int a = 0; a < data.size(); a++){
          xSum += data[a].carbonEmissions;
        }

        for(int b = 0; b < data.size(); b++){
          ySum += data[b].averageTemp;
        }

        for(int c = 0; c < data.size(); c++){
          xySum += ((data[c].carbonEmissions)*(data[c].averageTemp));
        }

        for(int d = 0; d < data.size(); d++){
          x2Sum += (data[d].carbonEmissions)*(data[d].carbonEmissions);
        }

        for(int e = 0; e < data.size(); e++){
          y2Sum += (data[d].averageTemp)*(data[d].averageTemp);
        }

        yIntercept = ((x2Sum*ySum)-(xSum*xySum))/((n*x2Sum)-(xSum*xSum));

    }

    public double findRSquaredValue()
    {
        n = data.size();

        //x value will be carbon emissions
        for(int a = 0; a < data.size(); a++){
          xSum += data[a].carbonEmissions;
        }

        for(int b = 0; b < data.size(); b++){
          ySum += data[b].averageTemp;
        }

        for(int c = 0; c < data.size(); c++){
          xySum += ((data[c].carbonEmissions)*(data[c].averageTemp));
        }

        for(int d = 0; d < data.size(); d++){
          x2Sum += (data[d].carbonEmissions)*(data[d].carbonEmissions);
        }

        for(int e = 0; e < data.size(); e++){
          y2Sum += (data[d].averageTemp)*(data[d].averageTemp);
        }

        rSquaredValue = ((n*xySum)-(xSum*ySum)*(n*xySum)-(xSum*ySum))/((n*x2Sum-((xSum)*(xSum)))*(n*y2Sum-((ySum)*(ySum))));
        return rSquaredValue;
    }

    //x axis = carbon, y axis = temp,
    public double carbonPrediction(double userInput, ArrayList<YearData> data)
    {
        prediction = (slope*userInput) + yIntercept;
        return prediction;
    }
}
