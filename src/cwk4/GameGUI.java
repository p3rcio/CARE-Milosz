package cwk4;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/**
 * Provide a GUI interface for the game
 *
 * @author A.A.Marczyk
 * @version 20/01/24
 */
public class GameGUI
{
    private CARE gp = new Tournament("Fred");
    private JFrame myFrame = new JFrame("Game GUI");
    private JTextArea listing = new JTextArea();

    private JLabel codeLabel = new JLabel ();
    private JButton meetBtn = new JButton("Meet Challenge");
    private JButton viewBtn = new JButton("View State");
    private JButton clearBtn = new JButton("Clear");
    private JButton quitBtn = new JButton("Quit");
    private JPanel eastPanel = new JPanel();



    public static void main(String[] args)
    {
        new GameGUI();
    }

    public GameGUI()
    {

        makeFrame();
        makeMenuBar(myFrame);

    }


    /**
     * Create the Swing frame and its content.
     */
    private void makeFrame()
    {
        myFrame.setLayout(new BorderLayout());
        myFrame.add(listing,BorderLayout.CENTER);
        listing.setVisible(false);
        myFrame.add(eastPanel, BorderLayout.EAST);
        // set panel layout and add components
        eastPanel.setLayout(new GridLayout(4,2));
        eastPanel.add(meetBtn);
        eastPanel.add(clearBtn);
        eastPanel.add(quitBtn);
        eastPanel.add(viewBtn);



        clearBtn.addActionListener(new ClearBtnHandler());
        meetBtn.addActionListener(new MeetBtnHandler());
        quitBtn.addActionListener(new QuitBtnHandler());

        viewBtn.addActionListener(new viewBtnHandler());

        meetBtn.setVisible(true);
        clearBtn.setVisible(true);
        quitBtn.setVisible(true);


        viewBtn.setVisible(true);

        // building is done - arrange the components and show
        myFrame.pack();
        myFrame.setVisible(true);
    }

    /**
     * Create the main frame's menu bar.
     */
    private void makeMenuBar(JFrame frame)
    {
        JMenuBar menubar = new JMenuBar();
        frame.setJMenuBar(menubar);

        // create the File menu
        JMenu championMenu = new JMenu("Champions");
        menubar.add(championMenu);

        JMenuItem listChampionItem = new JMenuItem("List Champions in reserve");
        listChampionItem.addActionListener(new ListReserveHandler());
        championMenu.add(listChampionItem);

        JMenuItem  ListChampionInteam = new JMenuItem("Champions In The Current Team");
        ListChampionInteam.addActionListener(new ListChampionInteam());
        championMenu.add(ListChampionInteam);

        JMenuItem  ViewAChampion = new JMenuItem("View a Champion");
        ViewAChampion.addActionListener(new ViewAChampion());
        championMenu.add(ViewAChampion);

        JMenuItem  EnterAChampion = new JMenuItem("Enter in a Champion");
        EnterAChampion.addActionListener(new EnterAChampion());
        championMenu.add(EnterAChampion);

        JMenu ChallengesMenu = new JMenu("Challenges");
        menubar.add(ChallengesMenu);

        JMenuItem DisplayAllChallenges = new JMenuItem("All Challenges"); // this is where the display all challenges drop down is added to the challenges menubar
        DisplayAllChallenges.addActionListener(new DisplayAllChallenges());    // calls on the displays all challenges function
        ChallengesMenu.add(DisplayAllChallenges); //


    }

    private class ListReserveHandler implements ActionListener
    {
        public void actionPerformed(ActionEvent e)
        {
            listing.setVisible(true);    //making sure the text is visible
            String xx = gp.getReserve(); //get the current reserves for all of the teams
            listing.setText(xx);         // setting the text of the reserve champions (displays the reserve champions)
        }
    }


    //DISPLAY ALL CHAMPIONS IN RESERVE
    private class ListChampionInReserve implements ActionListener // function to list all of the champions in reserves
    {
        public void actionPerformed(ActionEvent e) {
            listing.setVisible(true);               //making sure the text is visible
            String Champions = gp.getTeam();        //get the current team and shows what champions are in the team
            listing.setText(Champions);             // setting the text of the current team (displays the champions names)

        }
    }


