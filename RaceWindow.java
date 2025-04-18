import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RaceWindow {
    JFrame frame;
    RacePanel panel;

    public RaceWindow() {
        frame = new JFrame("Horse Race Simulator");
        panel = new RacePanel();

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);
        frame.add(panel);

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
                    Race race = new Race(info.raceLength, info.numOfHorses);
                    Horse horse1 = new Horse('A', "Horse1", 0.5);
                    Horse horse2 = new Horse('B', "Horse2", 0.5);
                    race.addHorse(horse1, 0);
                    race.addHorse(horse2, 1);
                    race.startRace(); 
                }
            }
        });

        JMenuItem stats = new JMenuItem("Statistics");
        JMenuItem bets = new JMenuItem("Bets");
        JMenuItem exit = new JMenuItem("Exit");
        menu.add(start);
        menu.add(stats);
        menu.add(bets);
        menu.add(exit);
        menubar.add(menu);
        frame.setJMenuBar(menubar);
        frame.setVisible(true);

    }

    //class to allow return of multiple values from askUser method
    static class RaceInfo
    {
        int raceLength;
        int numOfHorses;
        String metricUnit;

        RaceInfo() {
            this.raceLength = 0;
            this.numOfHorses = 0;
            this.metricUnit = null;
        }
    };
    
    static RaceInfo askUser(JFrame parent) {
        
        String[] units = {"metes", "yards", "miles"};
        String[] lengths = new String[]{"10", "30", "50"};
        String[] lanes = new String[]{"1", "2", "3", "4", "5", "6", "7", "8", "9", "10"};

        JComboBox<String> jcunits = new JComboBox<>(units);
        JComboBox<String> jclength = new JComboBox<>(lengths);
        JComboBox<String> jclanes = new JComboBox<>(lanes);

        jcunits.setEditable(true);
        jclength.setEditable(true);
        jclanes.setEditable(true);

        // Create a panel to hold the combo boxes
        JPanel panel = new JPanel(new GridLayout(3, 2));
        panel.add(new JLabel("Select Metric Unit:"));
        panel.add(jcunits);
        panel.add(new JLabel("Select Race Length:"));
        panel.add(jclength);
        panel.add(new JLabel("Select Number of Lanes:"));
        panel.add(jclanes);

        System.out.println("Opening race settings dialog...");


        // Show the dialog and get the user's response
        int result = JOptionPane.showConfirmDialog(parent, panel, "Race Settings", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        // parent.setVisible(true);
        if (result == JOptionPane.OK_OPTION) {
            // Retrieve the selected values
            RaceInfo raceInfo = new RaceInfo();
            raceInfo.metricUnit = (String) jcunits.getSelectedItem();
            raceInfo.raceLength = Integer.parseInt((String) jclength.getSelectedItem()); 
            raceInfo.numOfHorses = Integer.parseInt((String) jclanes.getSelectedItem()); 

            return raceInfo;
        } 
        else 
        {
            return null;
        }
    }

    // Inner class for custom drawing
    class RacePanel extends JPanel 
    {
        protected void paintComponent(Graphics g, int numOfLanes) 
        {
            super.paintComponent(g);

            // Draw the race track
            g.setColor(Color.GRAY);
            g.fillRect(50, 100, 700, 300);

            // Draw lanes
            g.setColor(Color.WHITE);
            for (int i = 0; i <= numOfLanes; i++) {
                g.drawLine(50, 100 + i * 50, 750, 100 + i * 50);
            }

            // Draw a horse (example)
            g.setColor(Color.BLUE);
            g.fillOval(100, 125, 50, 25); // Example horse
        }


    }
}
