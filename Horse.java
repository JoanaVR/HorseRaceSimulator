
/**
 * Write a description of class Horse here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
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
    /**
     * Constructor for objects of class Horse
     */
    public Horse(char horseSymbol, String horseName, double horseConfidence)
    {
        this.mHorseSymbol = horseSymbol;
        this.mHorseName = horseName;
        this.mHorseConfidence = horseConfidence;
        this.mDistanceTravelled = 0;
        this.mHasFallen = false;
    }
    
    
    
    //Other methods of class Horse
    public void fall()
    {
        this.mHasFallen = true;
    }
    
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
    
    public void goBackToStart()
    {
        this.mDistanceTravelled = 0;
    }
    
    public boolean hasFallen()
    {
        return this.mHasFallen;
    }

    public void moveForward()
    {
        this.mDistanceTravelled++;
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
    
}
