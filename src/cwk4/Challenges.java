package cwk4;

public class Challenges {
    private int challengeNo;
    private ChallengeType challengeType;
    private String enemyName;
    private int skillRequired;
    private int rewardGain;

    /**
     *
     * @param challNo - challenge number
     * @param challType - type of challenge (magic/mystery/fight)
     * @param enemy - name of enemy
     * @param skill - numerical skill of enemy
     * @param reward - reward gain from enemy
     */
    public Challenges(int challNo, ChallengeType challType, String enemy, int skill, int reward){
        challengeNo = challNo;
        challengeType = challType;
        enemyName = enemy;
        skillRequired = skill;
        rewardGain = reward;
    }

    public ChallengeType getChallengeType() {
        return challengeType;
    }

    public int getSkillRequired() {
        return skillRequired;
    }

    public int getReward(){
        return rewardGain;
    }

    public int getChallengeNo(){
        return challengeNo;
    }


    public String toString(){
        return "Challenge Number: " + challengeNo +
                "\nChallenge Type: " + challengeType +
                "\nEnemy Name: " + enemyName +
                "\nSkill Required: " + skillRequired +
                "\nReward Gain: " + rewardGain + "\n\n";
    }
}