    private class meetchallenge implements ActionListener
    {
        public void actionPerformed(ActionEvent e) {
            String ChallengeNumber = JOptionPane.showInputDialog(myFrame,"Please Enter in a Challenge Number", JOptionPane.INFORMATION_MESSAGE);
            try {
                int number = Integer.parseInt(ChallengeNumber);
                int ChampionDetails = gp.meetChallenge(number);
                JOptionPane.showMessageDialog(myFrame,ChampionDetails);

            } catch (NumberFormatException ex) {
                // Handle the case where the input is not a valid integer
                JOptionPane.showMessageDialog(null, "Invalid input. Please enter a valid integer.");
            }

        }
    }
    ///


    //DISPLAY ALL CHALLENGES
    private class DisplayAllChallenges implements ActionListener // function to display all of the challenges
    {

        public void actionPerformed(ActionEvent e) {
            listing.setVisible(true);
            String Allchallenges = gp.getAllChallenges();
            listing.setText(Allchallenges);
        }
    }


    /// VIEW A SPECIFIC CHAMPION
    private class ViewAChampion implements ActionListener
    {
        public void actionPerformed(ActionEvent e) {
            String ChampionName = JOptionPane.showInputDialog(myFrame,"Please Enter in a Champion Name", JOptionPane.INFORMATION_MESSAGE);
            String ChampionDetails = gp.getChampionDetails(ChampionName);
            JOptionPane.showMessageDialog(myFrame,ChampionDetails);

        }
    }


    //DISPLAY ALL CHAMPIONS IN THE CURRENT TEAM
    private class ListChampionInteam implements ActionListener
    {
        public void actionPerformed(ActionEvent e) {
            listing.setVisible(true);
            String ChampionDetails = gp.getTeam();
            listing.setText(ChampionDetails);
        }
    }


    // ADD A CHAMPION TO A TEAM // OUR METHOD
    private class EnterAChampion implements ActionListener
    {
        public void actionPerformed(ActionEvent e) {
            String ChampionName = JOptionPane.showInputDialog(myFrame,"Please Enter in a Champion Name to add to the team", JOptionPane.INFORMATION_MESSAGE);
            int Enterthechampion = gp.enterChampion(ChampionName);
            String ChampionDetails = gp.getChampionDetails(ChampionName);
            JOptionPane.showMessageDialog(myFrame, "You have Entered in the Champion:" + ChampionDetails);



        }
    }


    // viewstate of the game
    private class viewBtnHandler implements ActionListener {

        public void actionPerformed(ActionEvent e) {

            String currentTeam = gp.getTeam();
            String currentChallenge = gp.getChallenge(0);
            String currentReserve = gp.getReserve();
            int currentMoney = gp.getMoney();


            listing.setVisible(true);

            listing.setText("");

            String Gamestate = ("Game State:\n");
            String currentteam = ("Champions in Current Team: " + "\n"  + currentTeam + "\n");
            String currentchallenge = ("Current Challenge: " + "\n" + currentChallenge + "\n");
            String currentmoney = ("Current Money: " + "\n" + currentMoney + "\n");
            String currentreserve = ("Current Reserves: " + "\n" + currentReserve + "\n");

            listing.setText(Gamestate + "\n" + currentteam + "\n" + currentchallenge + "\n" + currentmoney + "\n" + currentreserve);



        }
    }

    private class ClearBtnHandler implements ActionListener
    {
        public void actionPerformed(ActionEvent e)
        {
            listing.setText(" ");
        }
    }

    private class MeetBtnHandler implements ActionListener
    {
        public void actionPerformed(ActionEvent e)
        {
            int result = -1;
            String answer = "no such challenge";
            String inputValue = JOptionPane.showInputDialog("Challenge number ?: ");
            int num = Integer.parseInt(inputValue);
            result = gp.meetChallenge(num);
            switch (result)
            {
                case 0:answer = "challenge won by champion"; break;
                case 1:answer = "challenge lost on skills, champion disqualified";break;
                case 2:answer = "challenge lost as no suitable champion is available";break;
                case 3:answer = "challenge lost and vizier completely defeated";break;
            }

            JOptionPane.showMessageDialog(myFrame,answer);
        }
    }

    private class QuitBtnHandler implements ActionListener
    {
        public void actionPerformed(ActionEvent e)
        {
            int answer = JOptionPane.showConfirmDialog(myFrame,
                    "Are you sure you want to quit?","Finish",
                    JOptionPane.YES_NO_OPTION);
            // closes the application
            if (answer == JOptionPane.YES_OPTION)
            {
                System.exit(0); //closes the application
            }
        }
    }

}