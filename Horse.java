
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
    private String mHorseSymbol;
    private double mHorseConfidence;
    private double mDistanceTravelled;
    private boolean mHasFallen;
    private double mHorseSpeed;

    private String mHorseBreed;
    private String mHorseColor;
    private boolean mHasSaddle;
    private boolean mHasHorseShoes;

    
    
      
    //Constructor of class Horse
    public Horse(String horseSymbol, String horseName, double horseConfidence)
    {
        this.mHorseSymbol = horseSymbol;
        this.mHorseName = horseName;
        this.mHorseConfidence = horseConfidence;
        this.mDistanceTravelled = 0;
        this.mHasFallen = false;
        mHorseSpeed = 0.5; //default speed of horse is 0.5

        mHorseBreed = ""; 
        mHorseColor = "";   
        mHasSaddle = false; 
        mHasHorseShoes = false;

        //change attributes of horse based on the breed, saddle and horseshoes
       
       
    }
    
    
    
    //getter methods
    public double getConfidence()
    {
        return this.mHorseConfidence;
    }
    
    public double getDistanceTravelled()
    {
        return this.mDistanceTravelled;
    }
    
    public String getName()
    {
        return this.mHorseName;
    }
    
    public String getSymbol()
    {
        return this.mHorseSymbol;
    }
    public double getSpeed()
    {
        return this.mHorseSpeed;
    }
    public boolean hasFallen()
    {
        return this.mHasFallen;
    }
    public String getBreed()
    {
        return this.mHorseBreed;
    }
    public String getColor()
    {
        return this.mHorseColor;
    }
    public boolean hasSaddle()
    {
        return this.mHasSaddle;
    }
    public boolean hasHorseShoes()
    {
        return this.mHasHorseShoes;
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
        this.mHorseConfidence = Math.round(this.mHorseConfidence * 100.0) / 100.0;

    }
    public void setSpeed(double newSpeed)
    {
        if(newSpeed >= 0.25 && newSpeed <= 1)
            this.mHorseSpeed = newSpeed;
        this.mHorseSpeed = Math.round(this.mHorseSpeed * 100.0) / 100.0;
        
    }
    
    public void setSymbol(String newSymbol)
    {
        this.mHorseSymbol = newSymbol;
    }
    public void setBreed(String newBreed)
    {
        this.mHorseBreed = newBreed;
        if(mHorseBreed.equals("Arabian Horse"))
        {
            setSpeed(mHorseSpeed + 0.25); 
        }
        else if(mHorseBreed.equals("Shire Horse"))
        {
            setSpeed(mHorseSpeed - 0.25); 
            setConfidence( mHorseConfidence + 0.10);
        }
        else if(mHorseBreed.equals("Appaloosa"))
        {
            setSpeed(mHorseSpeed + 0.15); 
        }
        else if(mHorseBreed.equals("Thoroughbred"))
        {
            setSpeed(mHorseSpeed + 0.20); 
            setConfidence(mHorseConfidence - 0.05);
        }
        else if(mHorseBreed.equals("Clydesdale"))
        {
            setSpeed(mHorseSpeed - 0.10); 
        }
    }
    public void setColor(String newColor)
    {
        this.mHorseColor = newColor;
    }
    public void setSaddle(boolean newSaddle)
    {
        this.mHasSaddle = newSaddle;
        
        if(mHasSaddle)
        {
            setSpeed(mHorseSpeed - 0.05); 
            setConfidence(mHorseConfidence + 0.1);
        }
    }
    public void setHorseShoes(boolean newHorseShoes)
    {
        this.mHasHorseShoes = newHorseShoes;
        if(mHasHorseShoes)
        {
            setSpeed(mHorseSpeed + 0.25); 
            setConfidence(mHorseConfidence + 0.05);
        }
    }

    //other methods
    public void goBackToStart()
    {
        this.mDistanceTravelled = 0;
        this.mHasFallen = false;
    }
    

    public void moveForward()
    {
        this.mDistanceTravelled = this.mDistanceTravelled + this.mHorseSpeed;
    }
    
}
