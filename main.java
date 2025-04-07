import java.util.concurrent.TimeUnit;
import java.lang.Math;

public class main {
    public static void main(String[] args)
    {
        Race race = new Race(10);
        Horse horse1 = new Horse('A', "Horse1", 0.5);
        Horse horse2 = new Horse('B', "Horse2", 0.5);
        Horse horse3 = new Horse('C', "Horse3", 0.5);
        race.addHorse(horse1, 1);
        race.addHorse(horse2, 2);
        race.addHorse(horse3, 3);
 
        race.startRace();
    }
}
