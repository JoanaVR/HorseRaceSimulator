import java.util.ArrayList;

public class HorseStats 
{
    private int numberOfRaces;
    private int numberOfWins;
    ArrayList<Double> speedHistory;
    ArrayList<Double> finishTimeHistory;
    ArrayList<Double> confidenceHistory;
    ArrayList<String> trackConditionHistory;
    ArrayList<String> tweatherHistory;

    public HorseStats()
    {
        this.numberOfRaces = 0;
        this.numberOfWins = 0;
        this.speedHistory = new ArrayList<>();
        this.finishTimeHistory = new ArrayList<>();
        this.confidenceHistory = new ArrayList<>();
        this.trackConditionHistory = new ArrayList<>();
        this.tweatherHistory = new ArrayList<>();
    }

    public void addRace(boolean didWin, double speed, double finishTime, double confidence, String trackCondition, String weather)
    {
        this.numberOfRaces++;
        this.speedHistory.add(speed);
        this.finishTimeHistory.add(finishTime);
        this.confidenceHistory.add(confidence);
        this.trackConditionHistory.add(trackCondition);
        this.tweatherHistory.add(weather);
        if(didWin)
        {
            this.numberOfWins++;
        }
    }
    //getter methods
    public int getNumberOfRaces()
    {
        return this.numberOfRaces;
    }
    public int getNumberOfWins()
    {
        return this.numberOfWins;
    }
    public ArrayList<Double> getSpeedHistory()
    {
        return this.speedHistory;
    }
    public ArrayList<Double> getFinishTimeHistory()
    {
        return this.finishTimeHistory;
    }
    public ArrayList<Double> getConfidenceHistory()
    {
        return this.confidenceHistory;
    }
    public ArrayList<String> getTrackConditionHistory()
    {
        return this.trackConditionHistory;
    }
    public ArrayList<String> getWeatherHistory()
    {
        return this.tweatherHistory;
    }
    public double getAverageSpeed()
    {
        return getAverage(this.speedHistory);
    }
    public double getAverageFinishTime()
    {
        return getAverage(this.finishTimeHistory);
    }
    public double getAverageConfidence()
    {
        return getAverage(this.confidenceHistory);
    }

    public double getAverage(ArrayList<Double> list)
    {
        double total = 0;
        for(double value : list)
        {
            total += value;
        }

        return Math.round((total / list.size()) * 100.0) / 100.0;
    }
    public double getWinPercentage()
    {
        if(this.numberOfRaces == 0)
        {
            return 0;
        }
        double result = (double)this.numberOfWins / this.numberOfRaces * 100;
        return Math.round(result * 100.0) / 100.0;
    }
    public double getConfidenceChange()
    {
        if(this.confidenceHistory.size() < 2)
        {
            return 0;
        }
        double difference = this.confidenceHistory.get(this.confidenceHistory.size() - 1) - this.confidenceHistory.get(0);
        return Math.round(difference * 100.0) / 100.0;
    }
    
}

