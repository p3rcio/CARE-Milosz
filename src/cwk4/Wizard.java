package cwk4;

public class Wizard implements Champion{
    private String champName;
    private int champSkill;
    private String spellSpeciality;
    private boolean isNecromancer;
    private int entryFee;
    private String champType;
    private ChampionState champState;

    public Wizard(String name, int skill, String spell, boolean necro){
        champName = name;
        champSkill = skill;
        spellSpeciality = spell;
        isNecromancer = necro;
        champType = "Wizard";
        champState = ChampionState.WAITING;
    }

    public String isNecromancer(){ // changes default output from boolean to Yes/No
        if(isNecromancer){
            return "Yes";
        } else {
            return "No";
        }
    }

    @Override
    public String getChampType() {
        return champType;
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
    public boolean canTalk() {
        return true;
    }

    @Override
    public int getEntryFee() {
        if (isNecromancer){ // if wizard is necromancer
            entryFee = 400;
        }
        else{
            entryFee = 300;
        }
        return entryFee;
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
                "\nSpell: " + spellSpeciality +
                "\nNecromancer: " + isNecromancer() +
                "\nEntry Fee: " + getEntryFee() + "\n";
        return sString;
    }
}
