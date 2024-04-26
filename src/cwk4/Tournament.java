package cwk4;
import java.util.*;
import java.io.*;
import java.io.IOException;
import java.util.ArrayList;
/**
 * This interface specifies the behaviour expected from CARE
 * as required for 5COM2007 Cwk 4
 *
 * @author
 * @version
 */
//
public class Tournament implements CARE {

    private Vizier vizier; // create new vizier object
    private BufferedReader reader;
    private ArrayList<Champion> allChampions = new ArrayList<Champion>();
    private ArrayList<Challenges> allChallenges = new ArrayList<Challenges>();
    private String viz;


//**************** CARE **************************

    /**
     * Constructor requires the name of the vizier
     *
     * @param viz the name of the vizier
     */
    public Tournament(String viz) {
        this.viz = viz;
        vizier = new Vizier(viz);
        setupChampions();
        setupChallenges();

    }

    /**
     * Constructor requires the name of the vizier and the
     * name of the file storing challenges
     *
     * @param viz      the name of the vizier
     * @param filename name of file storing challenges
     */
    public Tournament(String viz, String challengesAM, String Savegame, String fname)  //Task 3.5
    {
        setupChampions();
        readChallenges(challengesAM);
        saveGame(Savegame);
        loadGame(fname);
    }


    /**
     * Returns a String representation of the state of the game,
     * including the name of the vizier, state of the treasury,
     * whether defeated or not, and the champions currently in the
     * team,(or, "No champions" if team is empty)
     *
     * @return a String representation of the state of the game,
     * including the name of the vizier, state of the treasury,
     * whether defeated or not, and the champions currently in the
     * team,(or, "No champions" if team is empty)
     **/
    public String toString() {
        String s = "***************GAME STATE***************";
        s += "\nVizier: " + vizier.getVizierName() +
                "\nVizier Treasury: " + vizier.getGulden() +
                "\nDefeated: " + vizier.getDefeated() + "\n" +
                "\nVizier Team:" + vizier.getVizierTeam();

        return s;
    }


    /**
     * returns true if Treasury <=0 and the vizier's team has no
     * champions which can be retired.
     *
     * @returns true if Treasury <=0 and the vizier's team has no
     * champions which can be retired.
     */
    public boolean isDefeated() {
        return false;
    }

    /**
     * returns the amount of money in the Treasury
     *
     * @returns the amount of money in the Treasury
     */
    public int getMoney() {
        return vizier.getGulden();
    }


    /**
     * Returns a String representation of all champions in the reserves
     *
     * @return a String representation of all champions in the reserves
     **/
    public String getReserve() {
        String s = "************ Champions available in reserves********\n";
        for (Champion champion : allChampions) {
            s += champion + "\n";
        }
        return s;
    }


    /**
     * Returns details of the champion with the given name.
     * Champion names are unique.
     *
     * @return details of the champion with the given name
     **/
    public String getChampionDetails(String nme) {
        for (Champion champion : allChampions) { // if champion is in reserve
            if (Objects.equals(champion.getChampionName(), nme)) {
                return champion.toString() + "Champion State: " + champion.getChampionState();
            }
        }
        return vizier.getChampDetails(nme); // if champion is in vizier team
    }

    /**
     * returns whether champion is in reserve
     *
     * @param nme champion's name
     * @return true if champion in reserve, false otherwise
     */
    public boolean isInReserve(String nme) {
        for (Champion champion : allChampions) {
            if (champion.getChampionState() == ChampionState.WAITING) {
                return true;
            }
        }
        return false;
    }

    // ***************** Team champions ************************

    /**
     * Allows a champion to be entered for the vizier's team, if there
     * is enough money in the Treasury for the entry fee.The champion's
     * state is set to "active"
     * 0 if champion is entered in the vizier's team,
     * 1 if champion is not in reserve,
     * 2 if not enough money in the treasury,
     * -1 if there is no such champion
     *
     * @param nme represents the name of the champion
     * @return as shown above
     **/
    public int enterChampion(String nme) {
        // Find the champion with the given name in allChampions list
        Champion championToAdd = null;
        for (Champion champion : allChampions) {
            if (champion.getChampionName().equals(nme)) {
                championToAdd = champion;
                break;
            }
        }

        // If champion not found, return -1
        if (championToAdd == null) {
            return -1;
        }

        // Check if enough money in treasury
        if (getMoney() >= championToAdd.getEntryFee()) { // Assuming entryFee is defined somewhere
            // Add champion to vizierTeam
            vizier.addChampion(championToAdd); // Ensure vizier is an instance of the Vizier class
            vizier.deductGulden(championToAdd.getEntryFee());
            championToAdd.setChampionState(ChampionState.ENTERED); // set champ state to entered
            // Remove champion from allChampions
            allChampions.remove(championToAdd);
            return 0; // Champion entered successfully
        } else {
            return 2; // Not enough money in the treasury
        }
    }

