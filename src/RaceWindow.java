package src;
import javax.swing.*;

import src.Race;
import src.HorseStats;
import src.Horse;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RaceWindow {
    JFrame frame;
    RacePanel panel;
    Race currentRace;

    public RaceWindow() {
        frame = new JFrame("Horse Race Simulator");

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);
        frame.pack();
        //frame.add(panel);

        JMenuBar menubar = new JMenuBar();
        JMenu menu = new JMenu("Menu");

        //add items and their actions 
        JMenuItem start = new JMenuItem("Start Race");
        start.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) 
            {
                RaceInfo info = askUser(frame);

                if(info != null)
                {
                    Race race = new Race(info.raceLength, info.numOfLanes);
                    currentRace = race;
                    for(int i = 0; i < info.numOfHorses; i++)
                    {
                        HorseInfo horseInfo = getHorseInfo(frame);
                        Horse horse = new Horse(horseInfo.horseSymbol, horseInfo.horseName, horseInfo.horseConfidence);
                        horse.setBreed(horseInfo.breed);
                        horse.setColor(horseInfo.color);
                        horse.setSaddle(horseInfo.hasSaddle);
                        horse.setHorseShoes(horseInfo.hasHorseShoes);
                        race.addHorse(horse, i);
                    }
                    panel = new RacePanel(race.getHorses(), race.getRaceLength());
                    currentRace.setRacePanel(panel);
                    frame.getContentPane().removeAll(); // clear previous contents
                    frame.setLayout(new BorderLayout());
                    frame.add(panel, BorderLayout.CENTER);
                    JPanel buttonPanel = new JPanel();

                    JButton rematchBtn = new JButton("Rematch");
                    JButton addHorseBtn = new JButton("Rematch + New Horse");

                    rematchBtn.addActionListener(event -> restartRace());
                    addHorseBtn.addActionListener(event -> addHorseToRace());

                    buttonPanel.add(rematchBtn);
                    buttonPanel.add(addHorseBtn);
                    frame.add(buttonPanel, BorderLayout.SOUTH);

                    frame.revalidate(); // refresh layout
                    frame.repaint();

                    // Start the race (will update the panel every move)
                    Thread raceThread = new Thread(() -> race.startRace());
                    raceThread.start();
                    
                }
            }
        });

        JMenuItem stats = new JMenuItem("Statistics");
        stats.addActionListener(new ActionListener() 
        {
            @Override
            public void actionPerformed(ActionEvent e) 
            {
                //stats of the horses if there has been a race
                if(currentRace == null)
                {
                    JOptionPane.showMessageDialog(frame, "There hasnt been a race yet");
                }
                else
                {
                    //get the stats for the horses and show them in a message dialog
                    StringBuilder statsMessage = new StringBuilder("Horse Statistics:\n");
                    for (Horse horse : currentRace.getHorses()) 
                    {
                        if(horse != null)
                        {
                            HorseStats horseStats = horse.getHorseStats();
                            statsMessage.append("Name: ").append(horse.getName()).append("\n")
                                    .append("Symbol: ").append(horse.getSymbol()).append("\n")
                                    .append("Average Confidence: ").append(horseStats.getAverageConfidence()).append("\n")
                                    .append("Confidence Change: ").append(horseStats.getConfidenceChange()).append("\n")
                                    .append("Average Speed: ").append(horseStats.getAverageSpeed()).append("\n")
                                    .append("Average Finish Time: ").append(horseStats.getAverageFinishTime()).append("\n")
                                    .append("Number of Races: ").append(horseStats.getNumberOfRaces()).append("\n")
                                    .append("Number of Wins: ").append(horseStats.getNumberOfWins()).append("\n")
                                    .append("Win Percentage: ").append(horseStats.getWinPercentage()).append("\n\n");
                        }
                    }
                    JOptionPane.showMessageDialog(frame, statsMessage.toString(), "Horse Statistics", JOptionPane.INFORMATION_MESSAGE);
                }
            }
            
        });

        JMenuItem bets = new JMenuItem("Bets");
       
        menu.add(start);
        menu.add(stats);
        menu.add(bets);
        menubar.add(menu);
        frame.setJMenuBar(menubar);
        frame.setVisible(true);

    }

    //adding a new horse to the race and starting it
    public void addHorseToRace()
    {
        
        if(currentRace != null)
        {
            //if there is space for the horse add it 
            if(currentRace.getNumOfHorses() < currentRace.getHorses().length)
            {
                addHorse();
                restartRace();
            }
            //if there isnt space make a new race with enough space for the new horse and the old horses
            else
            {
                //remeber the old race in case the user cancels the new horse
                Race temp = currentRace;
                Race r = new Race((int)currentRace.getRaceLength(), currentRace.getHorses().length + 1);
                for(int i = 0; i < currentRace.getHorses().length; i++)
                {
                    r.addHorse(currentRace.getHorses()[i], i);
                }
                currentRace = r;
                if(addHorse() == false)
                {
                    currentRace = temp;
                }
                else
                     restartRace();
            }
            
            
        }
    }

    public boolean addHorse()
    {
        HorseInfo horseInfo = getHorseInfo(frame);
        if(horseInfo == null)
        {
            return false;
        }
        Horse horse = new Horse(horseInfo.horseSymbol, horseInfo.horseName, horseInfo.horseConfidence);
        horse.setBreed(horseInfo.breed);
        horse.setColor(horseInfo.color);
        horse.setSaddle(horseInfo.hasSaddle);
        horse.setHorseShoes(horseInfo.hasHorseShoes);
        currentRace.addHorse(horse, currentRace.getNumOfHorses());
        return true;
    }
    public void restartRace() 
    {
        if (currentRace != null) 
        {
            for(int i = 0; i < currentRace.getHorses().length; i++)
            {
                if(currentRace.getHorses()[i] != null)
                    currentRace.getHorses()[i].setWinner(false);
            }
            frame.getContentPane().remove(this.panel);  // Remove old race panel 
            this.panel = new RacePanel(currentRace.getHorses(), currentRace.getRaceLength());
            currentRace.setRacePanel(this.panel);
            frame.add(this.panel, BorderLayout.CENTER);
            frame.revalidate();
            frame.repaint();
    
            Thread raceThread = new Thread(() -> currentRace.startRace());
            raceThread.start();
        }
    }
    
    //class to allow return of multiple values from askUser method
    static class RaceInfo
    {
        int raceLength;
        int numOfLanes;
        String metricUnit;
        int numOfHorses;

        RaceInfo() {
            this.raceLength = 0;
            this.numOfLanes = 0;
            this.metricUnit = null;
            this.numOfHorses = 0;
        }
    };
    
    static RaceInfo askUser(JFrame parent) {
        
        String[] units = {"metes", "yards", "miles"};
        String[] lengths = new String[]{"10", "30", "50"};
        String[] lanes = new String[]{"2", "3", "4", "5", "6", "7", "8", "9", "10"};
        String[] horses = new String[]{"2", "3", "4", "5", "6", "7", "8", "9", "10"};

        JComboBox<String> jcunits = new JComboBox<>(units);
        JComboBox<String> jclength = new JComboBox<>(lengths);
        JComboBox<String> jclanes = new JComboBox<>(lanes);
        JComboBox<String> jchorses = new JComboBox<>(horses);

        jcunits.setEditable(true);
        jclength.setEditable(true);
        jclanes.setEditable(true);
        jchorses.setEditable(true);

        // Create a panel to hold the combo boxes
        JPanel panel = new JPanel(new GridLayout(4, 2));
        panel.add(new JLabel("Select Metric Unit:"));
        panel.add(jcunits);
        panel.add(new JLabel("Select Race Length:"));
        panel.add(jclength);
        panel.add(new JLabel("Select Number of Lanes:"));
        panel.add(jclanes);
        panel.add(new JLabel("Select Number of Horses:"));
        panel.add(jchorses);

        System.out.println("Opening race settings dialog...");


        // Show the dialog and get the user's response
        int result = JOptionPane.showConfirmDialog(parent, panel, "Race Settings", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        // parent.setVisible(true);
        if (result == JOptionPane.OK_OPTION) {
            // Retrieve the selected values
            RaceInfo raceInfo = new RaceInfo();
            raceInfo.metricUnit = (String) jcunits.getSelectedItem();
            raceInfo.raceLength = Integer.parseInt((String) jclength.getSelectedItem()); 
            raceInfo.numOfLanes = Integer.parseInt((String) jclanes.getSelectedItem()); 
            raceInfo.numOfHorses = Integer.parseInt((String) jchorses.getSelectedItem());

            return raceInfo;
        } 
        else 
        {
            return null;
        }
    }

    //fucntiont hat will allow the user to customise each horse.
    static class HorseInfo
    {
        String horseName;
        String horseSymbol;
        double horseConfidence;
        String breed;
        String color;
        boolean hasSaddle;
        boolean hasHorseShoes;
        
        HorseInfo() {
            this.horseName = null;
            this.horseSymbol = "X";
            this.horseConfidence = 0.5;
            this.breed = null;
            this.color = null;
            this.hasSaddle = false;
            this.hasHorseShoes = false;
        }
    };
    
    static HorseInfo getHorseInfo(JFrame parent) {
        
        String[] name = {"Horse"};
        String[] symbol = new String[]{"H"};
        String[] breed = new String[]{"Arabian Horse", "Shire Horse", "Appaloosa", "Thoroughbred", "Clydesdale"};
        String[] colour = new String[]{"Black", "White", "Brown", "Baige", "Multicoloured"};
        String[] saddle = new String[]{"false", "true"};
        String[] horseshoe = new String[]{"false", "true"};


        JComboBox<String> jcname = new JComboBox<>(name);
        JComboBox<String> jcsymbol = new JComboBox<>(symbol);
        JComboBox<String> jcbreed = new JComboBox<>(breed);
        JComboBox<String> jccolour = new JComboBox<>(colour);
        JComboBox<String> jcsaddle = new JComboBox<>(saddle);
        JComboBox<String> jchorseshoe = new JComboBox<>(horseshoe);

        jcname.setEditable(true);
        jcsymbol.setEditable(true);
        jcbreed.setEditable(true);
        jccolour.setEditable(true);
        jcsaddle.setEditable(true);
        jchorseshoe.setEditable(true);

        // Create a panel to hold the combo boxes
        JPanel panel = new JPanel(new GridLayout(6, 2));
        panel.add(new JLabel("Enter Horse Name:"));
        panel.add(jcname);
        panel.add(new JLabel("Enter Horse Symbol:"));
        panel.add(jcsymbol);
        panel.add(new JLabel("Select Horse Breed:"));
        panel.add(jcbreed);
        panel.add(new JLabel("Select Horse Colour:"));
        panel.add(jccolour);
        panel.add(new JLabel("Does this horse ahve a saddle:"));
        panel.add(jcsaddle);
        panel.add(new JLabel("DOes this horse have horseshoes:"));
        panel.add(jchorseshoe);

        // Show the dialog and get the user's response
        int result = JOptionPane.showConfirmDialog(parent, panel, "Race Settings", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        // parent.setVisible(true);
        if (result == JOptionPane.OK_OPTION) {
            // Retrieve the selected values
            HorseInfo hourseInfo = new HorseInfo();
            hourseInfo.horseName = (String) jcname.getSelectedItem();
            hourseInfo.horseSymbol = (String) jcsymbol.getSelectedItem(); 
            hourseInfo.breed = (String) jcbreed.getSelectedItem(); 
            hourseInfo.color = (String) jccolour.getSelectedItem();
            hourseInfo.hasSaddle = Boolean.valueOf((String) jcsaddle.getSelectedItem());
            hourseInfo.hasHorseShoes = Boolean.valueOf((String) jchorseshoe.getSelectedItem());

            return hourseInfo;
        } 
        else 
        {
            return null;
        }
    }

    class RacePanel extends JPanel 
    {
        private Horse[] horses;
        private int laneHeight = 50;
        private int lanePixelLength = 800;
        private double trackLength;

        public RacePanel(Horse[] horses, double trackLength) 
        {
            this.horses = horses;
            setPreferredSize(new Dimension(lanePixelLength + 50, horses.length * laneHeight + 50));
            this.trackLength = trackLength;
        }

        @Override
        protected void paintComponent(Graphics g) 
        {
            super.paintComponent(g);

            for (int i = 0; i < horses.length; i++) 
            {
                int y = i * laneHeight;

                // Draw lane
                g.setColor(Color.LIGHT_GRAY);
                g.fillRect(0, y, lanePixelLength, laneHeight);

                // Draw lane separator
                g.setColor(Color.BLACK);
                g.drawLine(0, y, lanePixelLength, y);

                if(horses[i] != null)
                {
                    Horse horse = horses[i];

                    // Calculate horse position
                    double progress = horse.getDistanceTravelled() / trackLength;
                    int x = (int) (progress * lanePixelLength);

                    if(horses[i].getColor().equals("Black"))
                    {
                        g.setColor(Color.BLACK);
                    }
                    else if(horses[i].getColor().equals("White"))
                    {
                        g.setColor(Color.WHITE);
                    }
                    else if(horses[i].getColor().equals("Brown"))
                    {
                        g.setColor(new Color(139, 69, 19)); // brown color
                    }
                    else if(horses[i].getColor().equals("Baige"))
                    {
                        g.setColor(new Color(245, 222, 179)); // beige color
                    }
                    else if(horses[i].getColor().equals("Multicoloured"))
                    {
                        g.setColor(Color.MAGENTA); // multicolored horse
                    }

                    // Draw horse
                    if(horses[i].hasFallen())
                    {
                        g.setColor(Color.RED);
                        g.drawString("❌", x, y +20);
                    }
                    else
                        g.drawString(horse.getSymbol(), x, y +20);
                    //print the horses name, confidence and speed 
                    g.setColor(Color.BLACK);
                    int infoX = lanePixelLength + 10;
                    g.drawString(horse.getName(), infoX, y + 20);
                    g.drawString("Confidence: " + horse.getConfidence(), infoX, y + 30);
                    g.drawString("Speed: " + horse.getSpeed(), infoX, y + 40);

                    if(horses[i].isWinner())
                    {
                        g.setColor(Color.RED);
                        String message = "Winner! " + horses[i].getName() + " has won the race!";
                        g.drawString(message ,10, horses.length * laneHeight + 80);
                    }
                }
                //showt he weather and the track conditions:
                g.setColor(Color.BLACK);
                g.drawString("Weather: " + currentRace.getWeather(), 10, horses.length * laneHeight + 20);
                g.drawString("Track Conditions: " + currentRace.getTrackCondition(), 10, horses.length * laneHeight + 40);
            }
        }
    }

}
