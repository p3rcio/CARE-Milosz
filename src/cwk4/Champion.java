package cwk4;

public interface Champion {

    public String getChampionName();
    public int getEntryFee();
    public int getChampSkill();
    public ChampionState getChampionState();
    public String toString();
    public ChampionState setChampionState(ChampionState state);
    public String getChampType();
    public boolean canTalk();

}
///