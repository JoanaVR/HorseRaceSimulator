import java.util.concurrent.TimeUnit;
import java.lang.Math;

/**
 * A three-horse race, each horse running in its own lane
 * for a given distance
 * 
 * @author JoanaRangelova
 * @version 1.0
 */
public class Race
{
    private int raceLength;
    private Horse lane1Horse;
    private Horse lane2Horse;
    private Horse lane3Horse;

    /**
     * Constructor for objects of class Race
     * Initially there are no horses in the lanes
     * 
     * @param distance the length of the racetrack (in metres/yards...)
     */
    public Race(int distance)
    {
        // initialise instance variables
        raceLength = distance;
        lane1Horse = null;
        lane2Horse = null;
        lane3Horse = null;
    }
    
    /**
     * Adds a horse to the race in a given lane
     * 
     * @param theHorse the horse to be added to the race
     * @param laneNumber the lane that the horse will be added to
     */
    public void addHorse(Horse theHorse, int laneNumber)
    {
        if (laneNumber == 1)
        {
            lane1Horse = theHorse;
        }
        else if (laneNumber == 2)
        {
            lane2Horse = theHorse;
        }
        else if (laneNumber == 3)
        {
            lane3Horse = theHorse;
        }
        else
        {
            System.out.println("Cannot add horse to lane " + laneNumber + " because there is no such lane");
        }
    }
    
    private boolean canStartRace()
    {
        if(lane1Horse != null && lane2Horse != null)
        {
            return true;
        }
        else if(lane2Horse != null && lane3Horse != null)
        {
            return true;
        }
        else if(lane1Horse != null && lane3Horse != null)
        {
            return true;
        }
        else
        {
            System.out.println("At least two horses are needed to start a race");
            return false;
        }
    }

    /**
     * Start the race
     * The horse are brought to the start and
     * then repeatedly moved forward until the 
     * race is finished
     */
    public void startRace()
    {
        //if there arent enough horses to start the race
        if(canStartRace() == false)
        {
            return;
        }

        //declare a local variable to tell us when the race is finished
        boolean finished = false;

        //reset all the lanes (all horses not fallen and back to 0).
        if(lane1Horse != null)
        {
            lane1Horse.goBackToStart();
        }
        if(lane2Horse != null)
        {
            lane2Horse.goBackToStart();
        }
        if(lane3Horse != null)
        {
            lane3Horse.goBackToStart();
        } 
                      
        while (!finished)
        {
            //move each horse
            if(lane1Horse != null)
            {
                moveHorse(lane1Horse);
            }
            if(lane2Horse != null)
            {
                moveHorse(lane2Horse);
            }
            if(lane3Horse != null)
            {
                moveHorse(lane3Horse);
            }
                        
            //print the race positions
            printRace();
            
            //if any of the three horses has won the race is finished
            if ( raceFinished()) 
            {
                finished = true;
            }


            //wait for 100 milliseconds
            try{ 
                TimeUnit.MILLISECONDS.sleep(100);
            }catch(Exception e){}
        }
        printRace();
    }

    private boolean raceFinished()
    {
        //if any of the three horses has won the race is finished
        if ( raceWonBy(lane1Horse) )
        {
            System.out.println(lane1Horse.getName() + " has won the race!");
            return true;
        }
        else if( raceWonBy(lane2Horse))
        {
            System.out.println(lane2Horse.getName() + " has won the race!");
            return true;
        }
        else if(raceWonBy(lane3Horse))
        {
            System.out.println(lane3Horse.getName() + " has won the race!");
            return true;
        }

        if(lane1Horse.hasFallen() && lane2Horse.hasFallen() && lane3Horse.hasFallen())
        {
            System.out.println("All horses have fallen!");
            return true;
        }
        return false;
    }
    
    /**
     * Randomly make a horse move forward or fall depending
     * on its confidence rating
     * A fallen horse cannot move
     * 
     * @param theHorse the horse to be moved
     */
    private void moveHorse(Horse theHorse)
    {
        //if the horse has fallen it cannot move, 
        //so only run if it has not fallen
        if  (!theHorse.hasFallen())
        {
            //the probability that the horse will move forward depends on the confidence;
            if (Math.random() < theHorse.getConfidence())
            {
               theHorse.moveForward();
            }
            
            //the probability that the horse will fall is very small (max is 0.1)
            //but will also will depends exponentially on confidence 
            //so if you double the confidence, the probability that it will fall is *2
            if (Math.random() < (0.1*theHorse.getConfidence()*theHorse.getConfidence()))
            {
                theHorse.fall();
                //decrease confidence if the horse falls
                theHorse.setConfidence(theHorse.getConfidence() - 0.1); 
            }
        }
    }
        
    /** 
     * Determines if a horse has won the race
     *
     * @param theHorse The horse we are testing
     * @return true if the horse has won, false otherwise.
     */
    private boolean raceWonBy(Horse theHorse)
    {
        if(theHorse == null)
        {
            return false;
        }
        if (theHorse.getDistanceTravelled() >= raceLength)
        {
            //increase confidence if the horse wins
            theHorse.setConfidence(theHorse.getConfidence() + 0.1);
            return true;
        }
        else
        {
            return false;
        }
    }


    
    /***
     * Print the race on the terminal
     */
    private void printRace()
    {
        System.out.print('\u000C');  //clear the terminal window
        
        multiplePrint('=',raceLength+3); //top edge of track
        System.out.println();
        
        printLane(lane1Horse);
        System.out.println();
        
        printLane(lane2Horse);
        System.out.println();
        
        printLane(lane3Horse);
        System.out.println();
        
        multiplePrint('=',raceLength+3); //bottom edge of track
        System.out.println();    
    }
    
    /**
     * print a horse's lane during the race
     * for example
     * |           X                      |
     * to show how far the horse has run
     */
    private void printLane(Horse theHorse)
    {
        if(theHorse == null)
        {
            System.out.print('|');
            multiplePrint(' ',raceLength+1);
            System.out.print('|');
            return;
        }
        //calculate how many spaces are needed before
        //and after the horse
        int spacesBefore = theHorse.getDistanceTravelled();
        int spacesAfter = raceLength - theHorse.getDistanceTravelled();
        
        //print a | for the beginning of the lane
        System.out.print('|');
        
        //print the spaces before the horse
        multiplePrint(' ',spacesBefore);
        
        //if the horse has fallen then print dead
        //else print the horse's symbol
        if(theHorse.hasFallen())
        {
            //System.out.print('X');
            System.out.print('\u2322');
        }
        else
        {
            System.out.print(theHorse.getSymbol());
        }
        
        //print the spaces after the horse
        multiplePrint(' ',spacesAfter);
        
        //print the | for the end of the track
        System.out.print('|');
        System.out.print( theHorse.getName() + " (Current confidence: " + theHorse.getConfidence() + ")");
    }
        
    
    /***
     * print a character a given number of times.
     * e.g. printmany('x',5) will print: xxxxx
     * 
     * @param aChar the character to Print
     */
    private void multiplePrint(char aChar, int times)
    {
        int i = 0;
        while (i < times)
        {
            System.out.print(aChar);
            i = i + 1;
        }
    }
}