    /**
     * Returns true if the champion with the name is in
     * the vizier's team, false otherwise.
     *
     * @param nme is the name of the champion
     * @return returns true if the champion with the name
     * is in the vizier's team, false otherwise.
     **/
    public boolean isInViziersTeam(String nme) {
        // if champion is in vizier's team
        if (vizier.isChampInTeam(nme)) {
            return true;
        }
        return false;
    }

    /**
     * Removes a champion from the team back to the reserves (if they are in the team)
     * Pre-condition: isChampion()
     * 0 - if champion is retired to reserves
     * 1 - if champion not retired because disqualified
     * 2 - if champion not retired because not in team
     * -1 - if no such champion
     *
     * @param nme is the name of the champion
     * @return as shown above
     **/
    public int retireChampion(String nme) {
        if (vizier.isChampInTeam(nme)) { // if champion is in vizier's team
            //find champion in vizier team
            Champion championToRetire = null;
            for (Champion champion : vizier.getVizierChampArray()) {
                if (champion.getChampionName().equals(nme)) {
                    championToRetire = champion;
                    break;
                }
            }
            // champion to retire was found AND the champion is not disqualified
            if (championToRetire != null && championToRetire.getChampionState() != ChampionState.DISQUALIFIED) {
                vizier.removeChampion(championToRetire); // retire champion from vizier team
                allChampions.add(championToRetire); // add champion back to reserves
                championToRetire.setChampionState(ChampionState.WAITING); // set champion state to waiting in reserve
                vizier.addGulden(championToRetire.getEntryFee() / 2); // add 1/2 price of retired champion to treasury
                return 0; // champion successfully retired
            } else if (championToRetire.getChampionState() == ChampionState.DISQUALIFIED) {
                return 1; // champion is disqualified
            }
            return 2; // champion not in team

        }
        return -1; // no such champion
    }


    /**
     * Returns a String representation of the champions in the vizier's team
     * or the message "No champions entered"
     *
     * @return a String representation of the champions in the vizier's team
     **/
    public String getTeam() {
        String s = "************ Vizier's Team of champions********\n";
        s += vizier.getVizierTeam();

        return s;
    }

    /**
     * Returns a String representation of the disquakified champions in the vizier's team
     * or the message "No disqualified champions "
     *
     * @return a String representation of the disqualified champions in the vizier's team
     **/
    public String getDisqualified() {
        String s = "************ Vizier's Disqualified champions********\n";
        s += vizier.getVizierDisqualifiedTeam();

        return s;
    }

//**********************Challenges*************************

    /**
     * returns true if the number represents a challenge
     *
     * @param num is the  number of the challenge
     * @return true if the  number represents a challenge
     **/
    public boolean isChallenge(int num) {
        for (Challenges challenge : allChallenges) {
            if (challenge.getChallengeNo() == num) {
                return true; // num in challenges
            }
        }
        return false; // number not found
    }

    /**
     * Provides a String representation of an challenge given by
     * the challenge number
     *
     * @param num the number of the challenge
     * @return returns a String representation of a challenge given by
     * the challenge number
     **/
    public String getChallenge(int num) {
        String chall = "";
        for (Challenges challenge : allChallenges) { // check whether challenge is in challenges
            if (isChallenge(num)) {
                chall += challenge.toString();
            }
        }
        return chall;
    }

    /**
     * Provides a String representation of all challenges
     *
     * @return returns a String representation of all challenges
     **/
    public String getAllChallenges() {
        String challenges = "\n************ All Challenges ************\n";
        for (Challenges challenge : allChallenges) {
            challenges += challenge + "\n";
        }
        return challenges;
    }


