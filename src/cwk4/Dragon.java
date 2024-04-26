package cwk4;

public class Dragon implements Champion{
    private String champName;
    private int champSkill;
    private boolean canTalk;
    private int entryFee;
    private String champType;
    private ChampionState champState;

    /**
     * sets up the constructor for the dragon object
     * @param name
     * @param skill
     * @param talk
     */

    public Dragon(String name, int skill, boolean talk){
        champName = name;
        champSkill = skill;
        canTalk = talk;
        champType = "Dragon";
        entryFee = 500;
        champState = ChampionState.WAITING;
    }

    /**
     *
     * @return
     */
    @Override
    public String getChampType() {
        return champType;
    }

    /**
     *
     * @return
     */
    @Override
    public String getChampionName() {
        return champName;
    }

    @Override
    public int getChampSkill() {
        return champSkill;
    }

    @Override
    public ChampionState getChampionState() {
        return champState;
    }

    @Override
    public int getEntryFee() {
        return entryFee;
    }

    public String getCanTalk(){

        if(canTalk){
            return "Yes";
        }else{
            return "No";
        }
    }


    @Override
    public boolean canTalk() {
        return canTalk;
    }

    @Override
    public ChampionState setChampionState(ChampionState state) {
        champState = state;
        return champState;
    }


    @Override
    public String toString() {
        String sString = "";  // combine with Champion toString method
        sString += "\nName: " + champName +
                "\nSkill: " + champSkill +
                "\nType: " + champType +
                "\nCan Talk: " + getCanTalk() +
                "\nEntry Fee: " +entryFee + "\n";
        return sString;
    }
}
