package cwk4;

public class Warrior implements Champion{
    private String champName;
    private int champSkill;
    private String warriorWeapon;
    private int entryFee;
    private String champType;
    private ChampionState champState;

    public Warrior(String name, int skill, String weapon, int fee){
        champName = name;
        champSkill = skill;
        warriorWeapon = weapon;
        entryFee = fee;
        champType = "Warrior";
        champState = ChampionState.WAITING;
    }


    @Override
    public String getChampType() {
        return champType;
    }

    @Override
    public ChampionState setChampionState(ChampionState state) {
        champState = state;
        return champState;
    }

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

    @Override
    public boolean canTalk() {
        return true;
    }


    @Override
    public String toString() {
        String sString = "";  // combine with Champion toString method
        sString += "\nName: " + champName +
                "\nSkill: " + champSkill +
                "\nType: " + champType +
                "\nWeapon: " + warriorWeapon +
                "\nEntry Fee: " + entryFee + "\n";
        return sString;
    }
}