    /**
     * Retrieves the challenge represented by the challenge
     * number.Finds a champion from the team who can meet the
     * challenge. The results of meeting a challenge will be
     * one of the following:
     * 0 - challenge won by champion, add reward to the treasury,
     * 1 - challenge lost on skills  - deduct reward from
     * treasury and record champion as "disqualified"
     * 2 - challenge lost as no suitable champion is  available, deduct
     * the reward from treasury
     * 3 - If a challenge is lost and vizier completely defeated (no money and
     * no champions to withdraw)
     * -1 - no such challenge
     *
     * @param chalNo is the number of the challenge
     * @return an int showing the result(as above) of fighting the challenge
     */
    public int meetChallenge(int chalNo) {
        // Find the challenge with the given challenge number
        Challenges challenge = pickAChallenge(chalNo);

        // If no challenge found with the given number, return -1
        if (challenge == null) {
            return -1;
        }

        // Check if there is a champion in the vizier's team who meets the requirements of the challenge
        Champion championForChallenge = getChampionForChall(challenge);

        // If no suitable champion is available, deduct the reward from the treasury and return 2
        if (championForChallenge == null) {
            vizier.deductGulden(challenge.getReward());
            return 2;
        }

        // Champion wins if skill level is more or equal than challenge skill required
        if (championForChallenge.getChampSkill() >= challenge.getSkillRequired()) {
            // Challenge won by champion, add reward to the treasury
            vizier.addGulden(challenge.getReward());
            return 0;
        } else {
            // Challenge lost on skills - deduct reward from treasury and record champion as disqualified
            vizier.deductGulden(challenge.getReward());
            vizier.removeChampion(championForChallenge); // remove lost champion from vizier team
            vizier.addDisqualifiedChamp(championForChallenge); // move lost champ to disqualified team
            championForChallenge.setChampionState(ChampionState.DISQUALIFIED);
            // Check if the vizier is completely defeated
            if (vizier.getGulden() <= 0 && vizier.getVizierChampArray().isEmpty()) {
                vizier.setDefeated();
                return 3;
            }
            return 1;
        }
    }


    //****************** private methods for Task 3 functionality*******************
    //*******************************************************************************
    private void setupChampions() {
        allChampions.add(new Wizard("Ganfrank", 7, "Transmutation", true));
        allChampions.add(new Wizard("Rudolf", 6, "Invisibility", true));
        allChampions.add(new Warrior("Elblond", 1, "Sword", 150));
        allChampions.add(new Warrior("Flimsi", 2, "Bow", 200));
        allChampions.add(new Dragon("Drabina", 7, false));
        allChampions.add(new Dragon("Golum", 7, true));
        allChampions.add(new Warrior("Argon", 9, "Mace", 900));
        allChampions.add(new Wizard("Neon", 2, "Translocation", false));
        allChampions.add(new Dragon("Xenon", 7, true));
        allChampions.add(new Warrior("Atlanta", 5, "Bow", 500));
        allChampions.add(new Wizard("Krypton", 8, "Fireball", false));
        allChampions.add(new Wizard("Hedwig", 1, "Flight", true));

    }

    private void setupChallenges() {
        allChallenges.add(new Challenges(1, ChallengeType.MAGIC, "Brog", 3, 100));
        allChallenges.add(new Challenges(2, ChallengeType.FIGHT, "Huns", 3, 120));
        allChallenges.add(new Challenges(3, ChallengeType.MYSTERY, "Ferengi", 3, 150));
        allChallenges.add(new Challenges(4, ChallengeType.MAGIC, "Vandal", 9, 200));
        allChallenges.add(new Challenges(5, ChallengeType.MYSTERY, "Brog", 7, 90));
        allChallenges.add(new Challenges(6, ChallengeType.FIGHT, "Goth", 8, 45));
        allChallenges.add(new Challenges(7, ChallengeType.MAGIC, "Frank", 10, 200));
        allChallenges.add(new Challenges(8, ChallengeType.FIGHT, "Sith", 10, 170));
        allChallenges.add(new Challenges(9, ChallengeType.MYSTERY, "Cardashian", 9, 300));
        allChallenges.add(new Challenges(10, ChallengeType.FIGHT, "Jute", 2, 300));
        allChallenges.add(new Challenges(11, ChallengeType.MAGIC, "Celt", 2, 250));
        allChallenges.add(new Challenges(12, ChallengeType.MYSTERY, "Loki", 1, 250));

    }

    // Possible useful private methods

    /**
     * Get a challenge
     *
     * @param no challenge number
     * @return the chosen challenge object
     */
    private Challenges pickAChallenge(int no) {
        for (Challenges challenge : allChallenges) {
            if (challenge.getChallengeNo() == no) {
                return challenge;
            }
        }
        return null; // if challenge not found
    }

    /**
     * Select a champion for a challenge
     *
     * @param chall - name of name of challenge
     * @return - selected champion object
     */
    private Champion getChampionForChall(Challenges chall) {
        for (Champion champ : vizier.getVizierChampArray()) {
            String champType = champ.getChampType();

            // Wizard can take on magic, fight, and mystery challenges
            if (champType.equals("Wizard") && (chall.getChallengeType() == ChallengeType.FIGHT || chall.getChallengeType() == ChallengeType.MYSTERY || chall.getChallengeType() == ChallengeType.MAGIC)) {
                return champ;
            }
            // Warrior can only take on fight challenges
            else if (champType.equals("Warrior") && chall.getChallengeType() == ChallengeType.FIGHT) {
                return champ;
            }
            // Dragon can take on fight challenges if it can talk
            else if (champType.equals("Dragon") && chall.getChallengeType() == ChallengeType.FIGHT && champ.canTalk()) {
                return champ;
            }
            // Dragon can take on mystery challenges if it can talk
            else if (champType.equals("Dragon") && chall.getChallengeType() == ChallengeType.MYSTERY && champ.canTalk()) {
                return champ;
            }
            // Dragon can take on fight challenges regardless of talking ability
            else if (champType.equals("Dragon") && chall.getChallengeType() == ChallengeType.FIGHT) {
                return champ;
            }
        }
        return null; // If champion not found
    }


