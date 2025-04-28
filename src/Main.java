package src;
import javax.swing.JFrame;


public class Main {
    public static void main(String[] args)
    {
        if (args.length > 0) 
        {
            if (args[0].equals("gui")) 
            {
                Main.startGuiRace();
            } 
            else 
            {
                Main.startRace();
            }
        } 
        else 
        {
            // Default to console race if no arguments
            Main.startRace();
        }

    }

    public static void startRace()
    {
        Horse horse1 = new Horse("1", "Horse 1", 0.5);
        Horse horse2 = new Horse("2", "Horse 2", 0.5);
        Race r = new Race(10, 2);
        r.addHorse(horse1, 0);
        r.addHorse(horse2, 1);
        r.setRacePanel(null);
        r.startRace();  
    }
    public static void startGuiRace()
    {
        RaceWindow window = new RaceWindow();
        window.frame.setSize(800, 600);
        window.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
