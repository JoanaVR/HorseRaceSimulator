
/**
 *The class Horse Contains information about the horses including their name, symbol and others. 
 * This class will contain functions that will allow the hourse to move, fall and get back to the start.
 * It will allow everything to be kept track of.
 * 
 * @author JoanaRangelova
 * @version 03-2025
 */
public class Horse
{
    //Fields of class Horse
    private String mHorseName;
    private char mHorseSymbol;
    private double mHorseConfidence;
    private int mDistanceTravelled;
    private boolean mHasFallen;
    
      
    //Constructor of class Horse
    public Horse(char horseSymbol, String horseName, double horseConfidence)
    {
        this.mHorseSymbol = horseSymbol;
        this.mHorseName = horseName;
        this.mHorseConfidence = horseConfidence;
        this.mDistanceTravelled = 0;
        this.mHasFallen = false;
    }
    
    
    
    //getter methods
    public double getConfidence()
    {
        return this.mHorseConfidence;
    }
    
    public int getDistanceTravelled()
    {
        return this.mDistanceTravelled;
    }
    
    public String getName()
    {
        return this.mHorseName;
    }
    
    public char getSymbol()
    {
        return this.mHorseSymbol;
    }
    public boolean hasFallen()
    {
        return this.mHasFallen;
    }
    
    //setter methods
    public void fall()
    {
        this.mHasFallen = true;
    }

    public void setConfidence(double newConfidence)
    {
        if(newConfidence >= 0 && newConfidence <= 1)
        this.mHorseConfidence = newConfidence;
    }
    
    public void setSymbol(char newSymbol)
    {
        this.mHorseSymbol = newSymbol;
    }

    //other methods
    public void goBackToStart()
    {
        this.mDistanceTravelled = 0;
    }
    

    public void moveForward()
    {
        this.mDistanceTravelled = this.mDistanceTravelled + 1;
    }
    
}