    //*******************************************************************************
    //*******************************************************************************

    /************************ Task 3.5 ************************************************/

    // ***************   file write/read  *********************

    /**
     * reads challenges from a comma-separated textfile and stores in the game
     *
     * @param filename of the comma-separated textfile storing information about challenges
     */
    public void readChallenges(String challengesAM) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(challengesAM));
            String line = reader.readLine();

            while (line != null) {
                StringTokenizer st = new StringTokenizer(line, ",");
                int stype = Integer.parseInt(st.nextToken().trim());
                String schallengeString = st.nextToken().trim(); // Read the string representation
                ChallengeType schallenge = ChallengeType.valueOf(schallengeString.toUpperCase());
                String sName = st.nextToken().trim();
                int sDifficulty = Integer.parseInt(st.nextToken().trim());
                int sReward = Integer.parseInt(st.nextToken().trim());

                // Create and store the challenge
                Challenges challenge = new Challenges(stype, schallenge, sName, sDifficulty, sReward);
                allChallenges.add(challenge);
                line = reader.readLine();
            }
            reader.close(); // Close the reader when done
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * reads all information about the game from the specified file
     * and returns a CARE reference to a Tournament object, or null
     *
     * @param fname name of file storing the game
     * @return the game (as a Tournament object)
     */
    public Tournament loadGame(String fname) {
        String vizierName = null; // Initialize vizierName
        try (BufferedReader reader = new BufferedReader(new FileReader(fname))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] tokens = line.split(",");
                if (tokens.length >= 2) {
                    String type = tokens[0].trim();
                    try {
                        if (type.equals("VIZIER")) { // Check if it's the VIZIER line
                            vizierName = tokens[1].trim(); // Set vizierName
                        } else if (type.equals("VIZIER_TEAM") || type.equals("RESERVE")) {
                            // Read champions from VIZIER_TEAM or RESERVE
                            String sChampionName = tokens[1].trim();
                            int sSkilllevel = Integer.parseInt(tokens[2].trim());

                            ChampionState championState;

                            // Check if the ChampionState exists in the enum
                            championState = ChampionState.valueOf(tokens[3].trim());


                            Champion champion = createChampion(sChampionName, sSkilllevel, championState);
                            if (champion != null) {
                                if (type.equals("VIZIER_TEAM")) {
                                    vizier.getVizierChampArray().add(champion);
                                } else {
                                    allChampions.add(champion);
                                }
                            }
                        } else if (type.equals("TREASURY")) {
                            // Handle treasury loading logic here
                        }
                    } catch (NumberFormatException e) {
                        e.printStackTrace();
                    } catch (IllegalArgumentException e) {
                        e.printStackTrace();
                    }
                }
            }

            // Construct and return the loaded Tournament object
            Tournament tournament = new Tournament(vizierName);
            return tournament;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }





    private Champion createChampion(String sChampionName, int sSkilllevel, ChampionState championState) {
        Champion champion = null;
        if (sChampionName.equals("Wizard")) {
            champion = new Wizard(sChampionName, sSkilllevel, "", true); // Adjust constructor parameters accordingly
        } else if (sChampionName.equals("Dragon")) {
            champion = new Dragon(sChampionName, sSkilllevel, true); // Adjust constructor parameters accordingly
        } else if (sChampionName.equals("Warrior")) {
            champion = new Warrior(sChampionName, sSkilllevel, "", 0); // Adjust constructor parameters accordingly
        }
        if (champion != null) {
            champion.setChampionState(championState);
        }
        return champion;
    }


    /**
     * Writes whole game to the specified file
     *
     * @param fname name of file storing requests
     */


    public void saveGame(String Savegame) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(Savegame))) {
            // Saving Vizier
            writer.printf("VIZIER,%s%n", vizier.getVizierName()); // Save Vizier's name


            // Saving vizierTeam
            writer.println("VIZIER_TEAM");
            for (Champion champion : vizier.getVizierChampArray()) {
                if (champion != null) {
                    writer.printf("%s,%d,%s%n", champion.getChampionName(), champion.getChampSkill(), champion.getChampionState().toString());
                }
            }

            // Saving champions in reserve
            writer.println("RESERVE");
            for (Champion champion : allChampions) {
                if (champion != null) {
                    writer.printf("%s,%d,%s%n", champion.getChampionName(), champion.getChampSkill(), champion.getChampionState().toString());
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}






